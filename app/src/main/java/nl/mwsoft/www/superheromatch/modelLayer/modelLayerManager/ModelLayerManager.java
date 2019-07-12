package nl.mwsoft.www.superheromatch.modelLayer.modelLayerManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import nl.mwsoft.www.superheromatch.modelLayer.database.matchedUser.MatchedUserDatabaseLayer;
import nl.mwsoft.www.superheromatch.modelLayer.database.user.UserDatabaseLayer;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.dateTimeUtil.DateTimeUtil;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.imageProcessing.ImageProcessingUtil;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.internet.InternetConnectionUtil;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.uuid.UUIDUtil;
import nl.mwsoft.www.superheromatch.modelLayer.model.CheckEmailResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.RegisterResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.RegistrationUser;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.modelLayer.network.NetworkLayer;

public class ModelLayerManager {


    private UserDatabaseLayer userDatabaseLayer;
    private MatchedUserDatabaseLayer matchedUserDatabaseLayer;
    private UUIDUtil uuidUtil;
    private DateTimeUtil dateTimeUtil;
    private ImageProcessingUtil imageProcessingUtil;
    private InternetConnectionUtil internetConnectionUtil;
    private NetworkLayer networkLayer;


    public ModelLayerManager() {
    }

    public ModelLayerManager(InternetConnectionUtil internetConnectionUtil, NetworkLayer networkLayer) {
        this.internetConnectionUtil = internetConnectionUtil;
        this.networkLayer = networkLayer;
    }

    public ModelLayerManager(UserDatabaseLayer userDatabaseLayer, InternetConnectionUtil internetConnectionUtil, NetworkLayer networkLayer) {
        this.userDatabaseLayer = userDatabaseLayer;
        this.internetConnectionUtil = internetConnectionUtil;
        this.networkLayer = networkLayer;
    }

    public ModelLayerManager(UserDatabaseLayer userDatabaseLayer, InternetConnectionUtil internetConnectionUtil, NetworkLayer networkLayer, UUIDUtil uuidUtil) {
        this.userDatabaseLayer = userDatabaseLayer;
        this.internetConnectionUtil = internetConnectionUtil;
        this.networkLayer = networkLayer;
        this.uuidUtil = uuidUtil;
    }

    public ModelLayerManager(UserDatabaseLayer userDatabaseLayer, MatchedUserDatabaseLayer matchedUserDatabaseLayer, UUIDUtil uuidUtil, DateTimeUtil dateTimeUtil, ImageProcessingUtil imageProcessingUtil, InternetConnectionUtil internetConnectionUtil, NetworkLayer networkLayer) {
        this.userDatabaseLayer = userDatabaseLayer;
        this.matchedUserDatabaseLayer = matchedUserDatabaseLayer;
        this.uuidUtil = uuidUtil;
        this.dateTimeUtil = dateTimeUtil;
        this.imageProcessingUtil = imageProcessingUtil;
        this.internetConnectionUtil = internetConnectionUtil;
        this.networkLayer = networkLayer;
    }

    // region User Database Layer

    public String getUserId(Context context) {
        return this.userDatabaseLayer.getUserId(context);
    }

    public String getUserName(Context context) {
        return this.userDatabaseLayer.getUserName(context);
    }

    public String getUserEmail(Context context) {
        return this.userDatabaseLayer.getUserEmail(context);
    }

    public String getUserMainProfilePicUrl(Context context) {
        return this.userDatabaseLayer.getUserMainProfilePicUrl(context);
    }

    public ArrayList<String> getUserMainProfilePicUrls(Context context) {
        return this.userDatabaseLayer.getUserMainProfilePicUrls(context);
    }

    public String getUserGender(Context context) {
        return this.userDatabaseLayer.getUserGender(context);
    }

    public String getUserLookingForGender(Context context) {
        return this.userDatabaseLayer.getUserLookingForGender(context);
    }


    public int getUserAge(Context context) {
        return this.userDatabaseLayer.getUserAge(context);
    }

