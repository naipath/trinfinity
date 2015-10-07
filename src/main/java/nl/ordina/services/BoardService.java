package nl.ordina.services;

import nl.ordina.Coordinate;
import nl.ordina.User;
import rx.Observable;

import java.util.HashSet;
import java.util.Set;

public class BoardService {

    private Set<Coordinate> board = new HashSet<>();

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

    public boolean isWinningConditionMetAndAlsoTheMostUglyMethodEver(Coordinate coordinate) {
        return  // Match Y
                (board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX(), coordinate.getRelativeY() - 1))
                        && board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX(), coordinate.getRelativeY() - 2)))

                        || (board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX(), coordinate.getRelativeY() - 1))
                        && board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX(), coordinate.getRelativeY() + 1)))

                        || (board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX(), coordinate.getRelativeY() + 1))
                        && board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX(), coordinate.getRelativeY() + 2)))

                        //Match X
                        || (board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() - 1, coordinate.getRelativeY()))
                        && board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() - 2, coordinate.getRelativeY())))

                        || (board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() - 1, coordinate.getRelativeY()))
                        && board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() + 1, coordinate.getRelativeY())))

                        || (board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() + 1, coordinate.getRelativeY()))
                        && board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() + 2, coordinate.getRelativeY())))

                        // Match Diagonal left high to right low
                        || (board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() - 1, coordinate.getRelativeY() - 1))
                        && board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() - 2, coordinate.getRelativeY() - 2)))

                        || (board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() - 1, coordinate.getRelativeY() - 1))
                        && board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() + 1, coordinate.getRelativeY() + 1)))

                        || (board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() + 1, coordinate.getRelativeY() + 1))
                        && board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() + 2, coordinate.getRelativeY() + 2)))

                        //Match left low to right high
                        || (board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() - 1, coordinate.getRelativeY() + 1))
                        && board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() - 2, coordinate.getRelativeY() + 2)))

                        || (board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() - 1, coordinate.getRelativeY() + 1))
                        && board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() + 1, coordinate.getRelativeY() - 1)))

                        || (board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() + 1, coordinate.getRelativeY() - 1))
                        && board.stream().filter(c -> c.matchesSessionId(coordinate.getSessionId())).anyMatch(coordinate1 -> coordinate1.matches(coordinate.getRelativeX() + 2, coordinate.getRelativeY() - 2)));
    }
}
