package nl.ordina;

import nl.ordina.message.CoordinateMessage;

public class Field {

    private static final String SEPERATOR = "_";

    public final int relativeX;
    public final int relativeY;
    private final User user;

    public Field(String coordinate, User user) {
        String[] split = coordinate.split(SEPERATOR);
        relativeX = Integer.parseInt(split[0]);
        relativeY = Integer.parseInt(split[1]);

        this.user = user;
    }

    public boolean matches(Field field) {
        return relativeX == field.relativeX && relativeY == field.relativeY;
    }

    public boolean matches(int xCoordinate, int yCoordinate) {
        return relativeX == xCoordinate
                && relativeY == yCoordinate;
    }

    public boolean matchesSessionId(String sessionId) {
        return user.getSessionId().equals(sessionId);
    }


    public boolean nextTo(Field field) {
        return !this.matches(field) &&
                (field.relativeX >= relativeX - 1 && field.relativeX <= relativeX + 1) &&
                (field.relativeY >= relativeY - 1 && field.relativeY <= relativeY + 1);
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
