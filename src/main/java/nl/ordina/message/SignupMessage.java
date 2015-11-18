package nl.ordina.message;

import static nl.ordina.message.MessageType.SIGNUP;

public class SignupMessage extends Message {

    private String name;

    public SignupMessage() {
        setType(SIGNUP);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}