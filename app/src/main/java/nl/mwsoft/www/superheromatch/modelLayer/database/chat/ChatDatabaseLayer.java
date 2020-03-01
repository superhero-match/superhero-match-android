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
package nl.mwsoft.www.superheromatch.modelLayer.database.chat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import nl.mwsoft.www.superheromatch.modelLayer.database.dbhelper.DBOpenHelper;
import nl.mwsoft.www.superheromatch.modelLayer.database.provider.SuperHeroMatchProvider;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.dateTimeUtil.DateTimeUtil;
import nl.mwsoft.www.superheromatch.modelLayer.model.Chat;
import nl.mwsoft.www.superheromatch.modelLayer.model.Message;
import nl.mwsoft.www.superheromatch.modelLayer.model.MessageQueueItem;
import nl.mwsoft.www.superheromatch.modelLayer.model.ReceivedOnlineMessage;

public class ChatDatabaseLayer {
    private DateTimeUtil dateTimeUtil;

    public ChatDatabaseLayer() {
    }

    public ChatDatabaseLayer(DateTimeUtil dateTimeUtil) {
        this.dateTimeUtil = dateTimeUtil;
    }

    public void updateMessageHasBeenReadByMessageId(int messageId, Context context) {
        ContentValues messageValues = new ContentValues();
        String selection = DBOpenHelper.MESSAGE_ID + "=" + messageId;
        messageValues.put(DBOpenHelper.MESSAGE_HAS_BEEN_READ, 1);

        context.getContentResolver().update(SuperHeroMatchProvider.CONTENT_URI_MESSAGE, messageValues, selection, null);
    }

