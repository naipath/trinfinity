package nl.ordina;

import lombok.Getter;

import javax.websocket.Session;
import java.awt.*;
import java.security.SecureRandom;

@Getter
public class User {

    public static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private Session session;
    private String hexColor;

    public User(Session session) {
        this.session = session;
        this.hexColor = generateHexColor();
    }

    private static String generateHexColor() {
        return "#" + Integer.toHexString(new Color(SECURE_RANDOM.nextInt()).getRGB()).substring(2);
    }

    public void sendCoordinate(Coordinate coordinate) {
        session.getAsyncRemote().sendText(coordinate.generateJson());
    }
}
