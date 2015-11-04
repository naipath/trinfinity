package nl.ordina.services;

import nl.ordina.Field;
import nl.ordina.User;
import nl.ordina.message.GameEndingMessage;
import rx.Observable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Board {

    private final Set<Field> board = new HashSet<>();

    public void add(Field field) {
        board.add(field);
    }

    public boolean isNotOccupied(Field field) {
        return !board.stream().anyMatch(c -> c.matches(field));
    }


    public Observable<Field> getAllCoordinates() {
        return Observable.create(subscriber -> {
            board.forEach(subscriber::onNext);
            subscriber.onCompleted();
        });
    }

    public void gameEnding(Observable<User> users, String username) {
        users.subscribe(
                user -> user.sendMessage(new GameEndingMessage(username)),
                Throwable::printStackTrace,
                board::clear
        );
    }

    private List<Field> getCoordinatesFromUser(String sessionId) {
        return board.stream().filter(c -> c.matchesSessionId(sessionId)).collect(Collectors.toList());
    }

    public boolean isWinningConditionMet(Field field) {
        List<Field> userFields = getCoordinatesFromUser(field.getSessionId());

        return userFields.stream()
                .filter(field::nextTo)
                .filter(coordinate2 -> hasLineOfThree(field, coordinate2, userFields)).count() > 0;
    }

    private boolean hasLineOfThree(Field field, Field field2, List<Field> userFields) {
        int xDiff = field.relativeX - field2.relativeX;
        int yDiff = field.relativeY - field2.relativeY;

        if (xDiff != 0 || yDiff != 0) {
            return userFields.stream().anyMatch(c -> c.matches(field2.relativeX - xDiff, field2.relativeY - yDiff))
                    || userFields.stream().anyMatch(c -> c.matches(field.relativeX + xDiff, field.relativeY + yDiff));
        }
        return false;
    }

    public void resetGame() {
        board.clear();
    }
}
