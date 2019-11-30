package nl.mwsoft.www.superheromatch.viewLayer.main.activity;

import android.Manifest;
import android.annotation.SuppressLint;
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

import com.crashlytics.android.Crashlytics;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
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
import io.fabric.sdk.android.Fabric;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.coordinator.RootCoordinator;
import nl.mwsoft.www.superheromatch.dependencyRegistry.DependencyRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.event.MainProfilePicSettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.ProfilePic1SettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.ProfilePic2SettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.ProfilePic3SettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.ProfilePic4SettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.SuperheroProfilePicEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.TextMessageEvent;
import nl.mwsoft.www.superheromatch.modelLayer.model.Chat;
import nl.mwsoft.www.superheromatch.modelLayer.model.Message;
import nl.mwsoft.www.superheromatch.modelLayer.model.SuggestionsResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.Superhero;
import nl.mwsoft.www.superheromatch.modelLayer.model.UpdateResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.presenterLayer.main.MainPresenter;
import nl.mwsoft.www.superheromatch.viewLayer.dialog.loadingDialog.LoadingDialogFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.ImageDetailFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.matches.MatchesChatsFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.UserProfilePictureSettingsFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.suggestions.SuggestionDescriptionFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.suggestions.SuggestionFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.UserProfileEditFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.UserProfileFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.UserProfileSettingsSuggestionsFragment;
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
    private LoadingDialogFragment loadingDialogFragment;
    private int offset = 0;
    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private Location currentLocation;
    private boolean loadingDialogIsActive;
    @BindView(R.id.frame_superhero_details)
    FrameLayout suggestionFrameLayout;
    public int currentProfileImageView = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        DependencyRegistry.shared.inject(this);
        setSupportActionBar(tlMain);

        init();
        // || mainPresenter.getUserIsLoggedIn(this) == 0
        if (mainPresenter.getUserId(this).equals("default")) {
            navigateToVerifyIdentity(this);
        } else {
            navigation.setOnNavigationItemSelectedListener(myOnNavigationItemSelectedListener);
            navigation.setSelectedItemId(R.id.navigation_suggestions);

//            if (checkLocationPermission()) {
//                showLoadingDialog();
//                initLocationUpdateService();
//                startLocationUpdates();
//            }
        }
    }

    private HashMap<String, Object> configureSuggestionsRequestBody(MainPresenter mainPresenter) {
        HashMap<String, Object> reqBodySuggestions =  new HashMap<>();
        reqBodySuggestions.put("id", mainPresenter.getUserId(this));
        reqBodySuggestions.put("lookingForGender", mainPresenter.getUserLookingForGender(this));
        reqBodySuggestions.put("gender", mainPresenter.getUserGender(this));
        reqBodySuggestions.put("lookingForAgeMin", mainPresenter.getUserLookingForMinAge(this));
        reqBodySuggestions.put("lookingForAgeMax", mainPresenter.getUserLookingForMaxAge(this));
        reqBodySuggestions.put("maxDistance", mainPresenter.getUserLookingForMaxDistance(this));
        reqBodySuggestions.put("distanceUnit", mainPresenter.getUserDistanceUnit(this));
        reqBodySuggestions.put("lat", mainPresenter.getUserLat(this));
        reqBodySuggestions.put("lon", mainPresenter.getUserLon(this));
        reqBodySuggestions.put("offset", offset);
        reqBodySuggestions.put("size", ConstantRegistry.PAGE_SIZE);

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
        HashMap<String, Object> reqBodyUpdateProfile =  new HashMap<>();
        reqBodyUpdateProfile.put("id", mainPresenter.getUserId(this));
        reqBodyUpdateProfile.put("lookingForGender", mainPresenter.getUserLookingForGender(MainActivity.this));
        reqBodyUpdateProfile.put("age",calculateUserAge(new Date(), mainPresenter.getUserBirthday(MainActivity.this)));
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
                    fragment = SuggestionFragment.newInstance(createMockSuperhero());
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_matches:
                    currFragmentPosition = 3;
                    fragment = MatchesChatsFragment.newInstance(createMockMatchChats());
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
            ivSuggestionDislike.setVisibility(View.GONE);
            ivSuggestionLike.setVisibility(View.GONE);
            ivSuperPowerIconSuggestion.setVisibility(View.GONE);
        } else {
            ivSuggestionDislike.setVisibility(View.VISIBLE);
            ivSuggestionLike.setVisibility(View.VISIBLE);
            ivSuperPowerIconSuggestion.setVisibility(View.VISIBLE);
        }
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

    public void closeSuggestionDescriptionWindow(){
        Animation slideDown = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_down);
        suggestionFrameLayout.setVisibility(View.GONE);
        suggestionFrameLayout.startAnimation(slideDown);
    }

    public void openSuggestionDescriptionWindow(){
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

    public void loadProfilePictureSettingsFragment(User user){
        loadBackStackFragment(
                UserProfilePictureSettingsFragment.newInstance(
                        user.getMainProfilePicUrl(),
                        user.getProfilePicsUrls()
                )
        );
    }

    public Superhero createMockSuperhero(){
        ArrayList<String> profilePicUrls = new ArrayList<>();
        profilePicUrls.add(0, "test");// add main profile pic first always
        profilePicUrls.add("test5");
        profilePicUrls.add("test2");
        profilePicUrls.add("test3");
        profilePicUrls.add("test7");

        return new Superhero(
                "id",
                "SuperheroName",
                "test",
                profilePicUrls,
                1,
                34,
                10.00,
                10.00,
                "Country",
                "City",
                "My Super Power described here but really long to check how it looks on the screen needs to be around 126 characters.",
                "FREE"
        );
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

    public ArrayList<Chat> createMockMatchChats() {
        ArrayList<Chat> matchChats = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Chat matchChat = new Chat();
            matchChat.setChatId(1);
            matchChat.setChatName("Test " + i);
            matchChat.setUserName("Super Hero " + i);
            matchChat.setLastActivityMessage("Test last message " + i);
            matchChat.setLastActivityDate("14-04-2018");
            if (i % 3 == 0) {
                matchChat.setUnreadMessageCount(5);
            } else {
                matchChat.setUnreadMessageCount(0);
            }

            matchChats.add(matchChat);
        }

        return matchChats;
    }

    public ArrayList<Message> createMockMessages() {
        ArrayList<Message> msgs = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Message message = new Message();
            message.setMessageChatId(1);
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

    public void sendMessageClickListener(String message) {
        Message msg = new Message();
        msg.setMessageChatId(1);
        msg.setMessageCreated("22-04-2018 16:51:00");
        msg.setMessageId(1);
        msg.setMessageText(message);
        msg.setMessageUUID(UUID.randomUUID().toString().replace("-", ""));
        msg.setMessageSenderId("311234567890L");

        messages.add(msg);
        EventBus.getDefault().post(new TextMessageEvent(messages));
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
        switch (getCurrentProfileImageView()){
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

        if (requestCode == ConstantRegistry.PICK_PROFILE_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
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

    private void getSuggestions(HashMap<String, Object> reqBody) {
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
                        Toast.makeText(
                                MainActivity.this,
                                R.string.smth_went_wrong,
                                Toast.LENGTH_LONG
                        ).show();

                        return;
                    }

                    Toast.makeText(MainActivity.this, res.toString(), Toast.LENGTH_LONG).show();
                }, throwable -> handleError());

        disposable.add(subscribeSuggestions);
    }

    public void updateUserProfile(){
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

                        return;
                    }

                    Toast.makeText(MainActivity.this, res.toString(), Toast.LENGTH_LONG).show();
                }, throwable -> handleError());

        disposable.add(subscribeUpdateProfile);
    }

    private void handleError() {
        closeLoadingDialog();
        stopLocationUpdates();
        Toast.makeText(MainActivity.this, R.string.smth_went_wrong, Toast.LENGTH_LONG).show();
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
                    Toast.makeText(
                            MainActivity.this,
                            "initLocationUpdateService Lat: " + currentLocation.getLatitude(),
                            Toast.LENGTH_SHORT
                    ).show();

                    Toast.makeText(
                            MainActivity.this,
                            "initLocationUpdateService Lon: " + currentLocation.getLongitude(),
                            Toast.LENGTH_SHORT
                    ).show();

                    setLatAndLon(
                            mainPresenter.getUserId(MainActivity.this),
                            currentLocation.getLatitude(),
                            currentLocation.getLongitude(),
                            MainActivity.this
                    );

                    setAddress(currentLocation.getLatitude(), currentLocation.getLongitude());
                }

                getSuggestions(configureSuggestionsRequestBody(mainPresenter));
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
                Toast.makeText(
                        MainActivity.this,
                        "City: " + addresses.get(0).getLocality(),
                        Toast.LENGTH_SHORT
                ).show();

                Toast.makeText(
                        MainActivity.this,
                        "Country: " + addresses.get(0).getCountryName(),
                        Toast.LENGTH_SHORT
                ).show();

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
                        Toast.makeText(
                                MainActivity.this,
                                getString(R.string.location_updates_started),
                                Toast.LENGTH_SHORT
                        ).show();

                        fusedLocationClient.requestLocationUpdates(
                                locationRequest,
                                locationCallback,
                                Looper.myLooper()
                        );

                        if (currentLocation != null) {
                            Toast.makeText(
                                    MainActivity.this,
                                    "startLocationUpdates Lat: " + currentLocation.getLatitude(),
                                    Toast.LENGTH_SHORT
                            ).show();

                            Toast.makeText(
                                    MainActivity.this,
                                    "startLocationUpdates Lon: " + currentLocation.getLongitude(),
                                    Toast.LENGTH_SHORT
                            ).show();

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
                            Toast.makeText(
                                    MainActivity.this,
                                    "OnFailureListener Lat: " + currentLocation.getLatitude(),
                                    Toast.LENGTH_SHORT
                            ).show();

                            Toast.makeText(
                                    MainActivity.this,
                                    "OnFailureListener Lon: " + currentLocation.getLongitude(),
                                    Toast.LENGTH_SHORT
                            ).show();

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
        // TO-DO: make request to server to process like and to check if there is match,
        // and then remove the user from the list and return to the suggestions list.
        loadNextSuggestion(SuggestionFragment.newInstance(createMockSuperhero()));
    }

    @OnClick(R.id.ivSuggestionDislike)
    public void onSuggestionDislike() {
        // TO-DO: make request to server to process dislike,
        // and then remove the user from the list and return to the suggestions list.
        loadNextSuggestion(SuggestionFragment.newInstance(createMockSuperhero()));
    }

    @OnClick(R.id.ivSuperPowerIconSuggestion)
    public void onSuggestionSuperpowerIcon() {
        openSuggestionDescriptionWindow();
       loadSuggestionDescriptionFragment(SuggestionDescriptionFragment.newInstance(createMockSuperhero()));
    }

}
