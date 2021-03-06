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
package nl.mwsoft.www.superheromatch.modelLayer.modelLayerManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.Observable;
import nl.mwsoft.www.superheromatch.modelLayer.database.chat.ChatDatabaseLayer;
import nl.mwsoft.www.superheromatch.modelLayer.database.user.UserDatabaseLayer;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.dateTimeUtil.DateTimeUtil;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.imageProcessing.ImageProcessingUtil;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.internet.InternetConnectionUtil;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.uuid.UUIDUtil;
import nl.mwsoft.www.superheromatch.modelLayer.model.Chat;
import nl.mwsoft.www.superheromatch.modelLayer.model.CheckEmailResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.Choice;
import nl.mwsoft.www.superheromatch.modelLayer.model.ChoiceResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.DeleteAccountResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.Message;
import nl.mwsoft.www.superheromatch.modelLayer.model.OfflineMessagesResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.ProfileResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.RegisterResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.StatusResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.SuggestionsResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.TokenResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.UpdateResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.modelLayer.network.NetworkLayer;

public class ModelLayerManager {


    private UserDatabaseLayer userDatabaseLayer;
    private ChatDatabaseLayer chatDatabaseLayer;
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

    public ModelLayerManager(UserDatabaseLayer userDatabaseLayer, UUIDUtil uuidUtil, DateTimeUtil dateTimeUtil, ImageProcessingUtil imageProcessingUtil, InternetConnectionUtil internetConnectionUtil, NetworkLayer networkLayer) {
        this.userDatabaseLayer = userDatabaseLayer;
        this.uuidUtil = uuidUtil;
        this.dateTimeUtil = dateTimeUtil;
        this.imageProcessingUtil = imageProcessingUtil;
        this.internetConnectionUtil = internetConnectionUtil;
        this.networkLayer = networkLayer;
    }

