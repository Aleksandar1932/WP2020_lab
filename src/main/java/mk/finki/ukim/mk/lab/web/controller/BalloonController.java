package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.model.enumerations.BalloonType;
import mk.finki.ukim.mk.lab.service.BalloonService;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = {"/balloons", "/"})
public class BalloonController {
    private final BalloonService balloonService;
    private final ManufacturerService manufacturerService;

    public BalloonController(BalloonService balloonService, ManufacturerService manufacturerService) {
        this.balloonService = balloonService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String getBalloonsPage(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String filterBy,
            Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Balloon> balloonList;

        if (filterBy != null && !filterBy.isEmpty()) {
            BalloonType type = BalloonType.valueOf(filterBy);

            balloonList = this.balloonService.filterByType(type);

        } else {
            balloonList = this.balloonService.listAll();
        }

        model.addAttribute("balloons", balloonList);
        model.addAttribute("allTypes", BalloonType.values());
        model.addAttribute("bodyContent", "list-balloons");

        return "master-template";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public String saveBalloon(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Long manufacturerId,
            @RequestParam String type,
            @RequestParam(required = false) Long balloonToUpdateId
    ) {

        BalloonType balloonType = BalloonType.valueOf(type);

        this.balloonService.save(name, description, manufacturerId, balloonType, balloonToUpdateId);

        return "redirect:/balloons";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/add-form")
    public String addBalloonPage(Model model) {
        List<Manufacturer> manufacturers = this.manufacturerService.findAll();

        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("types", BalloonType.values());

        model.addAttribute("bodyContent", "add-balloon");
        return "master-template";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/edit-form/{id}")
    public String addBalloonPage(@PathVariable Long id, Model model) {

        if (this.balloonService.findById(id).isPresent()) {
            Balloon balloon = this.balloonService.findById(id).get();

            List<Manufacturer> manufacturers = this.manufacturerService.findAll();

            model.addAttribute("manufacturers", manufacturers);
            model.addAttribute("types", BalloonType.values());
            model.addAttribute("balloon", balloon);

            model.addAttribute("bodyContent", "add-balloon");
            return "master-template";
        }
        return "redirect:/balloons?error=BalloonNotFound";
    }

    // TODO: WHY DELETE DOESN"T WORK
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteBalloon(@PathVariable Long id) {
        this.balloonService.deleteById(id);

        return "redirect:/balloons";
    }

    @PostMapping("/find")
    public String findAllByText(@RequestParam String text, Model model) {
        if (text.isEmpty()) {
            return "redirect:/balloons";
        }
        List<Balloon> balloons = this.balloonService.findAllByText(text);

        model.addAttribute("balloons", balloons);
        model.addAttribute("allTypes", BalloonType.values());

        model.addAttribute("bodyContent", "list-balloons");
        return "master-template";
    }
}
