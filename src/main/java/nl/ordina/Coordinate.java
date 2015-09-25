package nl.ordina;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Coordinate {

    private String coordinate;
    private User user;

    public boolean isSameCoordinate(String coordinate) {
        return this.coordinate.equals(coordinate);
    }

    public String generateJson() {
        return String.format("{ \"coordinate\" : \"%s\", \"hexcolor\" : \"%s\", \"user\" : \"%s\"}", coordinate, user.getHexColor(), user.getSessionId());
    }
}
