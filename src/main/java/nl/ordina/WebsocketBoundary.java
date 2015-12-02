package nl.ordina;

import nl.ordina.message.Message;
import nl.ordina.marshalling.MessageDecoder;
import nl.ordina.marshalling.MessageEncoder;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import javax.inject.Singleton;

@ServerEndpoint(value = "/actions",
    decoders = {MessageDecoder.class},
    encoders = {MessageEncoder.class})
public class WebsocketBoundary {

    @Inject
    private Game game ;

    @OnOpen
    public void onOpen(Session session) {
        game.addPlayer(session);

    }

    @OnMessage
    public void onMessage(Session s, Message message) throws IOException {
        message.setSessionId(s.getId());
        game.send(message);
    }

    @OnClose
    public void onClose(Session s) {
        game.removePlayer(s);
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
