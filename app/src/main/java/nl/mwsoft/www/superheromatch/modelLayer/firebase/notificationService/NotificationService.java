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
import nl.mwsoft.www.superheromatch.modelLayer.network.NetworkLayer;

public class NotificationService extends FirebaseMessagingService {

    private CompositeDisposable disposable = new CompositeDisposable();
    private NetworkLayer networkLayer = new NetworkLayer();
    private UserDatabaseLayer userDatabaseLayer = new UserDatabaseLayer();
    private ChatDatabaseLayer chatDatabaseLayer = new ChatDatabaseLayer();
    private InternetConnectionUtil internetConnectionUtil = new InternetConnectionUtil();
    private DateTimeUtil dateTimeUtil = new DateTimeUtil();
    private UUIDUtil uuidUtil = new UUIDUtil();

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
                        chatDatabaseLayer.getChatByContactId(
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
}
