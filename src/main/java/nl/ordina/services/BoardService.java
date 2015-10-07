package nl.ordina.services;

import nl.ordina.Coordinate;
import nl.ordina.User;
import rx.Observable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BoardService {

    private final Set<Coordinate> board = new HashSet<>();

    public void add(Coordinate coordinate) {
        board.add(coordinate);
    }

    public boolean isOccupied(Coordinate coordinate) {
        return board.stream().anyMatch(c -> c.matches(coordinate));
    }


    public Observable<Coordinate> getAllCoordinates() {
        return Observable.create(subscriber -> {
            board.forEach(subscriber::onNext);
            subscriber.onCompleted();
        });
    }

    public void clearBord(Observable<User> users) {
        users.subscribe(
                (User user) -> board.forEach(coordinate -> user.sendJson(coordinate.generateResetJson())),
                Throwable::printStackTrace,
                board::clear
        );
    }

    private List<Coordinate> getCoordinatesFromUser(String sessionId) {
        return board.stream().filter(c -> c.matchesSessionId(sessionId)).collect(Collectors.toList());
    }

    public boolean isWinningConditionMetAndAlsoTheMostUglyMethodEver(Coordinate coordinate) {
        List<Coordinate> userCoordinates = getCoordinatesFromUser(coordinate.getSessionId());

        return userCoordinates.stream()
                .filter(coordinate::nextTo)
                .filter(coordinate2 -> hasLineOfThree(coordinate, coordinate2, userCoordinates)).count() > 0;
    }

    private boolean hasLineOfThree(Coordinate coordinate, Coordinate coordinate2, List<Coordinate> userCoordinates) {
        int xDiff = coordinate.relativeX - coordinate2.relativeX;
        int yDiff = coordinate.relativeY - coordinate2.relativeY;

        if (xDiff != 0 || yDiff != 0) {
            return userCoordinates.stream().anyMatch(c -> c.matches(coordinate2.relativeX - xDiff, coordinate2.relativeY - yDiff))
                    || userCoordinates.stream().anyMatch(c -> c.matches(coordinate.relativeX + xDiff, coordinate.relativeY + yDiff));
        }
        return false;
    }
}
