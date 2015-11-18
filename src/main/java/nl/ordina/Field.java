package nl.ordina;

import nl.ordina.message.CoordinateMessage;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Field {

    private static final String SEPERATOR = "_";

    public final int relativeX;
    public final int relativeY;
    public final Player player;

    public Field(String coordinate, Player player) {
        String[] split = coordinate.split(SEPERATOR);
        relativeX = Integer.parseInt(split[0]);
        relativeY = Integer.parseInt(split[1]);

        this.player = player;
    }

    public boolean matches(Field field) {
        return equals(field);
    }

    public boolean matches(int xCoordinate, int yCoordinate) {
        return relativeX == xCoordinate
                && relativeY == yCoordinate;
    }

    public boolean matchesSessionId(String sessionId) {
        return player.getSessionId().equals(sessionId);
    }

    public boolean nextTo(Field field) {
        return !this.equals(field) && (field.relativeX >= relativeX - 1 && field.relativeX <= relativeX + 1)
                && (field.relativeY >= relativeY - 1 && field.relativeY <= relativeY + 1);
    }

    private String getStringCoordinate() {
        return relativeX + SEPERATOR + relativeY;
    }

    public String getSessionId() {
        return player.getSessionId();
    }

    public CoordinateMessage generateMessage() {
        return new CoordinateMessage(getStringCoordinate(), player.hexColor, player.getSessionId());
    }

    @Override
    public boolean equals(Object o) {
        Field other = (Field) o;
        return new EqualsBuilder()
                .append(this.relativeX, other.relativeX)
                .append(this.relativeY, other.relativeY)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.relativeX)
                .append(this.relativeY)
                .hashCode();
    }
}