    public int getUnreadMessageCountByChatId(Context context, String chatId) {
        int count = 0;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT COUNT (*) AS c FROM " + DBOpenHelper.TABLE_MESSAGE +
                " WHERE " + DBOpenHelper.MESSAGE_HAS_BEEN_READ + "=0 AND " +
                DBOpenHelper.MESSAGE_CHAT_ID + "='" + chatId + "'", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("c")));
            }
        }

        cursor.close();
        db.close();

        return count;
    }

    public Message getLastChatMessageByChatId(Context context, String chatId) {
        Message lastMessage = new Message();
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_MESSAGE + " WHERE " + DBOpenHelper.MESSAGE_CHAT_ID
                + "='" + chatId + "' ORDER BY " + DBOpenHelper.MESSAGE_ID + " DESC LIMIT 1", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                lastMessage.setMessageId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MESSAGE_ID))));
                lastMessage.setMessageText(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.TEXT_MESSAGE)));
                lastMessage.setMessageCreated(dateTimeUtil.getMessageCreated(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MESSAGE_CREATED))));
            }
        }
        cursor.close();
        db.close();

        return lastMessage;
    }

    public ArrayList<Message> getAllMessagesForChatWithId(Context context, String chatId) {
        ArrayList<Message> messages = new ArrayList<>();
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_MESSAGE +
                " WHERE " + DBOpenHelper.MESSAGE_CHAT_ID + "='" + chatId + "'", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Message message = new Message();
                message.setMessageId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MESSAGE_ID))));
                message.setMessageChatId(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MESSAGE_CHAT_ID)));
                message.setMessageSenderId(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MESSAGE_SENDER_ID)));
                message.setMessageCreated(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MESSAGE_CREATED)));
                message.setMessageUUID(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MESSAGE_UUID)));
                message.setMessageText(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.TEXT_MESSAGE)));

                messages.add(message);
            }
        }

        cursor.close();
        db.close();

        return messages;
    }

    public String getChatIdByChatName(Context context, String chatName) {
        String id = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT " + DBOpenHelper.CHAT_ID + " FROM " + DBOpenHelper.TABLE_CHAT +
                " WHERE " + DBOpenHelper.CHAT_NAME + "='" + chatName + "'", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                id = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.CHAT_ID));
            }
        }

        cursor.close();
        db.close();

        return id;
    }

    public ArrayList<Chat> getAllChats(Context context) {
        ArrayList<Chat> chats = new ArrayList<>();
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_CHAT, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Chat chat = new Chat();
                chat.setChatId(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.CHAT_ID)));
                chat.setChatName(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.CHAT_NAME)));
                chat.setMatchedUserId(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.CHAT_MATCHED_USER_ID)));
                chat.setMatchedUserMainProfilePic(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.CHAT_MATCHED_USER_MAIN_PROFILE_PIC)));
                if (getLastChatMessageByChatId(context, chat.getChatId()) != null) {
                    chat.setLastActivityMessage(getLastChatMessageByChatId(context, chat.getChatId()).getMessageText());
                }
                chat.setLastActivityDate(getLastChatMessageByChatId(context, chat.getChatId()).getMessageCreated());
                chats.add(chat);

                Log.d("tShoot", chat.toString());
            }
        }
        cursor.close();
        db.close();

        return chats;
    }

    public Chat getChatByContactId(Context context, String matchName) {
        Chat chat = null;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_CHAT +
                " WHERE " + DBOpenHelper.CHAT_MATCHED_USER_ID + "='" + matchName + "'", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                chat = new Chat();
                chat.setChatId(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.CHAT_ID)));
                chat.setChatName(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.CHAT_NAME)));
                chat.setMatchedUserId(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.CHAT_MATCHED_USER_ID)));
                chat.setMatchedUserMainProfilePic(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.CHAT_MATCHED_USER_MAIN_PROFILE_PIC)));
                if (getLastChatMessageByChatId(context, chat.getChatId()) != null) {
                    chat.setLastActivityMessage(getLastChatMessageByChatId(context, chat.getChatId()).getMessageText());
                }
                chat.setLastActivityDate(getLastChatMessageByChatId(context, chat.getChatId()).getMessageCreated());
            }
        }
        cursor.close();
        db.close();

        return chat;
    }

    public Chat getChatById(Context context, String chatId) {
        Chat chat = new Chat();
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_CHAT +
                " WHERE " + DBOpenHelper.CHAT_ID + "='" + chatId + "'", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                chat.setChatId(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.CHAT_ID)));
                chat.setChatName(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.CHAT_NAME)));
                chat.setMatchedUserId(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.CHAT_MATCHED_USER_ID)));
                chat.setMatchedUserMainProfilePic(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.CHAT_MATCHED_USER_MAIN_PROFILE_PIC)));
                if (getLastChatMessageByChatId(context, chat.getChatId()) != null) {
                    chat.setLastActivityMessage(getLastChatMessageByChatId(context, chat.getChatId()).getMessageText());
                }
                chat.setLastActivityDate(getLastChatMessageByChatId(context, chat.getChatId()).getMessageCreated());
            }
        }
        cursor.close();
        db.close();

        return chat;
    }

    public void deleteChatMessageById(int messageId, Context context) {
        String msgSelectionUpdate = DBOpenHelper.MESSAGE_ID + "=" + messageId;
        context.getContentResolver().delete(SuperHeroMatchProvider.CONTENT_URI_MESSAGE, msgSelectionUpdate, null);
    }

    public void deleteChatMessageBySenderId(String senderId, Context context) {
        String msgSelectionUpdate = DBOpenHelper.MESSAGE_SENDER_ID + "='" + senderId + "'";
        context.getContentResolver().delete(SuperHeroMatchProvider.CONTENT_URI_MESSAGE, msgSelectionUpdate, null);
    }

    public void insertChat(String chatId, String matchName, String matchedUserId, String chatProfilePic, Context context) {
        ContentValues setValues = new ContentValues();
        setValues.put(DBOpenHelper.CHAT_ID, chatId);
        setValues.put(DBOpenHelper.CHAT_NAME, matchName);
        setValues.put(DBOpenHelper.CHAT_MATCHED_USER_ID, matchedUserId);
        setValues.put(DBOpenHelper.CHAT_MATCHED_USER_MAIN_PROFILE_PIC, chatProfilePic);

        Uri setUri = context.getContentResolver().insert(SuperHeroMatchProvider.CONTENT_URI_CHAT, setValues);
    }

    public void insertChatMessageQueueItem(String messageUUID, String receiverId, Context context) {
        ContentValues setValues = new ContentValues();
        setValues.put(DBOpenHelper.MESSAGE_QUEUE_MESSAGE_UUID, messageUUID);
        setValues.put(DBOpenHelper.MESSAGE_QUEUE_MESSAGE_RECEIVER_ID, receiverId);

        Uri setUri = context.getContentResolver().insert(SuperHeroMatchProvider.CONTENT_URI_MESSAGE_QUEUE, setValues);
    }

    public MessageQueueItem getMessageQueueItemChat(Context context) {
        MessageQueueItem messageQueueItem = null;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_MESSAGE_QUEUE, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                messageQueueItem = new MessageQueueItem();
                messageQueueItem.set_id(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MESSAGE_QUEUE_ITEM_ID))));
                messageQueueItem.setMessageUUID(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MESSAGE_QUEUE_MESSAGE_UUID)));
                messageQueueItem.setReceiverId(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MESSAGE_QUEUE_MESSAGE_RECEIVER_ID)));
            }
        }
        cursor.close();
        db.close();

        return messageQueueItem;
    }

    public Message getChatMessageByUUID(Context context, String uuid) {
        Message message = null;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_MESSAGE + " WHERE " + DBOpenHelper.MESSAGE_UUID
                + " LIKE '" + uuid + "'", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                message = new Message();
                message.setMessageId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MESSAGE_ID))));
                message.setMessageChatId(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MESSAGE_CHAT_ID)));
                message.setMessageSenderId(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MESSAGE_SENDER_ID)));
                message.setMessageCreated(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MESSAGE_CREATED)));
                message.setMessageText(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.TEXT_MESSAGE)));
                message.setMessageUUID(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MESSAGE_UUID)));
            }
        }
        cursor.close();
        db.close();

        return message;
    }

    public void deleteChatMessageQueueItemByUUID(String uuid, Context context) {
        String msgSelectionUpdate = DBOpenHelper.MESSAGE_QUEUE_MESSAGE_UUID + "='" + uuid + "'";
        context.getContentResolver().delete(SuperHeroMatchProvider.CONTENT_URI_MESSAGE_QUEUE, msgSelectionUpdate, null);
    }

    public void insertChatMessage(Message chatMessage, Context context) {
        ContentValues setValues = new ContentValues();
        setValues.put(DBOpenHelper.MESSAGE_SENDER_ID, chatMessage.getMessageSenderId());
        setValues.put(DBOpenHelper.MESSAGE_CHAT_ID, chatMessage.getMessageChatId());
        setValues.put(DBOpenHelper.MESSAGE_HAS_BEEN_READ, 1);
        setValues.put(DBOpenHelper.MESSAGE_UUID, chatMessage.getMessageUUID());
        setValues.put(DBOpenHelper.MESSAGE_CREATED, chatMessage.getMessageCreated());
        setValues.put(DBOpenHelper.TEXT_MESSAGE, chatMessage.getMessageText());

        Uri setUri = context.getContentResolver().insert(SuperHeroMatchProvider.CONTENT_URI_MESSAGE, setValues);
    }

    public void deleteChatById(String chatId, Context context) {
        String chatSelectionUpdate = DBOpenHelper.CHAT_ID + "='" + chatId + "'";
        context.getContentResolver().delete(SuperHeroMatchProvider.CONTENT_URI_CHAT, chatSelectionUpdate, null);
    }

    public void insertReceivedOnlineMessage(String uuid, Context context) {
        ContentValues setValues = new ContentValues();
        setValues.put(DBOpenHelper.RECEIVED_ONLINE_MESSAGE_UUID, uuid);

        Uri setUri = context.getContentResolver().insert(SuperHeroMatchProvider.CONTENT_URI_RECEIVED_ONLINE_MESSAGE, setValues);
    }

    public void deleteReceivedOnlineMessageByUUID(String uuid, Context context) {
        String chatSelectionUpdate = DBOpenHelper.RECEIVED_ONLINE_MESSAGE_UUID + " LIKE ? ";
        context.getContentResolver().delete(SuperHeroMatchProvider.CONTENT_URI_RECEIVED_ONLINE_MESSAGE,
                chatSelectionUpdate, new String[]{uuid});
    }

    public ReceivedOnlineMessage getReceivedOnlineMessage(Context context) {
        ReceivedOnlineMessage receivedOnlineMessage = null;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_RECEIVED_ONLINE_MESSAGE, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                receivedOnlineMessage = new ReceivedOnlineMessage();
                receivedOnlineMessage.set_id(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.RECEIVED_ONLINE_MESSAGE_ID))));
                receivedOnlineMessage.setUuid(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.RECEIVED_ONLINE_MESSAGE_UUID)));
            }
        }
        cursor.close();
        db.close();

        return receivedOnlineMessage;
    }

    public String[] getReceivedOnlineMessageUUIDs(Context context) {
        ArrayList<String> receivedOnlineMessageUUIDs = new ArrayList<>();
        int size = 0;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_RECEIVED_ONLINE_MESSAGE, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String receivedOnlineMessageUUID = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.RECEIVED_ONLINE_MESSAGE_UUID));
                receivedOnlineMessageUUIDs.add(receivedOnlineMessageUUID);
                size++;
            }
        }
        cursor.close();
        db.close();
        String[] uuids = new String[size];
        return receivedOnlineMessageUUIDs.toArray(uuids);
    }
}
