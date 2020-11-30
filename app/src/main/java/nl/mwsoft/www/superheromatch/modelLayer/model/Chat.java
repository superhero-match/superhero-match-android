/*
  Copyright (C) 2019 - 2020 MWSOFT
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.mwsoft.www.superheromatch.modelLayer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Chat implements Parcelable{

    private String chatId;
    private String chatName;
    private String matchedUserId;
    private String matchedUserMainProfilePic;
    private String lastActivityMessage;
    private String lastActivityDate;
    private int unreadMessageCount;

    public Chat() {
    }

    public Chat(String chatId, String chatName, String matchedUserId, String matchedUserMainProfilePic, String lastActivityMessage, String lastActivityDate, int unreadMessageCount) {
        this.chatId = chatId;
        this.chatName = chatName;
        this.matchedUserId = matchedUserId;
        this.matchedUserMainProfilePic = matchedUserMainProfilePic;
        this.lastActivityMessage = lastActivityMessage;
        this.lastActivityDate = lastActivityDate;
        this.unreadMessageCount = unreadMessageCount;
    }

    protected Chat(Parcel in) {
        chatId = in.readString();
        chatName = in.readString();
        matchedUserId = in.readString();
        matchedUserMainProfilePic = in.readString();
        lastActivityMessage = in.readString();
        lastActivityDate = in.readString();
        unreadMessageCount = in.readInt();
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

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getMatchedUserId() {
        return matchedUserId;
    }

    public void setMatchedUserId(String matchedUserId) {
        this.matchedUserId = matchedUserId;
    }

    public String getMatchedUserMainProfilePic() {
        return matchedUserMainProfilePic;
    }

    public void setMatchedUserMainProfilePic(String matchedUserMainProfilePic) {
        this.matchedUserMainProfilePic = matchedUserMainProfilePic;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chatId);
        dest.writeString(chatName);
        dest.writeString(matchedUserId);
        dest.writeString(matchedUserMainProfilePic);
        dest.writeString(lastActivityMessage);
        dest.writeString(lastActivityDate);
        dest.writeInt(unreadMessageCount);
    }

    @Override
    public String toString() {
        return "Chat{" +
                "chatId='" + chatId + '\'' +
                ", chatName='" + chatName + '\'' +
                ", matchedUserId='" + matchedUserId + '\'' +
                ", matchedUserMainProfilePic='" + matchedUserMainProfilePic + '\'' +
                ", lastActivityMessage='" + lastActivityMessage + '\'' +
                ", lastActivityDate='" + lastActivityDate + '\'' +
                ", unreadMessageCount=" + unreadMessageCount +
                '}';
    }
}
