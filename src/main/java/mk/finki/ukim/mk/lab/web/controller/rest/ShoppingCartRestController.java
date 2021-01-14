package mk.finki.ukim.mk.lab.web.controller.rest;

import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartRestController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartRestController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/items")
    Integer getNumberOfBalloonsInCurrentUserShoppingCart(Principal principal) {
        String username = principal.getName();
        return shoppingCartService.getAllBalloonsInUserActiveShoppingCard(username).size();
    }
}
