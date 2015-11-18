package nl.ordina.services;

import nl.ordina.Player;
import nl.ordina.message.ResetMessage;
import rx.Observable;

import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PlayerRepository {

    private final Map<String, Player> players = new TreeMap<>();

    public void add(final Player player) {
        players.put(player.getSessionId(), player);
    }

    public Player get(String id) {
        return players.get(id);
    }

    public void remove(final String id) {
        players.remove(id);
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append(players).toString();
    }
}
