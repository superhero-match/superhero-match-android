package nl.mwsoft.www.superheromatch.modelLayer.firebase.notificationService;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import nl.mwsoft.www.superheromatch.dependencyRegistry.DependencyRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.database.chat.ChatDatabaseLayer;
import nl.mwsoft.www.superheromatch.modelLayer.database.user.UserDatabaseLayer;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.dateTimeUtil.DateTimeUtil;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.internet.InternetConnectionUtil;
import nl.mwsoft.www.superheromatch.modelLayer.network.NetworkLayer;

public class NotificationService extends FirebaseMessagingService {

    private CompositeDisposable disposable;
    private Disposable subscribeGetOfflineMessages;
    private Disposable subscribeDeleteRetrievedMessages;
    private Disposable subscribeGetGroupChatInvitations;
    private Disposable subscribeHistory;
    private NetworkLayer networkLayer;
    private UserDatabaseLayer userDatabaseLayer;
    private ChatDatabaseLayer chatDatabaseLayer;
    private InternetConnectionUtil internetConnectionUtil;
    private DateTimeUtil dateTimeUtil;

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        disposable = new CompositeDisposable();
        internetConnectionUtil = new InternetConnectionUtil();
        dateTimeUtil = new DateTimeUtil();
    }

}
