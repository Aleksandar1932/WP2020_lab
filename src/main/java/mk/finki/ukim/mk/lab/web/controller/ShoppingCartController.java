package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.ShoppingCart;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error,
                                      HttpSession session,
                                      Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        User user = (User) session.getAttribute("user");
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(user.getUsername());
        model.addAttribute("balloons", this.shoppingCartService.listAllBalloonsInShoppingCart(shoppingCart.getId()));
        return "shopping-cart";
    }


    @PostMapping("/add-balloon/{id}")
    public String addBalloonToShoppingCart(@PathVariable Long id, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            ShoppingCart shoppingCart = this.shoppingCartService.addBalloonToShoppingCart(user.getUsername(), id);

            return "redirect:/balloons";
        } catch (RuntimeException exception) {
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }

    @PostMapping("/remove-balloon/{id}")
    public String removeBalloonFromShoppingCart(@PathVariable Long id, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            ShoppingCart shoppingCart = this.shoppingCartService.removeBalloonFromShoppingCart(user.getUsername(), id);

            return "redirect:/shopping-cart";
        } catch (RuntimeException exception) {
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }
}
