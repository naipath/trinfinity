package nl.ordina.services;

import nl.ordina.Coordinate;
import nl.ordina.User;
import rx.Observable;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

public class UserService {

    private final Map<String, User> users = new HashMap<>();


    public void add(Session session) {
        users.put(session.getId(), new User(session));
    }

    public User get(String id) {
        return users.get(id);
    }

    public void remove(Session session) {
        users.remove(session.getId());
    }

    public void sendCoordinateToAllUsers(Coordinate coordinate) {
        this.getAllUsers().subscribe(user -> user.sendCoordinate(coordinate));
    }

    public Observable<User> getAllUsers() {
        return Observable.create(subscriber -> {
            users.values().stream().forEach(subscriber::onNext);
            subscriber.onCompleted();
        });
    }
}
