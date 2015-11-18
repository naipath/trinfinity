package nl.ordina.message;

import static nl.ordina.message.MessageType.ENDING;

public class GameEndingMessage extends Message {

    private String name;

    public GameEndingMessage() {
        setType(ENDING);
    }

    public GameEndingMessage(String name) {
        this.name = name;
        setType(ENDING);
    }

    public String getName() {
        return name;
    }
}
