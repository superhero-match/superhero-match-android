package nl.mwsoft.www.superheromatch.modelLayer.event;

import java.util.ArrayList;

import nl.mwsoft.www.superheromatch.modelLayer.model.Message;

public class TextMessageEvent {

    public ArrayList<Message> messages = new ArrayList<>();

    public TextMessageEvent() {
    }

    public TextMessageEvent(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
