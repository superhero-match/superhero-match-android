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
package nl.mwsoft.www.superheromatch.modelLayer.database.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.Nullable;

import nl.mwsoft.www.superheromatch.modelLayer.database.dbhelper.DBOpenHelper;

public class SuperHeroMatchProvider extends ContentProvider {

    private static final String AUTHORITY = "nl.mwsoft.www.superheromatch.provider.superheromatchprovider";
    private static final String MESSAGE_QUEUE_PATH = "message_queue";
    private static final String USER_PATH = "user";
    private static final String USER_PROFILE_PICTURE_PATH = "user_profile_picture";
    private static final String MATCH_CHAT_PATH = "match_chat";
    private static final String MATCH_CHAT_MESSAGE_PATH = "match_chat_message";
    private static final String RETRIEVED_OFFLINE_MESSAGE_UUID_PATH = "retrieved_offline_message_uuid";
    private static final String RECEIVED_ONLINE_MESSAGE_PATH = "received_online_message";
    private static final String CHOICE_PATH = "choice";
    private static final String REPORTED_USER_PATH = "reported_user";


    public static final Uri CONTENT_URI_MESSAGE_QUEUE = Uri.parse("content://" + AUTHORITY + "/" + MESSAGE_QUEUE_PATH);
    public static final Uri CONTENT_URI_USER = Uri.parse("content://" + AUTHORITY + "/" + USER_PATH);
    public static final Uri CONTENT_URI_USER_PROFILE_PICTURE = Uri.parse("content://" + AUTHORITY + "/" + USER_PROFILE_PICTURE_PATH);
   public static final Uri CONTENT_URI_CHAT = Uri.parse("content://" + AUTHORITY + "/" + MATCH_CHAT_PATH);
    public static final Uri CONTENT_URI_MESSAGE = Uri.parse("content://" + AUTHORITY + "/" + MATCH_CHAT_MESSAGE_PATH);
    public static final Uri CONTENT_URI_RETRIEVED_OFFLINE_MESSAGE_UUID = Uri.parse("content://" + AUTHORITY + "/" + RETRIEVED_OFFLINE_MESSAGE_UUID_PATH);
    public static final Uri CONTENT_URI_RECEIVED_ONLINE_MESSAGE = Uri.parse("content://" + AUTHORITY + "/" + RECEIVED_ONLINE_MESSAGE_PATH);
    public static final Uri CONTENT_URI_CHOICE = Uri.parse("content://" + AUTHORITY + "/" + CHOICE_PATH);
    public static final Uri CONTENT_URI_REPORTED_USER = Uri.parse("content://" + AUTHORITY + "/" + REPORTED_USER_PATH);


    // ConstantRegistry to identify the requested operation

    private static final int MESSAGE_QUEUE_ITEMS = 1;
    private static final int MESSAGE_QUEUE_ITEM_ID = 2;

    private static final int USERS = 3;
    private static final int USER_ID = 4;

    private static final int RETRIEVED_OFFLINE_MESSAGE_UUIDS = 5;
    private static final int RETRIEVED_OFFLINE_MESSAGE_UUID_ID = 6;

    private static final int RECEIVED_ONLINE_MESSAGES = 7;
    private static final int RECEIVED_ONLINE_MESSAGE_ID = 8;

    private static final int USER_PROFILE_PICTURES = 9;
    private static final int USER_PROFILE_PICTURE_ID = 10;

    private static final int MATCH_CHATS = 15;
    private static final int MATCH_CHAT_ID = 16;

    private static final int MATCH_CHAT_MESSAGES = 17;
    private static final int MATCH_CHAT_MESSAGE_ID = 18;

    private static final int CHOICES = 19;
    private static final int CHOICE_ID = 20;

    private static final int REPORTED_USERS = 21;
    private static final int REPORTED_USER_ID = 22;


