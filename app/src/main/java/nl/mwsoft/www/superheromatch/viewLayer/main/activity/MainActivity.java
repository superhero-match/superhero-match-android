package nl.mwsoft.www.superheromatch.viewLayer.main.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.crashlytics.android.Crashlytics;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.fabric.sdk.android.Fabric;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.coordinator.RootCoordinator;
import nl.mwsoft.www.superheromatch.dependencyRegistry.DependencyRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.event.SuperheroProfilePicEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.TextMessageEvent;
import nl.mwsoft.www.superheromatch.modelLayer.model.Chat;
import nl.mwsoft.www.superheromatch.modelLayer.model.Message;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.presenterLayer.main.MainPresenter;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.MatchesChatsFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.SuggestionsFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.UserProfileEditFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.UserProfileFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.UserProfileSettingsFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {

    private int currFragmentPosition;
    private User user;
    private ArrayList<Message> messages;
    private boolean isRedirectedToRegister = false;
    @BindView(R.id.bnMain)
    BottomNavigationView navigation;
    @BindView(R.id.tlMain)
    Toolbar tlMain;
    private Unbinder unbinder;
    private RootCoordinator rootCoordinator;
    private MainPresenter mainPresenter;
    private Uri profilePicURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        DependencyRegistry.shared.inject(this);
        setSupportActionBar(tlMain);
        messages = new ArrayList<>();
        messages.addAll(createMockMessages());
        user = new User();

        // navigateToRegister(this);

        // navigateToVerifyIdentity(this);

        if(getIntent().getAction() == null || !getIntent().getAction().equals("User")){
            isRedirectedToRegister = true;
            // navigateToRegister(this);
            navigateToVerifyIdentity(this);
        }

        if (getIntent().getExtras() != null) {
            user = getIntent().getExtras().getParcelable("User");
            Log.d("tShoot", user != null ? user.toString() : String.valueOf(R.string.smth_went_wrong));
        }

        if(!isRedirectedToRegister){
            navigation.setOnNavigationItemSelectedListener(myOnNavigationItemSelectedListener);
            navigation.setSelectedItemId(R.id.navigation_suggestions);
        }
    }

    public void configureWith(RootCoordinator rootCoordinator, MainPresenter mainPresenter){
        this.rootCoordinator = rootCoordinator;
        this.mainPresenter = mainPresenter;
    }

    public void navigateToIntro(Context context){
        rootCoordinator.navigateToIntroFromMain(context);
        finish();
    }

    public void navigateToVerifyIdentity(Context context){
        rootCoordinator.navigateToVerifyIdentityActivity(context);
        finish();
    }

    public void navigateToRegister(Context context){
        rootCoordinator.navigateToRegisterFromMain(context);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindButterKnife();
    }

    private void unbindButterKnife() {
        if(unbinder != null){
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
                    fragment = SuggestionsFragment.newInstance(createMockUsers());
                    loadFragment(fragment);
                    return true;
//                case R.id.navigation_location:
//                    currFragmentPosition = 2;
//                    fragment = NearbyFragment.newInstance(createMockUsers());
//                    loadFragment(fragment);
//                    return true;
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

    public void loadSuggestionUserProfileFragment(User user){
        loadBackStackFragment(UserProfileFragment.newInstance(user));
    }

    public void loadSuggestionUserProfileSettingsFragment(User user){
        loadBackStackFragment(UserProfileSettingsFragment.newInstance(user));
    }

    public void loadSuggestionUserProfileEditFragment(){
        loadBackStackFragment(UserProfileEditFragment.newInstance());
    }

    public User createMockUser(){
        ArrayList<String> urls = new ArrayList<>(createMockUserProfilePicturesUrls());
        User user = new User();
        user.setUserID("311234567890L");
        user.setName("Super Hero");
        user.setMainProfilePicUrl("main");
        user.setProfilePicsUrls(urls);
        user.setGender(1);
        user.setAge(32);
        user.setBirthday("30-05-1985");
        user.setCountry("Netherlands");
        user.setCity("Utrecht");
        user.setSuperPower("My Super Power described here but really long to check how it looks on the screen needs to be around 250 characters");
        user.setAccountType("PAID");

        return user;
    }

    public ArrayList<User> createMockUsers(){
        ArrayList<User> users = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            User user = createMockUser();
            user.setUserID("311234567890");
            users.add(user);
        }

        return users;
    }

    public ArrayList<String> createMockUserProfilePicturesUrls(){
        ArrayList<String> userProfilePicturesUrls = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            String userProfilePictureUrl = "mockUrl";
            userProfilePicturesUrls.add(userProfilePictureUrl);
        }

        return userProfilePicturesUrls;
    }

    public ArrayList<Chat> createMockMatchChats(){
        ArrayList<Chat> matchChats = new ArrayList<>();
        for(int i = 1; i <= 10; i++){
            Chat matchChat = new Chat();
            matchChat.setChatId(1);
            matchChat.setChatName("Test " + i);
            matchChat.setUserName("Super Hero " + i);
            matchChat.setLastActivityMessage("Test last message " + i);
            matchChat.setLastActivityDate("14-04-2018");
            if(i%3==0){
                matchChat.setUnreadMessageCount(5);
            }else{
                matchChat.setUnreadMessageCount(0);
            }

            matchChats.add(matchChat);
        }

        return matchChats;
    }

    public ArrayList<Message> createMockMessages(){
        ArrayList<Message> msgs = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            Message message = new Message();
            message.setMessageChatId(1);
            message.setMessageCreated("22-04-2018 16:51:00");
            message.setMessageId(i);
            message.setMessageText("Just testing.");
            message.setMessageUUID(UUID.randomUUID().toString().replace("-",""));
            if(i%3==0){
                message.setMessageSenderId("312345678900L");
            }else{
                message.setMessageSenderId("311234567890L");
            }

            msgs.add(message);
        }

        return msgs;
    }


    public ArrayList<Message> getMessages(){
        return messages;
    }

    public void sendMessageClickListener(String message) {
        Message msg = new Message();
        msg.setMessageChatId(1);
        msg.setMessageCreated("22-04-2018 16:51:00");
        msg.setMessageId(1);
        msg.setMessageText(message);
        msg.setMessageUUID(UUID.randomUUID().toString().replace("-",""));
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
        if(navigation.getVisibility()== View.VISIBLE){
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
        if(navigation.getVisibility()== View.GONE){
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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == ConstantRegistry.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
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
}
