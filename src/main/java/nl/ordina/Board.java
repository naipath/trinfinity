package nl.ordina;

import rx.Observer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Board implements Observer<Field> {

    private final Set<Field> fields = new HashSet<>();

    private List<Field> getCoordinatesFromPlayer(String sessionId) {
        return fields.stream().filter(field -> field.matchesSessionId(sessionId)).collect(Collectors.toList());
    }

    public boolean isWinningConditionMet(Field field) {
        List<Field> playerFields = getCoordinatesFromPlayer(field.getSessionId());
        return playerFields.stream()
            .filter(field::nextTo)
            .filter(coordinate2 -> hasLineOfThree(field, coordinate2, playerFields)).count() > 0;
    }

    private boolean hasLineOfThree(Field field, Field field2, List<Field> fields) {
        int xDiff = field.relativeX - field2.relativeX;
        int yDiff = field.relativeY - field2.relativeY;

        if (xDiff != 0 || yDiff != 0) {
            return fields.stream().anyMatch(c -> c.matches(field2.relativeX - xDiff, field2.relativeY - yDiff))
                || fields.stream().anyMatch(c -> c.matches(field.relativeX + xDiff, field.relativeY + yDiff));
        }
        return false;
    }

    public void resetGame() {
        fields.clear();
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(Field field) {
        fields.add(field);

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append(this.fields).toString();
    }
}
