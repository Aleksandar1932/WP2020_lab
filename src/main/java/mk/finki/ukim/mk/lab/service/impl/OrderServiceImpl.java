package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidDeliveryAddressException;
import mk.finki.ukim.mk.lab.repository.jpa.OrderRepository;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepository;
import mk.finki.ukim.mk.lab.service.OrderService;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    ShoppingCartService shoppingCartService;
    UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ShoppingCartService shoppingCartService, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.shoppingCartService = shoppingCartService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Order placeOrder(String username, String deliveryAddress, List<Balloon> balloons) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        if (deliveryAddress.isEmpty()) {
            throw new InvalidDeliveryAddressException();
        }

        Order order = new Order(user, deliveryAddress);
        order.getBalloons().addAll(balloons);

        orderRepository.save(order);
        shoppingCartService.changeStatusToFinishedOfActiveShoppingCart(user.getUsername());
        return order;
    }

    @Override
    public List<Order> getPlacedOrdersForUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return orderRepository.findAllByUser(user);
    }
}
