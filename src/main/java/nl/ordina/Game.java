package nl.ordina;

import nl.ordina.message.CoordinateMessage;
import nl.ordina.message.GameEndingMessage;
import nl.ordina.message.Message;
import nl.ordina.message.SignupMessage;
import nl.ordina.services.Board;
import nl.ordina.services.PlayerRepository;
import rx.Observable;
import rx.subjects.ReplaySubject;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;

@ApplicationScoped
public class Game {

    @Inject
    private Board board;
    @Inject
    private PlayerRepository players;

    private final ReplaySubject<Message> messages = ReplaySubject.create();
    private Observable<Field> fieldStream;
    private Observable<GameEndingMessage> gameEndingObservable;

    @PostConstruct
    public void setup() {
        fieldStream = messages.ofType(CoordinateMessage.class)
            .filter(cm -> players.get(cm.getSessionId()).hasSignedup())
            .map(cm -> new Field(cm.getX(), cm.getY(), players.get(cm.getSessionId())))
            .distinct();

        fieldStream.subscribe(board);

        gameEndingObservable = fieldStream
            .filter(board::isWinningConditionMet)
            .map(field -> new GameEndingMessage(field.player.getName()));

        messages.ofType(SignupMessage.class).subscribe(
            message -> players.get(message.getSessionId()).signup(message.getName()));
    }

    public void addPlayer(Session session) {
        players.add(session);
        Player player = players.get(session.getId());
        fieldStream.subscribe(player);
        gameEndingObservable.subscribe(player::sendMessage);
    }

    public void removePlayer(Session session) {
        resetGame();
        players.remove(session);
    }

    public void resetGame() {
        board.resetGame();
//        this.setup();

        players.sendReset();
    }

    public void send(Message message) {
        messages.onNext(message);
    }
}
