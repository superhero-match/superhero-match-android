package nl.mwsoft.www.superheromatch.modelLayer.firebase.instanceIdService;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nl.mwsoft.www.superheromatch.modelLayer.database.user.UserDatabaseLayer;
import nl.mwsoft.www.superheromatch.modelLayer.network.NetworkLayer;

public class MyFirebaseInstanceIdService extends FirebaseMessagingService {

    UserDatabaseLayer userDatabaseLayer = new UserDatabaseLayer();
    Disposable subscribeUpdateUserToken;
    NetworkLayer networkLayer = new NetworkLayer();

    @Override
    public void onNewToken(String s) {
        sendRegistrationToServer(s);
    }

    private void sendRegistrationToServer(String token) {
        if (userDatabaseLayer.getUserId(MyFirebaseInstanceIdService.this).length() > 0) {
            HashMap<String, Object> reqBody = configureUpdateFirebaseTokenRequestBody(
                    userDatabaseLayer.getUserId(MyFirebaseInstanceIdService.this),
                    token
            );

            subscribeUpdateUserToken = networkLayer.updateFirebaseToken(reqBody).
                    subscribeOn(Schedulers.io()).
                    subscribe(res -> {
                        if (res != 200) {
                            Log.e(MyFirebaseInstanceIdService.class.getName(), "Error while updating Firebase token");
                        }
                    }, throwable -> {
                        // Send error to Firebase
                    });
        }
    }

    private HashMap<String, Object> configureUpdateFirebaseTokenRequestBody(
            String userId,
            String token
    ) {
        HashMap<String, Object> reqBody = new HashMap<>();

        reqBody.put("superheroID", userId);
        reqBody.put("token", token);

        return reqBody;
    }
}
