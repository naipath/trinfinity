package nl.ordina.message;

import static nl.ordina.message.MessageType.COORDINATE;

public class CoordinateMessage extends Message {

    private String coordinate;
    private String hexColor;
    private String sessionId;

    public CoordinateMessage() {
        setType(COORDINATE);
    }

    public CoordinateMessage(String coordinate, String hexColor, String sessionId) {
        this.coordinate = coordinate;
        this.hexColor = hexColor;
        this.sessionId = sessionId;
        setType(COORDINATE);
    }

    public String getCoordinate() {
        return coordinate;
    }

    public String getHexColor() {
        return hexColor;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
