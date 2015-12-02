package nl.ordina.services;

import java.util.Collection;
import nl.ordina.Player;
import nl.ordina.message.ResetMessage;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;
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

    public int boardSize() {
        if (players.size() < 3) {
            return 3;
        }
        return players.size() + 1;
    }

    public Stream<Player> getAllPlayers() {
        return players.values().stream();
    }

    public Stream<Player> getAllSignedUpPlayers() {
        return getAllPlayers().filter(Player::hasSignedup);
    }

    public void sendReset() {
        this.getAllPlayers().forEach(player -> player.sendMessage(new ResetMessage()));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append(players).toString();
    }
}
