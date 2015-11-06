package nl.ordina;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;
import nl.ordina.message.CoordinateMessage;
import nl.ordina.message.Message;
import nl.ordina.message.SignupMessage;
import nl.ordina.services.Board;
import nl.ordina.services.UserRepository;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

@ApplicationScoped
public class Game {

    @Inject
    private Board board;
    @Inject
    private UserRepository users;

    private final Subject<Message, Message> messages = new SerializedSubject<>(PublishSubject.create());
    private Observable<Field> coordinateStream;

    @PostConstruct
    public void setup() {
        Observable<CoordinateMessage> coordinateMessageStream = messages.ofType(CoordinateMessage.class);

        Observable<SignupMessage> signupStream = messages.ofType(SignupMessage.class);

        coordinateStream = coordinateMessageStream
          .filter(cm -> users.get(cm.getSessionId()).hasSignedup())
          .map(cm -> new Field(cm.getCoordinate(), users.get(cm.getSessionId())))
          .filter(board::isNotOccupied);
        //.subscribe(this::addCoordinate);

        coordinateStream.subscribe(this::addCoordinate);

        signupStream.subscribe((signupMessage)
          -> users.get(signupMessage.getSessionId()).signupUser(signupMessage.getUsername()));
    }

    public void addCoordinate(Field field) {
        board.add(field);

        users.sendCoordinateToAllUsers(field);

        if (board.isWinningConditionMet(field)) {
            board.gameEnding(users.getAllUsers(), users.get(field.getSessionId()).getUsername());
        }
    }

    public void addUser(Session session) {
        users.add(session);
        User user = users.get(session.getId());
        board.getAllCoordinates().subscribe(user::sendCoordinate);
    }

    public void removeUser(Session session) {
        users.remove(session);
    }

    public void resetGame() {
        board.resetGame();
        users.sendReset();
    }

    public Subject<Message, Message> getMessages() {
        return messages;
    }
}
