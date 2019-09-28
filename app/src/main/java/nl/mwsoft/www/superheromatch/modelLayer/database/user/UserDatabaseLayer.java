package nl.mwsoft.www.superheromatch.modelLayer.database.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

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

        assert cursor != null;
        cursor.close();

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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
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

        assert cursor != null;
        cursor.close();
        db.close();

        return accountType;
    }

    public void saveInitiallyRegisteredUser(User user, Context context) {
        String selection = DBOpenHelper.U_ID + "='default'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.U_ID, user.getUserID());
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

    public void updateUserLongitudeAndLatitude(String userID, double lon, double lat, Context context) {
        String selection = DBOpenHelper.U_ID + "='" + userID + "'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.USER_LATEST_LONGITUDE, lon);
        contentValues.put(DBOpenHelper.USER_LATEST_LATITUDE, lat);

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
        setValues.put(DBOpenHelper.USER_PROFILE_PIC_URI, profilePicUri);
        setValues.put(DBOpenHelper.USER_PROFILE_PIC_URL, profilePicUrl);

        context.getContentResolver().insert(SuperHeroMatchProvider.CONTENT_URI_USER_PROFILE_PICTURE, setValues);
    }

}
