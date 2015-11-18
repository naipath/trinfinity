package nl.ordina;

import nl.ordina.message.Message;
import rx.Observer;

import javax.websocket.Session;
import java.awt.*;
import java.security.SecureRandom;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Player implements Observer<Field>, Comparable<Player> {

    public final String hexColor;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private final Session session;
    private String name;

    public Player(Session session) {
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
        if (session.isOpen()) {
            session.getAsyncRemote().sendObject(message);
        }
    }

    public void sendField(Field field) {
        sendMessage(field.generateMessage());
    }

    public void signup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean hasSignedup() {
        return name != null;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(Field field) {
        this.sendField(field);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append(name).toString();
    }

    @Override
    public int compareTo(Player that) {
        return this.getSessionId().compareTo(that.getSessionId());
    }
}
