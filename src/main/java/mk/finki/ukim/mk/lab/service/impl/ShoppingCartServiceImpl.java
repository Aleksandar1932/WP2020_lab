package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.ShoppingCart;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.enumerations.ShoppingCartStatus;
import mk.finki.ukim.mk.lab.model.exceptions.BalloonAlreadyInShoppingCartException;
import mk.finki.ukim.mk.lab.model.exceptions.BalloonNotFoundException;
import mk.finki.ukim.mk.lab.model.exceptions.ShoppingCartNotFoundException;
import mk.finki.ukim.mk.lab.model.exceptions.UserNotFoundException;
import mk.finki.ukim.mk.lab.repository.jpa.ShoppingCartRepository;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepository;
import mk.finki.ukim.mk.lab.service.BalloonService;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final BalloonService balloonService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   UserRepository userRepository,
                                   BalloonService balloonService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.balloonService = balloonService;
    }

    @Override
    public List<Balloon> listAllBalloonsInShoppingCart(Long cartId) {
        return this.shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new ShoppingCartNotFoundException(cartId))
                .getBalloons();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return this.shoppingCartRepository.findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(cart);
                });
    }

    @Override
    public ShoppingCart addBalloonToShoppingCart(String username, Long balloonId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);

        Balloon balloon = this.balloonService.findById(balloonId)
                .orElseThrow(() -> new BalloonNotFoundException(balloonId));

        if (shoppingCart.getBalloons()
                .stream().anyMatch(i -> i.getId().equals(balloonId)))
            throw new BalloonAlreadyInShoppingCartException(balloonId, username);

        shoppingCart.getBalloons().add(balloon);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void changeStatusToFinishedOfActiveShoppingCart(String username) {
        ShoppingCart activeShoppingCart = this.getActiveShoppingCart(username);

        activeShoppingCart.setStatus(ShoppingCartStatus.FINISHED);
        shoppingCartRepository.save(activeShoppingCart);
    }

    @Override
    public List<Balloon> getAllBalloonsInUserActiveShoppingCard(String username) {
        ShoppingCart activeShoppingCart = this.getActiveShoppingCart(username);
        return activeShoppingCart.getBalloons();
    }

    @Override
    public ShoppingCart removeBalloonFromShoppingCart(String username, Long balloonId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);

        Balloon balloon = this.balloonService.findById(balloonId)
                .orElseThrow(() -> new BalloonNotFoundException(balloonId));

        shoppingCart.getBalloons().remove(balloon);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}