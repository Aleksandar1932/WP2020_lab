package mk.finki.ukim.mk.lab.web.controller.rest;

import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartRestController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartRestController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/items")
    Integer getNumberOfBalloonsInCurrentUserShoppingCart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return shoppingCartService.getAllBalloonsInUserActiveShoppingCard(user.getUsername()).size();
    }
}
