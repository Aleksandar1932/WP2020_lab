package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    List<Balloon> listAllBalloonsInShoppingCart(Long cartId);

    List<Balloon> getAllBalloonsInUserActiveShoppingCard(String username);

    ShoppingCart getActiveShoppingCart(String username);

    void changeStatusToFinishedOfActiveShoppingCart(String username);

    ShoppingCart addBalloonToShoppingCart(String username, Long balloonId);

    ShoppingCart removeBalloonFromShoppingCart(String username, Long balloonId);

}
