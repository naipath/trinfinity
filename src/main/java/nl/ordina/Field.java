package nl.ordina;

import nl.ordina.message.CoordinateMessage;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
        return equals(field);
    }

    public boolean matches(int xCoordinate, int yCoordinate) {
        return relativeX == xCoordinate
          && relativeY == yCoordinate;
    }

    public boolean matchesSessionId(String sessionId) {
        return user.getSessionId().equals(sessionId);
    }

    public boolean nextTo(Field field) {
        return !this.equals(field) && (field.relativeX >= relativeX - 1 && field.relativeX <= relativeX + 1)
          && (field.relativeY >= relativeY - 1 && field.relativeY <= relativeY + 1);
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(relativeX).append(relativeY).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Field other = (Field) obj;
        if (this.relativeX != other.relativeX) {
            return false;
        }
        if (this.relativeY != other.relativeY) {
            return false;
        }
        return true;
    }
}
