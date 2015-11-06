package nl.ordina.marshalling;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.ordina.message.Message;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

public class MessageDecoder implements Decoder.Text<Message> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Message decode(String json) throws DecodeException {
        Message message = null;
        try {
            message = mapper.readValue(json, Message.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
