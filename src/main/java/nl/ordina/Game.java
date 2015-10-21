package nl.ordina;

import nl.ordina.message.CoordinateMessage;
import nl.ordina.message.Message;
import nl.ordina.message.MessageType;
import nl.ordina.message.SignupMessage;
import nl.ordina.services.BoardService;
import nl.ordina.services.UserService;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;

@ApplicationScoped
public class Game {

    @Inject
    private BoardService boardService;
    @Inject
    private UserService userService;

    private final Subject<Message, Message> messages = new SerializedSubject<>(PublishSubject.create());

    @PostConstruct
    public void setup() {
        Observable<CoordinateMessage> coordinateStream = messages
                .filter(message -> MessageType.COORDINATE == message.getType())
                .map(message -> (CoordinateMessage) message);

        Observable<SignupMessage> signupStream = messages
                .filter(message -> MessageType.SIGNUP == message.getType())
                .map(message -> (SignupMessage) message);

        coordinateStream
                .filter(cm -> userService.get(cm.getSessionId()).hasSignedup())
                .map(cm -> new Coordinate(cm.getCoordinate(), userService.get(cm.getSessionId())))
                .filter(boardService::isNotOccupied)
                .subscribe(this::addCoordinate);

        signupStream.subscribe((signupMessage) ->
                userService.get(signupMessage.getSessionId()).signupUser(signupMessage.getUsername()));
    }

    public void addCoordinate(Coordinate coordinate) {
        boardService.add(coordinate);

        userService.sendCoordinateToAllUsers(coordinate);

        if (boardService.isWinningConditionMet(coordinate)) {
            boardService.gameEnding(userService.getAllUsers(), userService.get(coordinate.getSessionId()).getUsername());
        }
    }

    public void addUser(Session session) {
        userService.add(session);
        User user = userService.get(session.getId());
        boardService.getAllCoordinates().subscribe(user::sendCoordinate);
    }

    public void removeUser(Session session) {
        userService.remove(session);
    }

    public void resetGame() {
        boardService.resetGame();
        userService.sendReset();
    }

    public Subject<Message, Message> getMessages() {
        return messages;
    }
}