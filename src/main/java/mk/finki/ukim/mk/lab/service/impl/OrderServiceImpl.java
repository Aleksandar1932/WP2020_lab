package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.exceptions.UserNotFoundException;
import mk.finki.ukim.mk.lab.repository.jpa.OrderRepository;
import mk.finki.ukim.mk.lab.service.OrderService;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    ShoppingCartService shoppingCartService;

    public OrderServiceImpl(OrderRepository orderRepository, ShoppingCartService shoppingCartService) {
        this.orderRepository = orderRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    @Transactional
    public Order placeOrder(User user, String deliveryAddress, List<Balloon> balloons) {
        Order order = new Order(user, deliveryAddress);
        order.getBalloons().addAll(balloons);

        orderRepository.save(order);
        shoppingCartService.deleteActiveShoppingCart(user.getUsername());

        return order;
    }

    @Override
    public List<Order> getPlacedOrdersForUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }

        return orderRepository.findAllByUser(user);
    }
}
