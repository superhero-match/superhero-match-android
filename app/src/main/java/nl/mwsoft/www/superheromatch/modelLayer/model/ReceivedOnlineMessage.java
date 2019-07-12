package nl.mwsoft.www.superheromatch.modelLayer.model;

public class ReceivedOnlineMessage {
    private int _id;
    private String uuid;
    private String chatName;

    public ReceivedOnlineMessage() {
    }

    public ReceivedOnlineMessage(String uuid, String chatName) {
        this.uuid = uuid;
        this.chatName = chatName;
    }
    public ReceivedOnlineMessage(int _id, String uuid, String chatName) {
        this._id = _id;
        this.uuid = uuid;
        this.chatName = chatName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }
}
