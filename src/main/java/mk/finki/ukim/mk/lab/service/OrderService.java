package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Balloon balloon, String balloonColor, String balloonSize, String clientName, String clientAddress);

    List<Order> findByUserName(String username);
}
