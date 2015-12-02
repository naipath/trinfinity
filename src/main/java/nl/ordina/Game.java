package nl.ordina;

import nl.ordina.message.*;
import nl.ordina.services.PlayerRepository;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import rx.Observable;
import rx.subjects.ReplaySubject;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

@ApplicationScoped
public class Game {

    private Board board;
    private Turn turn;
    private final PlayerRepository players = new PlayerRepository();

    private ReplaySubject<Message> messages;
    private Observable<Field> fieldStream;
    private Observable<GameEndingMessage> gameEndingObservable;

    public Game() {
        initialize();
    }

    private void initialize() {
        board = new Board();
        turn = new Turn();
        messages = ReplaySubject.create();
        fieldStream = messages.ofType(CoordinateMessage.class)
                .filter(cm -> players.get(cm.getSessionId()).hasSignedup())
                .filter(cm -> turn.hasTurn(players.get(cm.getSessionId())))
                .map(cm -> new Field(cm.getX(), cm.getY(), players.get(cm.getSessionId())))
                .distinct()
                .share();

        fieldStream.subscribe(board);
        fieldStream.subscribe(turn);

        gameEndingObservable = fieldStream
            .filter(board::isWinningConditionMet)
            .doOnNext(f -> resetGame())
            .map(field -> new GameEndingMessage(field.player.getName())).share();

        messages.ofType(SignupMessage.class).subscribe(
            message -> {
                players.get(message.getSessionId()).signup(message.getName());
                players.getAllSignupPlayers()
                        .subscribe(player1 -> player1.sendMessage(new ExpandMessage(players.boardSize())));
            });

        for (Player player : players.getAllPlayers().toList().toBlocking().first()) {
            fieldStream.subscribe(player);
            gameEndingObservable.subscribe(player::sendMessage);
            turn.add(player);
        }
    }

    public void addPlayer(Session session) {
        final Player player = new Player(session);
        players.add(player);
        turn.add(player);;
        fieldStream.subscribe(player);
        gameEndingObservable.subscribe(player::sendMessage);
    }

    public void removePlayer(Session session) {
        final String id = session.getId();
        players.remove(id);
        turn.remove(players.get(id));
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
