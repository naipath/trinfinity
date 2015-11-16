package nl.ordina;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.ordina.message.Message;

import javax.websocket.Session;
import java.awt.*;
import java.security.SecureRandom;

public class User {

    public final String hexColor;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private final Session session;
    private String username;

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

    public void sendMessage(Message message) {
        session.getAsyncRemote().sendObject(message);
    }

    public void sendCoordinate(Field field) {
        sendMessage(field.generateMessage());
    }

    public void signupUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public boolean hasSignedup() {
        return username != null;
    }
}
