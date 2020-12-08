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

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final BalloonService balloonService;
    private final ShoppingCartService shoppingCartService;

    public OrderController(OrderService orderService, BalloonService balloonService, ShoppingCartService shoppingCartService) {
        this.orderService = orderService;
        this.balloonService = balloonService;
        this.shoppingCartService = shoppingCartService;
    }

//    @GetMapping()
//    public String getOrdersByCurrentClient(HttpSession session, Model model) {
//        String clientName = (String) session.getAttribute("clientName");
//
//
//        if (clientName != null && !clientName.isEmpty()) {
//            List<Order> orderList = orderService.findByUserName(clientName);
//
//            if (orderList.isEmpty()) {
//                return "redirect:/balloons?error:NoOrders";
//            } else {
//                model.addAttribute("orders", orderList);
//
//                return "orders";
//            }
//
//
//        }
//        return "redirect:/balloons?error=You don't have any orders yet!";
//    }

    @GetMapping("/place")
    public String placeOrder(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        List<Balloon> balloons = shoppingCartService.getAllBalloonsInUserActiveShoppingCard(currentUser.getUsername());

        if (balloons.isEmpty() || balloons == null) {
            model.addAttribute("hasError", true);
            return "redirect:/balloons?error=NoBalloonsToOrder";
        }

        model.addAttribute("balloons", balloons);
        return "order-details";
    }

    @PostMapping("/place")
    public String makeOrder(@RequestParam String deliveryAddress, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        List<Balloon> balloons = shoppingCartService.getAllBalloonsInUserActiveShoppingCard(currentUser.getUsername());


        if (balloons.isEmpty() || balloons == null) {
            model.addAttribute("hasError", true);
            return "redirect:/balloons?error=NoBalloonsToOrder";
        }

        orderService.placeOrder(currentUser, deliveryAddress, balloons);

        return "redirect:/balloons";
    }

    @GetMapping("/placed")
    public String getPlacedOrdersForCurrentUser(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");

        List<Order> placedOrders = orderService.getPlacedOrdersForUser(currentUser);

        if (placedOrders.isEmpty()) {
            model.addAttribute("hasError", true);
            return "redirect:/balloons?error=No placed orders";
        }

        model.addAttribute("orders", placedOrders);

        return "orders-placed";
    }
}
