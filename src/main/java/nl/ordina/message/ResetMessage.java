package nl.ordina.message;

import static nl.ordina.message.MessageType.RESET;

public class ResetMessage extends Message {

    public ResetMessage() {
        setType(RESET);
    }
}
