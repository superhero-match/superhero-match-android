package nl.mwsoft.www.superheromatch.modelLayer.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Message implements Parcelable {

    private int messageId;
    private String messageChatId;
    private String messageSenderId;
    private String messageText;
    private String messageCreated;
    private String messageUUID;

    public Message() {
    }

    public Message(int messageId, String messageChatId, String messageSenderId, String messageText, String messageCreated, String messageUUID) {
        this.messageId = messageId;
        this.messageChatId = messageChatId;
        this.messageSenderId = messageSenderId;
        this.messageText = messageText;
        this.messageCreated = messageCreated;
        this.messageUUID = messageUUID;
    }

    protected Message(Parcel in) {
        messageId = in.readInt();
        messageChatId = in.readString();
        messageSenderId = in.readString();
        messageText = in.readString();
        messageCreated = in.readString();
        messageUUID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(messageId);
        dest.writeString(messageChatId);
        dest.writeString(messageSenderId);
        dest.writeString(messageText);
        dest.writeString(messageCreated);
        dest.writeString(messageUUID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessageChatId() {
        return messageChatId;
    }

    public void setMessageChatId(String messageChatId) {
        this.messageChatId = messageChatId;
    }

    public String getMessageSenderId() {
        return messageSenderId;
    }

    public void setMessageSenderId(String messageSenderId) {
        this.messageSenderId = messageSenderId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageCreated() {
        return messageCreated;
    }

    public void setMessageCreated(String messageCreated) {
        this.messageCreated = messageCreated;
    }

    public String getMessageUUID() {
        return messageUUID;
    }

    public void setMessageUUID(String messageUUID) {
        this.messageUUID = messageUUID;
    }
}

