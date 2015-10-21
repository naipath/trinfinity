package nl.ordina.message;

import static nl.ordina.message.MessageType.ENDING;

public class GameEndingMessage extends Message {

    public GameEndingMessage() {
        setType(ENDING);
    }
}
