package nl.ordina;

import nl.ordina.message.CoordinateMessage;
import nl.ordina.message.GameEndingMessage;
import nl.ordina.message.Message;
import nl.ordina.message.SignupMessage;
import nl.ordina.services.PlayerRepository;
import rx.Observable;
import rx.subjects.ReplaySubject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import javax.websocket.Session;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@ApplicationScoped
public class Game {

    private Board board;
    private final PlayerRepository players = new PlayerRepository();


    private ReplaySubject<Message> messages;
    private Observable<Field> fieldStream;
    private Observable<GameEndingMessage> gameEndingObservable;

    public Game() {
        initialize();
    }

    private void initialize() {
        board = new Board();
        messages = ReplaySubject.create();
        fieldStream = messages.ofType(CoordinateMessage.class)
            .filter(cm -> players.get(cm.getSessionId()).hasSignedup())
            .map(cm -> new Field(cm.getX(), cm.getY(), players.get(cm.getSessionId())))
            .distinct();

        fieldStream.subscribe(board);

        gameEndingObservable = fieldStream
            .filter(board::isWinningConditionMet)
            .doOnNext(f -> resetGame())
            .map(field -> new GameEndingMessage(field.player.getName()));

        messages.ofType(SignupMessage.class).subscribe(
            message -> players.get(message.getSessionId()).signup(message.getName()));

        for (Player player : players.getAllPlayers().toList().toBlocking().first()) {
            fieldStream.subscribe(player);
            gameEndingObservable.subscribe(player::sendMessage);
        }
    }

    public void addPlayer(Session session) {
        final Player player = new Player(session);
        players.add(player);
        fieldStream.subscribe(player);
        gameEndingObservable.subscribe(player::sendMessage);
    }

    public void removePlayer(Session session) {
        players.remove(session.getId());
        resetGame();
    }

    private void resetGame() {
        players.sendReset();
        initialize();
    }

    public void send(Message message) {
        messages.onNext(message);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append(board).append(players).toString();
    }

}
