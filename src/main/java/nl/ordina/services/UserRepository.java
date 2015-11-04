package nl.ordina.services;

import nl.ordina.Field;
import nl.ordina.User;
import nl.ordina.message.ResetMessage;
import rx.Observable;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {

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

    public void sendCoordinateToAllUsers(Field field) {
        this.getAllUsers().subscribe(user -> user.sendCoordinate(field));
    }

    public Observable<User> getAllUsers() {
        return Observable.create(subscriber -> {
            users.values().stream().forEach(subscriber::onNext);
            subscriber.onCompleted();
        });
    }

    public void sendReset(){
        this.getAllUsers().subscribe(user -> user.sendMessage(new ResetMessage()));
    }
}
