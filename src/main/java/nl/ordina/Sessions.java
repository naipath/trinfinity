package nl.ordina;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class Sessions {

    private Set<Session> sessions = new HashSet<>();

    public void add(Session s) {
        sessions.add(s);
    }

    public void remove(Session s) {
        sessions.remove(s);
    }

    public void sendMessageToAll(String message){
        sessions.forEach(session1 -> session1.getAsyncRemote().sendText(message));
    }
}
