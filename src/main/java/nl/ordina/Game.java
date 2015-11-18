package nl.ordina;

import nl.ordina.message.CoordinateMessage;
import nl.ordina.message.GameEndingMessage;
import nl.ordina.message.Message;
import nl.ordina.message.SignupMessage;
import nl.ordina.services.Board;
import nl.ordina.services.UserRepository;
import rx.Observable;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;

@ApplicationScoped
public class Game {

    @Inject
    private Board board;
    @Inject
    private UserRepository users;

    private final ReplaySubject<Message> messages = ReplaySubject.create();
    private Observable<Field> fieldStream;
    private Observable<GameEndingMessage> gameEndingObservable;

    @PostConstruct
    public void setup() {
        Observable<CoordinateMessage> coordinateMessageStream = messages.ofType(CoordinateMessage.class);

        Observable<SignupMessage> signupStream = messages.ofType(SignupMessage.class);

        fieldStream = coordinateMessageStream
            .filter(cm -> users.get(cm.getSessionId()).hasSignedup())
            .map(cm -> new Field(cm.getCoordinate(), users.get(cm.getSessionId())))
            .distinct();

        fieldStream.subscribe(board);

        gameEndingObservable = fieldStream.filter(board::isWinningConditionMet).map(field -> new GameEndingMessage(
            field.user.getUsername()));

        signupStream.subscribe((signupMessage)
            -> users.get(signupMessage.getSessionId()).signupUser(signupMessage.getUsername()));
    }

    public void addUser(Session session) {
        users.add(session);
        User user = users.get(session.getId());
        fieldStream.subscribe(user);
        gameEndingObservable.subscribe(user::sendMessage);
    }

    public void removeUser(Session session) {
        users.remove(session);
    }

    public void resetGame() {
        board.resetGame();
//        this.setup();

        users.sendReset();
    }

    public void send(Message message) {
        messages.onNext(message);
    }
}
