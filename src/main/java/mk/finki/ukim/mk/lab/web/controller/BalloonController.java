package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.model.enumerations.BalloonType;
import mk.finki.ukim.mk.lab.service.BalloonService;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/balloons")
public class BalloonController {
    private final BalloonService balloonService;
    private final ManufacturerService manufacturerService;

    public BalloonController(BalloonService balloonService, ManufacturerService manufacturerService) {
        this.balloonService = balloonService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String getBalloonsPage(@RequestParam(required = false) String error, @RequestParam(required = false) String typeToSort, Model model, HttpServletRequest request) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Balloon> balloonList;

        if (typeToSort != null && !typeToSort.isEmpty()) {
            BalloonType type = BalloonType.valueOf(typeToSort);

            balloonList = this.balloonService.listAllSortedByType(type);

        } else {
            balloonList = this.balloonService.listAll();
        }

        // Identify the user on the first entry
        request.getSession().setAttribute("user", (long) (Math.random() * 1000));


        model.addAttribute("balloons", balloonList);
        model.addAttribute("allTypes", BalloonType.values());

        return "listBalloons";
    }


    @PostMapping("/add")
    public String saveBalloon(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Long manufacturerId,
            @RequestParam String type
    ) {

//        BalloonType balloonType = new BalloonType.valueOf(type);
        BalloonType balloonType = BalloonType.valueOf(type);
        this.balloonService.save(name, description, manufacturerId, balloonType);

        return "redirect:/balloons";
    }


    @GetMapping("/add-form")
    public String addBalloonPage(Model model) {
        List<Manufacturer> manufacturers = this.manufacturerService.findAll();

        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("types", BalloonType.values());

        return "add-balloon";
    }

    @GetMapping("/edit-form/{id}")
    public String addBalloonPage(@PathVariable Long id, Model model) {

        if (this.balloonService.findById(id).isPresent()) {
            Balloon balloon = this.balloonService.findById(id).get();

            List<Manufacturer> manufacturers = this.manufacturerService.findAll();

            model.addAttribute("manufacturers", manufacturers);
            model.addAttribute("types", BalloonType.values());
            model.addAttribute("balloon", balloon);

            return "add-balloon";
        }
        return "redirect:/balloons?error=BalloonNotFound";
    }

    // TODO: WHY DELETE DOESN"T WORK
    @GetMapping("/delete/{id}")
    public String deleteBalloon(@PathVariable Long id) {
        this.balloonService.deleteById(id);

        return "redirect:/balloons";
    }
}
