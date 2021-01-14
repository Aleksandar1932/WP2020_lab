package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(String username, String deliveryAddress, List<Balloon> balloons);

    List<Order> getPlacedOrdersForUser(String username);

    Integer countAll(String username);
}
