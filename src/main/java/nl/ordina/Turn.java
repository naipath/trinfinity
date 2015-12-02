package nl.ordina;

import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;

/**
 *
 * @author Eric Jan Malotaux
 */
public class Turn {

    private final Queue<Player> players = new LinkedTransferQueue<>();

    public void add(Player player) {
        players.add(player);
    }

    public void remove(Player player) {
        players.remove(player);
    }

    public Player next() {
        Player next = players.remove();
        players.add(next);
        return next;
    }
}
