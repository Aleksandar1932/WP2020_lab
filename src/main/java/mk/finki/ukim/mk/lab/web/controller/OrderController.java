package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.ShoppingCart;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.BalloonService;
import mk.finki.ukim.mk.lab.service.OrderService;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;

    public OrderController(OrderService orderService, ShoppingCartService shoppingCartService) {
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/place")
    public String placeOrder(Model model, HttpSession session, HttpServletRequest req) {
        String username = req.getRemoteUser();
        List<Balloon> balloons = shoppingCartService.getAllBalloonsInUserActiveShoppingCard(username);

        if (balloons.isEmpty() || balloons == null) {
            model.addAttribute("hasError", true);
            return "redirect:/balloons?error=NoBalloonsToOrder";
        }

        model.addAttribute("balloons", balloons);

        model.addAttribute("bodyContent", "order-details");
        return "master-template";
    }

    @PostMapping("/place")
    public String makeOrder(@RequestParam String deliveryAddress, HttpSession session, Model model, HttpServletRequest req) {
        String username = req.getRemoteUser();
        List<Balloon> balloons = shoppingCartService.getAllBalloonsInUserActiveShoppingCard(username);


        if (balloons.isEmpty() || balloons == null) {
            model.addAttribute("hasError", true);
            return "redirect:/balloons?error=NoBalloonsToOrder";
        }

        orderService.placeOrder(username, deliveryAddress, balloons);

        return "redirect:/balloons";
    }

    @GetMapping("/placed")
    public String getPlacedOrdersForCurrentUser(HttpSession session, Model model, HttpServletRequest req) {
        String username = req.getRemoteUser();

        List<Order> placedOrders = orderService.getPlacedOrdersForUser(username);

        if (placedOrders.isEmpty()) {
            model.addAttribute("hasError", true);
            return "redirect:/balloons?error=No placed orders";
        }

        model.addAttribute("orders", placedOrders);


        model.addAttribute("bodyContent", "orders-placed");
        return "master-template";

    }
}
