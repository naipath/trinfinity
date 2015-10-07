package nl.ordina;

import nl.ordina.message.CoordinateMessage;

public class Coordinate {

    private static final String SEPERATOR = "_";

    public final int relativeX;
    public final int relativeY;
    private final User user;

    public Coordinate(String coordinate, User user) {
        String[] split = coordinate.split(SEPERATOR);
        relativeX = Integer.parseInt(split[0]);
        relativeY = Integer.parseInt(split[1]);

        this.user = user;
    }

    public boolean matches(Coordinate coordinate) {
        return relativeX == coordinate.relativeX && relativeY == coordinate.relativeY;
    }

    public boolean matches(int xCoordinate, int yCoordinate) {
        return relativeX == xCoordinate
                && relativeY == yCoordinate;
    }

    public boolean matchesSessionId(String sessionId) {
        return user.getSessionId().equals(sessionId);
    }


    public boolean nextTo(Coordinate coordinate) {
        return !this.matches(coordinate) &&
                (coordinate.relativeX >= relativeX - 1 && coordinate.relativeX <= relativeX + 1) &&
                (coordinate.relativeY >= relativeY - 1 && coordinate.relativeY <= relativeY + 1);
    }

    private String getStringCoordinate() {
        return relativeX + SEPERATOR + relativeY;
    }

    public String getSessionId() {
        return user.getSessionId();
    }

    public CoordinateMessage generateMessage() {
        return new CoordinateMessage(getStringCoordinate(), user.hexColor, user.getSessionId());
    }
}
