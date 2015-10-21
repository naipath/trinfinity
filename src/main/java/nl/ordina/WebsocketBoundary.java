package nl.ordina;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.ordina.message.CoordinateMessage;
import nl.ordina.message.Message;
import nl.ordina.message.SignupMessage;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;


@ServerEndpoint(value = "/actions")
public class WebsocketBoundary {

    private final ObjectMapper mapper = new ObjectMapper();

    @Inject
    private Game game;

    @OnOpen
    public void onOpen(Session session) {
        game.addUser(session);
    }

    @OnMessage
    public void onMessage(Session s, String json) throws IOException {
        Message message = mapper.readValue(json, Message.class);

        switch (message.getType()) {
            case SIGNUP:
                game.signup(s, (SignupMessage) message);
                break;
            case COORDINATE:
                game.addCoordinate((CoordinateMessage) message, s.getId());
                break;
        }
    }

    @OnClose
    public void onClose(Session s) {
        game.resetGame();
        game.removeUser(s);
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
