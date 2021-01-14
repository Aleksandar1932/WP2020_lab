package mk.finki.ukim.mk.lab.web.controller.rest;

import mk.finki.ukim.mk.lab.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/order")
public class OrderRestController {
    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/total")
    Integer getNumberOfOrdersForCurrentUser(Principal principal) {
        String username = principal.getName();
        return orderService.countAll(username);
    }
}
