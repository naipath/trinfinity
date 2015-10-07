package nl.ordina.message;

public class CoordinateMessage implements Message {

    private String coordinate;
    private String hexColor;
    private String sessionId;

    public CoordinateMessage(String coordinate, String hexColor, String sessionId) {
        this.coordinate = coordinate;
        this.hexColor = hexColor;
        this.sessionId = sessionId;
    }

    @Override
    public String getType() {
        return "coordinate";
    }

    @Override
    public String generateJson() {
        return String.format("{ \"type\" : \"" + getType() + "\", \"coordinate\" : \"%s\", \"hexcolor\" : \"%s\", \"user\" : \"%s\"}", coordinate, hexColor, sessionId);
    }
}
