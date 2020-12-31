/*
  Copyright (C) 2019 - 2021 MWSOFT
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
package nl.mwsoft.www.superheromatch.modelLayer.firebase.instanceIdService;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.database.user.UserDatabaseLayer;
import nl.mwsoft.www.superheromatch.modelLayer.model.TokenResponse;
import nl.mwsoft.www.superheromatch.modelLayer.network.NetworkLayer;

public class MyFirebaseInstanceIdService extends FirebaseMessagingService {

    UserDatabaseLayer userDatabaseLayer = new UserDatabaseLayer();
    Disposable subscribeUpdateUserToken;
    private Disposable subscribeGetAccessToken;
    private Disposable subscribeRefreshToken;
    NetworkLayer networkLayer = new NetworkLayer();

    @Override
    public void onNewToken(String s) {
        sendRegistrationToServer(s);
    }

    private void sendRegistrationToServer(String token) {
        if (userDatabaseLayer.getUserId(MyFirebaseInstanceIdService.this).length() > 0) {
            HashMap<String, Object> requestBody = configureUpdateFirebaseTokenRequestBody(
                    userDatabaseLayer.getUserId(MyFirebaseInstanceIdService.this),
                    token
            );

            subscribeUpdateUserToken = networkLayer.updateFirebaseToken(requestBody, MyFirebaseInstanceIdService.this).
                    subscribeOn(Schedulers.io()).
                    subscribe(res -> {
                        if (res.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                            Log.e(MyFirebaseInstanceIdService.class.getName(), "Error while updating Firebase token");
                        }

                        if (res.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                            HashMap<String, Object> reqBody = new HashMap<>();
                            SharedPreferences prefs = getSharedPreferences(
                                    ConstantRegistry.SHARED_PREFERENCES,
                                    Context.MODE_PRIVATE
                            );

                            reqBody.put("refreshToken", prefs.getString(ConstantRegistry.REFRESH_TOKEN, ""));

                            subscribeRefreshToken = networkLayer.refreshToken(reqBody)
                                    .subscribeOn(Schedulers.io())
                                    .subscribe((TokenResponse result) -> {

                                        if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                            Log.e(MyFirebaseInstanceIdService.class.getName(), "Error while refreshing token");

                                            return;
                                        }

                                        if (result.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                                            HashMap<String, Object> rBody = new HashMap<>();
                                            rBody.put("id", userDatabaseLayer.getUserId(MyFirebaseInstanceIdService.this));

                                            subscribeGetAccessToken = networkLayer.getToken(rBody)
                                                    .subscribeOn(Schedulers.io())
                                                    .subscribe((TokenResponse resultToken) -> {

                                                        if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                                            Log.e(MyFirebaseInstanceIdService.class.getName(), "Error while refreshing token");

                                                            return;
                                                        }

                                                        SharedPreferences sharedPrefs = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sharedPrefs.edit();
                                                        editor.putString(ConstantRegistry.ACCESS_TOKEN, resultToken.getAccessToken());
                                                        editor.putString(ConstantRegistry.REFRESH_TOKEN, resultToken.getRefreshToken());
                                                        editor.apply();

                                                        sendRegistrationToServer(token);
                                                    }, throwable -> {
                                                        // Send error to Firebase
                                                    });

                                            return;
                                        }

                                        SharedPreferences preferences = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString(ConstantRegistry.ACCESS_TOKEN, result.getAccessToken());
                                        editor.putString(ConstantRegistry.REFRESH_TOKEN, result.getRefreshToken());
                                        editor.apply();

                                        sendRegistrationToServer(token);
                                    }, throwable -> {
                                        // Send error to Firebase
                                    });
                        }
                    }, throwable -> {
                        // Send error to Firebase
                    });
        }
    }


    private HashMap<String, Object> configureUpdateFirebaseTokenRequestBody(String userId, String token) {
        HashMap<String, Object> reqBody = new HashMap<>();

        reqBody.put("superheroID", userId);
        reqBody.put("token", token);

        return reqBody;
    }
}
