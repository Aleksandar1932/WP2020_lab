package mk.finki.ukim.mk.lab.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class BalloonAlreadyInShoppingCartException extends RuntimeException {
    public BalloonAlreadyInShoppingCartException(Long id, String username) {
        super(String.format("Balloon with id: %d is already in the cart for %s", id, username));
    }
}