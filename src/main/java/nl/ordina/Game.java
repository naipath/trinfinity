package nl.ordina;

import rx.Observable;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class Game {

    private Set<Coordinate> board = new HashSet<>();
    private Map<String, User> users = new HashMap<>();

    public void addCoordinate(String coordinateId, String sessionId) {
        Coordinate coordinate = new Coordinate(coordinateId, users.get(sessionId));

        if (!isCoordinateAlreadyOccupied(coordinateId)) {
            board.add(coordinate);
            sendCoordinateToAllUsers(coordinate);
        }
    }

    private boolean isCoordinateAlreadyOccupied(String coordinateId) {
        return board.stream().anyMatch(coordinate1 -> coordinate1.getCoordinate().equals(coordinateId));
    }

    private Observable<Coordinate> getAllCoordinates() {
        return Observable.create(subscriber -> board.forEach(subscriber::onNext));
    }

    public void addUser(Session session) {
        users.put(session.getId(), new User(session));
        sendAllCoordinatesToUser(users.get(session.getId()));
    }

    public void removeUser(Session session) {
        users.remove(session.getId());
    }

    private void sendCoordinateToAllUsers(Coordinate coordinate) {
        users.forEach((s, user) -> user.sendCoordinate(coordinate));
    }

    private void sendAllCoordinatesToUser(User user) {
        getAllCoordinates().subscribe(user::sendCoordinate);
    }

    public void clearBord() {
        board.clear();
    }
}