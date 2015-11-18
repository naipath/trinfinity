package nl.ordina.message;

import static nl.ordina.message.MessageType.COORDINATE;

public class CoordinateMessage extends Message {

    private int x;
    private int y;

    private String hexColor;
    private String sessionId;

    public CoordinateMessage() {
        setType(COORDINATE);
    }

    public CoordinateMessage(int x, int y, String hexColor, String sessionId) {
        this.x = x;
        this.y = y;
        this.hexColor = hexColor;
        this.sessionId = sessionId;
        setType(COORDINATE);
    }

    public String getHexColor() {
        return hexColor;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
