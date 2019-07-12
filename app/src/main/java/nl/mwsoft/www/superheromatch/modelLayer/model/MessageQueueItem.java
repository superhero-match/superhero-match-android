package nl.mwsoft.www.superheromatch.modelLayer.model;

public class MessageQueueItem {

    private int _id;
    private String messageUUID;
    private String receiverId;

    public MessageQueueItem() {
    }

    public MessageQueueItem(int _id, String messageUUID, String receiverId) {
        this._id = _id;
        this.messageUUID = messageUUID;
        this.receiverId = receiverId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getMessageUUID() {
        return messageUUID;
    }

    public void setMessageUUID(String messageUUID) {
        this.messageUUID = messageUUID;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
