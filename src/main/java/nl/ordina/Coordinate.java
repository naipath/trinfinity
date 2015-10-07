package nl.ordina;

import lombok.Getter;

public class Coordinate {

    private static final String SEPERATOR = "_";

    @Getter
    private int relativeX;
    @Getter
    private int relativeY;
    private User user;

    public Coordinate(String coordinate, User user) {
        String[] split = coordinate.split(SEPERATOR);
        relativeX = Integer.parseInt(split[0]);
        relativeY = Integer.parseInt(split[1]);

        this.user = user;
    }

    public boolean matches(Coordinate coordinate) {
        return relativeX == coordinate.getRelativeX()
                && relativeY == coordinate.getRelativeY();
    }

    public boolean matches(int xCoordinate, int yCoordinate) {
        return relativeX == xCoordinate
                && relativeY == yCoordinate;
    }

    public boolean matchesSessionId(String sessionId) {
        return user.getSessionId().equals(sessionId);
    }


    private String getStringCoordinate() {
        return relativeX + SEPERATOR + relativeY;
    }

    public String getSessionId() {
        return user.getSessionId();
    }

    public String generateJson() {
        return String.format("{ \"coordinate\" : \"%s\", \"hexcolor\" : \"%s\", \"user\" : \"%s\"}", getStringCoordinate(), user.getHexColor(), user.getSessionId());
    }

    public String generateResetJson() {
        return String.format("{ \"coordinate\" : \"%s\", \"hexcolor\" : \"#fff\", \"user\" : \"%s\"}", getStringCoordinate(), user.getSessionId());
    }
}
