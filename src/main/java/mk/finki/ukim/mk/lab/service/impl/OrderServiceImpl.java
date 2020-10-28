package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidClientDetailsException;
import mk.finki.ukim.mk.lab.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public Order placeOrder(String balloonColor, String clientName, String address) {
        if (balloonColor == null || balloonColor.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (clientName == null || clientName.isEmpty() || address == null || address.isEmpty()) {
            throw new InvalidClientDetailsException();
        }

        return new Order(balloonColor, clientName, address);
    }
}
