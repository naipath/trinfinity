package nl.ordina;

import nl.ordina.message.Message;
import nl.ordina.marshalling.MessageDecoder;
import nl.ordina.marshalling.MessageEncoder;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/actions",
        decoders = {MessageDecoder.class},
        encoders = {MessageEncoder.class})
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
