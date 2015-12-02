package nl.ordina;

import nl.ordina.message.*;
import nl.ordina.services.PlayerRepository;
import rx.Observable;
import rx.subjects.ReplaySubject;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

@ApplicationScoped
public class Game {

    private Turn turn;
    private final PlayerRepository players = new PlayerRepository();

    private ReplaySubject<Message> messages;
    private Observable<Field> fields;
    private Observable<GameEndingMessage> gameOver;

    public Game() {
        initialize();
    }

    private void initialize() {
        Board board = new Board();
        turn = new Turn();
        messages = ReplaySubject.create();
        fields = messages.ofType(CoordinateMessage.class)
            .filter(cm -> players.get(cm.getSessionId()).hasSignedup())
            .filter(cm -> turn.hasTurn(players.get(cm.getSessionId())))
            .map(cm -> new Field(cm.getX(), cm.getY(), players.get(cm.getSessionId())))
            .share();

        fields.subscribe(board);
        fields.subscribe(turn);

        gameOver = fields
            .filter(board::isWinningConditionMet)
            .doOnNext(f -> resetGame())
            .map(field -> new GameEndingMessage(field.player.getName()))
            .share();

        Observable<SignupMessage> signup = messages.ofType(SignupMessage.class);
        signup.subscribe(
            message -> {
                players.get(message.getSessionId()).signup(message.getName());
                players.getAllSignedUpPlayers().forEach(p -> p.sendMessage(new ExpandMessage(players.boardSize())));
            });

        players.getAllPlayers().forEach(
            player -> {
                fields.subscribe(player);
                gameOver.subscribe(player::sendMessage);
                turn.add(player);
            });
    }

    public void addPlayer(Session session) {
        final Player player = new Player(session);
        players.add(player);
        turn.add(player);;
        fields.subscribe(player);
        gameOver.subscribe(player::sendMessage);
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

}
