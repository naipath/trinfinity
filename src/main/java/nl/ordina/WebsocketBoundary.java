package nl.ordina;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.ordina.message.Message;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/actions")
public class WebsocketBoundary {

    private final ObjectMapper mapper = new ObjectMapper();

    @Inject private Game game;

    @OnOpen
    public void onOpen(Session session) {
        game.addUser(session);
    }

    @OnMessage
    public void onMessage(Session s, String json) throws IOException {
        Message message = mapper.readValue(json, Message.class);
        message.setSessionId(s.getId());
        game.getMessages().onNext(message);
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
