package nl.ordina.message;

public class GameEndingMessage implements Message {
    @Override
    public String getType() {
        return "ending";
    }

    @Override
    public String generateJson() {
        return "{\"type\": \"" + getType() + "\", \"ending\": \"Iemand heeft gewonnen\"}";
    }
}
