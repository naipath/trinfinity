package nl.ordina.message;

public class ExpandMessage extends Message {

    private int size;

    public ExpandMessage(int size) {
        this.size = size;
        setType(MessageType.EXPAND);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
