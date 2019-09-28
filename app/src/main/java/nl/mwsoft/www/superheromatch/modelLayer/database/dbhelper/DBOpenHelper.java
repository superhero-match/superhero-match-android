package nl.mwsoft.www.superheromatch.modelLayer.database.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBOpenHelper extends SQLiteOpenHelper {

    // Constants for db name and version
    private static final String DATABASE_NAME = "superheromatch.db";
    private static final int DATABASE_VERSION = 2;

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
    private static final String INSERT_DEFAULT_USER = "INSERT INTO " + TABLE_USER +
            " VALUES ( 'default', 'default@default.com', 'defaultName', 'defaultUrl', 1, 2, 34, 18, 65, 100, 'km', 0.0, 0.0," +
            "'30-05-1985', 'Country', 'City', 'Super Power', 'FREE', 0, 0, '17-07-2019')";

    //=======================================================================================================================
    // user profile picture table
    //=======================================================================================================================
    public static final String TABLE_USER_PROFILE_PICTURE = "user_profile_picture";
    public static final String USER_PROFILE_PICTURE_ID = "_id";
    public static final String USER_ID = "user_id";
    public static final String USER_PROFILE_PIC_URI = "profile_pic_uri";
    public static final String USER_PROFILE_PIC_URL = "profile_pic_url";


    public static final String[] ALL_COLUMNS_USER_PROFILE_PICTURE = {USER_PROFILE_PICTURE_ID,
            USER_ID, USER_PROFILE_PIC_URI, USER_PROFILE_PIC_URL};

    // SQL to create table user
    private static final String TABLE_CREATE_USER_PROFILE_PICTURE =
            "CREATE TABLE " + TABLE_USER_PROFILE_PICTURE + " (" +
                    USER_PROFILE_PICTURE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_ID + " TEXT NOT NULL," +
                    USER_PROFILE_PIC_URI + " TEXT NOT NULL, " +
                    USER_PROFILE_PIC_URL + " TEXT NOT NULL, " +
                    " FOREIGN KEY(" + USER_ID + ") REFERENCES " + TABLE_USER + "(" + U_ID + ")" +
                    ")";

    // endregion


    // region Matched From Suggestions User

    //=======================================================================================================================
    // matched user table
    //=======================================================================================================================
    public static final String TABLE_MATCHED_USER = "matched_user";
    public static final String MATCHED_USER_ID = "_id";
    public static final String MATCHED_USER_NAME = "matched_user_name";
    public static final String MATCHED_USER_MAIN_PROFILE_PIC_URL = "matched_user_main_profile_pic_url";
    public static final String MATCHED_USER_GENDER = "matched_user_gender";
    public static final String MATCHED_USER_AGE = "matched_user_age";
    public static final String MATCHED_USER_COUNTRY = "matched_user_country";
    public static final String MATCHED_USER_CITY = "matched_user_city";
    public static final String MATCHED_USER_SUPER_POWER = "matched_user_super_power";
    public static final String MATCHED_USER_ACCOUNT_TYPE = "matched_user_account_type";
    public static final String MATCHED_WITH_USER_ID = "matched_with_user_id";
    public static final String MATCH_CREATED = "match_created";


    public static final String[] ALL_COLUMNS_MATCHED_USER = {MATCHED_USER_ID, MATCHED_USER_NAME, MATCHED_USER_MAIN_PROFILE_PIC_URL,
            MATCHED_USER_GENDER, MATCHED_USER_AGE, MATCHED_USER_COUNTRY, MATCHED_USER_CITY,
            MATCHED_USER_SUPER_POWER, MATCHED_USER_ACCOUNT_TYPE, MATCHED_WITH_USER_ID, MATCH_CREATED};


    // SQL to create table date_match
    private static final String TABLE_CREATE_MATCHED_USER =
            "CREATE TABLE " + TABLE_MATCHED_USER + " (" +
                    MATCHED_USER_ID + " TEXT PRIMARY KEY, " +
                    MATCHED_USER_NAME + " TEXT NOT NULL," +
                    MATCHED_USER_MAIN_PROFILE_PIC_URL + " TEXT NOT NULL," +
                    MATCHED_USER_GENDER + " TEXT NOT NULL," +
                    MATCHED_USER_AGE + " INTEGER NOT NULL," +
                    MATCHED_USER_COUNTRY + " TEXT NOT NULL," +
                    MATCHED_USER_CITY + " TEXT NOT NULL," +
                    MATCHED_USER_SUPER_POWER + " TEXT NOT NULL, " +
                    MATCHED_USER_ACCOUNT_TYPE + " TEXT NOT NULL," +
                    MATCHED_WITH_USER_ID + " TEXT NOT NULL," +
                    MATCH_CREATED + " TEXT default CURRENT_TIMESTAMP)";

    //=======================================================================================================================
    // match profile picture table
    //=======================================================================================================================
    public static final String TABLE_MATCH_PROFILE_PICTURE = "match_profile_picture";
    public static final String MATCH_PROFILE_PICTURE_ID = "_id";
    public static final String PICTURE_MATCH_ID = "match_id";
    public static final String MATCH_PROFILE_PIC_URL = "profile_pic_url";


    public static final String[] ALL_COLUMNS_MATCH_PROFILE_PICTURE = {MATCH_PROFILE_PICTURE_ID,
            PICTURE_MATCH_ID, MATCH_PROFILE_PIC_URL};

    // SQL to create table user
    private static final String TABLE_CREATE_MATCH_PROFILE_PICTURE =
            "CREATE TABLE " + TABLE_MATCH_PROFILE_PICTURE + " (" +
                    MATCH_PROFILE_PICTURE_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PICTURE_MATCH_ID + " TEXT NOT NULL," +
                    MATCH_PROFILE_PIC_URL + " TEXT NOT NULL, " +
                    " FOREIGN KEY(" + PICTURE_MATCH_ID + ") REFERENCES " + TABLE_MATCHED_USER + "(" + MATCHED_USER_ID + ")" +
                    ")";


    //=======================================================================================================================
    // chat table
    //=======================================================================================================================
    public static final String TABLE_CHAT = "chat";
    public static final String CHAT_ID = "_id";
    public static final String CHAT_NAME = "chat_name";
    public static final String MATCH_NAME = "match_name";
    public static final String CHAT_CREATED = "chat_created";


    public static final String[] ALL_COLUMNS_CHAT = {CHAT_ID, CHAT_NAME, MATCH_NAME, CHAT_CREATED};

    // SQL to create table chat
    private static final String TABLE_CREATE_MATCH_CHAT =
            "CREATE TABLE " + TABLE_CHAT + " (" +
                    CHAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CHAT_NAME + " TEXT NOT NULL," +
                    MATCH_NAME + " TEXT NOT NULL," +
                    CHAT_CREATED + " TEXT default CURRENT_TIMESTAMP , " +
                    " FOREIGN KEY(" + MATCH_NAME + ") REFERENCES " + TABLE_MATCHED_USER + "(" + MATCHED_USER_ID + ") " +
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
    public static final String MESSAGE_UUID = "message_uuid";
    public static final String MESSAGE_CREATED = "match_message_created";


    public static final String[] ALL_COLUMNS_MATCH_CHAT_MESSAGE = {MESSAGE_ID, MESSAGE_CHAT_ID,
            MESSAGE_SENDER_ID, TEXT_MESSAGE, MESSAGE_HAS_BEEN_READ, MESSAGE_UUID, MESSAGE_CREATED};

    // SQL to create table message
    private static final String TABLE_CREATE_CHAT_MESSAGE =
            "CREATE TABLE " + TABLE_MESSAGE + " (" +
                    MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MESSAGE_CHAT_ID + " INTEGER," +
                    MESSAGE_SENDER_ID + " TEXT NOT NULL," +
                    TEXT_MESSAGE + " TEXT," +
                    MESSAGE_HAS_BEEN_READ + " INTEGER default 0," +
                    MESSAGE_UUID + " TEXT NOT NULL UNIQUE," +
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
                    MESSAGE_QUEUE_MESSAGE_RECEIVER_ID + " INTEGER NOT NULL," +
                    " FOREIGN KEY(" + MESSAGE_QUEUE_MESSAGE_UUID + ") REFERENCES " + TABLE_MESSAGE + "(" + MESSAGE_UUID + ") " +
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
                    RETRIEVED_OFFLINE_MESSAGE_UUID + " TEXT NOT NULL UNIQUE," +
                    " FOREIGN KEY(" + RETRIEVED_OFFLINE_MESSAGE_UUID + ") REFERENCES " + TABLE_MESSAGE + "(" + MESSAGE_UUID + ")" +
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
                    RECEIVED_ONLINE_MESSAGE_UUID + " TEXT NOT NULL UNIQUE," +
                    " FOREIGN KEY(" + RECEIVED_ONLINE_MESSAGE_UUID + ") REFERENCES " + TABLE_MESSAGE + "(" + MESSAGE_UUID + ")" +
                    ")";

    //=======================================================================================================================

    // endregion

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_MATCHED_USER);
        db.execSQL(TABLE_CREATE_MATCH_CHAT);
        db.execSQL(TABLE_CREATE_CHAT_MESSAGE);
        db.execSQL(TABLE_CREATE_MESSAGE_QUEUE);
        db.execSQL(TABLE_CREATE_USER);
        db.execSQL(INSERT_DEFAULT_USER);
        db.execSQL(TABLE_CREATE_USER_PROFILE_PICTURE);
        db.execSQL(TABLE_CREATE_MATCH_PROFILE_PICTURE);
        db.execSQL(TABLE_CREATE_RETRIEVED_OFFLINE_MESSAGE_UUID);
        db.execSQL(TABLE_CREATE_RECEIVED_ONLINE_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCHED_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH_PROFILE_PICTURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PROFILE_PICTURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RETRIEVED_OFFLINE_MESSAGE_UUID);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEIVED_ONLINE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE_QUEUE);
        onCreate(db);
    }

}
