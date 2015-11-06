package nl.ordina;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.ordina.message.Message;
import nl.ordina.message.MessageDecoder;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/actions", decoders = {MessageDecoder.class})
public class WebsocketBoundary {

    @Inject private Game game;

    @OnOpen
    public void onOpen(Session session) {
        game.addUser(session);
    }

    @OnMessage
    public void onMessage(Session s, Message message) throws IOException {
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
