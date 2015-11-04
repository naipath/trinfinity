package nl.ordina;

import nl.ordina.message.CoordinateMessage;
import nl.ordina.message.Message;
import nl.ordina.message.SignupMessage;
import nl.ordina.services.Board;
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
    private Board boardService;
    @Inject
    private UserService userService;

    private final Subject<Message, Message> messages = new SerializedSubject<>(PublishSubject.create());

    private  Observable<Field> coordinateStream ;

    @PostConstruct
    public void setup() {
        Observable<CoordinateMessage> coordinateMessageStream = messages.ofType(CoordinateMessage.class);

        Observable<SignupMessage> signupStream = messages.ofType(SignupMessage.class);


        coordinateStream = coordinateMessageStream
                .filter(cm -> userService.get(cm.getSessionId()).hasSignedup())
                .map(cm -> new Field(cm.getCoordinate(), userService.get(cm.getSessionId())))
                .filter(boardService::isNotOccupied);
                //.subscribe(this::addCoordinate);

        coordinateStream.subscribe(this::addCoordinate);

        signupStream.subscribe((signupMessage) ->
                userService.get(signupMessage.getSessionId()).signupUser(signupMessage.getUsername()));
    }

    public void addCoordinate(Field field) {
        boardService.add(field);

        userService.sendCoordinateToAllUsers(field);

        if (boardService.isWinningConditionMet(field)) {
            boardService.gameEnding(userService.getAllUsers(), userService.get(field.getSessionId()).getUsername());
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