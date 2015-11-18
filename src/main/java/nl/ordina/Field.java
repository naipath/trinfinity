package nl.ordina;

import nl.ordina.message.CoordinateMessage;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Field implements Comparable<Field> {

    public final int relativeX;
    public final int relativeY;
    public final Player player;

    public Field(int x, int y, Player player) {
        relativeX = x;
        relativeY = y;
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

    public String getSessionId() {
        return player.getSessionId();
    }

    public CoordinateMessage generateMessage() {
        return new CoordinateMessage(relativeX, relativeY, player.hexColor, player.getSessionId());
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public int compareTo(Field that) {
        return (this.relativeX - that.relativeX) + (this.relativeY - that.relativeY);
    }
}
