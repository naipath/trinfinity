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

    public void addOccupation(String coordinate, String sessionId) {
        Coordinate coordinate1 = new Coordinate(coordinate, users.get(sessionId));
        board.add(coordinate1);
        sendMessageToAllUsers(coordinate1);
    }

    private Observable<Coordinate> getCoordinates() {
        return Observable.create(subscriber -> board.forEach(subscriber::onNext));
    }

    public void clearBord() {
        board.clear();
    }

    public void addUser(Session s) {
        users.put(s.getId(), new User(s));
        getCoordinates().subscribe(coordinate -> users.get(s.getId()).sendCoordinate(coordinate));
    }

    public void removeUser(Session session) {
        users.remove(session.getId());
    }

    private void sendMessageToAllUsers(Coordinate coordinate) {
        users.forEach((s, user) -> user.sendCoordinate(coordinate));
    }
}