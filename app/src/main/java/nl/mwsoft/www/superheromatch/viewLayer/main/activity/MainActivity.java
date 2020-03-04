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
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
import nl.mwsoft.www.superheromatch.modelLayer.event.MainProfilePicSettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.ProfilePic1SettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.ProfilePic2SettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.ProfilePic3SettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.ProfilePic4SettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.TextMessageEvent;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.Chat;
import nl.mwsoft.www.superheromatch.modelLayer.model.ChoiceResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.Message;
import nl.mwsoft.www.superheromatch.modelLayer.model.OutgoingMessage;
import nl.mwsoft.www.superheromatch.modelLayer.model.ProfilePicture;
import nl.mwsoft.www.superheromatch.modelLayer.model.SuggestionsResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.Superhero;
import nl.mwsoft.www.superheromatch.modelLayer.model.UpdateResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.presenterLayer.main.MainPresenter;
import nl.mwsoft.www.superheromatch.viewLayer.dialog.loadingDialog.LoadingDialogFragment;
import nl.mwsoft.www.superheromatch.viewLayer.dialog.matchDialog.MatchDialog;
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.MatchesChatsAdapter;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.matches.MatchesChatsFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.UserProfilePictureSettingsFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.suggestions.NoSuggestionsFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.suggestions.SuggestionDescriptionFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.suggestions.SuggestionFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.UserProfileEditFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.UserProfileFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.UserProfileSettingsSuggestionsFragment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private int currFragmentPosition;
    private ArrayList<Message> messages;
    @BindView(R.id.bnMain)
    BottomNavigationView navigation;
    @BindView(R.id.tlMain)
    Toolbar tlMain;
    @BindView(R.id.ivSuggestionDislike)
    ImageView ivSuggestionDislike;
    @BindView(R.id.ivSuggestionLike)
    ImageView ivSuggestionLike;
    @BindView(R.id.ivSuperPowerIconSuggestion)
    ImageView ivSuperPowerIconSuggestion;
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
    Disposable subscribeUpdateUserToken;
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
    private OkHttpClient client;
    private WebSocket webSocket;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        DependencyRegistry.shared.inject(this);
        setSupportActionBar(tlMain);

        init();

        setUpConnectionToServer();

        // || mainPresenter.getUserIsLoggedIn(this) == 0
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

    // region Socket.IO

    private void setUpConnectionToServer() {
        // default settings for all sockets
        IO.setDefaultOkHttpWebSocketFactory(OkHttpClientManager.setUpSecureClient());
        IO.setDefaultOkHttpCallFactory(OkHttpClientManager.setUpSecureClient());

        // set as an option
        IO.Options opts = new IO.Options();
        opts.transports = new String[]{io.socket.engineio.client.transports.WebSocket.NAME};
        opts.callFactory = OkHttpClientManager.setUpSecureClient();
        opts.webSocketFactory = OkHttpClientManager.setUpSecureClient();
        try {
            socket = IO.socket(
                    ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_CHAT_PORT),
                    opts
            );

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        socket.connect();

        socket.on(ConstantRegistry.CHAT_MESSAGE, handleIncomingMessages);
    }

    private Emitter.Listener handleIncomingMessages = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    long senderId = 0;
                    String chatName = "";
                    String messageText = "";
                    String messageCreated = "";
                    String uuid = "";
                    int chatId = 0;

                    try {
                        senderId = data.getLong(ConstantRegistry.SENDER_ID);
//                        chatName = data.getString(ConstantRegistry.CHATSTER_MESSAGE_CHAT_NAME);
//                        messageText = data.getString(ConstantRegistry.CHATSTER_MESSAGE_TEXT);
//                        messageCreated = data.getString(ConstantRegistry.CHATSTER_MESSAGE_CREATED);
//                        uuid = data.getString(ConstantRegistry.CHATSTER_MESSAGE_UUID);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

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
                        updateFirebaseToken(reqBody);
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
        reqBodySuggestions.put("retrievedSuperheroIds", this.retrievedSuperheroIds);

        return reqBodySuggestions;
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

    private void init() {
        messages = new ArrayList<>();
        messages.addAll(createMockMessages());
        disposable = new CompositeDisposable();
        superheroIds = new ArrayList<>();
        retrievedSuperheroIds = new ArrayList<>();
        suggestions = new ArrayList<>();
        client = OkHttpClientManager.setUpSecureClient();
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

//                    fragment = SuggestionFragment.newInstance(createMockSuperhero());
//                    loadFragment(fragment);

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

                    fragment = UserProfileFragment.newInstance(createMockUser());
                    loadFragment(fragment);

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

        if (currFragmentPosition != 1) {
            hideChoiceButtons();

            return;
        }

        showChoiceButtons();
    }

    public void showChoiceButtons() {
        ivSuggestionDislike.setVisibility(View.VISIBLE);
        ivSuggestionLike.setVisibility(View.VISIBLE);
        ivSuperPowerIconSuggestion.setVisibility(View.VISIBLE);
    }

    public void hideChoiceButtons() {
        ivSuggestionDislike.setVisibility(View.GONE);
        ivSuggestionLike.setVisibility(View.GONE);
        ivSuperPowerIconSuggestion.setVisibility(View.GONE);
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

    public void loadSuggestionUserProfileFragment(User user) {
        loadBackStackFragment(UserProfileFragment.newInstance(user));
    }

    public void loadSuggestionUserProfileSettingsFragment() {
        loadBackStackFragment(UserProfileSettingsSuggestionsFragment.newInstance());
    }

    public void loadSuggestionUserProfileEditFragment() {
        loadBackStackFragment(UserProfileEditFragment.newInstance());
    }

    public void loadProfilePictureSettingsFragment(User user) {
        loadBackStackFragment(
                UserProfilePictureSettingsFragment.newInstance(
                        user.getMainProfilePicUrl(),
                        user.getProfilePicsUrls()
                )
        );
    }

    public Superhero createMockSuperhero() {
        Superhero superhero = new Superhero();
        superhero.setId("id");
        superhero.setSuperheroName("Amazingwoman"); // Superwoman Wonderwoman Magicwoman Amazingwoman
        superhero.setMainProfilePicUrl("pdp_4");
        ArrayList<ProfilePicture> profilePictures = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ProfilePicture profilePicture = new ProfilePicture();
            profilePicture.setId(i + 1);
            profilePicture.setPosition(i + 1);
            profilePicture.setProfilePicUrl("pdp_" + (i + 2));
            profilePicture.setSuperheroId("id");
            profilePictures.add(profilePicture);
        }
        superhero.setProfilePictures(profilePictures);
        superhero.setGender(2);
        superhero.setAge(25);
        superhero.setLat(5.0);
        superhero.setLon(5.0);
        superhero.setCountry("USA");
        superhero.setCity("L.A.");
        superhero.setSuperpower("I always dance like nobody is watching");
        superhero.setAccountType("PAID");
        superhero.setHasLikedMe(true);
        superhero.setCreatedAt("2020-01-11");

        return superhero;
    }

    public User createMockUser() {
        ArrayList<String> profilePicUrls = new ArrayList<>();
        profilePicUrls.add(0, "test");// add main profile pic first always
        profilePicUrls.add("test5");
        profilePicUrls.add("test2");
        profilePicUrls.add("test3");
        profilePicUrls.add("test7");

        User user = new User();
        user.setId("311234567890L");
        user.setName("Superhero");
        user.setSuperHeroName("Superhero");
        user.setMainProfilePicUrl("test");
        user.setProfilePicsUrls(profilePicUrls);
        user.setGender(1);
        user.setAge(32);
        user.setBirthday("30-05-1985");
        user.setCountry("Netherlands");
        user.setCity("Utrecht");
        user.setSuperPower("My Super Power described here but really long to check how it looks on the screen needs to be around 126 characters.");
        user.setAccountType("PAID");

        return user;
    }

    public ArrayList<Message> createMockMessages() {
        ArrayList<Message> msgs = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Message message = new Message();
            message.setMessageChatId("testuuid");
            message.setMessageCreated("22-04-2018 16:51:00");
            message.setMessageId(i);
            message.setMessageText("Just testing.");
            message.setMessageUUID(UUID.randomUUID().toString().replace("-", ""));
            if (i % 3 == 0) {
                message.setMessageSenderId("312345678900L");
            } else {
                message.setMessageSenderId("311234567890L");
            }

            msgs.add(message);
        }

        return msgs;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void sendMessageClickListener(String message, String receiverId) {
        Message msg = new Message();
        msg.setMessageChatId("testuuid");
        msg.setMessageCreated("22-04-2018 16:51:00");
        msg.setMessageId(1);
        msg.setMessageText(message);
        msg.setMessageUUID(UUID.randomUUID().toString().replace("-", ""));
        msg.setMessageSenderId("311234567890L");

        messages.add(msg);
        EventBus.getDefault().post(new TextMessageEvent(messages));

        OutgoingMessage outgoingMessage = new OutgoingMessage(
                ConstantRegistry.CHAT_MESSAGE,
                mainPresenter.getUserId(MainActivity.this),
                receiverId,
                message
        );

        Log.d("tShoot", "sendMessageClickListener --> message: ");
        Log.d("tShoot", outgoingMessage.toString());

        webSocket.send(outgoingMessage.toString());
    }

    public String getUserId() {
        return "311234567890L";
    }

    public String getDateTime() {
        return "2018-04-22 16:40:00";
    }

    public String getContactNameById(Context context, String messageSenderId) {
        return "Niko";
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

    private void processUpdateProfilePic(Uri uri) {
        switch (getCurrentProfileImageView()) {
            case ConstantRegistry.MAIN_PROFILE_IMAGE_VIEW:
                MainProfilePicSettingsEvent mainProfilePicSettingsEvent = new MainProfilePicSettingsEvent(uri);
                EventBus.getDefault().post(mainProfilePicSettingsEvent);

                break;
            case ConstantRegistry.FIRST_PROFILE_IMAGE_VIEW:
                ProfilePic1SettingsEvent profilePic1SettingsEvent = new ProfilePic1SettingsEvent(uri);
                EventBus.getDefault().post(profilePic1SettingsEvent);

                break;
            case ConstantRegistry.SECOND_PROFILE_IMAGE_VIEW:
                ProfilePic2SettingsEvent profilePic2SettingsEvent = new ProfilePic2SettingsEvent(uri);
                EventBus.getDefault().post(profilePic2SettingsEvent);

                break;
            case ConstantRegistry.THIRD_PROFILE_IMAGE_VIEW:
                ProfilePic3SettingsEvent profilePic3SettingsEvent = new ProfilePic3SettingsEvent(uri);
                EventBus.getDefault().post(profilePic3SettingsEvent);

                break;
            case ConstantRegistry.FOURTH_PROFILE_IMAGE_VIEW:
                ProfilePic4SettingsEvent profilePic4SettingsEvent = new ProfilePic4SettingsEvent(uri);
                EventBus.getDefault().post(profilePic4SettingsEvent);

                break;
        }
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

    private void getSuggestions(HashMap<String, Object> reqBody, boolean isInitialRequest) {
        if (!loadingDialogIsActive) {
            showLoadingDialog();
        }

        subscribeSuggestions = mainPresenter.getSuggestions(reqBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((SuggestionsResponse res) -> {
                    stopLocationUpdates();

                    closeLoadingDialog();

                    if (res.getStatus() == 500) {
                        handleError();

                        return;
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
        )).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((ChoiceResponse res) -> {
                    if (res.getStatus() == 500) {
                        Toast.makeText(
                                MainActivity.this,
                                R.string.smth_went_wrong,
                                Toast.LENGTH_LONG
                        ).show();

                        return;
                    }

                    // If it's a match, show dialog.
                    if (res.isMatch()) {
                        handleMatch(mainPresenter, suggestion, mainPresenter.createUUID());
                    }
                }, throwable -> handleError());

        disposable.add(subscribeUploadChoice);
    }

    public void showDialogDeleteMatch(final String chatId, final int position, final ArrayList<Chat> chats,
                                      final MatchesChatsAdapter matchesChatsAdapter) {

        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        handleDeleteChatDialogButtonClick(button, mainPresenter, chatId, chats, position, matchesChatsAdapter);
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Delete match?")
                .setPositiveButton("Delete", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener)
                .show();
    }

    private void handleDeleteChatDialogButtonClick(
            int button,
            MainPresenter mainPresenter,
            String chatId,
            ArrayList<Chat> chats,
            int position,
            MatchesChatsAdapter matchesChatsAdapter
    ) {
        if (button == DialogInterface.BUTTON_NEGATIVE) {
            return;
        }

        mainPresenter.deleteChatById(chatId, MainActivity.this);
        deleteMatch(chats.get(position).getChatId());
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

    private void uploadMatch(HashMap<String, Object> reqBody) {
        subscribeUploadMatch = mainPresenter.uploadMatch(reqBody).subscribeOn(Schedulers.io())
                .subscribe((Integer res) -> {
                    if (res == 500) {
                        Log.e(MainActivity.class.getName(), getString(R.string.upload_match_error_msg));
                    }
                }, throwable -> handleErrorInBackground());

        disposable.add(subscribeUploadMatch);
    }

    private void updateFirebaseToken(HashMap<String, Object> reqBody) {
        subscribeUpdateUserToken = mainPresenter.updateFirebaseToken(reqBody).subscribeOn(Schedulers.io())
                .subscribe((Integer res) -> {
                    if (res == 500) {
                        Log.e(MainActivity.class.getName(), "Error while updating Firebase messaging token");
                    }
                }, throwable -> handleErrorInBackground());

        disposable.add(subscribeUpdateUserToken);
    }

    public void deleteMatch(String matchId) {
        HashMap<String, Object> reqBody = new HashMap<>();
        reqBody.put("id", matchId);

        subscribeDeleteMatch = mainPresenter.deleteMatch(reqBody).subscribeOn(Schedulers.io())
                .subscribe((Integer res) -> {
                    if (res == 500) {
                        Log.e(MainActivity.class.getName(), getString(R.string.upload_match_error_msg));
                    }
                }, throwable -> handleErrorInBackground());

        disposable.add(subscribeDeleteMatch);
    }


    public void updateUserProfile() {
        updateProfile(configureUpdateProfileRequestBody(mainPresenter));
    }

    private void updateProfile(HashMap<String, Object> reqBody) {
        if (!loadingDialogIsActive) {
            showLoadingDialog();
        }

        subscribeUpdateProfile = mainPresenter.updateProfile(reqBody)
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

    @OnClick(R.id.ivSuggestionLike)
    public void onSuggestionLike() {
        if (this.suggestions.size() == 0) {
            return;
        }

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

    @OnClick(R.id.ivSuggestionDislike)
    public void onSuggestionDislike() {
        if (this.suggestions.size() == 0) {
            return;
        }

        // Send the choice to the server.
        uploadChoice(
                this.mainPresenter,
                this.suggestions.get(this.currentSuggestion),
                ConstantRegistry.DISLIKE
        );

        loadNextSuggestion();
    }

    public void loadNextSuggestion() {
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

    @OnClick(R.id.ivSuperPowerIconSuggestion)
    public void onSuggestionSuperpowerIcon() {
        if ((this.suggestions.size() > 0) && ((this.suggestions.size() - 1) >= this.currentSuggestion)) {
            openSuggestionDescriptionWindow();

            loadSuggestionDescriptionFragment(
                    SuggestionDescriptionFragment.newInstance(
                            this.suggestions.get(this.currentSuggestion)
                    )
            );
        }
    }

    private void handleNotificationAction() {
        if (getIntent().getExtras() != null && getIntent().getAction() != null) {
            if (ConstantRegistry.NEW_MATCH_REQUEST.equals(getIntent().getAction())) {
                removeNotifications();
                navigation.setSelectedItemId(R.id.navigation_matches);
            }
        }
    }

    private void removeNotifications() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }
}
