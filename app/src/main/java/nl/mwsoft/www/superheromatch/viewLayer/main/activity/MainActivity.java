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
package nl.mwsoft.www.superheromatch.viewLayer.main.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.coordinator.RootCoordinator;
import nl.mwsoft.www.superheromatch.dependencyRegistry.DependencyRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.event.TextMessageEvent;
import nl.mwsoft.www.superheromatch.modelLayer.helper.compare.ProfilePicturePositionComparator;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.Chat;
import nl.mwsoft.www.superheromatch.modelLayer.model.Choice;
import nl.mwsoft.www.superheromatch.modelLayer.model.ChoiceResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.Message;
import nl.mwsoft.www.superheromatch.modelLayer.model.OutgoingMessage;
import nl.mwsoft.www.superheromatch.modelLayer.model.ProfilePicture;
import nl.mwsoft.www.superheromatch.modelLayer.model.ProfileResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.StatusResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.SuggestionsResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.Superhero;
import nl.mwsoft.www.superheromatch.modelLayer.model.TokenResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.UpdateResponse;
import nl.mwsoft.www.superheromatch.presenterLayer.main.MainPresenter;
import nl.mwsoft.www.superheromatch.viewLayer.dialog.loadingDialog.LoadingDialogFragment;
import nl.mwsoft.www.superheromatch.viewLayer.dialog.matchDialog.MatchDialog;
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.MatchesChatsAdapter;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.admob.AdFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.matches.MatchesChatsFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.SuggestionProfileFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.UserProfileEditFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.UserProfileFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.UserProfilePictureSettingsFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.UserProfileSettingsSuggestionsFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.suggestions.NoSuggestionsFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.suggestions.SuggestionFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private int currFragmentPosition;
    private ArrayList<Message> messages;
    @BindView(R.id.bnMain)
    BottomNavigationView navigation;
    @BindView(R.id.tlMain)
    Toolbar tlMain;
    private Unbinder unbinder;
    private RootCoordinator rootCoordinator;
    private MainPresenter mainPresenter;
    private Uri profilePicURI;
    private CompositeDisposable disposable;
    private Disposable subscribeSuggestions;
    private Disposable subscribeUpdateProfile;
    private Disposable subscribeUploadChoice;
    private Disposable subscribeUploadMatch;
    private Disposable subscribeDeleteMatch;
    private Disposable subscribeGetProfile;
    private Disposable subscribeDeleteProfilePicture;
    private Disposable subscribeReportUser;
    private Disposable subscribeDeleteAccount;
    Disposable subscribeUpdateUserToken;
    private Disposable subscribeGetAccessToken;
    private Disposable subscribeRefreshToken;
    private LoadingDialogFragment loadingDialogFragment;
    private MatchDialog matchDialog;
    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private Location currentLocation;
    private boolean loadingDialogIsActive;
    @BindView(R.id.frame_superhero_details)
    FrameLayout suggestionFrameLayout;
    FrameLayout mainFrameLayout;
    public int currentProfileImageView = 1;
    private ArrayList<String> superheroIds;
    private ArrayList<String> retrievedSuperheroIds;
    private ArrayList<Superhero> suggestions;
    private int currentSuggestion = 0;
    private Socket chatSocket;
    private Socket updateProfilePictureSocket;
    private Chat currentChat;
    private Superhero profile;
    private boolean isAddingNewProfilePicture;
    private int currentProfilePicturePosition = 0;
    private final static int WAIT_TIME = 5000;
    private Handler uiHandler;
    public static final int NUMBER_OF_ADS = 2;
    private AdLoader adLoader;
    private ArrayList<UnifiedNativeAd> nativeAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        DependencyRegistry.shared.inject(this);
        setSupportActionBar(tlMain);

        init();

        loadNativeAds();

        setUpConnectionToChatServer();
        setUpConnectionToUpdateMediaServer();

        deleteOldChoices();

        if (mainPresenter.getUserId(this).equals("default")) {
            navigateToVerifyIdentity(this);

            return;
        }

        updateToken(mainPresenter.getUserId(this));

        navigation.setOnNavigationItemSelectedListener(myOnNavigationItemSelectedListener);

        handleNotificationAction();

        if (checkLocationPermission()) {
            showLoadingDialog();
            initLocationUpdateService();
            startLocationUpdates();
        }
    }
    
    // region AdMob

    private void loadNativeAds() {
        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.ad_unit_id));
        adLoader = builder.forUnifiedNativeAd(
                new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // A native ad loaded successfully, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        nativeAds.add(unifiedNativeAd);
                        if (!adLoader.isLoading()) {
                            // insertAdsInMenuItems();
                        }
                    }
                }).withAdListener(
                new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // A native ad failed to load, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        Log.e("MainActivity", "The previous native ad failed to load. Attempting to"
                                + " load another.");
                        if (!adLoader.isLoading()) {
                            // insertAdsInMenuItems();
                        }
                    }
                }).build();

        // Load the Native ads.
        adLoader.loadAds(new AdRequest.Builder().build(), NUMBER_OF_ADS);
    }

    public UnifiedNativeAd getNativeAd() {
        return nativeAds.get(0);
    }

    // endregion

    // region Delete Choices

    private void deleteOldChoices() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            ArrayList<Choice> choices = mainPresenter.getAllChoices(MainActivity.this);
            for (Choice choice : choices) {
                Date choiceCratedAt = simpleDateFormat.parse(choice.getCreatedAt());
                Date now = simpleDateFormat.parse(mainPresenter.getDateTime());

                if (mainPresenter.isOlderThanOneDay(choiceCratedAt, now)) {
                    mainPresenter.deleteChoice(choice.getChoiceId(), MainActivity.this);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // endregion

    // region Socket.IO

    private void setUpConnectionToChatServer() {
        // default settings for all sockets
        IO.setDefaultOkHttpWebSocketFactory(OkHttpClientManager.setUpSecureClientWithoutAuthorization());
        IO.setDefaultOkHttpCallFactory(OkHttpClientManager.setUpSecureClientWithoutAuthorization());

        // set as an option
        IO.Options opts = new IO.Options();
        opts.transports = new String[]{io.socket.engineio.client.transports.WebSocket.NAME};
        opts.callFactory = OkHttpClientManager.setUpSecureClientWithoutAuthorization();
        opts.webSocketFactory = OkHttpClientManager.setUpSecureClientWithoutAuthorization();
        try {
            chatSocket = IO.socket(
                    ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_CHAT_PORT),
                    opts
            );

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        chatSocket.connect();

        chatSocket.on(ConstantRegistry.ON_MESSAGE, handleIncomingMessages);

        chatSocket.emit(ConstantRegistry.ON_OPEN, mainPresenter.getUserId(MainActivity.this));
    }

    private Emitter.Listener handleIncomingMessages = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String senderId = "";
                    String messageText = "";
                    String createdAt = "";

                    try {
                        senderId = data.getString(ConstantRegistry.SENDER_ID);
                        messageText = data.getString(ConstantRegistry.MESSAGE);
                        createdAt = data.getString(ConstantRegistry.MESSAGE_CREATED);
                        createdAt = mainPresenter.convertFromUtcToLocal(createdAt);

                        if (getCurrentChat() == null) {
                            saveMessageForNotCurrentChat(senderId, messageText, createdAt);

                            return;
                        }

                        if (!getCurrentChat().getMatchedUserId().equals(senderId)) {
                            saveMessageForNotCurrentChat(senderId, messageText, createdAt);

                            return;
                        }

                        saveMessageForCurrentChat(senderId, messageText, createdAt);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private void setUpConnectionToUpdateMediaServer() {
        // default settings for all sockets
        IO.setDefaultOkHttpWebSocketFactory(OkHttpClientManager.setUpSecureClientWithoutAuthorization());
        IO.setDefaultOkHttpCallFactory(OkHttpClientManager.setUpSecureClientWithoutAuthorization());

        // set as an option
        IO.Options opts = new IO.Options();
        opts.transports = new String[]{io.socket.engineio.client.transports.WebSocket.NAME};
        opts.callFactory = OkHttpClientManager.setUpSecureClientWithoutAuthorization();
        opts.webSocketFactory = OkHttpClientManager.setUpSecureClientWithoutAuthorization();
        try {
            updateProfilePictureSocket = IO.socket(
                    ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_UPDATE_MEDIA_PORT),
                    opts
            );

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        updateProfilePictureSocket.connect();

        updateProfilePictureSocket.on(ConstantRegistry.ON_UPDATE_PROFILE_PICTURE, handleIncomingProfilePictureUpdateMessage);
    }

    private Emitter.Listener handleIncomingProfilePictureUpdateMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String url = "";
                    int position = 0;

                    try {
                        url = data.getString(ConstantRegistry.PROFILE_PICTURE_URL);
                        position = data.getInt(ConstantRegistry.PROFILE_PICTURE_POSITION);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private void saveMessageForCurrentChat(String senderId, String messageText, String createdAt) {
        Message chatMessage = createMessage(senderId, messageText, currentChat, createdAt);
        mainPresenter.insertChatMessage(chatMessage, ConstantRegistry.MESSAGE_HAS_BEEN_READ, MainActivity.this);

        // Update Chat UI to display new message.
        messages.add(chatMessage);
        EventBus.getDefault().post(new TextMessageEvent(messages));
    }

    private void saveMessageForNotCurrentChat(String senderId, String messageText, String createdAt) {
        Chat tempChat = mainPresenter.getChatByMatchId(MainActivity.this, senderId);
        Message chatMessage = createMessage(senderId, messageText, tempChat, createdAt);
        mainPresenter.insertChatMessage(chatMessage, ConstantRegistry.MESSAGE_HAS_NOT_BEEN_READ, MainActivity.this);
    }

    private Message createMessage(String senderId, String messageText, Chat chat, String createdAt) {
        Message chatMessage = new Message();
        chatMessage.setMessageChatId(chat.getChatId());
        chatMessage.setMessageSenderId(senderId);
        chatMessage.setMessageText(messageText);
        chatMessage.setMessageCreated(createdAt);

        return chatMessage;
    }

    // endregion

    private HashMap<String, Object> configureUpdateFirebaseTokenRequestBody(String userId, String token) {
        HashMap<String, Object> reqBody = new HashMap<>();

        reqBody.put("superheroID", userId);
        reqBody.put("token", token);

        return reqBody;
    }

    private void updateToken(String userId) {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(MainActivity.class.getName(), "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        HashMap<String, Object> reqBody = new HashMap<>();
                        reqBody = configureUpdateFirebaseTokenRequestBody(userId, token);

                        updateFirebaseToken(reqBody, token);
                    }
                });
    }

    private HashMap<String, Object> configureMatchRequestBody(
            MainPresenter mainPresenter,
            Superhero suggestion,
            String matchId
    ) {
        HashMap<String, Object> reqBodyChoice = new HashMap<>();

        reqBodyChoice.put("matchId", matchId);
        reqBodyChoice.put("superheroId", mainPresenter.getUserId(this));
        reqBodyChoice.put("matchedSuperheroId", suggestion.getId());

        return reqBodyChoice;
    }

    private HashMap<String, Object> configureChoiceRequestBody(MainPresenter mainPresenter, Superhero suggestion, int choice) {
        HashMap<String, Object> reqBodyChoice = new HashMap<>();

        reqBodyChoice.put("superheroID", mainPresenter.getUserId(this));
        reqBodyChoice.put("chosenSuperheroID", suggestion.getId());
        reqBodyChoice.put("choice", choice);

        return reqBodyChoice;
    }

    private HashMap<String, Object> configureSuggestionsRequestBody(MainPresenter mainPresenter) {
        HashMap<String, Object> reqBodySuggestions = new HashMap<>();

        reqBodySuggestions.put("id", mainPresenter.getUserId(this));
        reqBodySuggestions.put("lookingForGender", mainPresenter.getUserLookingForGender(this));
        reqBodySuggestions.put("gender", mainPresenter.getUserGender(this));
        reqBodySuggestions.put("lookingForAgeMin", mainPresenter.getUserLookingForMinAge(this));
        reqBodySuggestions.put("lookingForAgeMax", mainPresenter.getUserLookingForMaxAge(this));
        reqBodySuggestions.put("maxDistance", mainPresenter.getUserLookingForMaxDistance(this));
        reqBodySuggestions.put("distanceUnit", mainPresenter.getUserDistanceUnit(this));
        reqBodySuggestions.put("lat", mainPresenter.getUserLat(this));
        reqBodySuggestions.put("lon", mainPresenter.getUserLon(this));

        // 1. Check if superheroIds is empty. If so, that means it is ES request.
        reqBodySuggestions.put("isEsRequest", false);

        if (this.superheroIds.size() == 0) {
            reqBodySuggestions.put("isEsRequest", true);
        }

        // 2. If superheroIds is not empty, then pop maximum first 10 ids from superheroIds
        // and put them in reqBodySuggestions --> superheroIds.
        ArrayList<String> superherosToBeFetched = new ArrayList<>();
        ArrayList<Integer> indicesToBeDeleted = new ArrayList<>();

        if (this.superheroIds.size() > 0) {
            int index = 0;

            for (String superheroId : this.superheroIds) {
                if (superherosToBeFetched.size() == ConstantRegistry.PAGE_SIZE) {
                    break;
                }

                superherosToBeFetched.add(superheroId);
                // After superhero id has been added to the list of ids to be fetched,
                // it can be removed there is no need to fetch the same suggestions.
                indicesToBeDeleted.add(index);
                index++;
            }

            for (Integer ignored : indicesToBeDeleted) {
                this.superheroIds.remove(0);
            }
        }

        reqBodySuggestions.put("superheroIds", superherosToBeFetched);

        // Add existing matched user ids to retrievedSuperheroIds in order to
        // prevent the matched users to be returned as suggestions.
        ArrayList<Chat> chats = mainPresenter.getAllChats(MainActivity.this);
        for (Chat chat : chats) {
            this.retrievedSuperheroIds.add(chat.getMatchedUserId());
        }

        // Add all user ids that have been liked, disliked or superliked within last day.
        // This is in order to prevent the same users showing up in search result too frequently.
        ArrayList<Choice> choices = mainPresenter.getAllChoices(MainActivity.this);
        for (Choice choice : choices) {
            this.retrievedSuperheroIds.add(choice.getChosenUserId());
        }

        // Add reported user ids. This is in order to prevent reported users showing up in results.
        ArrayList<String> reportedUsers = mainPresenter.getAllReportedUsers(MainActivity.this);
        this.retrievedSuperheroIds.addAll(reportedUsers);

        reqBodySuggestions.put("retrievedSuperheroIds", this.retrievedSuperheroIds);

        return reqBodySuggestions;
    }

    private HashMap<String, Object> configureReportUserRequestBody(String reportingUserId, String reportedUserId, String reportReason) {
        HashMap<String, Object> reqBody = new HashMap<>();

        reqBody.put("reportingUserID", reportingUserId);
        reqBody.put("reportedUserID", reportedUserId);
        reqBody.put("reason", reportReason);

        return reqBody;
    }

    public HashMap<String, Object> configureDeleteAccountRequestBody(String userId) {
        HashMap<String, Object> reqBody = new HashMap<>();

        reqBody.put("userId", userId);

        return reqBody;
    }

    public int calculateUserAge(Date currentDate, String birthday) {
        SimpleDateFormat sdf = new SimpleDateFormat(ConstantRegistry.SUPERHERO_BIRTHDAY_FORMAT, Locale.getDefault());

        long years = 0;

        try {
            Date birthDate = sdf.parse(birthday);

            long diff = currentDate.getTime() - birthDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            years = days / 365;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (int) years;
    }

    private HashMap<String, Object> configureUpdateProfileRequestBody(MainPresenter mainPresenter) {
        HashMap<String, Object> reqBodyUpdateProfile = new HashMap<>();
        reqBodyUpdateProfile.put("id", mainPresenter.getUserId(this));
        reqBodyUpdateProfile.put("lookingForGender", mainPresenter.getUserLookingForGender(MainActivity.this));
        reqBodyUpdateProfile.put("age", calculateUserAge(new Date(), mainPresenter.getUserBirthday(MainActivity.this)));
        reqBodyUpdateProfile.put("gender", mainPresenter.getUserGender(this));
        reqBodyUpdateProfile.put("lookingForAgeMin", mainPresenter.getUserLookingForMinAge(MainActivity.this));
        reqBodyUpdateProfile.put("lookingForAgeMax", mainPresenter.getUserLookingForMaxAge(MainActivity.this));
        reqBodyUpdateProfile.put("lookingForDistanceMax", mainPresenter.getUserLookingForMaxDistance(MainActivity.this));
        reqBodyUpdateProfile.put("distanceUnit", mainPresenter.getUserDistanceUnit(MainActivity.this));
        reqBodyUpdateProfile.put("lat", mainPresenter.getUserLat(MainActivity.this));
        reqBodyUpdateProfile.put("lon", mainPresenter.getUserLon(MainActivity.this));
        reqBodyUpdateProfile.put("country", mainPresenter.getUserCountry(MainActivity.this));
        reqBodyUpdateProfile.put("city", mainPresenter.getUserCity(MainActivity.this));
        reqBodyUpdateProfile.put("superPower", mainPresenter.getUserSuperPower(MainActivity.this));
        reqBodyUpdateProfile.put("accountType", mainPresenter.getUserAccountType(MainActivity.this));

        return reqBodyUpdateProfile;
    }

    private HashMap<String, Object> configureGetSuperheroProfileRequestBody(String superheroId) {
        HashMap<String, Object> reqBodyUpdateProfile = new HashMap<>();
        reqBodyUpdateProfile.put("superheroId", superheroId);

        return reqBodyUpdateProfile;
    }

    private void init() {
        messages = new ArrayList<>();
        disposable = new CompositeDisposable();
        superheroIds = new ArrayList<>();
        retrievedSuperheroIds = new ArrayList<>();
        suggestions = new ArrayList<>();
        uiHandler = new Handler();
        nativeAds = new ArrayList<>();
        MobileAds.initialize(this, getString(R.string.admob_app_id));
    }

    public void configureWith(RootCoordinator rootCoordinator, MainPresenter mainPresenter) {
        this.rootCoordinator = rootCoordinator;
        this.mainPresenter = mainPresenter;
    }

    public void navigateToIntro(Context context) {
        rootCoordinator.navigateToIntroFromMain(context);
        finish();
    }

    public void navigateToVerifyIdentity(Context context) {
        rootCoordinator.navigateToVerifyIdentityActivity(context);
        finish();
    }

    public void navigateToRegister(Context context) {
        rootCoordinator.navigateToRegisterFromMain(context);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindButterKnife();
        stopLocationUpdates();
        socketDisconnect();
    }

    private void socketDisconnect() {
        if (chatSocket != null && chatSocket.connected()) {
            chatSocket.disconnect();
        }
    }

    private void stopLocationUpdates() {
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    private void unbindButterKnife() {
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener myOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_suggestions:
                    currFragmentPosition = 1;

                    if (suggestions.size() > 0) {
                        fragment = SuggestionFragment.newInstance(suggestions.get(currentSuggestion));
                        loadFragment(fragment);

                        return true;
                    }

                    loadFragment(NoSuggestionsFragment.newInstance());

                    getSuggestions(configureSuggestionsRequestBody(mainPresenter), true);

                    return true;
                case R.id.navigation_matches:
                    currFragmentPosition = 3;

                    ArrayList<Chat> chats = mainPresenter.getAllChats(MainActivity.this);
                    fragment = MatchesChatsFragment.newInstance(chats);
                    loadFragment(fragment);

                    return true;
                case R.id.navigation_profile:
                    currFragmentPosition = 5;

                    getSuperheroProfile(
                            configureGetSuperheroProfileRequestBody(mainPresenter.getUserId(MainActivity.this))
                    );

                    return true;
            }

            return false;
        }
    };

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.frame_main_container, fragment);
        transaction.commit();
    }

    public void loadNextSuggestion(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.frame_main_container, fragment);
        transaction.commit();
    }

    public void loadBackStackFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.frame_main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void closeSuggestionDescriptionWindow() {
        Animation slideDown = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_down);
        suggestionFrameLayout.setVisibility(View.GONE);
        suggestionFrameLayout.startAnimation(slideDown);
    }

    public void openSuggestionDescriptionWindow() {
        Animation slideUp = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_up);
        suggestionFrameLayout.setVisibility(View.VISIBLE);
        suggestionFrameLayout.startAnimation(slideUp);
    }

    public void loadSuggestionDescriptionFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_superhero_details, fragment);
        transaction.commit();
    }

    public void loadSuggestionUserProfileFragment(Superhero superhero) {
        loadBackStackFragment(UserProfileFragment.newInstance(superhero));
    }

    public void loadSuggestionUserProfileSettingsFragment() {
        loadBackStackFragment(UserProfileSettingsSuggestionsFragment.newInstance());
    }

    public void loadSuggestionUserProfileEditFragment() {
        loadBackStackFragment(UserProfileEditFragment.newInstance());
    }

    public void loadProfilePictureSettingsFragment(Superhero superhero) {
        ArrayList<ProfilePicture> profilePictures = new ArrayList<>();
        ProfilePicture mainProfilePicture = new ProfilePicture();
        mainProfilePicture.setSuperheroId(superhero.getId());
        mainProfilePicture.setPosition(0);
        mainProfilePicture.setProfilePicUrl(superhero.getMainProfilePicUrl());
        profilePictures.add(mainProfilePicture);

        if (superhero.getProfilePictures() != null) {
            profilePictures.addAll(superhero.getProfilePictures());
        }

        loadBackStackFragment(UserProfilePictureSettingsFragment.newInstance(profilePictures));
    }

    public ArrayList<Message> getMessages(String chatId) {
        messages.addAll(mainPresenter.getAllMessagesForChatWithId(MainActivity.this, chatId));

        return messages;
    }

    public void sendMessageClickListener(String message, String receiverId) {
        Chat tempChat = mainPresenter.getChatByMatchId(MainActivity.this, receiverId);

        Message msg = new Message();
        msg.setMessageChatId(tempChat.getChatId());
        msg.setMessageText(message);
        msg.setMessageSenderId(mainPresenter.getUserId(MainActivity.this));
        msg.setMessageCreated(getDateTime());

        messages.add(msg);
        EventBus.getDefault().post(new TextMessageEvent(messages));

        mainPresenter.insertChatMessage(msg, ConstantRegistry.MESSAGE_HAS_BEEN_READ, MainActivity.this);

        OutgoingMessage outgoingMessage = new OutgoingMessage(
                ConstantRegistry.ON_MESSAGE,
                mainPresenter.getUserId(MainActivity.this),
                receiverId,
                message
        );

        chatSocket.emit(ConstantRegistry.ON_MESSAGE, outgoingMessage);
    }

    public String getUserId() {
        return mainPresenter.getUserId(MainActivity.this);
    }

    public String getDateTime() {
        return mainPresenter.getDateTime();
    }

    public String getContactNameById(Context context, String messageSenderId) {
        return mainPresenter.getChatByMatchId(context, messageSenderId).getChatName();
    }

    public void setToolbarStatusBarColorBlack() {
        hideBottomNavigation();
        changeToolbarColor(MainActivity.this, R.color.colorBlack);
        changeStatusBarColor(MainActivity.this, R.color.colorBlack);
    }

    public void changeStatusBarColor(Context context, int colorId) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(ContextCompat.getColor(context, colorId));
    }

    public void hideBottomNavigation() {
        if (navigation.getVisibility() == View.VISIBLE) {
            navigation.setVisibility(View.GONE);
        }
    }

    public void showBottomNavigation() {
        if (navigation.getVisibility() == View.GONE) {
            navigation.setVisibility(View.VISIBLE);
        }
    }

    public void setToolbarStatusBarColorPrimary() {
        showBottomNavigation();
        changeToolbarColor(MainActivity.this, R.color.colorPrimary);
        changeStatusBarColor(MainActivity.this, R.color.colorPrimary);
    }

    public void changeToolbarColor(Context context, int colorId) {
        tlMain.setBackgroundColor(ContextCompat.getColor(context, colorId));
    }

    public void setCurrentProfileImageView(int currentProfileImageView) {
        this.currentProfileImageView = currentProfileImageView;
    }

    public int getCurrentProfileImageView() {
        return currentProfileImageView;
    }

    public void showProfilePicChoice() {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType(ConstantRegistry.DOCUMENT_TYPE_IMAGE);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);//Intent.ACTION_OPEN_DOCUMENT Intent.ACTION_GET_CONTENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, ConstantRegistry.SELECT_PICTURE), ConstantRegistry.PICK_PROFILE_IMAGE_REQUEST);
    }

    public void showPopupDeniedPermission(View v) {
        LayoutInflater layoutInflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.permissions_denied_pop_up, null);
        Button btnPermissionsRequestDeniedGrant = (Button) popupView.findViewById(R.id.btnPermissionsRequestDeniedGrant);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.showAsDropDown(v);

        btnPermissionsRequestDeniedGrant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    public void showPopupProfilePictureInformation() {
        View v = findViewById(android.R.id.content).getRootView();
        LayoutInflater layoutInflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.profile_picture_info_pop_up, null);
        Button btnProfilePictureInformation = (Button) popupView.findViewById(R.id.btnPermissionsRequestDeniedGrant);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.showAsDropDown(v);

        btnProfilePictureInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    public void showPopupReportUser(String userId, Superhero superhero) {
        View v = findViewById(android.R.id.content).getRootView();
        LayoutInflater layoutInflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.report_user_pop_up, null);
        ImageView ivCancelReportUser = (ImageView) popupView.findViewById(R.id.ivCancelReportUser);
        Button btnReasonFakeAccount = (Button) popupView.findViewById(R.id.btnReasonFakeAccount);
        Button btnReasonUserIsUnderAge = (Button) popupView.findViewById(R.id.btnReasonUserIsUnderAge);
        Button btnReasonUsersPhotosAreInappropriate = (Button) popupView.findViewById(R.id.btnReasonUsersPhotosAreInappropriate);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.showAsDropDown(v);


        ivCancelReportUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        btnReasonFakeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.insertReportedUser(superhero.getId(), MainActivity.this);

                HashMap<String, Object> reportUserRequestBody = configureReportUserRequestBody(
                        userId,
                        superhero.getId(),
                        ConstantRegistry.REPORT_REASON_FAKE_ACCOUNT
                );

                reportUser(reportUserRequestBody);

                popupWindow.dismiss();

                loadNextSuggestion();
            }
        });

        btnReasonUserIsUnderAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.insertReportedUser(superhero.getId(), MainActivity.this);

                HashMap<String, Object> reportUserRequestBody = configureReportUserRequestBody(
                        userId,
                        superhero.getId(),
                        ConstantRegistry.REPORT_REASON_UNDER_AGE_OF_18
                );

                reportUser(reportUserRequestBody);

                popupWindow.dismiss();

                loadNextSuggestion();
            }
        });

        btnReasonUsersPhotosAreInappropriate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.insertReportedUser(superhero.getId(), MainActivity.this);

                HashMap<String, Object> reportUserRequestBody = configureReportUserRequestBody(
                        userId,
                        superhero.getId(),
                        ConstantRegistry.REPORT_REASON_PICTURES_ARE_INAPPROPRIATE
                );
                reportUser(reportUserRequestBody);

                popupWindow.dismiss();

                loadNextSuggestion();
            }
        });
    }

    private void processUpdateProfilePic(Uri uri) {
        showLoadingDialog();

        Runnable onUi = new Runnable() {
            @Override
            public void run() {
                closeLoadingDialog();
                navigation.setSelectedItemId(R.id.navigation_profile);
            }
        };

        Runnable background = new Runnable() {
            @Override
            public void run() {
                try {
                    String encodedImage = mainPresenter.encodeImageToString(MainActivity.this, uri);

                    if (encodedImage.equals(ConstantRegistry.ERROR)) {
                        Toast.makeText(MainActivity.this, R.string.smth_went_wrong, Toast.LENGTH_LONG).show();

                        return;
                    }

                    int position = 0;

                    if (isAddingNewProfilePicture) {
                        if (getProfile().getProfilePictures() == null) {
                            // Increment by 1 because position 0 is the main profile picture, so when adding
                            // new picture it will go into position 1.
                            position = position + 1;
                            uploadProfileImageToServer(encodedImage, onUi, position);

                            return;
                        }

                        position = getProfile().getProfilePictures().size() + 1;

                        uploadProfileImageToServer(encodedImage, onUi, position);

                        return;
                    }

                    // Update existing picture.
                    uploadProfileImageToServer(encodedImage, onUi, getCurrentProfilePicturePosition());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(background).start();
    }

    private void uploadProfileImageToServer(String encodedImage, Runnable onUi, int position) {
        updateProfilePictureSocket.emit(
                ConstantRegistry.ON_UPDATE_PROFILE_PICTURE,
                mainPresenter.getUserId(MainActivity.this),
                encodedImage,
                position
        );

        triggerOnUICloseDialog(onUi);
    }

    private void triggerOnUICloseDialog(Runnable onUi) {
        try {
            Thread.sleep(WAIT_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        uiHandler.post(onUi);
    }


    public void processDeleteProfilePic(int position) {
        showLoadingDialog();

        Runnable onUi = new Runnable() {
            @Override
            public void run() {
                closeLoadingDialog();
                navigation.setSelectedItemId(R.id.navigation_profile);
            }
        };

        Runnable background = new Runnable() {
            @Override
            public void run() {
                deleteProfilePicture(mainPresenter.getUserId(MainActivity.this), position);

                triggerOnUICloseDialog(onUi);
            }
        };

        new Thread(background).start();
    }

    public boolean accessFilesPermissionIsGranted() {
        return ContextCompat.checkSelfPermission(
                MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissionWriteExternalStorage() {
        ActivityCompat.requestPermissions(
                MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                ConstantRegistry.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ConstantRegistry.MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        initLocationUpdateService();
                        startLocationUpdates();
                    }
                }

                return;
            }
            case ConstantRegistry.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showProfilePicChoice();
                } else {
                    showPopupDeniedPermission(findViewById(android.R.id.content).getRootView());
                }

                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ConstantRegistry.PICK_PROFILE_IMAGE_REQUEST &&
                resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            processUpdateProfilePic(uri);
        }
    }

    private void closeLoadingDialog() {
        if (loadingDialogFragment != null) {
            loadingDialogFragment.dismiss();
        }

        loadingDialogIsActive = false;
    }

    private void showLoadingDialog() {
        loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.setCancelable(false);
        loadingDialogFragment.show(getSupportFragmentManager(), ConstantRegistry.LOADING);
        loadingDialogIsActive = true;
    }

    public void showMatchDialog(Superhero suggestion) {
        matchDialog = MatchDialog.newInstance(suggestion);
        matchDialog.setCancelable(true);
        matchDialog.show(getSupportFragmentManager(), ConstantRegistry.MATCH);
    }

    public void closeMatchDialog() {
        if (matchDialog != null) {
            matchDialog.dismiss();
        }
    }

    public void openChatsFragment() {
        navigation.setSelectedItemId(R.id.navigation_matches);
    }

    private void getSuggestions(HashMap<String, Object> requestBody, boolean isInitialRequest) {
        if (!loadingDialogIsActive) {
            showLoadingDialog();
        }

        subscribeSuggestions = mainPresenter.getSuggestions(requestBody, MainActivity.this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((SuggestionsResponse res) -> {
                    stopLocationUpdates();

                    closeLoadingDialog();

                    if (res.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                        handleError();

                        return;
                    }

                    if (res.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                        HashMap<String, Object> reqBody = new HashMap<>();
                        SharedPreferences prefs = getSharedPreferences(
                                ConstantRegistry.SHARED_PREFERENCES,
                                Context.MODE_PRIVATE
                        );

                        reqBody.put("refreshToken", prefs.getString(ConstantRegistry.REFRESH_TOKEN, ""));

                        showLoadingDialog();

                        subscribeRefreshToken = mainPresenter.refreshToken(reqBody)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe((TokenResponse result) -> {
                                    closeLoadingDialog();

                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                        Toast.makeText(
                                                MainActivity.this,
                                                R.string.smth_went_wrong,
                                                Toast.LENGTH_LONG
                                        ).show();

                                        return;
                                    }

                                    if (result.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                                        HashMap<String, Object> rBody = new HashMap<>();
                                        rBody.put("id", mainPresenter.getUserId(MainActivity.this));

                                        showLoadingDialog();

                                        subscribeGetAccessToken = mainPresenter.getToken(rBody)
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeOn(Schedulers.io())
                                                .subscribe((TokenResponse resultToken) -> {
                                                    closeLoadingDialog();

                                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                                        Toast.makeText(
                                                                MainActivity.this,
                                                                R.string.smth_went_wrong,
                                                                Toast.LENGTH_LONG
                                                        ).show();

                                                        return;
                                                    }

                                                    SharedPreferences sharedPrefs = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPrefs.edit();
                                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, resultToken.getAccessToken());
                                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, resultToken.getRefreshToken());
                                                    editor.apply();

                                                    getSuggestions(configureSuggestionsRequestBody(mainPresenter), isInitialRequest);
                                                }, throwable -> handleError());

                                        disposable.add(subscribeGetAccessToken);

                                        return;
                                    }

                                    SharedPreferences preferences = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, result.getAccessToken());
                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, result.getRefreshToken());
                                    editor.apply();

                                    getSuggestions(configureSuggestionsRequestBody(mainPresenter), isInitialRequest);
                                }, throwable ->  handleError());

                        disposable.add(subscribeRefreshToken);
                    }

                    if (res.getSuggestions().size() == 0) {
                        loadFragment(NoSuggestionsFragment.newInstance());

                        return;
                    }

                    // These are ids of cached suggestions. When the next request to fetch more
                    // suggestions will be made, maximum 10 of these ids will be passed to request
                    // so that the next batch of suggestions could be retrieved
                    this.superheroIds.addAll(res.getSuperheroIds());

                    // These are ids of the suggestions that were already retrieved and viewed.
                    // So if user will make more requests where all the cached suggestions
                    // were shown and the request to the Elasticsearch will have to be made again
                    // the users who were shown will be excluded from Elasticsearch results.
                    for (Superhero suggestion : res.getSuggestions()) {
                        this.retrievedSuperheroIds.add(suggestion.getId());
                    }

                    // These are suggestions that are going to be shown to the user.
                    this.suggestions.addAll(res.getSuggestions());

                    // This is the first batch of suggestions, so no need to open the
                    // Suggestions fragment until the suggestions are fetched from the server.
                    if (isInitialRequest) {
                        navigation.setSelectedItemId(R.id.navigation_suggestions);

                        return;
                    }

                    // This is not initial request, that means that the next suggestion needs to be loaded.
                    this.currentSuggestion++;

                    loadNextSuggestion(
                            SuggestionFragment.newInstance(
                                    this.suggestions.get(this.currentSuggestion)
                            )
                    );
                }, throwable -> handleError());

        disposable.add(subscribeSuggestions);
    }

    private void uploadChoice(MainPresenter mainPresenter, Superhero suggestion, int choice) {
        subscribeUploadChoice = mainPresenter.uploadChoice(configureChoiceRequestBody(
                mainPresenter,
                suggestion,
                choice
        ), MainActivity.this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((ChoiceResponse res) -> {
                    if (res.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                        Toast.makeText(
                                MainActivity.this,
                                R.string.smth_went_wrong,
                                Toast.LENGTH_LONG
                        ).show();

                        return;
                    }

                    if (res.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                        HashMap<String, Object> reqBody = new HashMap<>();
                        SharedPreferences prefs = getSharedPreferences(
                                ConstantRegistry.SHARED_PREFERENCES,
                                Context.MODE_PRIVATE
                        );

                        reqBody.put("refreshToken", prefs.getString(ConstantRegistry.REFRESH_TOKEN, ""));

                        showLoadingDialog();

                        subscribeRefreshToken = mainPresenter.refreshToken(reqBody)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe((TokenResponse result) -> {
                                    closeLoadingDialog();

                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                        Toast.makeText(
                                                MainActivity.this,
                                                R.string.smth_went_wrong,
                                                Toast.LENGTH_LONG
                                        ).show();

                                        return;
                                    }

                                    if (result.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                                        HashMap<String, Object> rBody = new HashMap<>();
                                        rBody.put("id", mainPresenter.getUserId(MainActivity.this));

                                        showLoadingDialog();

                                        subscribeGetAccessToken = mainPresenter.getToken(rBody)
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeOn(Schedulers.io())
                                                .subscribe((TokenResponse resultToken) -> {
                                                    closeLoadingDialog();

                                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                                        Toast.makeText(
                                                                MainActivity.this,
                                                                R.string.smth_went_wrong,
                                                                Toast.LENGTH_LONG
                                                        ).show();

                                                        return;
                                                    }

                                                    SharedPreferences sharedPrefs = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPrefs.edit();
                                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, resultToken.getAccessToken());
                                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, resultToken.getRefreshToken());
                                                    editor.apply();

                                                    uploadChoice(mainPresenter, suggestion, choice);
                                                }, throwable -> handleError());

                                        disposable.add(subscribeGetAccessToken);

                                        return;
                                    }

                                    SharedPreferences preferences = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, result.getAccessToken());
                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, result.getRefreshToken());
                                    editor.apply();

                                    uploadChoice(mainPresenter, suggestion, choice);
                                }, throwable -> handleError());

                        disposable.add(subscribeRefreshToken);
                    }

                    // If it's a match, show dialog.
                    if (res.isMatch()) {
                        handleMatch(mainPresenter, suggestion, mainPresenter.createUUID());
                    }
                }, throwable -> handleError());

        disposable.add(subscribeUploadChoice);
    }

    public void getSuperheroProfile(HashMap<String, Object> requestBody) {
        showLoadingDialog();

        subscribeGetProfile = mainPresenter.getSuperheroProfile(requestBody, MainActivity.this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((ProfileResponse res) -> {
                    closeLoadingDialog();

                    if (res.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                        Toast.makeText(
                                MainActivity.this,
                                R.string.smth_went_wrong,
                                Toast.LENGTH_LONG
                        ).show();

                        return;
                    }

                    if (res.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                        HashMap<String, Object> reqBody = new HashMap<>();
                        SharedPreferences prefs = getSharedPreferences(
                                ConstantRegistry.SHARED_PREFERENCES,
                                Context.MODE_PRIVATE
                        );

                        reqBody.put("refreshToken", prefs.getString(ConstantRegistry.REFRESH_TOKEN, ""));

                        showLoadingDialog();

                        subscribeRefreshToken = mainPresenter.refreshToken(reqBody)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe((TokenResponse result) -> {
                                    closeLoadingDialog();

                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                        Toast.makeText(
                                                MainActivity.this,
                                                R.string.smth_went_wrong,
                                                Toast.LENGTH_LONG
                                        ).show();

                                        return;
                                    }

                                    if (result.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                                        HashMap<String, Object> rBody = new HashMap<>();
                                        rBody.put("id", mainPresenter.getUserId(MainActivity.this));

                                        showLoadingDialog();

                                        subscribeGetAccessToken = mainPresenter.getToken(rBody)
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeOn(Schedulers.io())
                                                .subscribe((TokenResponse resultToken) -> {
                                                    closeLoadingDialog();

                                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                                        Toast.makeText(
                                                                MainActivity.this,
                                                                R.string.smth_went_wrong,
                                                                Toast.LENGTH_LONG
                                                        ).show();

                                                        return;
                                                    }

                                                    SharedPreferences sharedPrefs = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPrefs.edit();
                                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, resultToken.getAccessToken());
                                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, resultToken.getRefreshToken());
                                                    editor.apply();

                                                    getSuperheroProfile(
                                                            configureGetSuperheroProfileRequestBody(mainPresenter.getUserId(MainActivity.this))
                                                    );
                                                }, throwable -> handleGetProfileError());

                                        disposable.add(subscribeGetAccessToken);

                                        return;
                                    }

                                    SharedPreferences preferences = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, result.getAccessToken());
                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, result.getRefreshToken());
                                    editor.apply();

                                    getSuperheroProfile(
                                            configureGetSuperheroProfileRequestBody(mainPresenter.getUserId(MainActivity.this))
                                    );
                                }, throwable -> handleGetProfileError());

                        disposable.add(subscribeRefreshToken);
                    }

                    if (res.getProfile().getProfilePictures() != null) {
                        Collections.sort(res.getProfile().getProfilePictures(), new ProfilePicturePositionComparator());
                    }

                    loadFragment(UserProfileFragment.newInstance(res.getProfile()));
                }, throwable -> handleGetProfileError());

        disposable.add(subscribeGetProfile);
    }

    public void deleteUserAccount(HashMap<String, Object> requestBody) {
        showLoadingDialog();

        subscribeDeleteAccount = mainPresenter.deleteAccount(requestBody, MainActivity.this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((res) -> {
                    closeLoadingDialog();

                    if (res.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                        Toast.makeText(
                                MainActivity.this,
                                R.string.smth_went_wrong,
                                Toast.LENGTH_LONG
                        ).show();

                        return;
                    }

                    if (res.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                        HashMap<String, Object> reqBody = new HashMap<>();
                        SharedPreferences prefs = getSharedPreferences(
                                ConstantRegistry.SHARED_PREFERENCES,
                                Context.MODE_PRIVATE
                        );

                        reqBody.put("refreshToken", prefs.getString(ConstantRegistry.REFRESH_TOKEN, ""));

                        showLoadingDialog();

                        subscribeRefreshToken = mainPresenter.refreshToken(reqBody)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe((TokenResponse result) -> {
                                    closeLoadingDialog();

                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                        Toast.makeText(
                                                MainActivity.this,
                                                R.string.smth_went_wrong,
                                                Toast.LENGTH_LONG
                                        ).show();

                                        return;
                                    }

                                    if (result.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                                        HashMap<String, Object> rBody = new HashMap<>();
                                        rBody.put("id", mainPresenter.getUserId(MainActivity.this));

                                        showLoadingDialog();

                                        subscribeGetAccessToken = mainPresenter.getToken(rBody)
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeOn(Schedulers.io())
                                                .subscribe((TokenResponse resultToken) -> {
                                                    closeLoadingDialog();

                                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                                        Toast.makeText(
                                                                MainActivity.this,
                                                                R.string.smth_went_wrong,
                                                                Toast.LENGTH_LONG
                                                        ).show();

                                                        return;
                                                    }

                                                    SharedPreferences sharedPrefs = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPrefs.edit();
                                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, resultToken.getAccessToken());
                                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, resultToken.getRefreshToken());
                                                    editor.apply();

                                                    deleteUserAccount(configureDeleteAccountRequestBody(getUserId()));
                                                }, throwable -> handleDeleteAccountError());

                                        disposable.add(subscribeGetAccessToken);

                                        return;
                                    }

                                    SharedPreferences preferences = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, result.getAccessToken());
                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, result.getRefreshToken());
                                    editor.apply();

                                    deleteUserAccount(configureDeleteAccountRequestBody(getUserId()));
                                }, throwable -> handleDeleteAccountError());

                        disposable.add(subscribeRefreshToken);
                    }

                    this.mainPresenter.deleteDataFromAllTables(MainActivity.this);

                    this.mainPresenter.insertDefaultUser(MainActivity.this);

                    navigateToVerifyIdentity(MainActivity.this);
                }, throwable -> handleDeleteAccountError());

        disposable.add(subscribeDeleteAccount);
    }

    private void handleGetProfileError() {
        closeLoadingDialog();
        Log.e(MainActivity.class.getName(), getString(R.string.fetch_profile_error_msg));
        Toast.makeText(MainActivity.this, R.string.smth_went_wrong, Toast.LENGTH_LONG).show();
    }

    private void handleDeleteAccountError() {
        closeLoadingDialog();
        Log.e(MainActivity.class.getName(), "Error deleting account");
        Toast.makeText(MainActivity.this, R.string.smth_went_wrong, Toast.LENGTH_LONG).show();
    }

    public void showDialogDeleteMatch(
            final String chatId,
            final int position,
            final ArrayList<Chat> chats,
            final MatchesChatsAdapter matchesChatsAdapter,
            String superheroId,
            String matchedSuperheroId
    ) {

        DialogInterface.OnClickListener dialogDeleteClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        handleDeleteChatDialogButtonClick(
                                button,
                                mainPresenter,
                                chatId,
                                chats,
                                position,
                                matchesChatsAdapter,
                                superheroId,
                                matchedSuperheroId
                        );
                    }
                };

        DialogInterface.OnClickListener dialogCancelClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        dialog.dismiss();
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.delete_match_question)
                .setPositiveButton(R.string.delete, dialogDeleteClickListener)
                .setNegativeButton(R.string.cancel, dialogCancelClickListener)
                .show();
    }

    public void showDialogDeleteProfilePicture(final int position) {
        DialogInterface.OnClickListener dialogDeleteClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        processDeleteProfilePic(position);
                    }
                };

        DialogInterface.OnClickListener dialogCancelClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        dialog.dismiss();
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.delete_profile_picture_question)
                .setPositiveButton(R.string.delete, dialogDeleteClickListener)
                .setNegativeButton(R.string.cancel, dialogCancelClickListener)
                .show();

    }

    public void showDialogDeleteAccount() {
        DialogInterface.OnClickListener dialogDeleteClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        deleteUserAccount(configureDeleteAccountRequestBody(getUserId()));
                    }
                };

        DialogInterface.OnClickListener dialogCancelClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        dialog.dismiss();
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.are_you_sure_you_want_to_delete_your_account)
                .setPositiveButton(R.string.delete, dialogDeleteClickListener)
                .setNegativeButton(R.string.cancel, dialogCancelClickListener)
                .show();

    }

    private void handleDeleteChatDialogButtonClick(
            int button,
            MainPresenter mainPresenter,
            String chatId,
            ArrayList<Chat> chats,
            int position,
            MatchesChatsAdapter matchesChatsAdapter,
            String superheroId,
            String matchedSuperheroId
    ) {
        if (button == DialogInterface.BUTTON_NEGATIVE) {
            return;
        }

        mainPresenter.deleteChatById(chatId, MainActivity.this);
        deleteMatch(superheroId, matchedSuperheroId);
        chats.remove(position);
        matchesChatsAdapter.notifyDataSetChanged();
    }

    private void handleMatch(MainPresenter mainPresenter, Superhero suggestion, String matchId) {
        mainPresenter.insertChat(
                matchId,
                suggestion.getSuperheroName(),
                suggestion.getId(),
                suggestion.getMainProfilePicUrl(),
                MainActivity.this
        );

        showMatchDialog(suggestion);

        HashMap<String, Object> reqBody = configureMatchRequestBody(
                mainPresenter,
                suggestion,
                matchId
        );

        uploadMatch(reqBody);
    }

    private void uploadMatch(HashMap<String, Object> requestBody) {
        subscribeUploadMatch = mainPresenter.uploadMatch(requestBody, MainActivity.this)
                .subscribeOn(Schedulers.io())
                .subscribe((StatusResponse res) -> {
                    if (res.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                        Log.e(MainActivity.class.getName(), getString(R.string.upload_match_error_msg));
                    }

                    if (res.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                        HashMap<String, Object> reqBody = new HashMap<>();
                        SharedPreferences prefs = getSharedPreferences(
                                ConstantRegistry.SHARED_PREFERENCES,
                                Context.MODE_PRIVATE
                        );

                        reqBody.put("refreshToken", prefs.getString(ConstantRegistry.REFRESH_TOKEN, ""));

                        subscribeRefreshToken = mainPresenter.refreshToken(reqBody)
                                .subscribeOn(Schedulers.io())
                                .subscribe((TokenResponse result) -> {

                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                        Toast.makeText(
                                                MainActivity.this,
                                                R.string.smth_went_wrong,
                                                Toast.LENGTH_LONG
                                        ).show();

                                        return;
                                    }

                                    if (result.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                                        HashMap<String, Object> rBody = new HashMap<>();
                                        rBody.put("id", mainPresenter.getUserId(MainActivity.this));

                                        subscribeGetAccessToken = mainPresenter.getToken(rBody)
                                                .subscribeOn(Schedulers.io())
                                                .subscribe((TokenResponse resultToken) -> {

                                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                                        Toast.makeText(
                                                                MainActivity.this,
                                                                R.string.smth_went_wrong,
                                                                Toast.LENGTH_LONG
                                                        ).show();

                                                        return;
                                                    }

                                                    SharedPreferences sharedPrefs = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPrefs.edit();
                                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, resultToken.getAccessToken());
                                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, resultToken.getRefreshToken());
                                                    editor.apply();

                                                    uploadMatch(requestBody);
                                                }, throwable -> handleErrorInBackground());

                                        disposable.add(subscribeGetAccessToken);

                                        return;
                                    }

                                    SharedPreferences preferences = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, result.getAccessToken());
                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, result.getRefreshToken());
                                    editor.apply();

                                    uploadMatch(requestBody);
                                }, throwable -> handleErrorInBackground());

                        disposable.add(subscribeRefreshToken);
                    }
                }, throwable -> handleErrorInBackground());

        disposable.add(subscribeUploadMatch);
    }

    private void updateFirebaseToken(HashMap<String, Object> requestBody, String token) {
        subscribeUpdateUserToken = mainPresenter.updateFirebaseToken(requestBody, MainActivity.this)
                .subscribeOn(Schedulers.io())
                .subscribe((StatusResponse res) -> {
                    if (res.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                        Log.e(MainActivity.class.getName(), "Error while updating Firebase messaging token");
                    }

                    if (res.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                        HashMap<String, Object> reqBody = new HashMap<>();
                        SharedPreferences prefs = getSharedPreferences(
                                ConstantRegistry.SHARED_PREFERENCES,
                                Context.MODE_PRIVATE
                        );

                        reqBody.put("refreshToken", prefs.getString(ConstantRegistry.REFRESH_TOKEN, ""));

                        subscribeRefreshToken = mainPresenter.refreshToken(reqBody)
                                .subscribeOn(Schedulers.io())
                                .subscribe((TokenResponse result) -> {

                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                        Toast.makeText(
                                                MainActivity.this,
                                                R.string.smth_went_wrong,
                                                Toast.LENGTH_LONG
                                        ).show();

                                        return;
                                    }

                                    if (result.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                                        HashMap<String, Object> rBody = new HashMap<>();
                                        rBody.put("id", mainPresenter.getUserId(MainActivity.this));

                                        subscribeGetAccessToken = mainPresenter.getToken(rBody)
                                                .subscribeOn(Schedulers.io())
                                                .subscribe((TokenResponse resultToken) -> {

                                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                                        Toast.makeText(
                                                                MainActivity.this,
                                                                R.string.smth_went_wrong,
                                                                Toast.LENGTH_LONG
                                                        ).show();

                                                        return;
                                                    }

                                                    SharedPreferences sharedPrefs = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPrefs.edit();
                                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, resultToken.getAccessToken());
                                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, resultToken.getRefreshToken());
                                                    editor.apply();

                                                    updateFirebaseToken(
                                                            configureUpdateFirebaseTokenRequestBody(
                                                                mainPresenter.getUserId(MainActivity.this),
                                                                token
                                                            ),
                                                            token
                                                    );
                                                }, throwable -> handleErrorInBackground());

                                        disposable.add(subscribeGetAccessToken);

                                        return;
                                    }

                                    SharedPreferences preferences = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, result.getAccessToken());
                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, result.getRefreshToken());
                                    editor.apply();

                                    updateFirebaseToken(
                                            configureUpdateFirebaseTokenRequestBody(
                                                    mainPresenter.getUserId(MainActivity.this),
                                                    token
                                            ),
                                            token
                                    );
                                }, throwable -> handleErrorInBackground());

                        disposable.add(subscribeRefreshToken);
                    }
                }, throwable -> handleErrorInBackground());

        disposable.add(subscribeUpdateUserToken);
    }

    public void deleteMatch(String superheroId, String matchedSuperheroId) {
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("superheroId", superheroId);
        requestBody.put("matchedSuperheroId", matchedSuperheroId);

        subscribeDeleteMatch = mainPresenter.deleteMatch(requestBody, MainActivity.this)
                .subscribeOn(Schedulers.io())
                .subscribe((StatusResponse res) -> {
                    if (res.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                        Log.e(MainActivity.class.getName(), getString(R.string.upload_match_error_msg));
                    }

                    if (res.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                        HashMap<String, Object> reqBody = new HashMap<>();
                        SharedPreferences prefs = getSharedPreferences(
                                ConstantRegistry.SHARED_PREFERENCES,
                                Context.MODE_PRIVATE
                        );

                        reqBody.put("refreshToken", prefs.getString(ConstantRegistry.REFRESH_TOKEN, ""));

                        subscribeRefreshToken = mainPresenter.refreshToken(reqBody)
                                .subscribeOn(Schedulers.io())
                                .subscribe((TokenResponse result) -> {

                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                        Toast.makeText(
                                                MainActivity.this,
                                                R.string.smth_went_wrong,
                                                Toast.LENGTH_LONG
                                        ).show();

                                        return;
                                    }

                                    if (result.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                                        HashMap<String, Object> rBody = new HashMap<>();
                                        rBody.put("id", mainPresenter.getUserId(MainActivity.this));

                                        subscribeGetAccessToken = mainPresenter.getToken(rBody)
                                                .subscribeOn(Schedulers.io())
                                                .subscribe((TokenResponse resultToken) -> {

                                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                                        Toast.makeText(
                                                                MainActivity.this,
                                                                R.string.smth_went_wrong,
                                                                Toast.LENGTH_LONG
                                                        ).show();

                                                        return;
                                                    }

                                                    SharedPreferences sharedPrefs = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPrefs.edit();
                                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, resultToken.getAccessToken());
                                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, resultToken.getRefreshToken());
                                                    editor.apply();

                                                    deleteMatch(superheroId, matchedSuperheroId);
                                                }, throwable -> handleErrorInBackground());

                                        disposable.add(subscribeGetAccessToken);

                                        return;
                                    }

                                    SharedPreferences preferences = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, result.getAccessToken());
                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, result.getRefreshToken());
                                    editor.apply();

                                    deleteMatch(superheroId, matchedSuperheroId);
                                }, throwable -> handleErrorInBackground());

                        disposable.add(subscribeRefreshToken);
                    }
                }, throwable -> handleErrorInBackground());

        disposable.add(subscribeDeleteMatch);
    }

    public void deleteProfilePicture(String superheroId, int position) {
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("superheroId", superheroId);
        requestBody.put("position", position);

        subscribeDeleteProfilePicture = mainPresenter.deleteProfilePicture(requestBody, MainActivity.this)
                .subscribeOn(Schedulers.io())
                .subscribe((StatusResponse res) -> {
                    if (res.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                        Log.e(MainActivity.class.getName(), getString(R.string.delete_profile_picture_error));
                    }

                    if (res.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                        HashMap<String, Object> reqBody = new HashMap<>();
                        SharedPreferences prefs = getSharedPreferences(
                                ConstantRegistry.SHARED_PREFERENCES,
                                Context.MODE_PRIVATE
                        );

                        reqBody.put("refreshToken", prefs.getString(ConstantRegistry.REFRESH_TOKEN, ""));

                        subscribeRefreshToken = mainPresenter.refreshToken(reqBody)
                                .subscribeOn(Schedulers.io())
                                .subscribe((TokenResponse result) -> {

                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                        Toast.makeText(
                                                MainActivity.this,
                                                R.string.smth_went_wrong,
                                                Toast.LENGTH_LONG
                                        ).show();

                                        return;
                                    }

                                    if (result.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                                        HashMap<String, Object> rBody = new HashMap<>();
                                        rBody.put("id", mainPresenter.getUserId(MainActivity.this));

                                        subscribeGetAccessToken = mainPresenter.getToken(rBody)
                                                .subscribeOn(Schedulers.io())
                                                .subscribe((TokenResponse resultToken) -> {

                                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                                        Toast.makeText(
                                                                MainActivity.this,
                                                                R.string.smth_went_wrong,
                                                                Toast.LENGTH_LONG
                                                        ).show();

                                                        return;
                                                    }

                                                    SharedPreferences sharedPrefs = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPrefs.edit();
                                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, resultToken.getAccessToken());
                                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, resultToken.getRefreshToken());
                                                    editor.apply();

                                                    deleteProfilePicture(mainPresenter.getUserId(MainActivity.this), position);
                                                }, throwable -> handleErrorInBackground());

                                        disposable.add(subscribeGetAccessToken);

                                        return;
                                    }

                                    SharedPreferences preferences = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, result.getAccessToken());
                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, result.getRefreshToken());
                                    editor.apply();

                                    deleteProfilePicture(mainPresenter.getUserId(MainActivity.this), position);
                                }, throwable -> handleErrorInBackground());

                        disposable.add(subscribeRefreshToken);
                    }
                }, throwable -> handleErrorInBackground());

        disposable.add(subscribeDeleteProfilePicture);
    }

    private void reportUser(HashMap<String, Object> requestBody) {
        subscribeReportUser = mainPresenter.reportUser(requestBody, MainActivity.this)
                .subscribeOn(Schedulers.io())
                .subscribe((StatusResponse res) -> {
                    if (res.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                        Log.e(MainActivity.class.getName(), getString(R.string.report_user_error_msg));
                    }

                    if (res.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                        HashMap<String, Object> reqBody = new HashMap<>();
                        SharedPreferences prefs = getSharedPreferences(
                                ConstantRegistry.SHARED_PREFERENCES,
                                Context.MODE_PRIVATE
                        );

                        reqBody.put("refreshToken", prefs.getString(ConstantRegistry.REFRESH_TOKEN, ""));

                        subscribeRefreshToken = mainPresenter.refreshToken(reqBody)
                                .subscribeOn(Schedulers.io())
                                .subscribe((TokenResponse result) -> {

                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                        Toast.makeText(
                                                MainActivity.this,
                                                R.string.smth_went_wrong,
                                                Toast.LENGTH_LONG
                                        ).show();

                                        return;
                                    }

                                    if (result.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                                        HashMap<String, Object> rBody = new HashMap<>();
                                        rBody.put("id", mainPresenter.getUserId(MainActivity.this));

                                        subscribeGetAccessToken = mainPresenter.getToken(rBody)
                                                .subscribeOn(Schedulers.io())
                                                .subscribe((TokenResponse resultToken) -> {

                                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                                        Toast.makeText(
                                                                MainActivity.this,
                                                                R.string.smth_went_wrong,
                                                                Toast.LENGTH_LONG
                                                        ).show();

                                                        return;
                                                    }

                                                    SharedPreferences sharedPrefs = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPrefs.edit();
                                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, resultToken.getAccessToken());
                                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, resultToken.getRefreshToken());
                                                    editor.apply();

                                                    reportUser(requestBody);
                                                }, throwable -> handleErrorInBackground());

                                        disposable.add(subscribeGetAccessToken);

                                        return;
                                    }

                                    SharedPreferences preferences = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, result.getAccessToken());
                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, result.getRefreshToken());
                                    editor.apply();

                                    reportUser(requestBody);
                                }, throwable -> handleErrorInBackground());

                        disposable.add(subscribeRefreshToken);
                    }
                }, throwable -> handleErrorInBackground());

        disposable.add(subscribeReportUser);
    }


    public void updateUserProfile() {
        updateProfile(configureUpdateProfileRequestBody(mainPresenter));
    }

    private void updateProfile(HashMap<String, Object> requestBody) {
        if (!loadingDialogIsActive) {
            showLoadingDialog();
        }

        subscribeUpdateProfile = mainPresenter.updateProfile(requestBody, MainActivity.this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((UpdateResponse res) -> {
                    closeLoadingDialog();

                    if (res.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                        Toast.makeText(
                                MainActivity.this,
                                R.string.smth_went_wrong,
                                Toast.LENGTH_LONG
                        ).show();
                    }

                    if (res.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                        HashMap<String, Object> reqBody = new HashMap<>();
                        SharedPreferences prefs = getSharedPreferences(
                                ConstantRegistry.SHARED_PREFERENCES,
                                Context.MODE_PRIVATE
                        );

                        reqBody.put("refreshToken", prefs.getString(ConstantRegistry.REFRESH_TOKEN, ""));

                        showLoadingDialog();

                        subscribeRefreshToken = mainPresenter.refreshToken(reqBody)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe((TokenResponse result) -> {
                                    closeLoadingDialog();

                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                        Toast.makeText(
                                                MainActivity.this,
                                                R.string.smth_went_wrong,
                                                Toast.LENGTH_LONG
                                        ).show();

                                        return;
                                    }

                                    if (result.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                                        HashMap<String, Object> rBody = new HashMap<>();
                                        rBody.put("id", mainPresenter.getUserId(MainActivity.this));

                                        showLoadingDialog();

                                        subscribeGetAccessToken = mainPresenter.getToken(rBody)
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeOn(Schedulers.io())
                                                .subscribe((TokenResponse resultToken) -> {
                                                    closeLoadingDialog();

                                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                                        Toast.makeText(
                                                                MainActivity.this,
                                                                R.string.smth_went_wrong,
                                                                Toast.LENGTH_LONG
                                                        ).show();

                                                        return;
                                                    }

                                                    SharedPreferences sharedPrefs = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPrefs.edit();
                                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, resultToken.getAccessToken());
                                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, resultToken.getRefreshToken());
                                                    editor.apply();

                                                    updateProfile(configureUpdateProfileRequestBody(mainPresenter));
                                                }, throwable -> handleError());

                                        disposable.add(subscribeGetAccessToken);

                                        return;
                                    }

                                    SharedPreferences preferences = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, result.getAccessToken());
                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, result.getRefreshToken());
                                    editor.apply();

                                    updateProfile(configureUpdateProfileRequestBody(mainPresenter));
                                }, throwable -> handleError());

                        disposable.add(subscribeRefreshToken);
                    }
                }, throwable -> handleError());

        disposable.add(subscribeUpdateProfile);
    }

    private void handleError() {
        closeLoadingDialog();
        stopLocationUpdates();
        loadFragment(NoSuggestionsFragment.newInstance());
        Toast.makeText(MainActivity.this, R.string.smth_went_wrong, Toast.LENGTH_LONG).show();
    }

    private void handleErrorInBackground() {
        Log.e(MainActivity.class.getName(), getString(R.string.upload_match_error_msg));
    }

    public void setLatAndLon(String userID, double lat, double lon, Context context) {
        mainPresenter.updateUserLongitudeAndLatitude(userID, lat, lon, context);
    }

    private void initLocationUpdateService() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        settingsClient = LocationServices.getSettingsClient(MainActivity.this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                currentLocation = locationResult.getLastLocation();

                if (currentLocation != null) {
                    setLatAndLon(
                            mainPresenter.getUserId(MainActivity.this),
                            currentLocation.getLatitude(),
                            currentLocation.getLongitude(),
                            MainActivity.this
                    );

                    setAddress(currentLocation.getLatitude(), currentLocation.getLongitude());
                }

                getSuggestions(configureSuggestionsRequestBody(mainPresenter), true);
            }
        };

        locationRequest = new LocationRequest();
        locationRequest.setInterval(ConstantRegistry.UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(ConstantRegistry.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();
    }

    private void setAddress(double lat, double lon) {
        try {
            Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(lat, lon, 1);
            if (addresses.size() > 0) {
                mainPresenter.updateUserCountryAndCity(
                        mainPresenter.getUserId(MainActivity.this),
                        addresses.get(0).getCountryName(),
                        addresses.get(0).getLocality(),
                        MainActivity.this
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(
                                        MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        ConstantRegistry.MY_PERMISSIONS_REQUEST_LOCATION
                                );
                            }
                        })
                        .create()
                        .show();
            }

            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ConstantRegistry.MY_PERMISSIONS_REQUEST_LOCATION
            );

            return false;
        }

        return true;
    }

    /**
     * Check whether location settings are satisfied and then
     * location updates will be requested.
     */
    private void startLocationUpdates() {
        settingsClient
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(MainActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        fusedLocationClient.requestLocationUpdates(
                                locationRequest,
                                locationCallback,
                                Looper.myLooper()
                        );

                        if (currentLocation != null) {
                            setLatAndLon(
                                    mainPresenter.getUserId(MainActivity.this),
                                    currentLocation.getLatitude(),
                                    currentLocation.getLongitude(),
                                    MainActivity.this
                            );

                            setAddress(currentLocation.getLatitude(), currentLocation.getLongitude());
                        }
                    }
                })
                .addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(MainActivity.this, ConstantRegistry.REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Toast.makeText(
                                            MainActivity.this,
                                            getString(R.string.smth_went_wrong),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }

                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                Toast.makeText(
                                        MainActivity.this,
                                        getString(R.string.fix_location_settings),
                                        Toast.LENGTH_LONG
                                ).show();
                        }

                        if (currentLocation != null) {
                            setLatAndLon(
                                    mainPresenter.getUserId(MainActivity.this),
                                    currentLocation.getLatitude(),
                                    currentLocation.getLongitude(),
                                    MainActivity.this
                            );

                            setAddress(currentLocation.getLatitude(), currentLocation.getLongitude());
                        }
                    }
                });
    }

    public int getUserLookingForMinAge() {
        return mainPresenter.getUserLookingForMinAge(MainActivity.this);
    }

    public int getUserLookingForMaxAge() {
        return mainPresenter.getUserLookingForMaxAge(MainActivity.this);
    }

    public void updateLookingForAgeRange(int minAge, int maxAge) {
        mainPresenter.updateUserLookingForAgeRange(
                mainPresenter.getUserId(MainActivity.this),
                minAge,
                maxAge,
                MainActivity.this
        );
    }

    public void updateUserLookingForMaxDistance(int maxDistance) {
        mainPresenter.updateUserLookingForMaxDistance(
                mainPresenter.getUserId(MainActivity.this),
                maxDistance,
                MainActivity.this
        );
    }

    public String getUserDistanceUnit() {
        return mainPresenter.getUserDistanceUnit(MainActivity.this);
    }

    public int getUserMaxDistance() {
        return mainPresenter.getUserLookingForMaxDistance(MainActivity.this);
    }

    public int getUserLookingForGender() {
        return mainPresenter.getUserLookingForGender(MainActivity.this);
    }

    public void updateUserLookingForGender(int gender) {
        mainPresenter.updateUserLookingForGender(
                mainPresenter.getUserId(MainActivity.this),
                gender,
                MainActivity.this
        );
    }

    public void updateUserDistanceUnit(String distanceUnit) {
        mainPresenter.updateUserDistanceUnit(
                mainPresenter.getUserId(MainActivity.this),
                distanceUnit,
                MainActivity.this
        );
    }

    public String getUserSuperPower() {
        return mainPresenter.getUserSuperPower(MainActivity.this);
    }

    public void updateUserSuperPower(String superPower) {
        mainPresenter.updateUserSuperPower(
                mainPresenter.getUserId(MainActivity.this),
                superPower,
                MainActivity.this
        );
    }

    public void onSuggestionLike() {
        if (this.suggestions.size() == 0) {
            return;
        }

        mainPresenter.insertChoice(
                this.suggestions.get(this.currentSuggestion).getId(),
                ConstantRegistry.LIKE,
                mainPresenter.getDateTime(),
                MainActivity.this
        );

        // Check if it is a match.
        if (this.suggestions.get(this.currentSuggestion).isHasLikedMe()) {
            handleMatch(
                    this.mainPresenter,
                    this.suggestions.get(this.currentSuggestion),
                    this.mainPresenter.createUUID()
            );

            return;
        }

        // It wasn't match, send the choice to the server.
        uploadChoice(
                this.mainPresenter,
                this.suggestions.get(this.currentSuggestion),
                ConstantRegistry.LIKE
        );

        loadNextSuggestion();
    }

    public void onSuggestionDislike() {
        if (this.suggestions.size() == 0) {
            return;
        }

        mainPresenter.insertChoice(
                this.suggestions.get(this.currentSuggestion).getId(),
                ConstantRegistry.DISLIKE,
                mainPresenter.getDateTime(),
                MainActivity.this
        );

        // Send the choice to the server.
        uploadChoice(
                this.mainPresenter,
                this.suggestions.get(this.currentSuggestion),
                ConstantRegistry.DISLIKE
        );

        loadNextSuggestion();
    }

    public void nextSuggestion() {
        // If there are still suggestions left, display next suggestion.
        if ((this.suggestions.size() > 0) && ((this.suggestions.size() - 1) > this.currentSuggestion)) {
            this.currentSuggestion++;

            loadNextSuggestion(
                    SuggestionFragment.newInstance(
                            this.suggestions.get(this.currentSuggestion)
                    )
            );

            return;
        }

        // If not, fetch more suggestions.
        getSuggestions(configureSuggestionsRequestBody(this.mainPresenter), false);
    }

    public void loadNextSuggestion() {
        if ((this.currentSuggestion % ConstantRegistry.NUMBER_OF_SUGGESTIONS_BEFORE_SHOWING_AD) == 0) {
            loadNextSuggestion(AdFragment.newInstance());

            return;
        }

        nextSuggestion();
    }

    public void onReportUser() {
        if ((this.suggestions.size() > 0) && ((this.suggestions.size() - 1) >= this.currentSuggestion)) {
            showPopupReportUser(mainPresenter.getUserId(MainActivity.this), this.suggestions.get(this.currentSuggestion));
        }
    }

    private void handleNotificationAction() {
        if (getIntent().getExtras() != null && getIntent().getAction() != null) {
            switch (getIntent().getAction()) {
                case ConstantRegistry.NEW_MATCH_REQUEST:
                case ConstantRegistry.NEW_OFFLINE_MESSAGE_REQUEST:
                    removeNotifications();
                    navigation.setSelectedItemId(R.id.navigation_matches);
                    break;
            }
        }
    }

    private void removeNotifications() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }

    public void setCurrentChat(Chat chat) {
        this.currentChat = chat;
    }

    public Chat getCurrentChat() {
        return this.currentChat;
    }

    public Superhero getProfile() {
        return profile;
    }

    public void setProfile(Superhero profile) {
        this.profile = profile;
    }

    public boolean isAddingNewProfilePicture() {
        return isAddingNewProfilePicture;
    }

    public void setAddingNewProfilePicture(boolean addingNewProfilePicture) {
        isAddingNewProfilePicture = addingNewProfilePicture;
    }

    public int getCurrentProfilePicturePosition() {
        return currentProfilePicturePosition;
    }

    public void setCurrentProfilePicturePosition(int currentProfilePicturePosition) {
        this.currentProfilePicturePosition = currentProfilePicturePosition;
    }

    public void loadSuggestionProfileFragment(String superheroId) {
        showLoadingDialog();

        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("superheroId", superheroId);

        subscribeGetProfile = mainPresenter.getSuperheroProfile(requestBody, MainActivity.this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((ProfileResponse res) -> {
                    closeLoadingDialog();

                    if (res.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                        Toast.makeText(
                                MainActivity.this,
                                R.string.smth_went_wrong,
                                Toast.LENGTH_LONG
                        ).show();

                        return;
                    }

                    if (res.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                        HashMap<String, Object> reqBody = new HashMap<>();
                        SharedPreferences prefs = getSharedPreferences(
                                ConstantRegistry.SHARED_PREFERENCES,
                                Context.MODE_PRIVATE
                        );

                        reqBody.put("refreshToken", prefs.getString(ConstantRegistry.REFRESH_TOKEN, ""));

                        showLoadingDialog();

                        subscribeRefreshToken = mainPresenter.refreshToken(reqBody)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe((TokenResponse result) -> {
                                    closeLoadingDialog();

                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                        Toast.makeText(
                                                MainActivity.this,
                                                R.string.smth_went_wrong,
                                                Toast.LENGTH_LONG
                                        ).show();

                                        return;
                                    }

                                    if (result.getStatus() == ConstantRegistry.SERVER_STATUS_UNAUTHORIZED) {
                                        HashMap<String, Object> rBody = new HashMap<>();
                                        rBody.put("id", mainPresenter.getUserId(MainActivity.this));

                                        showLoadingDialog();

                                        subscribeGetAccessToken = mainPresenter.getToken(rBody)
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeOn(Schedulers.io())
                                                .subscribe((TokenResponse resultToken) -> {
                                                    closeLoadingDialog();

                                                    if (result.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                                                        Toast.makeText(
                                                                MainActivity.this,
                                                                R.string.smth_went_wrong,
                                                                Toast.LENGTH_LONG
                                                        ).show();

                                                        return;
                                                    }

                                                    SharedPreferences sharedPrefs = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPrefs.edit();
                                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, resultToken.getAccessToken());
                                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, resultToken.getRefreshToken());
                                                    editor.apply();

                                                    loadSuggestionProfileFragment(superheroId);
                                                }, throwable -> handleError());

                                        disposable.add(subscribeGetAccessToken);

                                        return;
                                    }

                                    SharedPreferences preferences = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString(ConstantRegistry.ACCESS_TOKEN, result.getAccessToken());
                                    editor.putString(ConstantRegistry.REFRESH_TOKEN, result.getRefreshToken());
                                    editor.apply();

                                    loadSuggestionProfileFragment(superheroId);
                                }, throwable -> handleError());

                        disposable.add(subscribeRefreshToken);
                    }

                    if (res.getProfile().getProfilePictures() != null) {
                        Collections.sort(res.getProfile().getProfilePictures(), new ProfilePicturePositionComparator());
                    }

                    loadBackStackFragment(SuggestionProfileFragment.newInstance(res.getProfile()));
                }, throwable -> handleGetProfileError());

        disposable.add(subscribeGetProfile);
    }

    public void updateMessageHasBeenReadByMessageId(int messageId, Context context) {
        mainPresenter.updateMessageHasBeenReadByMessageId(messageId, context);
    }
}
