package nl.mwsoft.www.superheromatch.presenterLayer.main;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import nl.mwsoft.www.superheromatch.modelLayer.model.RegistrationUser;
import nl.mwsoft.www.superheromatch.modelLayer.model.SuggestionsResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.modelLayer.modelLayerManager.ModelLayerManager;

public class MainPresenter {

    private ModelLayerManager modelLayerManager;


    public MainPresenter() {
    }

    public MainPresenter(ModelLayerManager modelLayerManager) {
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

    public int getUserLookingForMinAge(Context context) {
        return this.modelLayerManager.getUserLookingForMinAge(context);
    }

    public int getUserLookingForMaxAge(Context context) {
        return this.modelLayerManager.getUserLookingForMaxAge(context);
    }

    public int getUserLookingForMaxDistance(Context context) {
        return this.modelLayerManager.getUserLookingForMaxDistance(context);
    }

    public String getUserDistanceUnit(Context context) {
        return this.modelLayerManager.getUserDistanceUnit(context);
    }

    public double getUserLat(Context context) {
        return this.modelLayerManager.getUserLat(context);
    }

    public double getUserLon(Context context) {
        return this.modelLayerManager.getUserLon(context);
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

    public void updateInitiallyRegisteredUser(RegistrationUser registrationUser, Context context) {
        this.modelLayerManager.updateInitiallyRegisteredUser(registrationUser, context);
    }

    public void updateUserLongitudeAndLatitude(String userId, double lon, double lat, Context context) {
        this.modelLayerManager.updateUserLongitudeAndLatitude(userId, lon, lat, context);
    }

    public void updateUserCountryAndCity(String userID, String country, String city, Context context) {
        this.modelLayerManager.updateUserCountryAndCity(userID, country, city, context);
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

    // region Matched User Database Layer

    public User getMatchedUserById(Context context, String matchedUserId) {
        return this.modelLayerManager.getMatchedUserById(context, matchedUserId);
    }

    public ArrayList<User> getAllMatchedUsers(String matchedUserID, Context context) {
        return this.modelLayerManager.getAllMatchedUsers(matchedUserID, context);
    }

    public String getMatchedUserNameById(Context context, String matchedUserId) {
        return this.modelLayerManager.getMatchedUserNameById(context, matchedUserId);
    }

    public String getMatchedUserMainProfilePicUrlById(Context context, String matchedUserId) {
        return this.modelLayerManager.getMatchedUserMainProfilePicUrlById(context, matchedUserId);
    }

    public ArrayList<String> getMatchedUserProfilePicUrlsById(Context context, String matchedUserId) {
        return this.modelLayerManager.getMatchedUserProfilePicUrlsById(context, matchedUserId);
    }

    public int getMatchedUserGenderById(Context context, String matchedUserId) {
        return this.modelLayerManager.getMatchedUserGenderById(context, matchedUserId);
    }

    public int getMatchedUserAge(Context context, String matchedUserId) {
        return this.modelLayerManager.getMatchedUserAge(context, matchedUserId);
    }

    public String getMatchedUserCountry(Context context, String matchedUserId) {
        return this.modelLayerManager.getMatchedUserCountry(context, matchedUserId);
    }

    public String getMatchedUserCity(Context context, String matchedUserId) {
        return this.modelLayerManager.getMatchedUserCity(context, matchedUserId);
    }

    public String getMatchedUserAbout(Context context, String matchedUserId) {
        return this.modelLayerManager.getMatchedUserSuperPower(context, matchedUserId);
    }

    public void insertMatchedUser(User user, Context context) {
        this.modelLayerManager.insertMatchedUser(user, context);
    }

    public void insertMatchedUserProfilePic(String userId, String profilePicUrl, Context context) {
        this.modelLayerManager.insertMatchedUserProfilePic(userId, profilePicUrl, context);
    }

    public void insertMatchChat(String matchedUserId, String chatName, Context context) {
        this.modelLayerManager.insertMatchChat(matchedUserId, chatName, context);
    }

    public void insertMatchChatMessage(String chatName, String senderId, String message, String messageUUID, Context context) {
        this.modelLayerManager.insertMatchChatMessage(chatName, senderId, message, messageUUID, context);
    }

    // endregion

    // region Util

    // region Date Time Util

    public String createUUID() {
        return this.modelLayerManager.createUUID();
    }

    public String getDateTime() {
        return this.modelLayerManager.getDateTime();
    }

    public String convertFromUtcToLocal(String utc){
        return this.modelLayerManager.convertFromUtcToLocal(utc);
    }

    public String getUtcTime(){
        return this.modelLayerManager.getUtcTime();
    }

    public String getMessageCreated(String fullDate) {
        return this.modelLayerManager.getMessageCreated(fullDate);
    }

    // endregion

    // region Image Processing Util

    public File createImageFile(Context context) throws IOException {
        return this.modelLayerManager.createImageFile(context);
    }

    public int getRotation(Context context) {
        return this.modelLayerManager.getRotation(context);
    }

    public Bitmap rotateImageIfRequired(Context context, Bitmap img) {
        return this.modelLayerManager.rotateImageIfRequired(context, img);
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        return this.modelLayerManager.calculateInSampleSize(options, reqWidth, reqHeight);
    }

    public Bitmap decodeSampledBitmap(Context context, Uri selectedImage) throws IOException {
        return this.modelLayerManager.decodeSampledBitmap(context, selectedImage);
    }

    public Bitmap decodeImage(String data) {
        return this.modelLayerManager.decodeImage(data);
    }

    public Uri saveIncomingImageMessage(Context context, Bitmap inImage) {
        return this.modelLayerManager.saveIncomingImageMessage(context, inImage);
    }

    public String encodeImageToString(Context context, Uri imageUrl) throws IOException {
        return this.modelLayerManager.encodeImageToString(context, imageUrl);
    }

    // endregion

    // region Internet Util

    public boolean hasInternetConnection() {
        return this.modelLayerManager.hasInternetConnection();
    }

    // endregion

    // endregion

    // region Network

    // region Save User's Choice

    public Observable<String> saveChoice(String userId, String chosenUserId, String choiceType) {
        return this.modelLayerManager.saveChoice(userId, chosenUserId, choiceType);
    }

    // endregion

    // region Delete User's Account

    public Observable<String> deleteAccount(String userId){
        return this.modelLayerManager.deleteAccount(userId);
    }

    // endregion

    // region Retrieve Suggestions

    public Observable<SuggestionsResponse> getSuggestions(HashMap<String, Object> body){
        return this.modelLayerManager.getSuggestions(body);
    }

    // endregion

    // region Update User's Firebase Messaging Token

    public Observable<String> getUpdateUserTokenResponse(String userId, String messagingToken) {
        return this.modelLayerManager.getUpdateUserTokenResponse(userId, messagingToken);
    }

    // endregion

    // region Update User's Data

    public Observable<String> updateUserData(String body) {
        return this.modelLayerManager.updateUserData(body);
    }

    // endregion

    // region Upgrade User's Account

    public Observable<String> upgradeAccount(String userId, String upgradedAccountType) {
        return this.modelLayerManager.upgradeAccount(userId, upgradedAccountType);
    }

    // endregion

    // endregion

}
