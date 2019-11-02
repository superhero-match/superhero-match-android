package nl.mwsoft.www.superheromatch.modelLayer.database.matchedUser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;

import nl.mwsoft.www.superheromatch.modelLayer.database.dbhelper.DBOpenHelper;
import nl.mwsoft.www.superheromatch.modelLayer.database.provider.SuperHeroMatchProvider;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;

public class MatchedUserDatabaseLayer {

    public MatchedUserDatabaseLayer() {
    }

    public User getMatchedUserById(Context context, String matchedUserId) {
        User matchedUser = null;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_MATCHED_USER
                + " WHERE " + DBOpenHelper.MATCHED_USER_ID + "=" + matchedUserId, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                matchedUser = new User();
                matchedUser.setId(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_ID)));
                matchedUser.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_NAME)));
                matchedUser.setMainProfilePicUrl(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_MAIN_PROFILE_PIC_URL)));
                matchedUser.setProfilePicsUrls(getMatchedUserProfilePicUrlsByID(context, matchedUserId));
                matchedUser.setGender(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_GENDER))));
                matchedUser.setAge(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_AGE))));
                matchedUser.setCountry(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_COUNTRY)));
                matchedUser.setCity(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_CITY)));
                matchedUser.setSuperPower(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_SUPER_POWER)));
                matchedUser.setAccountType(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_ACCOUNT_TYPE)));
            }
        }

        cursor.close();
        db.close();

        return matchedUser;
    }

    public ArrayList<User> getAllMatchedUsers(String userID, Context context) {
        User matchedUser = null;
        ArrayList<User> matchedUsers = new ArrayList<>();
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_MATCHED_USER + " WHERE " +
                DBOpenHelper.MATCHED_WITH_USER_ID + "='" + userID + "'", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                matchedUser = new User();
                matchedUser.setId(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_ID)));
                matchedUser.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_NAME)));
                matchedUser.setMainProfilePicUrl(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_MAIN_PROFILE_PIC_URL)));
                matchedUser.setProfilePicsUrls(getMatchedUserProfilePicUrlsByID(context, matchedUser.getId()));
                matchedUser.setGender(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_GENDER))));
                matchedUser.setAge(Integer.parseInt(String.valueOf(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_AGE))))));
                matchedUser.setCountry(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_COUNTRY)));
                matchedUser.setCity(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_CITY)));
                matchedUser.setSuperPower(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_SUPER_POWER)));
                matchedUser.setAccountType(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_ACCOUNT_TYPE)));
                matchedUsers.add(matchedUser);
            }
        }

        cursor.close();
        db.close();

        return matchedUsers;
    }

    public String getMatchedUserNameByID(Context context, String matchedUserID) {
        String userName = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_MATCHED_USER
                + " WHERE " + DBOpenHelper.MATCHED_USER_ID + "='" + matchedUserID + "'", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                userName = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_NAME));
            }
        }

        cursor.close();
        db.close();

        return userName;
    }

    public String getMatchedUserMainProfilePicUrlByID(Context context, String matchedUserID) {
        String mainProfilePicUrl = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_MATCHED_USER
                + " WHERE " + DBOpenHelper.MATCHED_USER_ID + "='" + matchedUserID + "'", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                mainProfilePicUrl = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_MAIN_PROFILE_PIC_URL));
            }
        }

        cursor.close();
        db.close();

        return mainProfilePicUrl;
    }

    public ArrayList<String> getMatchedUserProfilePicUrlsByID(Context context, String matchedUserId) {
        ArrayList<String> matchProfilePicUrls = new ArrayList<>();
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_MATCH_PROFILE_PICTURE
                + " WHERE " + DBOpenHelper.MATCHED_USER_ID + "='" + matchedUserId + "'", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String matchProfilePicUrl = "";
                matchProfilePicUrl = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCH_PROFILE_PIC_URL));
                if(matchProfilePicUrl != null){
                    matchProfilePicUrls.add(matchProfilePicUrl);
                }
            }
        }

        cursor.close();
        db.close();

        return matchProfilePicUrls;
    }

    public int getMatchedUserGenderByID(Context context, String matchedUserID) {
        int myGender = 0;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_MATCHED_USER
                + " WHERE " + DBOpenHelper.MATCHED_USER_ID + "='" + matchedUserID + "'", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                myGender = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_GENDER)));
            }
        }

        cursor.close();
        db.close();

        return myGender;
    }

    public int getMatchedUserAge(Context context, String matchedUserID) {
        int myAge = 0;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_MATCHED_USER
                + " WHERE " + DBOpenHelper.MATCHED_USER_ID + "='" + matchedUserID + "'", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                myAge = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_AGE)));
            }
        }

        cursor.close();
        db.close();

        return myAge;
    }

    public String getMatchedUserCountry(Context context, String matchedUserID) {
        String myCountry = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_MATCHED_USER
                + " WHERE " + DBOpenHelper.MATCHED_USER_ID + "='" + matchedUserID + "'", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                myCountry = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_COUNTRY));
            }
        }

        cursor.close();
        db.close();

        return myCountry;
    }

    public String getMatchedUserCity(Context context, String matchedUserID) {
        String myCity = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_MATCHED_USER
                + " WHERE " + DBOpenHelper.MATCHED_USER_ID + "='" + matchedUserID + "'", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                myCity = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_CITY));
            }
        }

        cursor.close();
        db.close();

        return myCity;
    }

    public String getMatchedUserSuperPower(Context context, String matchedUserID) {
        String about = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_MATCHED_USER
                + " WHERE " + DBOpenHelper.MATCHED_USER_ID + "='" + matchedUserID + "'", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                about = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.MATCHED_USER_SUPER_POWER));
            }
        }

        cursor.close();
        db.close();

        return about;
    }

    public void insertMatchedUser(User user, Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.MATCHED_USER_ID, user.getId());
        contentValues.put(DBOpenHelper.MATCHED_USER_NAME, user.getName());
        contentValues.put(DBOpenHelper.MATCHED_USER_AGE, user.getAge());
        contentValues.put(DBOpenHelper.MATCHED_USER_MAIN_PROFILE_PIC_URL, user.getMainProfilePicUrl());
        contentValues.put(DBOpenHelper.MATCHED_USER_GENDER, user.getGender());
        contentValues.put(DBOpenHelper.MATCHED_USER_COUNTRY, user.getCountry());
        contentValues.put(DBOpenHelper.MATCHED_USER_CITY, user.getCity());
        contentValues.put(DBOpenHelper.MATCHED_USER_ACCOUNT_TYPE, user.getAccountType());
        contentValues.put(DBOpenHelper.MATCHED_USER_SUPER_POWER, user.getSuperPower());

        context.getContentResolver().insert(SuperHeroMatchProvider.CONTENT_URI_MATCHED_USER, contentValues);
    }

    public void insertMatchedUserProfilePic(String userID, String profilePicUrl, Context context) {
        ContentValues setValues = new ContentValues();
        setValues.put(DBOpenHelper.PICTURE_MATCH_ID, userID);
        setValues.put(DBOpenHelper.MATCH_PROFILE_PIC_URL, profilePicUrl);
        Uri setUri = context.getContentResolver().insert(SuperHeroMatchProvider.CONTENT_URI_MATCH_PROFILE_PICTURE, setValues);
    }

    public void insertMatchChat(String matchedUserID, String chatName, Context context) {
        ContentValues setValues = new ContentValues();
        setValues.put(DBOpenHelper.MATCH_NAME, matchedUserID);
        setValues.put(DBOpenHelper.CHAT_NAME, chatName);
        Uri setUri = context.getContentResolver().insert(SuperHeroMatchProvider.CONTENT_URI_CHAT, setValues);
    }

    public void insertMatchChatMessage(String chatName, String senderID, String message, String messageUUID, Context context) {
        ContentValues setValues = new ContentValues();
        setValues.put(DBOpenHelper.MESSAGE_CHAT_ID, chatName);
        setValues.put(DBOpenHelper.MESSAGE_SENDER_ID, senderID);
        setValues.put(DBOpenHelper.TEXT_MESSAGE, message);
        setValues.put(DBOpenHelper.MESSAGE_UUID, messageUUID);
        Uri setUri = context.getContentResolver().insert(SuperHeroMatchProvider.CONTENT_URI_MESSAGE, setValues);
    }
}
