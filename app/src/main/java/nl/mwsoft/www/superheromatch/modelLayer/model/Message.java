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

import androidx.annotation.Nullable;


public class Message implements Parcelable {

    private int messageId;
    private String messageChatId;
    private String messageSenderId;
    private String messageText;
    private String messageCreated;

    public Message() {
    }

    public Message(int messageId, String messageChatId, String messageSenderId, String messageText, String messageCreated) {
        this.messageId = messageId;
        this.messageChatId = messageChatId;
        this.messageSenderId = messageSenderId;
        this.messageText = messageText;
        this.messageCreated = messageCreated;
    }

    protected Message(Parcel in) {
        messageId = in.readInt();
        messageChatId = in.readString();
        messageSenderId = in.readString();
        messageText = in.readString();
        messageCreated = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(messageId);
        dest.writeString(messageChatId);
        dest.writeString(messageSenderId);
        dest.writeString(messageText);
        dest.writeString(messageCreated);
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
}

