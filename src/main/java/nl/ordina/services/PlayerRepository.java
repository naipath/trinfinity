package nl.ordina.services;

import nl.ordina.Field;
import nl.ordina.Player;
import nl.ordina.message.ResetMessage;
import rx.Observable;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

public class PlayerRepository {

    private final Map<String, Player> players = new HashMap<>();

    public void add(Session session) {
        players.put(session.getId(), new Player(session));
    }

    public Player get(String id) {
        return players.get(id);
    }

    public void remove(Session session) {
        players.remove(session.getId());
    }

    public Observable<Player> getAllPlayers() {
        return Observable.create(subscriber -> {
            players.values().stream().forEach(subscriber::onNext);
            subscriber.onCompleted();
        });
    }

    public void sendReset() {
        this.getAllPlayers().subscribe(player -> player.sendMessage(new ResetMessage()));
    }
}
