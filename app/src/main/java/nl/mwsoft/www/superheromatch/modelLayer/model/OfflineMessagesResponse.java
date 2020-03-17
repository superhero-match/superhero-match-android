package nl.mwsoft.www.superheromatch.modelLayer.model;

import java.util.ArrayList;

public class OfflineMessagesResponse {

    private ArrayList<OfflineMessage> messages;
    private int status;

    public OfflineMessagesResponse() {
    }

    public OfflineMessagesResponse(int status) {
        this.status = status;
    }

    public OfflineMessagesResponse(ArrayList<OfflineMessage> messages, int status) {
        this.messages = messages;
        this.status = status;
    }

    public ArrayList<OfflineMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<OfflineMessage> messages) {
        this.messages = messages;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
