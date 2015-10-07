package nl.ordina.message;

public class CoordinateMessage implements Message {

    private final String coordinate;
    private final String hexColor;
    private final String sessionId;

    public CoordinateMessage(String coordinate, String hexColor, String sessionId) {
        this.coordinate = coordinate;
        this.hexColor = hexColor;
        this.sessionId = sessionId;
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

    @Override
    public String getType() {
        return "coordinate";
    }
}
