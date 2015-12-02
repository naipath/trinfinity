package nl.ordina;

import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;
import nl.ordina.message.NewTurnMessage;
import rx.Observer;

public class Turn implements Observer<Field> {

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
        return playerOnTurn();
    }

    public Player playerOnTurn() {
        return players.peek();
    }

    public boolean hasTurn(Player player) {
        return playerOnTurn().equals(player);
    }

    @Override
    public void onCompleted() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onError(Throwable e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onNext(Field t) {
        Player next = next();
        for (Player player : players) {
            player.sendMessage(new NewTurnMessage(next.getName()));
        }
    }
}
