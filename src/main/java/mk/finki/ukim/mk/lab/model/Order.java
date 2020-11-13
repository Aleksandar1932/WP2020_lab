package mk.finki.ukim.mk.lab.model;

import lombok.Data;

@Data
public class Order {
    Balloon balloon;
    String balloonColor;

    String balloonSize;

    String clientName;

    String clientAddress;

    Long orderId;

    public Order(Balloon balloon, String balloonColor, String balloonSize, String clientName, String clientAddress) {
        this.orderId = (long) (Math.random() * 1000);
        this.balloon = balloon;
        this.balloonColor = balloonColor;
        this.balloonSize = balloonSize;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
    }

}
