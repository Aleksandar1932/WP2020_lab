package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String getManufacturersPage(Model model) {
        List<Manufacturer> manufacturers = manufacturerService.findAll();
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("bodyContent", "list-manufacturers");
        return "master-template";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteManufacturer(@PathVariable Long id) {
        this.manufacturerService.deleteById(id);
        return "redirect:/balloons";
    }

    @GetMapping("/edit-form/{id}")
    public String editManufacturerPage(@PathVariable Long id, Model model) {
        if (this.manufacturerService.findById(id).isPresent()) {
            Manufacturer manufacturer = this.manufacturerService.findById(id).get();
            model.addAttribute("manufacturer", manufacturer);

            model.addAttribute("bodyContent", "add-manufacturer");
            return "master-template";
        }
        return "redirect:/balloons?error=ManufacturerNotFound";
    }

    @GetMapping("/add-form")
    public String addManufacturerPage(Model model) {
        model.addAttribute("bodyContent", "add-manufacturer");
        return "master-template";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public String saveManufacturer(
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String country,
            @RequestParam(required = false) Long manufacturerToUpdateId
    ) {
        this.manufacturerService.save(name, address, country, manufacturerToUpdateId);

        return "redirect:/balloons";
    }
}
