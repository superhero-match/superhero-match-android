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
package nl.mwsoft.www.superheromatch.presenterLayer.main;


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
import nl.mwsoft.www.superheromatch.modelLayer.model.Chat;
import nl.mwsoft.www.superheromatch.modelLayer.model.Choice;
import nl.mwsoft.www.superheromatch.modelLayer.model.ChoiceResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.DeleteAccountResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.Message;
import nl.mwsoft.www.superheromatch.modelLayer.model.OfflineMessagesResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.ProfileResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.RegistrationUser;
import nl.mwsoft.www.superheromatch.modelLayer.model.SuggestionsResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.TokenResponse;
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

    public void updateMessageHasBeenReadByMessageId(int messageId, Context context) {
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

    public Chat getChatByMatchId(Context context, String matchId) {
        return this.modelLayerManager.getChatByMatchId(context, matchId);
    }

    public Chat getChatById(Context context, String chatId) {
        return this.modelLayerManager.getChatById(context, chatId);
    }

    public void deleteChatMessageById(int messageId, Context context) {
        this.modelLayerManager.deleteChatMessageById(messageId, context);
    }

    public void deleteChatMessageBySenderId(String senderId, Context context) {
        this.modelLayerManager.deleteChatMessageBySenderId(senderId, context);
    }

    public void insertChat(String chatId, String matchName, String matchedUserId, String chatProfilePic, Context context) {
        this.modelLayerManager.insertChat(chatId, matchName, matchedUserId, chatProfilePic, context);
    }

    public void insertChatMessage(Message chatMessage, int messageHasBeenRead, Context context) {
        this.modelLayerManager.insertChatMessage(chatMessage, messageHasBeenRead, context);
    }

    public void deleteChatById(String chatId, Context context) {
        this.modelLayerManager.deleteChatById(chatId, context);
    }

    public ArrayList<Choice> getAllChoices(Context context) {
        return this.modelLayerManager.getAllChoices(context);
    }

    public void insertChoice(String chosenUserId, int choice, String createdAt, Context context) {
        this.modelLayerManager.insertChoice(chosenUserId, choice, createdAt, context);
    }

    public void deleteChoice(int id, Context context) {
        this.modelLayerManager.deleteChoice(id, context);
    }

    public ArrayList<String> getAllReportedUsers(Context context) {
        return this.modelLayerManager.getAllReportedUsers(context);
    }

    public void insertReportedUser(String reportedUserId, Context context) {
        this.modelLayerManager.insertReportedUser(reportedUserId, context);
    }

    public void insertDefaultUser(Context context) {
        this.modelLayerManager.insertDefaultUser(context);
    }

    public void deleteDataFromAllTables(Context context) {
        this.modelLayerManager.deleteDataFromAllTables(context);
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

    public String convertFromUtcToLocal(String utc) {
        return this.modelLayerManager.convertFromUtcToLocal(utc);
    }

    public String getUtcTime() {
        return this.modelLayerManager.getUtcTime();
    }

    public String getMessageCreated(String fullDate) {
        return this.modelLayerManager.getMessageCreated(fullDate);
    }

    public boolean isOlderThanOneDay(Date startDate, Date endDate) {
        return this.modelLayerManager.isOlderThanOneDay(startDate, endDate);
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

    // region Delete User's Account

    public Observable<DeleteAccountResponse> deleteAccount(HashMap<String, Object> body, Context context){
        return this.modelLayerManager.deleteAccount(body, context);
    }

    // endregion

    // region Retrieve Suggestions

    public Observable<SuggestionsResponse> getSuggestions(HashMap<String, Object> body, Context context) {
        return this.modelLayerManager.getSuggestions(body, context);
    }

    // endregion

    // region Upload Choice

    public Observable<ChoiceResponse> uploadChoice(HashMap<String, Object> body, Context context) {
        return this.modelLayerManager.uploadChoice(body, context);
    }

    // endregion

    // region Upload Match

    public Observable<Integer> uploadMatch(HashMap<String, Object> body, Context context) {
        return this.modelLayerManager.uploadMatch(body, context);
    }

    // endregion

    // region Delete Match

    public Observable<Integer> deleteMatch(HashMap<String, Object> body, Context context) {
        return this.modelLayerManager.deleteMatch(body, context);
    }

    // endregion

    // region Update Firebase Messaging Token

    public Observable<Integer> updateFirebaseToken(HashMap<String, Object> body, Context context) {
        return this.modelLayerManager.updateFirebaseToken(body, context);
    }

    // endregion

    // region Get Suggestion Profile

    public Observable<ProfileResponse> getSuperheroProfile(HashMap<String, Object> body, Context context) {
        return this.modelLayerManager.getSuperheroProfile(body, context);
    }

    // endregion

    // region Update User's Data

    public Observable<UpdateResponse> updateProfile(HashMap<String, Object> body, Context context) {
        return this.modelLayerManager.updateProfile(body, context);
    }

    // endregion

    // region Get Offline Messages

    public Observable<OfflineMessagesResponse> getOfflineMessages(HashMap<String, Object> body, Context context) {
        return this.modelLayerManager.getOfflineMessages(body, context);
    }

    // endregion

    // region Delete Profile Picture

    public Observable<Integer> deleteProfilePicture(HashMap<String, Object> body, Context context) {
        return this.modelLayerManager.deleteProfilePicture(body, context);
    }

    // endregion

    // region Report User

    public Observable<Integer> reportUser(HashMap<String, Object> body, Context context){
        return this.modelLayerManager.reportUser(body, context);
    }

    // endregion

    // region Token

    public Observable<TokenResponse> getToken(HashMap<String, Object> body) {
        return this.modelLayerManager.getToken(body);
    }

    // endregion

    // endregion

}