    public String getUserCountry(Context context) {
        return this.userDatabaseLayer.getUserCountry(context);
    }

    public String getUserCity(Context context) {
        return this.userDatabaseLayer.getUserCity(context);
    }

    public String getUserAbout(Context context) {
        return this.userDatabaseLayer.getUserSuperPower(context);
    }

    public int getUserIsVerified(Context context) {
        return this.userDatabaseLayer.getUserIsVerified(context);
    }

    public int getUserIsLoggedIn(Context context) {
        return this.userDatabaseLayer.getUserIsLoggedIn(context);
    }

    public String getUserCreated(Context context) {
        return this.userDatabaseLayer.getUserCreated(context);
    }

    public String getUserAccountType(Context context) {
        return this.userDatabaseLayer.getUserAccountType(context);
    }

    public void updateInitiallyRegisteredUser(RegistrationUser registrationUser, Context context) {
        this.userDatabaseLayer.saveInitiallyRegisteredUser(registrationUser, context);
    }

    public void updateUserLongitudeAndLatitude(String userId, double lon, double lat, Context context) {
        this.userDatabaseLayer.updateUserLongitudeAndLatitude(userId, lon, lat, context);
    }

    public void updateUserAccountType(String userId, String accountType, Context context) {
        this.userDatabaseLayer.updateUserAccountType(userId, accountType, context);
    }

    public void updateUserMainProfilePic(String userId, String mainProfilePic, Context context) {
        this.userDatabaseLayer.updateUserMainProfilePic(userId, mainProfilePic, context);
    }

    public void insertProfilePic(String userId, String profilePicUri, String profilePicUrl, Context context) {
        this.userDatabaseLayer.insertProfilePic(userId, profilePicUri, profilePicUrl, context);
    }

    // endregion

    // region Matched User Database Layer

    public User getMatchedUserById(Context context, String matchedUserId) {
        return this.matchedUserDatabaseLayer.getMatchedUserById(context, matchedUserId);
    }

    public ArrayList<User> getAllMatchedUsers(String matchedUserID, Context context) {
        return this.matchedUserDatabaseLayer.getAllMatchedUsers(matchedUserID, context);
    }

    public String getMatchedUserNameById(Context context, String matchedUserId) {
        return this.matchedUserDatabaseLayer.getMatchedUserNameByID(context, matchedUserId);
    }

    public String getMatchedUserMainProfilePicUrlById(Context context, String matchedUserId) {
        return this.matchedUserDatabaseLayer.getMatchedUserMainProfilePicUrlByID(context, matchedUserId);
    }

    public ArrayList<String> getMatchedUserProfilePicUrlsById(Context context, String matchedUserId) {
        return this.matchedUserDatabaseLayer.getMatchedUserProfilePicUrlsByID(context, matchedUserId);
    }

    public int getMatchedUserGenderById(Context context, String matchedUserId) {
        return this.matchedUserDatabaseLayer.getMatchedUserGenderByID(context, matchedUserId);
    }

    public int getMatchedUserAge(Context context, String matchedUserId) {
        return this.matchedUserDatabaseLayer.getMatchedUserAge(context, matchedUserId);
    }

    public String getMatchedUserCountry(Context context, String matchedUserId) {
        return this.matchedUserDatabaseLayer.getMatchedUserCountry(context, matchedUserId);
    }

    public String getMatchedUserCity(Context context, String matchedUserId) {
        return this.matchedUserDatabaseLayer.getMatchedUserCity(context, matchedUserId);
    }

    public String getMatchedUserSuperPower(Context context, String matchedUserId) {
        return this.matchedUserDatabaseLayer.getMatchedUserSuperPower(context, matchedUserId);
    }

    public void insertMatchedUser(User user, Context context) {
        this.matchedUserDatabaseLayer.insertMatchedUser(user, context);
    }

    public void insertMatchedUserProfilePic(String userId, String profilePicUrl, Context context) {
        this.matchedUserDatabaseLayer.insertMatchedUserProfilePic(userId, profilePicUrl, context);
    }

