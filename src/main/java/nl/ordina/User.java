package nl.ordina;

import javax.websocket.Session;
import java.awt.*;
import java.security.SecureRandom;

public class User {

    public static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final Session session;
    public final String hexColor;

    public User(Session session) {
        this.session = session;
        this.hexColor = generateRandomHexColor();
    }

    public String getSessionId() {
        return session.getId();
    }

    private static String generateRandomHexColor() {
        return "#" + Integer.toHexString(new Color(SECURE_RANDOM.nextInt()).getRGB()).substring(2);
    }

    public void sendCoordinate(Coordinate coordinate) {
        if (session.isOpen()) {
            session.getAsyncRemote().sendText(coordinate.generateJson());
        }
    }

    public void sendJson(String json) {
        if (session.isOpen()) {
            session.getAsyncRemote().sendText(json);
        }
    }
}