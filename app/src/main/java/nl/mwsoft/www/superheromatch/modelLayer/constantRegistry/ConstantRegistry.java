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
package nl.mwsoft.www.superheromatch.modelLayer.constantRegistry;

public class ConstantRegistry {

    public static final String DEFAULT_USER_ID = "default";

    public static final String IMAGE = "image";
    public static final String TEXT = "text";

    public static final int INTRO_VIEW_PAGER_FIRST_PAGE = 0;
    public static final int INTRO_VIEW_PAGER_LAST_PAGE = 2;
    public static final int INTRO_VIEW_PAGER_ONE_BEFORE_LAST_PAGE = 1;
    public static final int INTRO_VIEW_PAGER_TOTAL_AMOUNT_PAGES = 3;

    public static final int MAIN_PROFILE_IMAGE_VIEW = 1;
    public static final int FIRST_PROFILE_IMAGE_VIEW = 2;
    public static final int SECOND_PROFILE_IMAGE_VIEW = 3;
    public static final int THIRD_PROFILE_IMAGE_VIEW = 4;
    public static final int FOURTH_PROFILE_IMAGE_VIEW = 5;

    public static final String LOADING = "LOADING";
    public static final String MATCH = "MATCH";
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String DONE = "done";

    public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1001;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1002;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1003;

    public static final int MAKE_MANAGE_CALLS = 0;
    public static final int ACCESS_FILES = 1;
    public static final int PERMISSION_DENIED = 2;
    public static final int ALL_PERMISSIONS_GRANTED = 3;

    public static final int SERVER_RESPONSE_ERROR = 500;

    // public static final String BASE_SERVER_URL = "https://192.168.0.64";
    public static final String BASE_SERVER_URL = "https://34.217.6.95";
    public static final String IMAGE_URL_PREFIX = "https:";
    public static final String CHAT_ENDPOINT = "/ws";

    public static final String SUPERHERO_REGISTER_PORT = ":3000";
    public static final String SUPERHERO_UPDATE_PORT = ":3100";
    public static final String SUPERHERO_SCREEN_PORT = ":3200";
    public static final String SUPERHERO_DELETE_PORT = ":3300";
    public static final String SUPERHERO_SUGGESTIONS_PORT = ":4000";
    public static final String SUPERHERO_CHOICE_PORT = ":4100";
    public static final String SUPERHERO_MATCH_PORT = ":4200";
    public static final String SUPERHERO_DELETE_MATCH_PORT = ":4300";
    public static final String SUPERHERO_PROFILE_PORT = ":4400";
    public static final String SUPERHERO_GET_MATCH_PORT = ":4500";
    public static final String SUPERHERO_CHAT_PORT = ":5000";
    public static final String SUPERHERO_OFFLINE_MESSAGE_PORT = ":5100";
    public static final String SUPERHERO_DELETE_OFFLINE_MESSAGE_PORT = ":5200";
    public static final String SUPERHERO_FIREBASE_MESSAGING_TOKEN_PORT = ":6000";
    public static final String SUPERHERO_REGISTER_MEDIA_PORT = ":7000";
    public static final String SUPERHERO_UPDATE_MEDIA_PORT = ":7100";
    public static final String SUPERHERO_DELETE_MEDIA_PORT = ":7200";
    public static final String SUPERHERO_REPORT_USER_PORT = ":9000";
    public static final String SUPERHERO_AUTH_PORT = ":9100";

    public static final String DATE_TIME_FORMAT = "yyyyMMdd_HHmmss";
    public static final String JPG_EXTENSION = ".jpg";
    public static final String IMAGE_NAME_PART1 = "JPEG_";
    public static final String IMAGE_NAME_PART2 = "_";
    public static final String PROJECTION_ORIENTATION = "orientation";
    public static final String PROJECTION_DATE_ADDED = "date_added";
    public static final String SORT_ORDER_DATE_ADDED_DESC = "date_added desc";
    public static final String TEMP_IMAGE_NAME = "temporary_file.jpg";
    public static final int MAX_HEIGHT = 1024;
    public static final int MAX_WIDTH = 1024;

    public static final int PICK_IMAGE_REQUEST = 1;
    public static final int REQUEST_IMAGE_CAPTURE = 2;
    public static final String EMPTY_STRING = "";
    public static final String SPACE_STRING = " ";
    public static final String EQUALS = "=";
    public static final String OPEN_CURLY_BRACES = "{";
    public static final String CLOSE_CURLY_BRACES = "}";
    public static final String COMMA = ",";
    public static final String DASH = "-";

    public static final String FRAGMENT_TAG = "fragment_tag";

    public static final String DOCUMENT_TYPE_IMAGE = "image/*";

    public static final String SUPERHERO_MATCH_PHONE_TO_VERIFY = "phoneToVerify";

    public static final String  SUPERHERO_MATCH_PRIVACY_POLICY_URL = "http://superhero-match.com/privacy-policy.html";
    public static final String  SUPERHERO_MATCH_TERMS_AND_POLICIES_URL = "http://superhero-match.com/terms-policies.html";
    public static final String  SUPERHERO_MATCH_GDPR_RIGHTS_URL = "http://superhero-match.com/gdpr-rights.html";

    public static final String  SUPERHERO_BIRTHDAY_FORMAT = "yyyy-MM-dd";

    public static final int PICK_PROFILE_IMAGE_REQUEST = 2;
    public static final String SELECT_PICTURE = "Select Picture";

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 20000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    public static final int REQUEST_CHECK_SETTINGS = 100;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public static final int MALE = 1;
    public static final int FEMALE = 2;
    public static final int BOTH = 3;

