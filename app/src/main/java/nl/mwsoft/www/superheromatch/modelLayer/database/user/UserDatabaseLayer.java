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
package nl.mwsoft.www.superheromatch.modelLayer.database.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import nl.mwsoft.www.superheromatch.modelLayer.database.dbhelper.DBOpenHelper;
import nl.mwsoft.www.superheromatch.modelLayer.database.provider.SuperHeroMatchProvider;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;


public class UserDatabaseLayer {

    public UserDatabaseLayer() {
    }

    public String getUserId(Context context) {
        String id = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                id = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.U_ID));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return id;
    }

    public String getUserName(Context context) {
        String userName = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                userName = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_NAME));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return userName;
    }

    public String getUserEmail(Context context) {
        String userName = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                userName = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_EMAIL));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return userName;
    }

    public String getUserMainProfilePicUrl(Context context) {
        String mainProfilePicUrl = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                mainProfilePicUrl = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_MAIN_PROFILE_PIC_URL));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return mainProfilePicUrl;
    }

    public ArrayList<String> getUserMainProfilePicUrls(Context context) {
        ArrayList<String> mainProfilePicUrls = new ArrayList<>();
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER_PROFILE_PICTURE, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String mainProfilePicUrl = "";
                mainProfilePicUrl = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_PROFILE_PIC_URL));
                if (mainProfilePicUrl != null) {
                    mainProfilePicUrls.add(mainProfilePicUrl);
                }
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return mainProfilePicUrls;
    }

    public int getUserGender(Context context) {
        int myGender = 0;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                myGender = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_GENDER)));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return myGender;
    }

    public int getUserLookingForGender(Context context) {
        int lookingForGender = 0;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                lookingForGender = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_LOOKING_FOR_GENDER)));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return lookingForGender;
    }

    public int getUserAge(Context context) {
        int myAge = 0;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                myAge = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_AGE)));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return myAge;
    }

    public int getUserLookingForMinAge(Context context) {
        int minAge = 0;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                minAge = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_LOOKING_FOR_MIN_AGE)));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return minAge;
    }

    public int getUserLookingForMaxAge(Context context) {
        int maxAge = 0;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                maxAge = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_LOOKING_FOR_MAX_AGE)));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return maxAge;
    }

    public int getUserLookingForMaxDistance(Context context) {
        int maxDistance = 0;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                maxDistance = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_LOOKING_FOR_MAX_DISTANCE)));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return maxDistance;
    }

    public String getUserDistanceUnit(Context context) {
        String distanceUnit = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                distanceUnit = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_DISTANCE_UNIT));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return distanceUnit;
    }

    public double getUserLat(Context context) {
        double lat = 0.0;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                lat = Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_LATEST_LATITUDE)));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return lat;
    }

    public double getUserLon(Context context) {
        double lon = 0.0;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                lon = Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_LATEST_LONGITUDE)));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return lon;
    }

    public String getUserBirthday(Context context) {
        String birthday = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                birthday = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_BIRTHDAY));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return birthday;
    }

    public String getUserCountry(Context context) {
        String myCountry = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                myCountry = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_COUNTRY));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return myCountry;
    }

    public String getUserCity(Context context) {
        String myCity = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                myCity = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_CITY));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return myCity;
    }

    public String getUserSuperPower(Context context) {
        String superPower = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                superPower = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_SUPER_POWER));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return superPower;
    }

    public int getUserIsVerified(Context context) {
        int verified = 0;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                verified = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_VERIFIED)));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return verified;
    }

    public int getUserIsLoggedIn(Context context) {
        int loggedIn = 0;
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                loggedIn = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_IS_LOGGED_IN)));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return loggedIn;
    }

    public String getUserCreated(Context context) {
        String created = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                created = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_CREATED));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return created;
    }

    public String getUserAccountType(Context context) {
        String accountType = "";
        Cursor cursor = null;
        SQLiteDatabase db = new DBOpenHelper(context).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_USER, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                accountType = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USER_ACCOUNT_TYPE));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return accountType;
    }

    public void saveInitiallyRegisteredUser(User user, Context context) {
        String selection = DBOpenHelper.U_ID + "='default'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.U_ID, user.getId());
        contentValues.put(DBOpenHelper.USER_EMAIL, user.getEmail());
        contentValues.put(DBOpenHelper.USER_NAME, user.getName());
        contentValues.put(DBOpenHelper.USER_MAIN_PROFILE_PIC_URL, user.getMainProfilePicUrl());
        contentValues.put(DBOpenHelper.USER_GENDER, user.getGender());
        contentValues.put(DBOpenHelper.USER_LOOKING_FOR_GENDER, user.getLookingForGender());
        contentValues.put(DBOpenHelper.USER_AGE, user.getAge());
        contentValues.put(DBOpenHelper.USER_LOOKING_FOR_MIN_AGE, user.getLookingForAgeMin());
        contentValues.put(DBOpenHelper.USER_LOOKING_FOR_MAX_AGE, user.getLookingForAgeMax());
        contentValues.put(DBOpenHelper.USER_LOOKING_FOR_MAX_DISTANCE, user.getLookingForDistanceMax());
        contentValues.put(DBOpenHelper.USER_DISTANCE_UNIT, user.getDistanceUnit());
        contentValues.put(DBOpenHelper.USER_LATEST_LATITUDE, user.getLat());
        contentValues.put(DBOpenHelper.USER_LATEST_LONGITUDE, user.getLon());
        contentValues.put(DBOpenHelper.USER_BIRTHDAY, user.getBirthday());
        contentValues.put(DBOpenHelper.USER_COUNTRY, user.getCountry());
        contentValues.put(DBOpenHelper.USER_CITY, user.getCity());
        contentValues.put(DBOpenHelper.USER_SUPER_POWER, user.getSuperPower());
        contentValues.put(DBOpenHelper.USER_ACCOUNT_TYPE, user.getAccountType());

        context.getContentResolver().update(SuperHeroMatchProvider.CONTENT_URI_USER, contentValues, selection, null);
    }

    public void updateUserId(String userID, Context context) {
        String selection = DBOpenHelper.U_ID + "='" + userID + "'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.U_ID, userID);

        context.getContentResolver().update(SuperHeroMatchProvider.CONTENT_URI_USER, contentValues, selection, null);
    }

    public void updateUserLongitudeAndLatitude(String userID, double lat, double lon, Context context) {
        String selection = DBOpenHelper.U_ID + "='" + userID + "'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.USER_LATEST_LATITUDE, lat);
        contentValues.put(DBOpenHelper.USER_LATEST_LONGITUDE, lon);

        context.getContentResolver().update(SuperHeroMatchProvider.CONTENT_URI_USER, contentValues, selection, null);
    }

    public void updateUserCountryAndCity(String userID, String country, String city, Context context) {
        String selection = DBOpenHelper.U_ID + "='" + userID + "'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.USER_COUNTRY, country);
        contentValues.put(DBOpenHelper.USER_CITY, city);

        context.getContentResolver().update(SuperHeroMatchProvider.CONTENT_URI_USER, contentValues, selection, null);
    }

    public void updateUserAccountType(String userID, String accountType, Context context) {
        String selection = DBOpenHelper.U_ID + "='" + userID + "'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.USER_ACCOUNT_TYPE, accountType);

        context.getContentResolver().update(SuperHeroMatchProvider.CONTENT_URI_USER, contentValues, selection, null);
    }

    public void updateUserMainProfilePic(String userID, String mainProfilePic, Context context) {
        String selection = DBOpenHelper.U_ID + "='" + userID + "'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.USER_MAIN_PROFILE_PIC_URL, mainProfilePic);

        context.getContentResolver().update(SuperHeroMatchProvider.CONTENT_URI_USER, contentValues, selection, null);
    }

    public void insertProfilePic(String userID, String profilePicUri, String profilePicUrl, Context context) {
        ContentValues setValues = new ContentValues();
        setValues.put(DBOpenHelper.USER_ID, userID);
        setValues.put(DBOpenHelper.PROFILE_PIC_POSITION, profilePicUri);
        setValues.put(DBOpenHelper.USER_PROFILE_PIC_URL, profilePicUrl);

        context.getContentResolver().insert(SuperHeroMatchProvider.CONTENT_URI_USER_PROFILE_PICTURE, setValues);
    }

    public void saveUserToDB(User user, Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.U_ID, user.getId());
        contentValues.put(DBOpenHelper.USER_EMAIL, user.getEmail());
        contentValues.put(DBOpenHelper.USER_NAME, user.getName());
        contentValues.put(DBOpenHelper.USER_MAIN_PROFILE_PIC_URL, user.getMainProfilePicUrl());
        contentValues.put(DBOpenHelper.USER_GENDER, user.getGender());
        contentValues.put(DBOpenHelper.USER_LOOKING_FOR_GENDER, user.getLookingForGender());
        contentValues.put(DBOpenHelper.USER_AGE, user.getAge());
        contentValues.put(DBOpenHelper.USER_LOOKING_FOR_MIN_AGE, user.getLookingForAgeMin());
        contentValues.put(DBOpenHelper.USER_LOOKING_FOR_MAX_AGE, user.getLookingForAgeMax());
        contentValues.put(DBOpenHelper.USER_LOOKING_FOR_MAX_DISTANCE, user.getLookingForDistanceMax());
        contentValues.put(DBOpenHelper.USER_DISTANCE_UNIT, user.getDistanceUnit());
        contentValues.put(DBOpenHelper.USER_LATEST_LATITUDE, user.getLat());
        contentValues.put(DBOpenHelper.USER_LATEST_LONGITUDE, user.getLon());
        contentValues.put(DBOpenHelper.USER_BIRTHDAY, user.getBirthday());
        contentValues.put(DBOpenHelper.USER_COUNTRY, user.getCountry());
        contentValues.put(DBOpenHelper.USER_CITY, user.getCity());
        contentValues.put(DBOpenHelper.USER_SUPER_POWER, user.getSuperPower());
        contentValues.put(DBOpenHelper.USER_ACCOUNT_TYPE, user.getAccountType());

        context.getContentResolver().insert(SuperHeroMatchProvider.CONTENT_URI_USER, contentValues);
    }

    public void updateUserLookingForAgeRange(String userID, int ageMin, int ageMax, Context context) {
        String selection = DBOpenHelper.U_ID + "='" + userID + "'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.USER_LOOKING_FOR_MIN_AGE, ageMin);
        contentValues.put(DBOpenHelper.USER_LOOKING_FOR_MAX_AGE, ageMax);

        context.getContentResolver().update(SuperHeroMatchProvider.CONTENT_URI_USER, contentValues, selection, null);
    }

    public void updateUserLookingForMaxDistance(String userID, int maxDistance, Context context) {
        String selection = DBOpenHelper.U_ID + "='" + userID + "'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.USER_LOOKING_FOR_MAX_DISTANCE, maxDistance);

        context.getContentResolver().update(SuperHeroMatchProvider.CONTENT_URI_USER, contentValues, selection, null);
    }

    public void updateUserLookingForGender(String userID, int gender, Context context) {
        String selection = DBOpenHelper.U_ID + "='" + userID + "'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.USER_LOOKING_FOR_GENDER, gender);

        context.getContentResolver().update(SuperHeroMatchProvider.CONTENT_URI_USER, contentValues, selection, null);
    }

    public void updateUserDistanceUnit(String userID, String distanceUnit, Context context) {
        String selection = DBOpenHelper.U_ID + "='" + userID + "'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.USER_DISTANCE_UNIT, distanceUnit);

        context.getContentResolver().update(SuperHeroMatchProvider.CONTENT_URI_USER, contentValues, selection, null);
    }

    public void updateUserSuperPower(String userID, String superPower, Context context) {
        String selection = DBOpenHelper.U_ID + "='" + userID + "'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.USER_SUPER_POWER, superPower);

        context.getContentResolver().update(SuperHeroMatchProvider.CONTENT_URI_USER, contentValues, selection, null);
    }
}
