package nl.ordina;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value = "/actions")
public class WebsocketBoundary {

    @Inject
    private Sessions sessions;

    @Inject
    private Game game;

    @OnOpen
    public void onOpen(Session session) {
        game.getCoordinates().subscribe(s -> session.getAsyncRemote().sendText(s));
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(Session s, String message) {
        game.addOccupation(message, s.getId());
        sessions.sendMessageToAll(message);
    }

    @OnClose
    public void onClose(Session s) {
        sessions.remove(s);
        game.clearBord();
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
