package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.User;

import java.util.List;

public interface OrderService {

    Order placeOrder(User user, String deliveryAddress, List<Balloon> balloons);

    List<Order> getPlacedOrdersForUser(User user);
}
