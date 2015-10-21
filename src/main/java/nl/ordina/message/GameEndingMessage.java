package nl.ordina.message;

import static nl.ordina.message.MessageType.ENDING;

public class GameEndingMessage extends Message {

    private String username;

    public GameEndingMessage() {
        setType(ENDING);
    }

    public GameEndingMessage(String username) {
        this.username = username;
        setType(ENDING);
    }

    public String getUsername() {
        return username;
    }
}
