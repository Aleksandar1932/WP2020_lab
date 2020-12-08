//package mk.finki.ukim.mk.lab.repository.impl;
//
//import mk.finki.ukim.mk.lab.booststrap.DataHolder;
//import mk.finki.ukim.mk.lab.model.Balloon;
//import mk.finki.ukim.mk.lab.model.Order;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Repository
//public class InMemoryOrderRepository {
//    private Object Order;
//
//    public Optional<Order> save(Balloon balloon, String balloonColor, String balloonSize, String clientName, String clientAddress) {
//        Order order = new Order(balloon, balloonColor, balloonSize, clientName, clientAddress);
//
//        DataHolder.ordersList.removeIf(order1 -> order1.getOrderId().equals(order.getOrderId()));
//        DataHolder.ordersList.add(order);
//
//        return Optional.of(order);
//    }
//
//    public List<Order> findByUserName(String username) {
//        return DataHolder.ordersList.stream().filter(order -> order.getClientName().equals(username)).collect(Collectors.toList());
//    }
//}