    public void insertMatchChat(String matchedUserId, String chatName, Context context) {
        this.matchedUserDatabaseLayer.insertMatchChat(matchedUserId, chatName, context);
    }

    public void insertMatchChatMessage(String chatName, String senderId, String message, String messageUUID, Context context) {
        this.matchedUserDatabaseLayer.insertMatchChatMessage(chatName, senderId, message, messageUUID, context);
    }

    // endregion

    // region Util

    // region Date Time Util

    public String createUUID() {
        return this.uuidUtil.generateUUID();
    }

    public String getDateTime() {
        return this.dateTimeUtil.getDateTime();
    }

    public String convertFromUtcToLocal(String utc){
        return this.dateTimeUtil.convertFromUtcToLocal(utc);
    }

    public String getUtcTime(){
        return this.dateTimeUtil.getUtcTime();
    }

    public String getMessageCreated(String fullDate) {
        return this.dateTimeUtil.getMessageCreated(fullDate);
    }

    // endregion

    // region Image Processing Util

    public File createImageFile(Context context) throws IOException {
        return this.imageProcessingUtil.createImageFile(context);
    }

    public int getRotation(Context context) {
        return this.imageProcessingUtil.getRotation(context);
    }

    public Bitmap rotateImageIfRequired(Context context, Bitmap img) {
        return this.imageProcessingUtil.rotateImageIfRequired(context, img);
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        return this.imageProcessingUtil.calculateInSampleSize(options, reqWidth, reqHeight);
    }

    public Bitmap decodeSampledBitmap(Context context, Uri selectedImage) throws IOException {
        return this.imageProcessingUtil.decodeSampledBitmap(context, selectedImage);
    }

    public Bitmap decodeImage(String data) {
        return this.imageProcessingUtil.decodeImage(data);
    }

    public Uri saveIncomingImageMessage(Context context, Bitmap inImage) {
        return this.imageProcessingUtil.saveIncomingImageMessage(context, inImage);
    }

    public String encodeImageToString(Context context, Uri imageUrl) throws IOException {
        return this.imageProcessingUtil.encodeImageToString(context, imageUrl);
    }

    // endregion

    // region Util

    public boolean hasInternetConnection() {
        return this.internetConnectionUtil.hasInternetConnection();
    }

    public String getUUID() {
        return this.uuidUtil.generateUUID();
    }

    // endregion

    // endregion

    // region Network

    // region Check If Email Already Exists

    public Observable<CheckEmailResponse> checkEmailAlreadyExists(String email) {
        return this.networkLayer.checkEmailAlreadyExists(email);
    }

    // endregion

    // region Save User's Choice

    public Observable<String> saveChoice(String userId, String chosenUserId, String choiceType) {
        return this.networkLayer.saveChoice(userId, chosenUserId, choiceType);
    }

    // endregion

    // region Confirm User's Phone


    // endregion

    // region Delete User's Account

    public Observable<String> deleteAccount(String userId){
        return this.networkLayer.deleteAccount(userId);
    }

    // endregion

    // region Register

    public Observable<RegisterResponse> register(HashMap<String, Object> body){
        return this.networkLayer.register(body);
    }

    // endregion

    // region Retrieve Suggestions

    public Observable<String> getSuggestions(String body){
        return this.networkLayer.getSuggestions(body);
    }

    // endregion

    // region Update User's Firebase Messaging Token

    public Observable<String> getUpdateUserTokenResponse(String userId, String messagingToken) {
        return this.networkLayer.getUpdateUserTokenResponse(userId, messagingToken);
    }

    // endregion

    // region Update User's Data

    public Observable<String> updateUserData(String toDo) {
        return this.networkLayer.updateUserData(toDo);
    }

    // endregion

    // region Upgrade User's Account

    public Observable<String> upgradeAccount(String userId, String upgradedAccountType) {
        return this.networkLayer.upgradeAccount(userId, upgradedAccountType);
    }

    // endregion

    // endregion
}
