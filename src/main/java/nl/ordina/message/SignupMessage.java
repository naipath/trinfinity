package nl.ordina.message;

import static nl.ordina.message.MessageType.SIGNUP;

public class SignupMessage extends Message {

    public String username;

    public SignupMessage() {
        setType(SIGNUP);
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }
}