package nl.ordina.message;

public class GameEndingMessage implements Message {
    @Override
    public String getType() {
        return "ending";
    }
}