    public ModelLayerManager(UserDatabaseLayer userDatabaseLayer, ChatDatabaseLayer chatDatabaseLayer, UUIDUtil uuidUtil, DateTimeUtil dateTimeUtil, ImageProcessingUtil imageProcessingUtil, InternetConnectionUtil internetConnectionUtil, NetworkLayer networkLayer) {
        this.userDatabaseLayer = userDatabaseLayer;
        this.chatDatabaseLayer = chatDatabaseLayer;
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

    public int getUserGender(Context context) {
        return this.userDatabaseLayer.getUserGender(context);
    }

    public int getUserLookingForGender(Context context) {
        return this.userDatabaseLayer.getUserLookingForGender(context);
    }

    public int getUserAge(Context context) {
        return this.userDatabaseLayer.getUserAge(context);
    }

    public String getUserBirthday(Context context) {
        return this.userDatabaseLayer.getUserBirthday(context);
    }

    public int getUserLookingForMinAge(Context context) {
        return this.userDatabaseLayer.getUserLookingForMinAge(context);
    }

    public int getUserLookingForMaxAge(Context context) {
        return this.userDatabaseLayer.getUserLookingForMaxAge(context);
    }

    public int getUserLookingForMaxDistance(Context context) {
        return this.userDatabaseLayer.getUserLookingForMaxDistance(context);
    }

    public String getUserDistanceUnit(Context context) {
        return this.userDatabaseLayer.getUserDistanceUnit(context);
    }

    public String getUserCountry(Context context) {
        return this.userDatabaseLayer.getUserCountry(context);
    }

    public String getUserCity(Context context) {
        return this.userDatabaseLayer.getUserCity(context);
    }

    public double getUserLat(Context context) {
        return this.userDatabaseLayer.getUserLat(context);
    }

    public double getUserLon(Context context) {
        return this.userDatabaseLayer.getUserLon(context);
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

    public String getUserSuperPower(Context context) {
        return this.userDatabaseLayer.getUserSuperPower(context);
    }

    public void updateInitiallyRegisteredUser(User registrationUser, Context context) {
        this.userDatabaseLayer.saveInitiallyRegisteredUser(registrationUser, context);
    }

    public void saveUserToDB(User user, Context context) {
        this.userDatabaseLayer.saveUserToDB(user, context);
    }

    public void updateUserLongitudeAndLatitude(String userId, double lat, double lon, Context context) {
        this.userDatabaseLayer.updateUserLongitudeAndLatitude(userId, lat, lon, context);
    }

    public void updateUserLookingForAgeRange(String userID, int ageMin, int ageMax, Context context) {
        this.userDatabaseLayer.updateUserLookingForAgeRange(userID, ageMin, ageMax, context);
    }

    public void updateUserLookingForMaxDistance(String userID, int maxDistance, Context context) {
        this.userDatabaseLayer.updateUserLookingForMaxDistance(userID, maxDistance, context);
    }

    public void updateUserLookingForGender(String userID, int gender, Context context) {
        this.userDatabaseLayer.updateUserLookingForGender(userID, gender, context);
    }

    public void updateUserCountryAndCity(String userID, String country, String city, Context context) {
        this.userDatabaseLayer.updateUserCountryAndCity(userID, country, city, context);
    }

    public void updateUserAccountType(String userId, String accountType, Context context) {
        this.userDatabaseLayer.updateUserAccountType(userId, accountType, context);
    }

    public void updateUserDistanceUnit(String userID, String distanceUnit, Context context) {
        this.userDatabaseLayer.updateUserDistanceUnit(userID, distanceUnit, context);
    }

    public void updateUserSuperPower(String userID, String superPower, Context context) {
        this.userDatabaseLayer.updateUserSuperPower(userID, superPower, context);
    }

    public void updateUserMainProfilePic(String userId, String mainProfilePic, Context context) {
        this.userDatabaseLayer.updateUserMainProfilePic(userId, mainProfilePic, context);
    }

    public void insertProfilePic(String userId, String profilePicUri, String profilePicUrl, Context context) {
        this.userDatabaseLayer.insertProfilePic(userId, profilePicUri, profilePicUrl, context);
    }

    // endregion


    // region Chat Database Layer

    public void updateMessageHasBeenReadByMessageId(int messageId, Context context){
        this.chatDatabaseLayer.updateMessageHasBeenReadByMessageId(messageId, context);
    }

    public int getUnreadMessageCountByChatId(Context context, String chatId) {
        return this.chatDatabaseLayer.getUnreadMessageCountByChatId(context, chatId);
    }

    public Message getLastChatMessageByChatId(Context context, String chatId) {
        return this.chatDatabaseLayer.getLastChatMessageByChatId(context, chatId);
    }

    public ArrayList<Message> getAllMessagesForChatWithId(Context context, String chatId) {
        return this.chatDatabaseLayer.getAllMessagesForChatWithId(context, chatId);
    }

    public String getChatIdByChatName(Context context, String chatName) {
        return this.chatDatabaseLayer.getChatIdByChatName(context, chatName);
    }

    public ArrayList<Chat> getAllChats(Context context) {
        return this.chatDatabaseLayer.getAllChats(context);
    }

    public Chat getChatByMatchId(Context context, String matchId) {
        return this.chatDatabaseLayer.getChatByMatchId(context, matchId);
    }

    public Chat getChatById(Context context, String chatId) {
        return this.chatDatabaseLayer.getChatById(context, chatId);
    }

    public void deleteChatMessageById(int messageId, Context context){
        this.chatDatabaseLayer.deleteChatMessageById(messageId, context);
    }

    public void deleteChatMessageBySenderId(String senderId, Context context){
        this.chatDatabaseLayer.deleteChatMessageBySenderId(senderId, context);
    }

    public void insertChat(String chatId, String matchName, String matchedUserId, String chatProfilePic, Context context) {
        this.chatDatabaseLayer.insertChat(chatId, matchName, matchedUserId, chatProfilePic, context);
    }

    public void insertChatMessage(Message chatMessage, int messageHasBeenRead, Context context) {
        this.chatDatabaseLayer.insertChatMessage(chatMessage, messageHasBeenRead, context);
    }

    public void deleteChatById(String chatId, Context context){
        this.chatDatabaseLayer.deleteChatById(chatId, context);
    }

    public ArrayList<Choice> getAllChoices(Context context) {
        return this.userDatabaseLayer.getAllChoices(context);
    }

    public void insertChoice(String chosenUserId, int choice, String createdAt, Context context) {
        this.userDatabaseLayer.insertChoice(chosenUserId, choice, createdAt, context);
    }

    public void deleteChoice(int id, Context context) {
        this.userDatabaseLayer.deleteChoice(id, context);
    }

    public ArrayList<String> getAllReportedUsers(Context context) {
        return this.userDatabaseLayer.getAllReportedUsers(context);
    }

    public void insertReportedUser(String reportedUserId, Context context) {
        this.userDatabaseLayer.insertReportedUser(reportedUserId, context);
    }

    public void insertDefaultUser(Context context) {
        this.userDatabaseLayer.insertDefaultUser(context);
    }

    public void deleteDataFromAllTables(Context context) {
        this.userDatabaseLayer.deleteDataFromAllTables(context);
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

    public boolean isOlderThanOneDay(Date startDate, Date endDate) {
        return this.dateTimeUtil.isOlderThanOneDay(startDate, endDate);
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

    // region Delete User's Account

    public Observable<DeleteAccountResponse> deleteAccount(HashMap<String, Object> body, Context context){
        return this.networkLayer.deleteAccount(body, context);
    }

    // endregion

    // region Register

    public Observable<RegisterResponse> register(HashMap<String, Object> body, Context context){
        return this.networkLayer.register(body, context);
    }

    // endregion

    // region Retrieve Suggestions

    public Observable<SuggestionsResponse> getSuggestions(HashMap<String, Object> body, Context context){
        return this.networkLayer.getSuggestions(body, context);
    }

    // endregion

    // region Upload Choice

    public Observable<ChoiceResponse> uploadChoice(HashMap<String, Object> body, Context context){
        return this.networkLayer.uploadChoice(body, context);
    }

    // endregion

    // region Upload Match

    public Observable<StatusResponse> uploadMatch(HashMap<String, Object> body, Context context){
        return this.networkLayer.uploadMatch(body, context);
    }

    // endregion

    // region Delete Match

    public Observable<StatusResponse> deleteMatch(HashMap<String, Object> body, Context context){
        return this.networkLayer.deleteMatch(body, context);
    }

    // endregion

    // region Update Firebase Messaging Token

    public Observable<StatusResponse> updateFirebaseToken(HashMap<String, Object> body, Context context){
        return this.networkLayer.updateFirebaseToken(body, context);
    }

    // endregion

    // region Get Suggestion Profile

    public Observable<ProfileResponse> getSuperheroProfile(HashMap<String, Object> body, Context context){
        return this.networkLayer.getSuperheroProfile(body, context);
    }

    // endregion

    // region Update User's Data

    public Observable<UpdateResponse> updateProfile(HashMap<String, Object> body, Context context) {
        return this.networkLayer.updateProfile(body, context);
    }

    // endregion

    // region Get Offline Messages

    public Observable<OfflineMessagesResponse> getOfflineMessages(HashMap<String, Object> body, Context context){
        return this.networkLayer.getOfflineMessages(body, context);
    }

    // endregion

    // region Delete Profile Picture

    public Observable<StatusResponse> deleteProfilePicture(HashMap<String, Object> body, Context context){
        return this.networkLayer.deleteProfilePicture(body, context);
    }

    // endregion

    // region Report User

    public Observable<StatusResponse> reportUser(HashMap<String, Object> body, Context context){
        return this.networkLayer.reportUser(body, context);
    }

    // endregion

    // region Token

    public Observable<TokenResponse> getToken(HashMap<String, Object> body) {
        return this.networkLayer.getToken(body);
    }

    // endregion

    // region RefreshToken

    public Observable<TokenResponse> refreshToken(HashMap<String, Object> body) {
        return this.networkLayer.refreshToken(body);
    }

    // endregion

    // endregion
}
