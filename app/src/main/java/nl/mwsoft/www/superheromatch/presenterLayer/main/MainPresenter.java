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
import nl.mwsoft.www.superheromatch.modelLayer.model.Chat;
import nl.mwsoft.www.superheromatch.modelLayer.model.ChoiceResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.Message;
import nl.mwsoft.www.superheromatch.modelLayer.model.ProfileResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.RegistrationUser;
import nl.mwsoft.www.superheromatch.modelLayer.model.SuggestionsResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.UpdateResponse;
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

    public String getUserBirthday(Context context) {
        return this.modelLayerManager.getUserBirthday(context);
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

    public String getUserSuperPower(Context context) {
        return this.modelLayerManager.getUserSuperPower(context);
    }

    public void updateUserDistanceUnit(String userID, String distanceUnit, Context context) {
        this.modelLayerManager.updateUserDistanceUnit(userID, distanceUnit, context);
    }

    public void updateUserSuperPower(String userID, String superPower, Context context) {
        this.modelLayerManager.updateUserSuperPower(userID, superPower, context);
    }

    public void updateInitiallyRegisteredUser(RegistrationUser registrationUser, Context context) {
        this.modelLayerManager.updateInitiallyRegisteredUser(registrationUser, context);
    }

    public void updateUserLongitudeAndLatitude(String userId, double lat, double lon, Context context) {
        this.modelLayerManager.updateUserLongitudeAndLatitude(userId, lat, lon, context);
    }

    public void updateUserLookingForAgeRange(String userID, int ageMin, int ageMax, Context context) {
        this.modelLayerManager.updateUserLookingForAgeRange(userID, ageMin, ageMax, context);
    }

    public void updateUserLookingForMaxDistance(String userID, int maxDistance, Context context) {
        this.modelLayerManager.updateUserLookingForMaxDistance(userID, maxDistance, context);
    }

    public void updateUserLookingForGender(String userID, int gender, Context context) {
        this.modelLayerManager.updateUserLookingForGender(userID, gender, context);
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

    // region Chat Database Layer

    public void updateMessageHasBeenReadByMessageId(int messageId, Context context){
        this.modelLayerManager.updateMessageHasBeenReadByMessageId(messageId, context);
    }

    public int getUnreadMessageCountByChatId(Context context, String chatId) {
        return this.modelLayerManager.getUnreadMessageCountByChatId(context, chatId);
    }

    public Message getLastChatMessageByChatId(Context context, String chatId) {
        return this.modelLayerManager.getLastChatMessageByChatId(context, chatId);
    }

    public ArrayList<Message> getAllMessagesForChatWithId(Context context, String chatId) {
        return this.modelLayerManager.getAllMessagesForChatWithId(context, chatId);
    }

    public String getChatIdByChatName(Context context, String chatName) {
        return this.modelLayerManager.getChatIdByChatName(context, chatName);
    }

    public ArrayList<Chat> getAllChats(Context context) {
        return this.modelLayerManager.getAllChats(context);
    }

    public Chat getChatByContactId(Context context, String matchName) {
        return this.modelLayerManager.getChatByContactId(context, matchName);
    }

    public Chat getChatById(Context context, String chatId) {
        return this.modelLayerManager.getChatById(context, chatId);
    }

    public void deleteChatMessageById(int messageId, Context context){
        this.modelLayerManager.deleteChatMessageById(messageId, context);
    }

    public void deleteChatMessageBySenderId(String senderId, Context context){
        this.modelLayerManager.deleteChatMessageBySenderId(senderId, context);
    }

    public void insertChat(String chatId, String matchName, String matchedUserId, String chatProfilePic, Context context) {
        this.modelLayerManager.insertChat(chatId, matchName, matchedUserId, chatProfilePic, context);
    }

    public Message getChatMessageByUUID(Context context, String uuid) {
        return this.modelLayerManager.getChatMessageByUUID(context, uuid);
    }

    public void insertChatMessage(Message chatMessage, Context context) {
        this.modelLayerManager.insertChatMessage(chatMessage, context);
    }

    public void deleteChatById(String chatId, Context context){
        this.modelLayerManager.deleteChatById(chatId, context);
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

    // region Upload Choice

    public Observable<ChoiceResponse> uploadChoice(HashMap<String, Object> body){
        return this.modelLayerManager.uploadChoice(body);
    }

    // endregion

    // region Upload Match

    public Observable<Integer> uploadMatch(HashMap<String, Object> body){
        return this.modelLayerManager.uploadMatch(body);
    }

    // endregion

    // region Delete Match

    public Observable<Integer> deleteMatch(HashMap<String, Object> body){
        return this.modelLayerManager.deleteMatch(body);
    }

    // endregion

    // region Get Suggestion Profile

    public Observable<ProfileResponse> getSuggestionProfile(HashMap<String, Object> body){
        return this.modelLayerManager.getSuggestionProfile(body);
    }

    // endregion

    // region Update User's Firebase Messaging Token

    public Observable<String> getUpdateUserTokenResponse(String userId, String messagingToken) {
        return this.modelLayerManager.getUpdateUserTokenResponse(userId, messagingToken);
    }

    // endregion

    // region Update User's Data

    public Observable<UpdateResponse> updateProfile(HashMap<String, Object> body) {
        return this.modelLayerManager.updateProfile(body);
    }

    // endregion

    // region Upgrade User's Account

    public Observable<String> upgradeAccount(String userId, String upgradedAccountType) {
        return this.modelLayerManager.upgradeAccount(userId, upgradedAccountType);
    }

    // endregion

    // endregion

}
