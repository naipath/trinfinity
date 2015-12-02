package nl.ordina.message;

import static nl.ordina.message.MessageType.NEWTURN;

public class NewTurnMessage extends Message {

    private String name;

    public NewTurnMessage() {
        setType(NEWTURN);
    }

    public NewTurnMessage(String name) {
        this.name = name;
        setType(NEWTURN);
    }

    public String getName() {
        return name;
    }
}
