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

    public static final int MAKE_MANAGE_CALLS = 0;
    public static final int ACCESS_FILES = 1;
    public static final int PERMISSION_DENIED = 2;
    public static final int ALL_PERMISSIONS_GRANTED = 3;

    public static final int SERVER_RESPONSE_ERROR = 500;

    public static final String BASE_SERVER_URL = "https://192.168.0.101";
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
    public static final String SUPERHERO_CHAT_PORT = ":5000";
    public static final String SUPERHERO_FIREBASE_MESSAGING_TOKEN_PORT = ":6000";

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
    public static final String OPEN_SQUARE_BRACKETS = "[";
    public static final String CLOSE_SQUARE_BRACKETS = "]";
    public static final String OPEN_ROUND_BRACKETS = "(";
    public static final String CLOSE_ROUND_BRACKETS = ")";
    public static final String OPEN_CURLY_BRACES = "{";
    public static final String CLOSE_CURLY_BRACES = "}";
    public static final String FORWARD_SLASH = "/";
    public static final String LETTER_N = "N";
    public static final String COMMA = ",";
    public static final String POINT = ".";
    public static final String STAR = "*";
    public static final String SEMICOLON = ";";
    public static final String HASH_TAG = "#";
    public static final String MINUS = "-";
    public static final String DASH = "-";
    public static final String AT = "@";
    public static final String QUESTION_MARK = "?";
    public static final String PLUS = "+";
    public static final String ZERO = "0";
    public static final String DOUBLE_ZERO = "00";

    public static final String FRAGMENT_TAG = "fragment_tag";

    public static final String DOCUMENT_TYPE_IMAGE = "image/*";

    public static final String SUPERHERO_MATCH_PHONE_TO_VERIFY = "phoneToVerify";

    public static final String  SUPERHERO_MATCH_PRIVACY_POLICY_URL = "url";

    public static final String  SUPERHERO_BIRTHDAY_FORMAT = "yyyy-MM-dd";

    public static final int PICK_PROFILE_IMAGE_REQUEST = 2;
    public static final String SELECT_PICTURE = "Select Picture";

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 60000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 2000;
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

    public static final int NEW_MATCH_PENDING_INTENT_REQUEST = 1000;

    public static final String ON_OPEN = "onOpen";
    public static final String MESSAGE_TYPE = "messageType";
    public static final String ON_MESSAGE = "message";

    public static final String SENDER_ID = "senderId";
    public static final String RECEIVER_ID = "receiverId";
    public static final String MESSAGE = "message";
    public static final String MESSAGE_CREATED = "createdAt";

}
