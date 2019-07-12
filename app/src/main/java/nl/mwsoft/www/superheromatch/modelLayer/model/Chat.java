package nl.mwsoft.www.superheromatch.modelLayer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Chat implements Parcelable{

    private int chatId;
    private String chatName;
    private String userName;
    private String lastActivityMessage;
    private String lastActivityDate;
    private int unreadMessageCount;

    public Chat() {
    }

    public Chat(int chatId, String chatName, String userName, String lastActivityMessage, String lastActivityDate, int unreadMessageCount) {
        this.chatId = chatId;
        this.chatName = chatName;
        this.userName = userName;
        this.lastActivityMessage = lastActivityMessage;
        this.lastActivityDate = lastActivityDate;
        this.unreadMessageCount = unreadMessageCount;
    }

    protected Chat(Parcel in) {
        chatId = in.readInt();
        chatName = in.readString();
        userName = in.readString();
        lastActivityMessage = in.readString();
        lastActivityDate = in.readString();
        unreadMessageCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(chatId);
        dest.writeString(chatName);
        dest.writeString(userName);
        dest.writeString(lastActivityMessage);
        dest.writeString(lastActivityDate);
        dest.writeInt(unreadMessageCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Chat> CREATOR = new Creator<Chat>() {
        @Override
        public Chat createFromParcel(Parcel in) {
            return new Chat(in);
        }

        @Override
        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastActivityMessage() {
        return lastActivityMessage;
    }

    public void setLastActivityMessage(String lastActivityMessage) {
        this.lastActivityMessage = lastActivityMessage;
    }

    public String getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(String lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public int getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public void setUnreadMessageCount(int unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
    }
}