    public static final int PAGE_SIZE = 10;

    public static final int DEFAULT_AGE_MIN = 25;
    public static final int DEFAULT_AGE_MAX = 55;

    public static final int DEFAULT_DISTANCE_MAX = 50;
    public static final String DEFAULT_DISTANCE_UNIT = "km";

    public static final String DEFAULT_ACCOUNT_TYPE = "FREE";

    public static final String DEFAULT_COUNTRY = "Country";
    public static final String DEFAULT_CITY = "City";

    public static final double DEFAULT_LATITUDE = 0.0;
    public static final double DEFAULT_LONGITUDE = 0.0;

    public static final String KILOMETERS = "km";
    public static final String MILES = "mi";

    public static final int LIKE = 1;
    public static final int DISLIKE = 2;

    public static final String NEW_MATCH = "{data_type=new_match";
    public static final String DELETE_MATCH = "{data_type=delete_match";
    public static final String NEW_LIKE = "{data_type=new_like";
    public static final String NEW_MESSAGE = "{data_type=new_message";

    public static final String NEW_MATCH_INTENT = "NEW_MATCH_INTENT";
    public static final String NEW_MATCH_REQUEST = "NEW_MATCH_REQUEST";

    public static final String NEW_OFFLINE_MESSAGE_INTENT = "NEW_OFFLINE_MESSAGE_INTENT";
    public static final String NEW_OFFLINE_MESSAGE_REQUEST = "NEW_OFFLINE_MESSAGE_REQUEST";

    public static final int NEW_MATCH_PENDING_INTENT_REQUEST = 1000;
    public static final int NEW_OFFLINE_MESSAGE_PENDING_INTENT_REQUEST = 1001;

    public static final String ON_OPEN = "onOpen";
    public static final String MESSAGE_TYPE = "messageType";
    public static final String ON_MESSAGE = "message";

    public static final String SENDER_ID = "senderId";
    public static final String RECEIVER_ID = "receiverId";
    public static final String MESSAGE = "message";
    public static final String MESSAGE_CREATED = "createdAt";

    public static final String PROFILE_PICTURE_URL = "url";
    public static final String PROFILE_PICTURE_POSITION = "position";

    public static final String STATUS = "status";
    public static final int SERVER_STATUS_OK = 200;
    public static final String MAIN_PROFILE_PICTURE_URL = "mainProfilePictureURL";
    public static final String ON_UPLOAD_MAIN_PROFILE_PICTURE = "onUploadMainProfilePicture";
    public static final String ON_UPDATE_PROFILE_PICTURE = "updateProfilePictureURL";
    public static final String ON_UPDATE_EXISTING_PROFILE_PICTURE = "onUpdateProfilePicture";
    public static final String ON_UPLOAD_NEW_PROFILE_PICTURE = "onUploadNewProfilePicture";
    public static final String ON_DELETE_PROFILE_PICTURE = "onDeleteProfilePicture";

    public static final int CURRENT_POSITION_SUPERHERO_NAME = 0;
    public static final int CURRENT_POSITION_SUPERHERO_BIRTHDAY = 1;
    public static final int CURRENT_POSITION_DISTANCE_UNIT = 2;
    public static final int CURRENT_POSITION_SUPERHERO_GENDER = 3;
    public static final int CURRENT_POSITION_LOOKING_FOR_GENDER = 4;
    public static final int CURRENT_POSITION_SUPERHERO_SUPERPOWER = 5;
    public static final int CURRENT_POSITION_SUPERHERO_PROFILE_PICTURE = 6;

    public static final String REGISTER = "register";
    public static final String NAME = "name";
    public static final String EMAIL = "email";


    public static final String ID = "id";
    public static final String SUPERHERO_NAME = "superheroName";
    public static final String MAIN_PROFILE_PIC_URL = "mainProfilePicUrl";
    public static final String GENDER = "gender";
    public static final String LOOKING_FOR_GENDER = "lookingForGender";
    public static final String AGE = "age";
    public static final String LOOKING_FOR_AGE_MIN = "lookingForAgeMin";
    public static final String LOOKING_FOR_AGE_MAX = "lookingForAgeMax";
    public static final String LOOKING_FOR_DISTANCE_MAX = "lookingForDistanceMax";
    public static final String DISTANCE_UNIT = "distanceUnit";
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "lon";
    public static final String BIRTHDAY = "birthday";
    public static final String COUNTRY = "country";
    public static final String CITY = "city";
    public static final String SUPERPOWER = "superpower";
    public static final String ACCOUNT_TYPE = "accountType";
    public static final String FIREBASE_TOKEN = "firebaseToken";

    public static final int INTRO_FRAGMENTS_COUNT = 3;

    public static final int MESSAGE_HAS_NOT_BEEN_READ = 0;
    public static final int MESSAGE_HAS_BEEN_READ = 1;

    public static final int NUMBER_OF_SUGGESTIONS_BEFORE_SHOWING_AD = 7;

    public static final String  REPORT_REASON_FAKE_ACCOUNT = "FAKE_ACCOUNT";
    public static final String  REPORT_REASON_UNDER_AGE_OF_18 = "UNDER_AGE_OF_18";
    public static final String  REPORT_REASON_PICTURES_ARE_INAPPROPRIATE = "PICTURES_ARE_INAPPROPRIATE";

    public static final String SHARED_PREFERENCES = "nl.mwsoft.superheromatch";

    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";

    public static final int SERVER_STATUS_UNAUTHORIZED = 401;
}
