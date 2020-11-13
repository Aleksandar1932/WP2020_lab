package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.service.BalloonService;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public String getBalloonsPage(@RequestParam(required = false) String error, Model model, HttpServletRequest request) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        // Identify the user on the first entry
        request.getSession().setAttribute("user", (long) (Math.random() * 1000));

        List<Balloon> balloonList = this.balloonService.listAll();

        model.addAttribute("balloons", balloonList);

        return "listBalloons";
    }

    @PostMapping("/add")
    public String saveBalloon(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Long manufacturerId
    ) {
        this.balloonService.save(name, description, manufacturerId);

        return "redirect:/balloons";
    }


    @GetMapping("/add-form")
    public String addBalloonPage(Model model) {
        List<Manufacturer> manufacturers = this.manufacturerService.findAll();

        model.addAttribute("manufacturers", manufacturers);

        return "add-balloon";
    }

    @GetMapping("/edit-form/{id}")
    public String addBalloonPage(@PathVariable Long id, Model model) {

        if (this.balloonService.findById(id).isPresent()) {
            Balloon balloon = this.balloonService.findById(id).get();

            List<Manufacturer> manufacturers = this.manufacturerService.findAll();

            model.addAttribute("manufacturers", manufacturers);
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
