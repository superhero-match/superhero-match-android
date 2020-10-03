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
package nl.mwsoft.www.superheromatch.modelLayer.database.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBOpenHelper extends SQLiteOpenHelper {

    // Constants for db name and version
    private static final String DATABASE_NAME = "superheromatch.db";
    private static final int DATABASE_VERSION = 7;

    //=======================================================================================================================
    // Constants for identifying tables and columns
    //=======================================================================================================================

    // region User

    //=======================================================================================================================
    // user table
    //=======================================================================================================================
    public static final String TABLE_USER = "user";
    public static final String U_ID = "_id";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_NAME = "user_name";
    public static final String USER_MAIN_PROFILE_PIC_URL = "user_main_profile_pic_url";
    public static final String USER_GENDER = "user_gender";
    public static final String USER_LOOKING_FOR_GENDER = "user_looking_for_gender";
    public static final String USER_AGE = "user_age";
    public static final String USER_LOOKING_FOR_MIN_AGE = "user_looking_for_min_age";
    public static final String USER_LOOKING_FOR_MAX_AGE = "user_looking_for_max_age";
    public static final String USER_LOOKING_FOR_MAX_DISTANCE = "user_looking_for_max_distance";
    public static final String USER_DISTANCE_UNIT = "user_distance_unit";
    public static final String USER_LATEST_LATITUDE = "user_latest_latitude";
    public static final String USER_LATEST_LONGITUDE = "user_latest_longitude";
    public static final String USER_BIRTHDAY = "user_birthday";
    public static final String USER_COUNTRY = "user_country";
    public static final String USER_CITY = "user_city";
    public static final String USER_SUPER_POWER = "user_super_power";
    public static final String USER_ACCOUNT_TYPE = "user_account_type";
    public static final String USER_VERIFIED = "user_verified";
    public static final String USER_IS_LOGGED_IN = "user_is_logged_in";
    public static final String USER_CREATED = "user_created";


    public static final String[] ALL_COLUMNS_USER = {U_ID, USER_EMAIL, USER_NAME, USER_MAIN_PROFILE_PIC_URL,
            USER_GENDER, USER_LOOKING_FOR_GENDER, USER_AGE, USER_LOOKING_FOR_MIN_AGE, USER_LOOKING_FOR_MAX_AGE,
            USER_LOOKING_FOR_MAX_DISTANCE, USER_DISTANCE_UNIT, USER_LATEST_LATITUDE, USER_LATEST_LONGITUDE,
            USER_BIRTHDAY, USER_COUNTRY, USER_CITY, USER_SUPER_POWER, USER_ACCOUNT_TYPE,
            USER_VERIFIED, USER_IS_LOGGED_IN, USER_CREATED};

    // SQL to create table user
    private static final String TABLE_CREATE_USER =
            "CREATE TABLE " + TABLE_USER + " (" +
                    U_ID + " TEXT PRIMARY KEY, " +
                    USER_EMAIL + " TEXT NOT NULL," +
                    USER_NAME + " TEXT NOT NULL," +
                    USER_MAIN_PROFILE_PIC_URL + " TEXT," +
                    USER_GENDER + " INTEGER NOT NULL," +
                    USER_LOOKING_FOR_GENDER + " INTEGER NOT NULL," +
                    USER_AGE + " INTEGER NOT NULL," +
                    USER_LOOKING_FOR_MIN_AGE + " INTEGER NOT NULL," +
                    USER_LOOKING_FOR_MAX_AGE + " INTEGER NOT NULL," +
                    USER_LOOKING_FOR_MAX_DISTANCE + " INTEGER NOT NULL," +
                    USER_DISTANCE_UNIT + " TEXT NOT NULL," +
                    USER_LATEST_LATITUDE + " REAL NOT NULL, " +
                    USER_LATEST_LONGITUDE + " REAL NOT NULL, " +
                    USER_BIRTHDAY + " TEXT NOT NULL," +
                    USER_COUNTRY + " TEXT NOT NULL," +
                    USER_CITY + " TEXT NOT NULL," +
                    USER_SUPER_POWER + " TEXT NOT NULL, " +
                    USER_ACCOUNT_TYPE + " TEXT NOT NULL," +
                    USER_VERIFIED + " INTEGER, " +
                    USER_IS_LOGGED_IN + " INTEGER, " +
                    USER_CREATED + " TEXT default CURRENT_TIMESTAMP)";

    // insert default user
    public static final String INSERT_DEFAULT_USER = "INSERT INTO " + TABLE_USER +
            " VALUES ( 'default', 'default@default.com', 'defaultName', 'defaultUrl', 1, 2, 34, 18, 65, 100, 'km', 0.0, 0.0," +
            "'30-05-1985', 'Country', 'City', 'Super Power', 'FREE', 0, 0, '17-07-2019')";

    //=======================================================================================================================
    // user profile picture table
    //=======================================================================================================================
    public static final String TABLE_USER_PROFILE_PICTURE = "user_profile_picture";
    public static final String USER_PROFILE_PICTURE_ID = "_id";
    public static final String USER_ID = "user_id";
    public static final String USER_PROFILE_PIC_URL = "profile_pic_url";
    public static final String PROFILE_PIC_POSITION = "profile_pic_position";


    public static final String[] ALL_COLUMNS_USER_PROFILE_PICTURE = {USER_PROFILE_PICTURE_ID,
            USER_ID, PROFILE_PIC_POSITION, USER_PROFILE_PIC_URL};

    // SQL to create table user
    private static final String TABLE_CREATE_USER_PROFILE_PICTURE =
            "CREATE TABLE " + TABLE_USER_PROFILE_PICTURE + " (" +
                    USER_PROFILE_PICTURE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_ID + " TEXT NOT NULL," +
                    USER_PROFILE_PIC_URL + " TEXT NOT NULL, " +
                    PROFILE_PIC_POSITION + " INTEGER NOT NULL, " +
                    " FOREIGN KEY(" + USER_ID + ") REFERENCES " + TABLE_USER + "(" + U_ID + ")" +
                    ")";

    // endregion

    //=======================================================================================================================
    // chat table
    //=======================================================================================================================
    public static final String TABLE_CHAT = "chat";
    public static final String CHAT_ID = "_id";
    public static final String CHAT_NAME = "chat_name";
    public static final String CHAT_MATCHED_USER_ID = "chat_matched_user_id";
    public static final String CHAT_MATCHED_USER_MAIN_PROFILE_PIC = "chat_matched_user_main_profile_pic";
    public static final String CHAT_CREATED = "chat_created";


    public static final String[] ALL_COLUMNS_CHAT = {CHAT_ID, CHAT_NAME, CHAT_MATCHED_USER_ID,
            CHAT_MATCHED_USER_MAIN_PROFILE_PIC, CHAT_CREATED};

    // SQL to create table chat
    private static final String TABLE_CREATE_MATCH_CHAT =
            "CREATE TABLE " + TABLE_CHAT + " (" +
                    CHAT_ID + " TEXT PRIMARY KEY, " +
                    CHAT_NAME + " TEXT NOT NULL," +
                    CHAT_MATCHED_USER_ID + " TEXT NOT NULL," +
                    CHAT_MATCHED_USER_MAIN_PROFILE_PIC + " TEXT NOT NULL," +
                    CHAT_CREATED + " TEXT default CURRENT_TIMESTAMP " +
                    ")";

    //=======================================================================================================================
    // match chat message table
    //=======================================================================================================================
    public static final String TABLE_MESSAGE = "message";
    public static final String MESSAGE_ID = "_id";
    public static final String MESSAGE_CHAT_ID = "message_chat_id";
    public static final String MESSAGE_SENDER_ID = "message_sender_id";
    public static final String TEXT_MESSAGE = "text_message";
    public static final String MESSAGE_HAS_BEEN_READ = "message_has_been_read";
    public static final String MESSAGE_CREATED = "match_message_created";


    public static final String[] ALL_COLUMNS_MATCH_CHAT_MESSAGE = {MESSAGE_ID, MESSAGE_CHAT_ID,
            MESSAGE_SENDER_ID, TEXT_MESSAGE, MESSAGE_HAS_BEEN_READ, MESSAGE_CREATED};

    // SQL to create table message
    private static final String TABLE_CREATE_CHAT_MESSAGE =
            "CREATE TABLE " + TABLE_MESSAGE + " (" +
                    MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MESSAGE_CHAT_ID + " TEXT NOT NULL," +
                    MESSAGE_SENDER_ID + " TEXT NOT NULL," +
                    TEXT_MESSAGE + " TEXT," +
                    MESSAGE_HAS_BEEN_READ + " INTEGER default 0," +
                    MESSAGE_CREATED + " TEXT default CURRENT_TIMESTAMP , " +
                    " FOREIGN KEY(" + MESSAGE_CHAT_ID + ") REFERENCES " + TABLE_CHAT + "(" + CHAT_ID + ") " +
                    ")";

    // endregion

    // region Message Queue

    //=======================================================================================================================
    // message queue table
    //=======================================================================================================================
    public static final String TABLE_MESSAGE_QUEUE = "message_queue";
    public static final String MESSAGE_QUEUE_ITEM_ID = "_id";
    public static final String MESSAGE_QUEUE_MESSAGE_UUID = "message_queue_message_uuid";
    public static final String MESSAGE_QUEUE_MESSAGE_RECEIVER_ID = "message_queue_message_receiver_id";


    public static final String[] ALL_COLUMNS_MESSAGE_QUEUE = {MESSAGE_QUEUE_ITEM_ID, MESSAGE_QUEUE_MESSAGE_UUID,
            MESSAGE_QUEUE_MESSAGE_RECEIVER_ID};

    // SQL to create table text_message
    private static final String TABLE_CREATE_MESSAGE_QUEUE =
            "CREATE TABLE " + TABLE_MESSAGE_QUEUE + " (" +
                    MESSAGE_QUEUE_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MESSAGE_QUEUE_MESSAGE_UUID + " TEXT NOT NULL UNIQUE," +
                    MESSAGE_QUEUE_MESSAGE_RECEIVER_ID + " INTEGER NOT NULL" +
                    ")";


    //=======================================================================================================================
    // retrieved offline message uuid table
    //=======================================================================================================================
    public static final String TABLE_RETRIEVED_OFFLINE_MESSAGE_UUID = "retrieved_offline_message_uuid";
    public static final String RETRIEVED_OFFLINE_MESSAGE_UUID_ID = "_id";
    public static final String RETRIEVED_OFFLINE_MESSAGE_UUID = "retrieved_offline_message_uuid_uuid";


    public static final String[] ALL_COLUMNS_RETRIEVED_OFFLINE_MESSAGE_UUID = {RETRIEVED_OFFLINE_MESSAGE_UUID_ID,
            RETRIEVED_OFFLINE_MESSAGE_UUID};

    // SQL to create table retrieved offline message
    private static final String TABLE_CREATE_RETRIEVED_OFFLINE_MESSAGE_UUID =
            "CREATE TABLE " + TABLE_RETRIEVED_OFFLINE_MESSAGE_UUID + " (" +
                    RETRIEVED_OFFLINE_MESSAGE_UUID_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    RETRIEVED_OFFLINE_MESSAGE_UUID + " TEXT NOT NULL UNIQUE" +
                    ")";

    //=======================================================================================================================
    // received online message table
    //=======================================================================================================================
    public static final String TABLE_RECEIVED_ONLINE_MESSAGE = "received_online_message";
    public static final String RECEIVED_ONLINE_MESSAGE_ID = "_id";
    public static final String RECEIVED_ONLINE_MESSAGE_UUID = "received_online_message_uuid";


    public static final String[] ALL_COLUMNS_RECEIVED_ONLINE_MESSAGE = {RECEIVED_ONLINE_MESSAGE_ID,
            RECEIVED_ONLINE_MESSAGE_UUID};

    // SQL to create table text_message
    private static final String TABLE_CREATE_RECEIVED_ONLINE_MESSAGE =
            "CREATE TABLE " + TABLE_RECEIVED_ONLINE_MESSAGE + " (" +
                    RECEIVED_ONLINE_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    RECEIVED_ONLINE_MESSAGE_UUID + " TEXT NOT NULL UNIQUE" +
                    ")";

    //=======================================================================================================================

    //=======================================================================================================================
    // choice table
    //=======================================================================================================================
    public static final String TABLE_CHOICE = "choice";
    public static final String CHOICE_ID = "_id";
    public static final String CHOSEN_USER_ID = "chosen_user_id";
    public static final String CHOICE = "choice";
    public static final String CHOICE_CREATED_AT = "created_at";


    public static final String[] ALL_COLUMNS_CHOICE = {CHOICE_ID,
            CHOSEN_USER_ID, CHOICE, CHOICE_CREATED_AT};

    // SQL to create table text_message
    private static final String TABLE_CREATE_CHOICE =
            "CREATE TABLE " + TABLE_CHOICE + " (" +
                    CHOICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CHOSEN_USER_ID + " TEXT NOT NULL, " +
                    CHOICE + " INTEGER NOT NULL, " +
                    CHOICE_CREATED_AT + " TEXT default CURRENT_TIMESTAMP " +
                    ")";

    //=======================================================================================================================

    //=======================================================================================================================
    // reported_user table
    //=======================================================================================================================
    public static final String TABLE_REPORTED_USER = "reported_user";
    public static final String REPORTED_USER_ID = "_id";
    public static final String REPORTED_USER_USER_ID = "reported_user_id";
    public static final String REPORTED_USER_CREATED_AT = "created_at";


    public static final String[] ALL_COLUMNS_REPORTED_USER = {REPORTED_USER_ID,
            REPORTED_USER_USER_ID, REPORTED_USER_CREATED_AT};

    // SQL to create table reported_user
    private static final String TABLE_CREATE_REPORTED_USER =
            "CREATE TABLE " + TABLE_REPORTED_USER + " (" +
                    REPORTED_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    REPORTED_USER_USER_ID + " TEXT NOT NULL, " +
                    REPORTED_USER_CREATED_AT + " TEXT default CURRENT_TIMESTAMP " +
                    ")";

    //=======================================================================================================================

    // endregion

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_MATCH_CHAT);
        db.execSQL(TABLE_CREATE_CHAT_MESSAGE);
        db.execSQL(TABLE_CREATE_MESSAGE_QUEUE);
        db.execSQL(TABLE_CREATE_USER);
        db.execSQL(INSERT_DEFAULT_USER);
        db.execSQL(TABLE_CREATE_USER_PROFILE_PICTURE);
        db.execSQL(TABLE_CREATE_RETRIEVED_OFFLINE_MESSAGE_UUID);
        db.execSQL(TABLE_CREATE_RECEIVED_ONLINE_MESSAGE);
        db.execSQL(TABLE_CREATE_CHOICE);
        db.execSQL(TABLE_CREATE_REPORTED_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PROFILE_PICTURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RETRIEVED_OFFLINE_MESSAGE_UUID);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEIVED_ONLINE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE_QUEUE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHOICE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORTED_USER);
        onCreate(db);
    }

}
