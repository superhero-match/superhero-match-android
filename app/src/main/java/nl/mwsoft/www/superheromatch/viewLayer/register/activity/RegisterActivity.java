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
package nl.mwsoft.www.superheromatch.viewLayer.register.activity;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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
import nl.mwsoft.www.superheromatch.modelLayer.event.SuperheroProfilePicEvent;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.RegisterResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.TokenResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.presenterLayer.register.RegisterPresenter;
import nl.mwsoft.www.superheromatch.viewLayer.dialog.loadingDialog.LoadingDialogFragment;
import nl.mwsoft.www.superheromatch.viewLayer.register.fragment.RegistrationVPFragment;

public class RegisterActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private Location currentLocation;
    private Unbinder unbinder;
    private CompositeDisposable disposable;
    private Disposable subscribe;
    private Disposable subscribeGetToken;
    private LoadingDialogFragment loadingDialogFragment;
    private User user;
    @BindView(R.id.tlRegister)
    Toolbar tlRegister;
    private RootCoordinator rootCoordinator;
    private RegisterPresenter registerPresenter;
    private Uri profilePicURI;
    private Socket socket;
    private final int WAIT_TIME = 5000;
    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(tlRegister);
        user = new User();
        disposable = new CompositeDisposable();
        DependencyRegistry.shared.inject(this);
        loadFragment(RegistrationVPFragment.newInstance());

        configureInitialValues();

        setUpConnectionToServer();

        if (checkLocationPermission()) {
            init();
            startLocationUpdates();
        }
    }

    private void configureInitialValues() {
        uiHandler = new Handler(); // anything posted to this handler will run on the UI Thread

        if (getIntent().getAction() == null) {
            return;
        }

        if (getIntent().getAction().equals(ConstantRegistry.REGISTER)) {
            String name = Objects.requireNonNull(getIntent().getExtras()).getString(ConstantRegistry.NAME);
            user.setName(name);

            String email = Objects.requireNonNull(getIntent().getExtras()).getString(ConstantRegistry.EMAIL);
            user.setEmail(email);

            String userID = registerPresenter.getUUID();
            user.setId(userID);

            user.setLookingForAgeMax(ConstantRegistry.DEFAULT_AGE_MAX);
            user.setLookingForAgeMin(ConstantRegistry.DEFAULT_AGE_MIN);
            user.setLookingForDistanceMax(ConstantRegistry.DEFAULT_DISTANCE_MAX);
            user.setLookingForAgeMax(ConstantRegistry.DEFAULT_AGE_MAX);
            user.setDistanceUnit(ConstantRegistry.DEFAULT_DISTANCE_UNIT);
            user.setAccountType(ConstantRegistry.DEFAULT_ACCOUNT_TYPE);
            user.setCountry(ConstantRegistry.DEFAULT_COUNTRY);
            user.setCity(ConstantRegistry.DEFAULT_CITY);
            user.setLat(ConstantRegistry.DEFAULT_LATITUDE);
            user.setLon(ConstantRegistry.DEFAULT_LONGITUDE);
        }
    }

    public void configureWith(RootCoordinator rootCoordinator, RegisterPresenter registerPresenter) {
        this.rootCoordinator = rootCoordinator;
        this.registerPresenter = registerPresenter;
    }

    private void setUpConnectionToServer() {
        // default settings for all sockets
        IO.setDefaultOkHttpWebSocketFactory(OkHttpClientManager.setUpSecureClientWithoutAuthorization());
        IO.setDefaultOkHttpCallFactory(OkHttpClientManager.setUpSecureClientWithoutAuthorization());

        // set as an option
        IO.Options opts = new IO.Options();
        opts.transports = new String[]{io.socket.engineio.client.transports.WebSocket.NAME};
        opts.callFactory = OkHttpClientManager.setUpSecureClientWithoutAuthorization();
        opts.webSocketFactory = OkHttpClientManager.setUpSecureClientWithoutAuthorization();
        try {
            socket = IO.socket(
                    ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_REGISTER_MEDIA_PORT),
                    opts
            );

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        socket.connect();

        socket.on(ConstantRegistry.MAIN_PROFILE_PICTURE_URL, handleIncomingMainProfilePictureURL);
    }

    private Emitter.Listener handleIncomingMainProfilePictureURL = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    closeLoadingDialog();

                    String mainProfilePictureURL = (String) args[0];

                    if (mainProfilePictureURL == null || mainProfilePictureURL.isEmpty()) {
                        Toast.makeText(RegisterActivity.this, R.string.smth_went_wrong, Toast.LENGTH_LONG).show();

                        return;
                    }

                    user.setMainProfilePicUrl(mainProfilePictureURL);
                }
            });
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindButterKnife();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        if (fusedLocationClient == null || locationCallback == null) {
            return;
        }

        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    public void loadBackStackFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_register_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_register_container, fragment);
        transaction.commit();
    }

    private void unbindButterKnife() {
        if (unbinder == null) {
            return;
        }

        unbinder.unbind();
    }

    public void navigateToMain(Context context) {
        rootCoordinator.navigateToMainFromRegister(context);
        finish();
    }

    public User getUser() {
        return user;
    }

    public boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void setUserName(String userName) {
        user.setName(userName);
    }

    public void setSuperHeroName(String superHeroName) {
        user.setSuperHeroName(superHeroName);
    }

    public void setUserBirthday(String birthday) {
        user.setBirthday(birthday);
    }

    public void setEmail(String email) {
        user.setEmail(email);
    }

    public void setGender(int gender) {
        user.setGender(gender);
    }

    public void setDistanceUnit(String distanceUnit) {
        user.setDistanceUnit(distanceUnit);
    }

    public void setLookingForGender(int lookingForGender) {
        user.setLookingForGender(lookingForGender);
    }

    public void setAge(int age) {
        user.setAge(age);
    }

    public void setCountry(String myCountry) {
        user.setCountry(myCountry);
    }

    public void setCity(String myCity) {
        user.setCity(myCity);
    }

    public void setSuperPower(String superPower) {
        user.setSuperPower(superPower);
    }

    public Uri getProfilePicURI() {
        return profilePicURI;
    }

    public void setProfilePicURI(Uri profilePicURI) {
        this.profilePicURI = profilePicURI;
    }

    public void setLat(double lat) {
        user.setLat(lat);
    }

    public void setLon(double lon) {
        user.setLon(lon);
    }

    public double getLat() {
        return user.getLat();
    }

    public double getLon() {
        return user.getLon();
    }

    public boolean superheroNameHasBeenChosen() {
        if (user.getName() == null) {
            Toast.makeText(
                    RegisterActivity.this,
                    this.getString(R.string.all_fields_are_required),
                    Toast.LENGTH_LONG
            ).show();

            return false;
        }

        return true;
    }

    public boolean superheroBirthdayHasBeenChosen() {
        if (user.getBirthday() == null) {
            Toast.makeText(
                    RegisterActivity.this,
                    this.getString(R.string.all_fields_are_required),
                    Toast.LENGTH_LONG
            ).show();

            return false;
        }

        return true;
    }

    public boolean superheroGenderHasBeenChosen() {
        if (user.getGender() == 0) {
            Toast.makeText(
                    RegisterActivity.this,
                    this.getString(R.string.your_gender),
                    Toast.LENGTH_LONG
            ).show();

            return false;
        }

        return true;
    }

    public boolean superheroLookingForGenderHasBeenChosen() {
        if (user.getLookingForGender() == 0) {
            Toast.makeText(
                    RegisterActivity.this,
                    this.getString(R.string.your_favorite_gender),
                    Toast.LENGTH_LONG
            ).show();

            return false;
        }

        return true;
    }

    public boolean superheroSuperpowerHasBeenChosen() {
        if (user.getSuperPower() == null) {
            Toast.makeText(
                    RegisterActivity.this,
                    this.getString(R.string.all_fields_are_required),
                    Toast.LENGTH_LONG
            ).show();

            return false;
        }

        if (user.getSuperPower().equals("")) {
            Toast.makeText(
                    RegisterActivity.this,
                    this.getString(R.string.all_fields_are_required),
                    Toast.LENGTH_LONG
            ).show();

            return false;
        }

        return true;
    }

    public boolean superheroDistanceUnitHasBeenChosen() {
        if (user.getDistanceUnit() == null) {
            Toast.makeText(
                    RegisterActivity.this,
                    this.getString(R.string.all_fields_are_required),
                    Toast.LENGTH_LONG
            ).show();

            return false;
        }

        if (user.getDistanceUnit().equals("")) {
            Toast.makeText(
                    RegisterActivity.this,
                    this.getString(R.string.all_fields_are_required),
                    Toast.LENGTH_LONG
            ).show();

            return false;
        }

        return true;
    }

    public boolean superheroProfilePictureHasBeenChosen() {
        if (getProfilePicURI() == null || user.getMainProfilePicUrl() == null || user.getMainProfilePicUrl().isEmpty()) {
            Toast.makeText(
                    RegisterActivity.this,
                    this.getString(R.string.profile_pic_required),
                    Toast.LENGTH_LONG
            ).show();

            return false;
        }

        return true;
    }

    public boolean verifySuperheroAge(Date currentDate, Date birthday, Context context) {
        long diff = currentDate.getTime() - birthday.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long years = days / 365;

        setAge((int) years);

        if (years >= 18) {
            return true;
        }

        Toast.makeText(context, context.getString(R.string.minimum_age), Toast.LENGTH_SHORT).show();

        return false;
    }

    public void showProfilePicChoice() {
        // Show only images, no videos or anything else
        Intent intent = new Intent();
        intent.setType(ConstantRegistry.DOCUMENT_TYPE_IMAGE);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT); // Intent.ACTION_OPEN_DOCUMENT Intent.ACTION_GET_CONTENT
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

    private void triggerOnUICloseDialog(Runnable onUi) {
        try {
            Thread.sleep(WAIT_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        closeLoadingDialog();

        uiHandler.post(onUi);
    }

    private void processUpdateProfilePic(Uri uri) {
        showLoadingDialog();

        Runnable onUi = new Runnable() {
            @Override
            public void run() {
                closeLoadingDialog();

                setProfilePicURI(uri);
                SuperheroProfilePicEvent event = new SuperheroProfilePicEvent(uri);
                EventBus.getDefault().post(event);
            }
        };

        Runnable background = new Runnable() {
            @Override
            public void run() {
                String encodedImage = registerPresenter.encodeImageToString(RegisterActivity.this, uri);

                if (encodedImage.equals(ConstantRegistry.ERROR)) {
                    Toast.makeText(RegisterActivity.this, R.string.smth_went_wrong, Toast.LENGTH_LONG).show();

                    return;
                }

                socket.emit(
                        ConstantRegistry.ON_UPLOAD_MAIN_PROFILE_PICTURE,
                        user.getId(),
                        encodedImage
                );

                triggerOnUICloseDialog(onUi);
            }
        };

        new Thread(background).start();
    }

    public boolean accessFilesPermissionIsGranted() {
        return ContextCompat.checkSelfPermission(
                RegisterActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissionWriteExternalStorage() {
        ActivityCompat.requestPermissions(
                RegisterActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                ConstantRegistry.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ConstantRegistry.MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length == 0) {
                    return;
                }

                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                init();
                startLocationUpdates();

                break;
            case ConstantRegistry.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showProfilePicChoice();
                } else {
                    showPopupDeniedPermission(findViewById(android.R.id.content).getRootView());
                }

                return;
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

    private void init() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                currentLocation = locationResult.getLastLocation();
            }
        };

        locationRequest = new LocationRequest();
        locationRequest.setInterval(ConstantRegistry.UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(ConstantRegistry.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();

        getLastLocation();

        getLatLong();
    }

    public void getLatLong() {
        if (checkLocationPermission()) {
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                setLatAndLong(location);
            }
        }
    }

    public void getLastLocation() {
        if (checkLocationPermission()) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                setLatAndLong(location);
                            }
                        }
                    });
        }
    }

    private void setLatAndLong(Location currentLocation) {
        if (currentLocation == null) {
            return;
        }

        setLat(currentLocation.getLatitude());
        setLon(currentLocation.getLongitude());
        setAddress(getLat(), getLon());
    }

    private void setAddress(double lat, double lon) {
        try {
            Geocoder gcd = new Geocoder(RegisterActivity.this, Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(lat, lon, 1);

            if (addresses.size() == 0) {
                return;
            }

            if (addresses.get(0).getLocality() != null || !addresses.get(0).getLocality().isEmpty()) {
                user.setCity(addresses.get(0).getLocality());
            }

            if (addresses.get(0).getCountryName() != null || !addresses.get(0).getCountryName().isEmpty()) {
                user.setCountry(addresses.get(0).getCountryName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(
                                        RegisterActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        ConstantRegistry.MY_PERMISSIONS_REQUEST_LOCATION
                                );
                            }
                        })
                        .create()
                        .show();
            }

            ActivityCompat.requestPermissions(
                    this,
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
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        getLastLocation();
                        getLatLong();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(
                                            RegisterActivity.this,
                                            ConstantRegistry.REQUEST_CHECK_SETTINGS
                                    );
                                } catch (IntentSender.SendIntentException sie) {
                                    Toast.makeText(
                                            RegisterActivity.this,
                                            getString(R.string.smth_went_wrong),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }

                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                Toast.makeText(
                                        RegisterActivity.this,
                                        getString(R.string.fix_location_settings),
                                        Toast.LENGTH_LONG
                                ).show();
                        }

                        getLastLocation();
                        getLatLong();
                    }
                });
    }

    private void closeLoadingDialog() {
        if (loadingDialogFragment == null) {
            return;
        }

        loadingDialogFragment.dismissAllowingStateLoss();
    }

    private void showLoadingDialog() {
        loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.setCancelable(true);
        loadingDialogFragment.show(getSupportFragmentManager(), ConstantRegistry.LOADING);
    }

    public void registerUser() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.d(RegisterActivity.class.getName(), "Fetching FCM registration token failed", task.getException());

                            Toast.makeText(RegisterActivity.this, R.string.firebase_token_error, Toast.LENGTH_LONG).show();


                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        register(convertToJSON(getUser(), token));
                    }
                });
    }

    public HashMap<String, Object> convertToJSON(User user, String token) {
        HashMap<String, Object> userJson = new HashMap();

        userJson.put(ConstantRegistry.ID, user.getId());
        userJson.put(ConstantRegistry.EMAIL, user.getEmail());
        userJson.put(ConstantRegistry.NAME, user.getName());
        userJson.put(ConstantRegistry.SUPERHERO_NAME, user.getSuperHeroName());
        userJson.put(ConstantRegistry.MAIN_PROFILE_PIC_URL, user.getMainProfilePicUrl());
        userJson.put(ConstantRegistry.GENDER, user.getGender());
        userJson.put(ConstantRegistry.LOOKING_FOR_GENDER, user.getLookingForGender());
        userJson.put(ConstantRegistry.AGE, user.getAge());
        userJson.put(ConstantRegistry.LOOKING_FOR_AGE_MIN, user.getLookingForAgeMin());
        userJson.put(ConstantRegistry.LOOKING_FOR_AGE_MAX, user.getLookingForAgeMax());
        userJson.put(ConstantRegistry.LOOKING_FOR_DISTANCE_MAX, user.getLookingForDistanceMax());
        userJson.put(ConstantRegistry.DISTANCE_UNIT, user.getDistanceUnit());
        userJson.put(ConstantRegistry.LATITUDE, user.getLat());
        userJson.put(ConstantRegistry.LONGITUDE, user.getLon());
        userJson.put(ConstantRegistry.BIRTHDAY, user.getBirthday());
        userJson.put(ConstantRegistry.COUNTRY, user.getCountry());
        userJson.put(ConstantRegistry.CITY, user.getCity());
        userJson.put(ConstantRegistry.SUPERPOWER, user.getSuperPower());
        userJson.put(ConstantRegistry.ACCOUNT_TYPE, user.getAccountType());
        userJson.put(ConstantRegistry.FIREBASE_TOKEN, token);

        return userJson;
    }

    public void register(HashMap<String, Object> body) {
        showLoadingDialog();

        subscribe = registerPresenter.register(body, RegisterActivity.this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((RegisterResponse res) -> {
                    closeLoadingDialog();

                    if (res.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                        Toast.makeText(
                                RegisterActivity.this,
                                R.string.smth_went_wrong,
                                Toast.LENGTH_LONG
                        ).show();

                        return;
                    }

                    if (!res.isRegistered()) {
                        return;
                    }

                    registerPresenter.updateInitiallyRegisteredUser(user, RegisterActivity.this);
                    navigateToMain(RegisterActivity.this);
                }, throwable -> handleError());

        disposable.add(subscribe);
    }

    private void getToken(String firebaseToken) {
        showLoadingDialog();

        HashMap<String, Object> tokenRequestBody = new HashMap<>();
        tokenRequestBody.put("id", user.getId());

        subscribeGetToken = registerPresenter.getToken(tokenRequestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((TokenResponse res) -> {
                    closeLoadingDialog();

                    if (res.getStatus() != ConstantRegistry.SERVER_STATUS_OK) {
                        Toast.makeText(
                                RegisterActivity.this,
                                R.string.smth_went_wrong,
                                Toast.LENGTH_LONG
                        ).show();

                        return;
                    }

                    SharedPreferences prefs = getSharedPreferences(ConstantRegistry.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(ConstantRegistry.ACCESS_TOKEN, res.getAccessToken());
                    editor.putString(ConstantRegistry.REFRESH_TOKEN, res.getRefreshToken());
                    editor.apply();

                    register(convertToJSON(getUser(), firebaseToken));
                }, throwable -> handleError());

        disposable.add(subscribeGetToken);
    }

    private void handleError() {
        closeLoadingDialog();

        Toast.makeText(
                RegisterActivity.this,
                R.string.smth_went_wrong,
                Toast.LENGTH_LONG
        ).show();
    }
}
