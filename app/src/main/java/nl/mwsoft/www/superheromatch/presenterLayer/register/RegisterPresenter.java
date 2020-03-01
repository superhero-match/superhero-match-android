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
package nl.mwsoft.www.superheromatch.presenterLayer.register;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import nl.mwsoft.www.superheromatch.modelLayer.model.CheckEmailResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.RegisterResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.RegistrationUser;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.modelLayer.modelLayerManager.ModelLayerManager;
import nl.mwsoft.www.superheromatch.viewLayer.register.activity.RegisterActivity;

public class RegisterPresenter {

    private ModelLayerManager modelLayerManager;

    public RegisterPresenter() {
    }

    public RegisterPresenter(ModelLayerManager modelLayerManager) {
        this.modelLayerManager = modelLayerManager;
    }

    // region User Database Layer

    public String getUserId(Context context) {
        return this.modelLayerManager.getUserId(context);
    }

    public String getUserName(Context context) {
        return this.modelLayerManager.getUserName(context);
    }

    public String getUserEmail(Context context) {
        return this.modelLayerManager.getUserEmail(context);
    }

    public String getUserMainProfilePicUrl(Context context) {
        return this.modelLayerManager.getUserMainProfilePicUrl(context);
    }

    public ArrayList<String> getUserMainProfilePicUrls(Context context) {
        return this.modelLayerManager.getUserMainProfilePicUrls(context);
    }

    public int getUserGender(Context context) {
        return this.modelLayerManager.getUserGender(context);
    }

    public int getUserLookingForGender(Context context) {
        return this.modelLayerManager.getUserLookingForGender(context);
    }

    public int getUserAge(Context context) {
        return this.modelLayerManager.getUserAge(context);
    }

    public String getUserCountry(Context context) {
        return this.modelLayerManager.getUserCountry(context);
    }

    public String getUserCity(Context context) {
        return this.modelLayerManager.getUserCity(context);
    }

    public String getUserAbout(Context context) {
        return this.modelLayerManager.getUserAbout(context);
    }

    public int getUserIsVerified(Context context) {
        return this.modelLayerManager.getUserIsVerified(context);
    }

    public int getUserIsLoggedIn(Context context) {
        return this.modelLayerManager.getUserIsLoggedIn(context);
    }

    public String getUserCreated(Context context) {
        return this.modelLayerManager.getUserCreated(context);
    }

    public String getUserAccountType(Context context) {
        return this.modelLayerManager.getUserAccountType(context);
    }

    public void updateInitiallyRegisteredUser(User registrationUser, Context context) {
        this.modelLayerManager.updateInitiallyRegisteredUser(registrationUser, context);
    }

    public void updateUserLongitudeAndLatitude(String userId, double lon, double lat, Context context) {
        this.modelLayerManager.updateUserLongitudeAndLatitude(userId, lon, lat, context);
    }

    public void updateUserAccountType(String userId, String accountType, Context context) {
        this.modelLayerManager.updateUserAccountType(userId, accountType, context);
    }

    public void updateUserMainProfilePic(String userId, String mainProfilePic, Context context) {
        this.modelLayerManager.updateUserMainProfilePic(userId, mainProfilePic, context);
    }

    public void insertProfilePic(String userId, String profilePicUri, String profilePicUrl, Context context) {
        this.modelLayerManager.insertProfilePic(userId, profilePicUri, profilePicUrl, context);
    }

    // endregion

    // region Network

    // region Register

    public Observable<RegisterResponse> register(HashMap<String, Object> body){
        return this.modelLayerManager.register(body);
    }

    // endregion

    // endregion

    // region Internet Util

    public boolean hasInternetConnection() {
        return this.modelLayerManager.hasInternetConnection();
    }

    public String getUUID() {
        return this.modelLayerManager.getUUID();
    }

    // endregion
}