    private static  final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final String CONTENT_ITEM_MESSAGE_QUEUE = "message_queue";
    private static final String CONTENT_ITEM_USER = "user";
    private static final String CONTENT_ITEM_USER_PROFILE_PICTURE = "user_profile_picture";
    private static final String CONTENT_ITEM_MATCH_CHAT = "match_chat";
    private static final String CONTENT_ITEM_MATCH_CHAT_MESSAGE = "match_chat_message";
    private static final String CONTENT_ITEM_RETRIEVED_OFFLINE_MESSAGE_UUID = "retrieved_offline_message_uuid";
    private static final String CONTENT_ITEM_RECEIVED_ONLINE_MESSAGE = "received_online_message";
    private static final String CONTENT_ITEM_CHOICE = "choice";
    private static final String CONTENT_ITEM_REPORTED_USER = "reported_user";


    static {

        uriMatcher.addURI(AUTHORITY, MESSAGE_QUEUE_PATH, MESSAGE_QUEUE_ITEMS);
        uriMatcher.addURI(AUTHORITY, MESSAGE_QUEUE_PATH + "/#", MESSAGE_QUEUE_ITEM_ID);

        uriMatcher.addURI(AUTHORITY, USER_PATH, USERS);
        uriMatcher.addURI(AUTHORITY, USER_PATH + "/#", USER_ID);

        uriMatcher.addURI(AUTHORITY, RETRIEVED_OFFLINE_MESSAGE_UUID_PATH, RETRIEVED_OFFLINE_MESSAGE_UUIDS);
        uriMatcher.addURI(AUTHORITY, RETRIEVED_OFFLINE_MESSAGE_UUID_PATH + "/#", RETRIEVED_OFFLINE_MESSAGE_UUID_ID);

        uriMatcher.addURI(AUTHORITY, RECEIVED_ONLINE_MESSAGE_PATH, RECEIVED_ONLINE_MESSAGES);
        uriMatcher.addURI(AUTHORITY, RECEIVED_ONLINE_MESSAGE_PATH + "/#", RECEIVED_ONLINE_MESSAGE_ID);

        uriMatcher.addURI(AUTHORITY, USER_PROFILE_PICTURE_PATH, USER_PROFILE_PICTURES);
        uriMatcher.addURI(AUTHORITY, USER_PROFILE_PICTURE_PATH + "/#", USER_PROFILE_PICTURE_ID);

        uriMatcher.addURI(AUTHORITY, MATCH_CHAT_PATH, MATCH_CHATS);
        uriMatcher.addURI(AUTHORITY, MATCH_CHAT_PATH + "/#", MATCH_CHAT_ID);

        uriMatcher.addURI(AUTHORITY, MATCH_CHAT_MESSAGE_PATH, MATCH_CHAT_MESSAGES);
        uriMatcher.addURI(AUTHORITY, MATCH_CHAT_MESSAGE_PATH + "/#", MATCH_CHAT_MESSAGE_ID);

        uriMatcher.addURI(AUTHORITY, CHOICE_PATH, CHOICES);
        uriMatcher.addURI(AUTHORITY, CHOICE_PATH + "/#", CHOICE_ID);

        uriMatcher.addURI(AUTHORITY, REPORTED_USER_PATH, REPORTED_USERS);
        uriMatcher.addURI(AUTHORITY, REPORTED_USER_PATH + "/#", REPORTED_USER_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {

        DBOpenHelper helper = new DBOpenHelper(getContext());

        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor _cursor;
         if(uriMatcher.match(uri) == MESSAGE_QUEUE_ITEM_ID){
            selection = DBOpenHelper.MESSAGE_QUEUE_ITEM_ID + "=" + uri.getLastPathSegment();
        } else if(uriMatcher.match(uri) == USER_ID){
            selection = DBOpenHelper.U_ID + "=" + uri.getLastPathSegment();
        } else if(uriMatcher.match(uri) == RETRIEVED_OFFLINE_MESSAGE_UUID_ID){
            selection = DBOpenHelper.RETRIEVED_OFFLINE_MESSAGE_UUID_ID + "=" + uri.getLastPathSegment();
        } else if(uriMatcher.match(uri) == RECEIVED_ONLINE_MESSAGE_ID){
            selection = DBOpenHelper.RECEIVED_ONLINE_MESSAGE_ID + "=" + uri.getLastPathSegment();
        } else if(uriMatcher.match(uri) == USER_PROFILE_PICTURE_ID){
            selection = DBOpenHelper.USER_PROFILE_PICTURE_ID + "=" + uri.getLastPathSegment();
        } else if(uriMatcher.match(uri) == MATCH_CHAT_ID){
            selection = DBOpenHelper.CHAT_ID + "=" + uri.getLastPathSegment();
        }  else if(uriMatcher.match(uri) == MATCH_CHAT_MESSAGE_ID){
            selection = DBOpenHelper.MESSAGE_ID + "=" + uri.getLastPathSegment();
        }  else if(uriMatcher.match(uri) == CHOICE_ID){
            selection = DBOpenHelper.CHOICE_ID + "=" + uri.getLastPathSegment();
        } else if(uriMatcher.match(uri) == REPORTED_USER_ID){
            selection = DBOpenHelper.REPORTED_USER_ID + "=" + uri.getLastPathSegment();
        }


        switch (uriMatcher.match(uri)){

            case MESSAGE_QUEUE_ITEMS:
                _cursor = database.query(DBOpenHelper.TABLE_MESSAGE_QUEUE, DBOpenHelper.ALL_COLUMNS_MESSAGE_QUEUE,
                        selection, null, null, null, null);
                break;
            case USERS:
                _cursor = database.query(DBOpenHelper.TABLE_USER, DBOpenHelper.ALL_COLUMNS_USER,
                        selection, null, null, null, null);
                break;
            case RETRIEVED_OFFLINE_MESSAGE_UUIDS:
                _cursor = database.query(DBOpenHelper.TABLE_RETRIEVED_OFFLINE_MESSAGE_UUID, DBOpenHelper.ALL_COLUMNS_RETRIEVED_OFFLINE_MESSAGE_UUID,
                        selection, null, null, null, null);
                break;
            case RECEIVED_ONLINE_MESSAGES:
                _cursor = database.query(DBOpenHelper.TABLE_RECEIVED_ONLINE_MESSAGE, DBOpenHelper.ALL_COLUMNS_RECEIVED_ONLINE_MESSAGE,
                        selection, null, null, null, null);
                break;
            case USER_PROFILE_PICTURES:
                _cursor = database.query(DBOpenHelper.TABLE_USER_PROFILE_PICTURE, DBOpenHelper.ALL_COLUMNS_USER_PROFILE_PICTURE,
                        selection, null, null, null, null);
                break;
            case MATCH_CHATS:
                _cursor = database.query(DBOpenHelper.TABLE_CHAT, DBOpenHelper.ALL_COLUMNS_CHAT,
                        selection, null, null, null, null);
                break;
            case MATCH_CHAT_MESSAGES:
                _cursor = database.query(DBOpenHelper.TABLE_MESSAGE, DBOpenHelper.ALL_COLUMNS_MATCH_CHAT_MESSAGE,
                        selection, null, null, null, null);
                break;
            case CHOICES:
                _cursor = database.query(DBOpenHelper.TABLE_CHOICE, DBOpenHelper.ALL_COLUMNS_CHOICE,
                        selection, null, null, null, null);
                break;
            case REPORTED_USERS:
                _cursor = database.query(DBOpenHelper.TABLE_REPORTED_USER, DBOpenHelper.ALL_COLUMNS_REPORTED_USER,
                        selection, null, null, null, null);
                break;

            default: throw new SQLException("Failed to retrieve row from " + uri);
        }

        return _cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri _uri = null;

        switch (uriMatcher.match(uri)){
            case MESSAGE_QUEUE_ITEMS:
                long message_queue_item_id = database.insert(DBOpenHelper.TABLE_MESSAGE_QUEUE, null, values);

                //---if added successfully---
                if (message_queue_item_id > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_MESSAGE_QUEUE, message_queue_item_id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                break;
            case USERS:
                long u_id = database.insert(DBOpenHelper.TABLE_USER, null, values);

                //---if added successfully---
                if (u_id > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_USER, u_id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                break;
            case RETRIEVED_OFFLINE_MESSAGE_UUIDS:
                long r_off_id = database.insert(DBOpenHelper.TABLE_RETRIEVED_OFFLINE_MESSAGE_UUID, null, values);

                //---if added successfully---
                if (r_off_id > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_RETRIEVED_OFFLINE_MESSAGE_UUID, r_off_id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                break;
            case RECEIVED_ONLINE_MESSAGES:
                long r_on_id = database.insert(DBOpenHelper.TABLE_RECEIVED_ONLINE_MESSAGE, null, values);

                //---if added successfully---
                if (r_on_id > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_RECEIVED_ONLINE_MESSAGE, r_on_id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                break;
            case USER_PROFILE_PICTURES:
                long upp = database.insert(DBOpenHelper.TABLE_USER_PROFILE_PICTURE, null, values);

                //---if added successfully---
                if (upp > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_USER_PROFILE_PICTURE, upp);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                break;
            case MATCH_CHATS:
                long mc = database.insert(DBOpenHelper.TABLE_CHAT, null, values);

                //---if added successfully---
                if (mc > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_CHAT, mc);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                break;
            case MATCH_CHAT_MESSAGES:
                long mcm = database.insert(DBOpenHelper.TABLE_MESSAGE, null, values);

                //---if added successfully---
                if (mcm > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_MESSAGE, mcm);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                break;
            case CHOICES:
                long cm = database.insert(DBOpenHelper.TABLE_CHOICE, null, values);

                //---if added successfully---
                if (cm > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_CHOICE, cm);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                break;
            case REPORTED_USERS:
                long ru = database.insert(DBOpenHelper.TABLE_REPORTED_USER, null, values);

                //---if added successfully---
                if (ru > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_REPORTED_USER, ru);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                break;

            default: throw new SQLException("Failed to insert row into " + uri);
        }
        return _uri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        Uri _uri = null;
        int result;

        switch (uriMatcher.match(uri)){
            case MESSAGE_QUEUE_ITEMS:
                long message_queue_item_id = database.delete(DBOpenHelper.TABLE_MESSAGE_QUEUE, selection, selectionArgs);

                //---if added successfully---
                if (message_queue_item_id > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_MESSAGE_QUEUE, message_queue_item_id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)message_queue_item_id;
                break;
            case USERS:
                long u_id = database.delete(DBOpenHelper.TABLE_USER, selection, selectionArgs);

                //---if added successfully---
                if (u_id > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_USER, u_id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)u_id;
                break;
            case RETRIEVED_OFFLINE_MESSAGE_UUIDS:
                long r_off_id = database.delete(DBOpenHelper.TABLE_RETRIEVED_OFFLINE_MESSAGE_UUID, selection, selectionArgs);

                //---if added successfully---
                if (r_off_id > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_RETRIEVED_OFFLINE_MESSAGE_UUID, r_off_id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)r_off_id;
                break;
            case RECEIVED_ONLINE_MESSAGES:
                long r_on_id = database.delete(DBOpenHelper.TABLE_RECEIVED_ONLINE_MESSAGE, selection, selectionArgs);

                //---if added successfully---
                if (r_on_id > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_RECEIVED_ONLINE_MESSAGE, r_on_id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)r_on_id;
                break;
            case USER_PROFILE_PICTURES:
                long upp = database.delete(DBOpenHelper.TABLE_USER_PROFILE_PICTURE, selection, selectionArgs);

                //---if added successfully---
                if (upp > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_USER_PROFILE_PICTURE, upp);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)upp;
                break;
            case MATCH_CHATS:
                long mc = database.delete(DBOpenHelper.TABLE_CHAT, selection, selectionArgs);

                //---if added successfully---
                if (mc > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_CHAT, mc);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)mc;
                break;
            case MATCH_CHAT_MESSAGES:
                long mcm = database.delete(DBOpenHelper.TABLE_MESSAGE, selection, selectionArgs);

                //---if added successfully---
                if (mcm > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_MESSAGE, mcm);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)mcm;
                break;
            case CHOICES:
                long cm = database.delete(DBOpenHelper.TABLE_CHOICE, selection, selectionArgs);

                //---if added successfully---
                if (cm > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_CHOICE, cm);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)cm;
                break;
            case REPORTED_USERS:
                long ru = database.delete(DBOpenHelper.TABLE_REPORTED_USER, selection, selectionArgs);

                //---if added successfully---
                if (ru > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_REPORTED_USER, ru);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)ru;
                break;

            default: throw new SQLException("Failed to delete row in " + uri);
        }
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        Uri _uri = null;
        int result;

        switch (uriMatcher.match(uri)){
            case MESSAGE_QUEUE_ITEMS:
                long message_queue_item_id = database.update(DBOpenHelper.TABLE_MESSAGE_QUEUE, values, selection, selectionArgs);

                //---if added successfully---
                if (message_queue_item_id > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_MESSAGE_QUEUE, message_queue_item_id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)message_queue_item_id;
                break;
            case USERS:
                long u_id = database.update(DBOpenHelper.TABLE_USER, values, selection, selectionArgs);

                //---if added successfully---
                if (u_id > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_USER, u_id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)u_id;
                break;
            case RETRIEVED_OFFLINE_MESSAGE_UUIDS:
                long r_off_id = database.update(DBOpenHelper.TABLE_RETRIEVED_OFFLINE_MESSAGE_UUID, values, selection, selectionArgs);

                //---if added successfully---
                if (r_off_id > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_RETRIEVED_OFFLINE_MESSAGE_UUID, r_off_id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)r_off_id;
                break;
            case RECEIVED_ONLINE_MESSAGES:
                long r_on_id = database.update(DBOpenHelper.TABLE_RECEIVED_ONLINE_MESSAGE, values, selection, selectionArgs);

                //---if added successfully---
                if (r_on_id > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_RECEIVED_ONLINE_MESSAGE, r_on_id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)r_on_id;
                break;
            case USER_PROFILE_PICTURES:
                long upp = database.update(DBOpenHelper.TABLE_USER_PROFILE_PICTURE, values, selection, selectionArgs);

                //---if added successfully---
                if (upp > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_USER_PROFILE_PICTURE, upp);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)upp;
                break;
            case MATCH_CHATS:
                long mc = database.update(DBOpenHelper.TABLE_CHAT, values, selection, selectionArgs);

                //---if added successfully---
                if (mc > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_CHAT, mc);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)mc;
                break;
            case MATCH_CHAT_MESSAGES:
                long mcm = database.update(DBOpenHelper.TABLE_MESSAGE, values, selection, selectionArgs);

                //---if added successfully---
                if (mcm > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_MESSAGE, mcm);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)mcm;
                break;
            case CHOICES:
                long cm = database.update(DBOpenHelper.TABLE_CHOICE, values, selection, selectionArgs);

                //---if added successfully---
                if (cm > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_CHOICE, cm);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)cm;
                break;
            case REPORTED_USERS:
                long ru = database.update(DBOpenHelper.TABLE_REPORTED_USER, values, selection, selectionArgs);

                //---if added successfully---
                if (ru > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_REPORTED_USER, ru);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }

                result = (int)ru;
                break;

            default: throw new SQLException("Failed to update row in " + uri);
        }
        return result;
    }
}
