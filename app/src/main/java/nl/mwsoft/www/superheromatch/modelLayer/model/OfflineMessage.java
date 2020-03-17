package nl.mwsoft.www.superheromatch.modelLayer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OfflineMessage implements Parcelable {

    private String senderId;
    private String receiverId;
    private String message;
    private String createdAt;

    public OfflineMessage() {
    }

    public OfflineMessage(String senderId, String receiverId, String message, String createdAt) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.createdAt = createdAt;
    }

    protected OfflineMessage(Parcel in) {
        senderId = in.readString();
        receiverId = in.readString();
        message = in.readString();
        createdAt = in.readString();
    }

    public static final Creator<OfflineMessage> CREATOR = new Creator<OfflineMessage>() {
        @Override
        public OfflineMessage createFromParcel(Parcel in) {
            return new OfflineMessage(in);
        }

        @Override
        public OfflineMessage[] newArray(int size) {
            return new OfflineMessage[size];
        }
    };

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "OfflineMessage{" +
                "senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", message='" + message + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(senderId);
        dest.writeString(receiverId);
        dest.writeString(message);
        dest.writeString(createdAt);
    }
}
