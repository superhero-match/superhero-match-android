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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
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
import nl.mwsoft.www.superheromatch.modelLayer.event.SuperheroProfilePicEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.TextMessageEvent;
import nl.mwsoft.www.superheromatch.modelLayer.model.Chat;
import nl.mwsoft.www.superheromatch.modelLayer.model.Message;
import nl.mwsoft.www.superheromatch.modelLayer.model.SuggestionsResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.Superhero;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.presenterLayer.main.MainPresenter;
import nl.mwsoft.www.superheromatch.viewLayer.dialog.loadingDialog.LoadingDialogFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.MatchesChatsFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.SuggestionFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.UserProfileEditFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.UserProfileFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.UserProfileSettingsFragment;
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
    private HashMap<String, Object> reqBody;
    private CompositeDisposable disposable;
    private Disposable subscribe;
    private LoadingDialogFragment loadingDialogFragment;
    private int offset = 0;
    private double lat = 0.0;
    private double lon = 0.0;
    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private Location currentLocation;
    private boolean loadingDialogIsActive;

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

    private void configureRequestBody() {
        reqBody.put("id", mainPresenter.getUserId(this));
        reqBody.put("lookingForGender", mainPresenter.getUserLookingForGender(this));
        reqBody.put("gender", mainPresenter.getUserGender(this));
        reqBody.put("lookingForAgeMin", mainPresenter.getUserLookingForMinAge(this));
        reqBody.put("lookingForAgeMax", mainPresenter.getUserLookingForMaxAge(this));
        reqBody.put("maxDistance", mainPresenter.getUserLookingForMaxDistance(this));
        reqBody.put("distanceUnit", mainPresenter.getUserDistanceUnit(this));

        reqBody.put("lat", lat);
        if (lat == 0.0) {
            reqBody.put("lat", mainPresenter.getUserLat(this));
        }

        reqBody.put("lon", lon);
        if (lon == 0.0) {
            reqBody.put("lon", mainPresenter.getUserLon(this));
        }

        reqBody.put("offset", offset);
        reqBody.put("size", ConstantRegistry.PAGE_SIZE);
    }

    private void init() {
        messages = new ArrayList<>();
        messages.addAll(createMockMessages());
        reqBody = new HashMap<>();
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
                    Superhero superhero = new Superhero(
                            "id",
                            "SuperheroName",
                            "mainProfilePicUrl",
                            null,
                            1,
                            34,
                            10.00,
                            10.00,
                            "Country",
                            "City",
                            "My Super Power described here but really long to check how it looks on the screen needs to be around 126 characters.",
                            "FREE"
                    );
                    fragment = SuggestionFragment.newInstance(superhero);
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
        transaction.replace(R.id.frame_main_container, fragment);
        transaction.commit();
    }

    public void loadBackStackFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void loadSuggestionUserProfileFragment(User user) {
        loadBackStackFragment(UserProfileFragment.newInstance(user));
    }

    public void loadSuggestionUserProfileSettingsFragment(User user) {
        loadBackStackFragment(UserProfileSettingsFragment.newInstance(user));
    }

    public void loadSuggestionUserProfileEditFragment() {
        loadBackStackFragment(UserProfileEditFragment.newInstance());
    }

    public User createMockUser() {
        ArrayList<String> urls = new ArrayList<>(createMockUserProfilePicturesUrls());
        User user = new User();
        user.setId("311234567890L");
        user.setName("Super Hero");
        user.setMainProfilePicUrl("main");
        user.setProfilePicsUrls(urls);
        user.setGender(1);
        user.setAge(32);
        user.setBirthday("30-05-1985");
        user.setCountry("Netherlands");
        user.setCity("Utrecht");
        user.setSuperPower("My Super Power described here but really long to check how it looks on the screen needs to be around 126 characters.");
        user.setAccountType("PAID");

        return user;
    }

    public ArrayList<User> createMockUsers() {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = createMockUser();
            user.setId("311234567890");
            users.add(user);
        }

        return users;
    }

    public ArrayList<String> createMockUserProfilePicturesUrls() {
        ArrayList<String> userProfilePicturesUrls = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            String userProfilePictureUrl = "mockUrl";
            userProfilePicturesUrls.add(userProfilePictureUrl);
        }

        return userProfilePicturesUrls;
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

    public void setToolbarStatusBarColorPrimary() {
        showBottomNavigation();
        changeToolbarColor(MainActivity.this, R.color.colorPrimary);
        changeStatusBarColor(MainActivity.this, R.color.colorPrimary);
    }

    public void changeToolbarColor(Context context, int colorId) {
        tlMain.setBackgroundColor(ContextCompat.getColor(context, colorId));
    }

    public void showBottomNavigation() {
        if (navigation.getVisibility() == View.GONE) {
            navigation.setVisibility(View.VISIBLE);
        }
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
                ViewGroup.LayoutParams.WRAP_CONTENT);

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

    public Uri getProfilePicURI() {
        return profilePicURI;
    }

    public void setProfilePicURI(Uri profilePicURI) {
        this.profilePicURI = profilePicURI;
    }

    private void processUpdateProfilePic(Uri uri) {
        // TO-DO: Upload the new picture to the server.
        // Response from server should contain the CloudFront url to the image,
        // and this url should be passed to the event and new item should be added to the list of
        // profile pictures.
        setProfilePicURI(uri);
        SuperheroProfilePicEvent event = new SuperheroProfilePicEvent(uri);
        EventBus.getDefault().post(event);
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

        subscribe = mainPresenter.getSuggestions(reqBody)
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

        disposable.add(subscribe);
    }

    private void handleError() {
        closeLoadingDialog();
        stopLocationUpdates();
        Toast.makeText(MainActivity.this, R.string.smth_went_wrong, Toast.LENGTH_LONG).show();
    }

    public void setLatAndLon(String userID, double lat, double lon, Context context) {
        this.lat = lat;
        this.lon = lon;

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

                configureRequestBody();
                Log.d("tShoot", reqBody.toString());
                getSuggestions(reqBody);
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

}