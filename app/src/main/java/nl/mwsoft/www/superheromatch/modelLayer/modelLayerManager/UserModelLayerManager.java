package nl.mwsoft.www.superheromatch.modelLayer.modelLayerManager;

import android.content.Context;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import nl.mwsoft.www.superheromatch.dependencyRegistry.DependencyRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.database.user.UserDatabaseLayer;
import nl.mwsoft.www.superheromatch.modelLayer.model.RegisterResponse;
import nl.mwsoft.www.superheromatch.modelLayer.network.NetworkLayer;

public class UserModelLayerManager {


    private UserDatabaseLayer userDatabaseLayer;
    private NetworkLayer networkLayer;

    public UserModelLayerManager() {
        this.userDatabaseLayer = DependencyRegistry.shared.createUserDatabaseLayer();
        this.networkLayer = DependencyRegistry.shared.createNetworkLayer();
    }

    // region User DB

    public String getUserId(Context context) {
        return this.userDatabaseLayer.getUserId(context);
    }

    public String getUserName(Context context) {
        return this.userDatabaseLayer.getUserName(context);
    }

    public String getUserProfilePicUri(Context context) {
        return this.userDatabaseLayer.getUserMainProfilePicUrl(context);
    }

    public void updateUserId(String userID, Context context){
        this.userDatabaseLayer.updateUserId(userID, context);
    }

    public void updateUserProfilePic(String userID, String uri, Context context){
        this.userDatabaseLayer.updateUserMainProfilePic(userID, uri, context);
    }

    // region User Network

    // region Confirm Phone Number


    // endregion

    // region Register User

    public Observable<RegisterResponse> registerUser(HashMap<String, Object> userName){
        return this.networkLayer.register(userName);
    }

    // endregion

    // region Update User Token

    public Observable<String> updateUserToken(String userId, String  messagingToken){
        return this.networkLayer.getUpdateUserTokenResponse(userId, messagingToken);
    }

    // endregion
}
