package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.service.BalloonService;
import mk.finki.ukim.mk.lab.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final BalloonService balloonService;

    public OrderController(OrderService orderService, BalloonService balloonService) {
        this.orderService = orderService;
        this.balloonService = balloonService;
    }

    @GetMapping()
    public String getOrdersByCurrentClient(HttpSession session, Model model) {
        String clientName = (String) session.getAttribute("clientName");


        if (clientName != null && !clientName.isEmpty()) {
            List<Order> orderList = orderService.findByUserName(clientName);

            if (orderList.isEmpty()) {
                return "redirect:/balloons?error:NoOrders";
            } else {
                model.addAttribute("orders", orderList);

                return "orders";
            }


        }
        return "redirect:/balloons?error:NoOrders";
    }


    @GetMapping("/place/{balloonId}")
    public String chooseBalloonDetails(@PathVariable Long balloonId, Model model) {
        if (this.balloonService.findById(balloonId).isPresent()) {
//            Balloon balloon = this.balloonService.findById(balloonId).get();
            model.addAttribute("balloonId", balloonId);

            return "choose-balloon-details";
        }

        model.addAttribute("hasError", true);
        return "redirect:/balloons?error=BalloonNotFound";
    }

    @PostMapping("/place/{balloonId}")
    public String makeOrder(@PathVariable Long balloonId,
                            @RequestParam String size,
                            @RequestParam String color,
                            @RequestParam String clientName,
                            @RequestParam String clientDeliveryAddress, HttpSession session) {

        if (this.balloonService.findById(balloonId).isPresent()) {
            Balloon balloon = this.balloonService.findById(balloonId).get();
            Order order = new Order(balloon, color, size, clientName, clientDeliveryAddress);

            orderService.placeOrder(balloon, color, size, clientName, clientDeliveryAddress);
            session.setAttribute("clientName", clientName);

            return "redirect:/balloons";

        }

        return "redirect:/balloons?error=BalloonNotAvailable";

    }

}
