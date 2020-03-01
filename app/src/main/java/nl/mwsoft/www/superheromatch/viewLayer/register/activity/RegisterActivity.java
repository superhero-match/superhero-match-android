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
package nl.mwsoft.www.superheromatch.viewLayer.register.activity;

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

import com.crashlytics.android.Crashlytics;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

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
import nl.mwsoft.www.superheromatch.modelLayer.model.RegisterResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.presenterLayer.register.RegisterPresenter;
import nl.mwsoft.www.superheromatch.viewLayer.dialog.loadingDialog.LoadingDialogFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.register.fragment.RegistrationVPFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

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
    private LoadingDialogFragment loadingDialogFragment;
    private User user;
    @BindView(R.id.tlRegister)
    Toolbar tlRegister;
    private RootCoordinator rootCoordinator;
    private RegisterPresenter registerPresenter;
    private Uri profilePicURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_register);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(tlRegister);
        user = new User();
        disposable = new CompositeDisposable();
        DependencyRegistry.shared.inject(this);
        loadFragment(RegistrationVPFragment.newInstance());

        configureInitialValues();

        if (checkLocationPermission()) {
            init();
            startLocationUpdates();
        }
    }

    private void configureInitialValues() {
        if (getIntent().getAction() == null) {
            return;
        }

        if (getIntent().getAction().equals("register")) {
            String name = Objects.requireNonNull(getIntent().getExtras()).getString("name");
            user.setName(name);

            String email = Objects.requireNonNull(getIntent().getExtras()).getString("email");
            user.setEmail(email);

            String userID = registerPresenter.getUUID();
            user.setId(userID);

            user.setLookingForAgeMax(ConstantRegistry.DEFAULT_AGE_MAX);
            user.setLookingForAgeMin(ConstantRegistry.DEFAULT_AGE_MIN);
            user.setLookingForDistanceMax(ConstantRegistry.DEFAULT_DISTANCE_MAX);
            user.setLookingForAgeMax(ConstantRegistry.DEFAULT_AGE_MAX);
            user.setDistanceUnit(ConstantRegistry.DEFAULT_DISTANCE_UNIT);
            user.setAccountType(ConstantRegistry.DEFAULT_ACCOUNT_TYPE);
        }
    }

    public void configureWith(RootCoordinator rootCoordinator, RegisterPresenter registerPresenter) {
        this.rootCoordinator = rootCoordinator;
        this.registerPresenter = registerPresenter;
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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
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
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public void navigateToMain(Context context) {
        rootCoordinator.navigateToMainFromRegister(context);
        finish();
    }

    public User getUser() {
        return user;
    }

    public boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
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

    public boolean processSuperheroNameContinue() {
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

    public boolean processSuperheroBirthdayContinue() {
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

    public boolean processSuperheroGenderContinue() {
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

    public boolean processSuperheroLookingForGenderContinue() {
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

    public boolean processSuperPowerContinue() {
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

    public boolean processDistanceUnitContinue() {
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

    public boolean processProfilePicContinue() {
        if (getProfilePicURI() == null) {
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

    private void processUpdateProfilePic(Uri uri) {
        setProfilePicURI(uri);
        SuperheroProfilePicEvent event = new SuperheroProfilePicEvent(uri);
        EventBus.getDefault().post(event);
    }

    public boolean accessFilesPermissionIsGranted() {
        return ContextCompat.checkSelfPermission(
                RegisterActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissionWriteExternalStorage() {
        ActivityCompat.requestPermissions(
                RegisterActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                ConstantRegistry.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ConstantRegistry.MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        init();
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

    private void init() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                currentLocation = locationResult.getLastLocation();

                setLatAndLong(currentLocation);
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

    private void setLatAndLong(Location currentLocation) {
        if (currentLocation != null) {
            Toast.makeText(
                    RegisterActivity.this,
                    "setLatAndLong Lat: " + currentLocation.getLatitude(),
                    Toast.LENGTH_SHORT
            ).show();

            Toast.makeText(
                    RegisterActivity.this,
                    "setLatAndLong Lon: " + currentLocation.getLongitude(),
                    Toast.LENGTH_SHORT
            ).show();

            setLat(currentLocation.getLatitude());
            setLon(currentLocation.getLongitude());

            setAddress(getLat(), getLon());
        }
    }

    private void setAddress(double lat, double lon) {
        try {
            Geocoder gcd = new Geocoder(RegisterActivity.this, Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(lat, lon, 1);
            if (addresses.size() > 0) {
                Toast.makeText(
                        RegisterActivity.this,
                        "City: " + addresses.get(0).getLocality(),
                        Toast.LENGTH_SHORT
                ).show();

                Toast.makeText(
                        RegisterActivity.this,
                        "Country: " + addresses.get(0).getCountryName(),
                        Toast.LENGTH_SHORT
                ).show();

                user.setCity(addresses.get(0).getLocality());
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
                        Toast.makeText(getApplicationContext(), getString(R.string.location_updates_started), Toast.LENGTH_SHORT).show();

                        fusedLocationClient.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());

                        setLatAndLong(currentLocation);
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

                        setLatAndLong(currentLocation);
                    }
                });
    }

    private void closeLoadingDialog() {
        if (loadingDialogFragment != null) {
            loadingDialogFragment.dismiss();
        }
    }

    private void showLoadingDialog() {
        loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.setCancelable(false);
        loadingDialogFragment.show(getSupportFragmentManager(), ConstantRegistry.LOADING);
    }

    public void registerUser() {
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

                        register(convertToJSON(getUser(), token));
                    }
                });
    }

    public HashMap<String, Object> convertToJSON(User user, String token) {
        HashMap<String, Object> userJson = new HashMap();
        userJson.put("id", user.getId());
        userJson.put("email", user.getEmail());
        userJson.put("name", user.getName());
        userJson.put("superheroName", user.getSuperHeroName());
        userJson.put("mainProfilePicUrl", user.getMainProfilePicUrl());
        userJson.put("gender", user.getGender());
        userJson.put("lookingForGender", user.getLookingForGender());
        userJson.put("age", user.getAge());
        userJson.put("lookingForAgeMin", user.getLookingForAgeMin());
        userJson.put("lookingForAgeMax", user.getLookingForAgeMax());
        userJson.put("lookingForDistanceMax", user.getLookingForDistanceMax());
        userJson.put("distanceUnit", user.getDistanceUnit());
        userJson.put("lat", user.getLat());
        userJson.put("lon", user.getLon());
        userJson.put("birthday", user.getBirthday());
        userJson.put("country", user.getCountry());
        userJson.put("city", user.getCity());
        userJson.put("superpower", user.getSuperPower());
        userJson.put("accountType", user.getAccountType());
        userJson.put("firebaseToken", token);

        return userJson;
    }

    public void register(HashMap<String, Object> body) {
        showLoadingDialog();

        Log.d("tShoot", body.toString());

        subscribe = registerPresenter.register(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((RegisterResponse res) -> {
                    closeLoadingDialog();
                    Toast.makeText(
                            RegisterActivity.this,
                            res.toString(),
                            Toast.LENGTH_LONG
                    ).show();


                    if (res.getStatus() == 500) {
                        Toast.makeText(
                                RegisterActivity.this,
                               "res.getStatus() == 500",
                                Toast.LENGTH_LONG
                        ).show();
                        Toast.makeText(
                                RegisterActivity.this,
                                R.string.smth_went_wrong,
                                Toast.LENGTH_LONG
                        ).show();

                        return;
                    }

                    if (res.isRegistered()) {
                        Toast.makeText(
                                RegisterActivity.this,
                                "res.isRegistered()",
                                Toast.LENGTH_LONG
                        ).show();
                        registerPresenter.updateInitiallyRegisteredUser(user, RegisterActivity.this);
                        navigateToMain(RegisterActivity.this);
                    }

                    Toast.makeText(RegisterActivity.this, res.toString(), Toast.LENGTH_LONG).show();
                }, throwable -> handleError());

        disposable.add(subscribe);
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
