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
package nl.mwsoft.www.superheromatch.modelLayer.firebase.notificationService;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.database.chat.ChatDatabaseLayer;
import nl.mwsoft.www.superheromatch.modelLayer.database.user.UserDatabaseLayer;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.dateTimeUtil.DateTimeUtil;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.internet.InternetConnectionUtil;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.notificationUtil.NotificationUtil;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.uuid.UUIDUtil;
import nl.mwsoft.www.superheromatch.modelLayer.model.Chat;
import nl.mwsoft.www.superheromatch.modelLayer.model.Message;
import nl.mwsoft.www.superheromatch.modelLayer.model.OfflineMessage;
import nl.mwsoft.www.superheromatch.modelLayer.network.NetworkLayer;

public class NotificationService extends FirebaseMessagingService {

    private CompositeDisposable disposable = new CompositeDisposable();
    private NetworkLayer networkLayer = new NetworkLayer();
    private UserDatabaseLayer userDatabaseLayer = new UserDatabaseLayer();
    private InternetConnectionUtil internetConnectionUtil = new InternetConnectionUtil();
    private DateTimeUtil dateTimeUtil = new DateTimeUtil();
    private UUIDUtil uuidUtil = new UUIDUtil();
    private ChatDatabaseLayer chatDatabaseLayer = new ChatDatabaseLayer(dateTimeUtil);

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String notificationData = remoteMessage.getData().toString();

        Log.d(NotificationService.class.getName(), "remoteMessage: " + notificationData);

        String[] notification = notificationData.split(ConstantRegistry.COMMA);

        switch (notification[0]) {
            case ConstantRegistry.NEW_MATCH:
                String matchedSuperheroId = notification[1].
                        split(ConstantRegistry.EQUALS)[1].
                        replace(ConstantRegistry.CLOSE_CURLY_BRACES, ConstantRegistry.EMPTY_STRING);

                String superheroId = userDatabaseLayer.getUserId(NotificationService.this);

                handleNewMatch(matchedSuperheroId, superheroId);

                break;
            case ConstantRegistry.DELETE_MATCH:
                String matchedUserId = notification[1].
                        split(ConstantRegistry.EQUALS)[1].
                        replace(ConstantRegistry.CLOSE_CURLY_BRACES, ConstantRegistry.EMPTY_STRING);

                chatDatabaseLayer.deleteChatById(
                        chatDatabaseLayer.getChatByMatchId(
                                NotificationService.this,
                                matchedUserId
                        ).getChatId(),
                        NotificationService.this
                );

                break;
            case ConstantRegistry.NEW_LIKE:
                // Fetch user that liked.
                // TO-DO: create new table like and like db layer.
                // TO-DO: don't forget to check if user has PAID account before showing the
                // profile of the user that liked. But always show the like.
                break;
            case ConstantRegistry.NEW_MESSAGE:
                // Fetch the message and show user notification.
                // Once messages were saved to local db successfully,
                // delete messages from cache on server.
                handleNewOfflineMessages(userDatabaseLayer.getUserId(NotificationService.this));

                break;
        }
    }

    private void handleNewMatch(String matchedSuperheroId, String superheroId) {
        Disposable subscribeGetMatch = networkLayer.getMatch(configureGetMatchRequestBody(superheroId, matchedSuperheroId)).
                subscribeOn(Schedulers.io()).
                subscribe(res -> {
                    if (res.getStatus() != 200) {
                        Log.e(NotificationService.class.getName(), "Error while fetching match");

                        return;
                    }

                    String chatId = uuidUtil.generateUUID();

                    chatDatabaseLayer.insertChat(
                            chatId,
                            res.getMatch().getSuperheroName(),
                            res.getMatch().getId(),
                            res.getMatch().getMainProfilePicUrl(),
                            NotificationService.this
                    );

                    NotificationUtil.sendNewMatchNotification(
                            NotificationService.this,
                            chatDatabaseLayer.getChatById(NotificationService.this, chatId)
                    );
                }, Log::getStackTraceString);

        disposable.add(subscribeGetMatch);
    }

    private HashMap<String, Object> configureGetMatchRequestBody(String superheroId, String matchedSuperheroId) {
        HashMap<String, Object> reqBodyChoice = new HashMap<>();

        reqBodyChoice.put("superheroId", superheroId);
        reqBodyChoice.put("matchedSuperheroId", matchedSuperheroId);

        return reqBodyChoice;
    }

    private HashMap<String, Object> configureGetOfflineMessagesRequestBody(String superheroId) {
        HashMap<String, Object> reqBody = new HashMap<>();

        reqBody.put("superheroId", superheroId);

        return reqBody;
    }

    private HashMap<String, Object> configureDeleteOfflineMessagesRequestBody(String superheroId) {
        HashMap<String, Object> reqBody = new HashMap<>();

        reqBody.put("superheroId", superheroId);

        return reqBody;
    }

    private Message createMessage(String senderId, String messageText, String chatId) {
        Message chatMessage = new Message();
        chatMessage.setMessageChatId(chatId);
        chatMessage.setMessageSenderId(senderId);
        chatMessage.setMessageText(messageText);

        return chatMessage;
    }

    private void handleNewOfflineMessages(String superheroId) {
        Disposable subscribeGetNewOfflineMessages = networkLayer.
                getOfflineMessages(configureGetOfflineMessagesRequestBody(superheroId)).
                subscribeOn(Schedulers.io()).
                subscribe(res -> {
                    if (res.getStatus() != ConstantRegistry.SERVER_STATUS_OK) {
                        Log.e(
                                NotificationService.class.getName(),
                                "Error while fetching new offline messages"
                        );

                        return;
                    }

                    for (OfflineMessage offlineMessage : res.getMessages()) {
                        Chat chat = chatDatabaseLayer.getChatByMatchId(
                                NotificationService.this,
                                offlineMessage.getSenderId()
                        );

                        Message msg = createMessage(
                                offlineMessage.getSenderId(),
                                offlineMessage.getMessage(),
                                chat.getChatId()
                        );

                        chatDatabaseLayer.insertChatMessage(msg, NotificationService.this);

                        deleteOfflineMessages(superheroId);

                        NotificationUtil.sendNewOfflineMessageNotification(
                                NotificationService.this,
                                chat
                        );
                    }

                }, Log::getStackTraceString);

        disposable.add(subscribeGetNewOfflineMessages);
    }

    private void deleteOfflineMessages(String superheroId) {
        Disposable subscribeDeleteOfflineMessages = networkLayer.
                deleteOfflineMessages(configureDeleteOfflineMessagesRequestBody(superheroId)).
                subscribeOn(Schedulers.io()).
                subscribe(res -> {
                    if (res != ConstantRegistry.SERVER_STATUS_OK) {
                        Log.e(
                                NotificationService.class.getName(),
                                "Error while deleting new offline messages"
                        );
                    }


                }, Log::getStackTraceString);

        disposable.add(subscribeDeleteOfflineMessages);
    }
}
