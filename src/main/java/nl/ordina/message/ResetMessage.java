package nl.ordina.message;

public class ResetMessage implements Message {
    @Override
    public String getType() {
        return "reset";
    }
}
