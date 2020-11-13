package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidClientDetailsException;
import mk.finki.ukim.mk.lab.model.exceptions.OrderCannotBePlacedException;
import mk.finki.ukim.mk.lab.repository.InMemoryOrderRepository;
import mk.finki.ukim.mk.lab.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final InMemoryOrderRepository orderRepository;

    public OrderServiceImpl(InMemoryOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order placeOrder(Balloon balloon, String balloonColor, String balloonSize, String clientName, String clientAddress) {
        if (balloonColor == null || balloonColor.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (clientName == null || clientName.isEmpty() || clientAddress == null || clientAddress.isEmpty()) {
            throw new InvalidClientDetailsException();
        }

        return orderRepository.save(balloon, balloonColor, balloonSize, clientName, clientAddress).orElseThrow(OrderCannotBePlacedException::new);
    }

    @Override
    public List<Order> findByUserName(String username) {
        if (username != null && !username.isEmpty()) {
            return orderRepository.findByUserName(username);
        }

        throw new IllegalArgumentException();
    }
}
