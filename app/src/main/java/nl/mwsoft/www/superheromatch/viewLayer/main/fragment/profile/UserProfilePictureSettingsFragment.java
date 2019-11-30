package nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.event.MainProfilePicSettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.ProfilePic1SettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.ProfilePic2SettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.ProfilePic3SettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.ProfilePic4SettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.SuperheroProfilePicEvent;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class UserProfilePictureSettingsFragment extends Fragment {

    @BindView(R.id.ivMainProfilePic)
    ImageView ivMainProfilePic;
    @BindView(R.id.ivProfilePic1)
    ImageView ivProfilePic1;
    @BindView(R.id.ivProfilePic2)
    ImageView ivProfilePic2;
    @BindView(R.id.ivProfilePic3)
    ImageView ivProfilePic3;
    @BindView(R.id.ivProfilePic4)
    ImageView ivProfilePic4;
    @BindView(R.id.btnUploadImages)
    Button btnUploadImages;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private String mainProfilePicURL;
    private ArrayList<String> profilePicsURLs;
    private static final String MAIN_PROFILE_PIC_URL = "main_profile_pic_url";
    private static final String PROFILE_PIC_URLS = "profile_pic_urls";

    public static UserProfilePictureSettingsFragment newInstance(String mainProfilePicURL, ArrayList<String> profilePicURLs) {
        Bundle args = new Bundle();
        args.putString(MAIN_PROFILE_PIC_URL, mainProfilePicURL);
        args.putStringArrayList(PROFILE_PIC_URLS, profilePicURLs);
        UserProfilePictureSettingsFragment fragment = new UserProfilePictureSettingsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_pictures_settings, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity.hideBottomNavigation();

        Bundle arguments = getArguments();
        if(arguments == null){
            Toast.makeText(mainActivity,"Something went wrong!", Toast.LENGTH_SHORT).show();

            return;
        }

        mainProfilePicURL = arguments.getString(MAIN_PROFILE_PIC_URL);
        if(mainProfilePicURL == null){
            Toast.makeText(mainActivity,"Something went wrong!",Toast.LENGTH_SHORT).show();

            return;
        }

        Picasso.with(mainActivity).load(R.drawable.test).into(ivMainProfilePic);

        profilePicsURLs = arguments.getStringArrayList(PROFILE_PIC_URLS);
        if(profilePicsURLs == null){
            Toast.makeText(mainActivity,"Something went wrong!",Toast.LENGTH_SHORT).show();

            return;
        }

        for(int i = 0; i < profilePicsURLs.size(); i++) {
            switch (i+1){
                case 1:
                    Picasso.with(mainActivity).load(R.drawable.test5).into(ivProfilePic1);
                    break;
                case 2:
                    Picasso.with(mainActivity).load(R.drawable.test2).into(ivProfilePic2);
                    break;
                case 3:
                    Picasso.with(mainActivity).load(R.drawable.test3).into(ivProfilePic3);
                    break;
                case 4:
                    Picasso.with(mainActivity).load(R.drawable.test7).into(ivProfilePic4);
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mainActivity.showBottomNavigation();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @OnClick(R.id.btnUploadImages)
    public void OnClickUploadImages(){
        Toast.makeText(mainActivity, "Upload Images", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.ivMainProfilePic)
    public void OnClickChooseMainImage(){
        mainActivity.setCurrentProfileImageView(ConstantRegistry.MAIN_PROFILE_IMAGE_VIEW);
        mainActivity.showProfilePicChoice();
    }

    @OnLongClick(R.id.ivMainProfilePic)
    public boolean OnLongClickChooseMainImage(){
        Picasso.
                with(mainActivity).
                load(R.drawable.user_512).
                into(ivMainProfilePic);

        return true;
    }

    @OnClick(R.id.ivProfilePic1)
    public void OnClickChooseProfileImage1(){
        mainActivity.setCurrentProfileImageView(ConstantRegistry.FIRST_PROFILE_IMAGE_VIEW);
        mainActivity.showProfilePicChoice();
    }

    @OnLongClick(R.id.ivProfilePic1)
    public boolean OnLongClickChooseProfileImage1(){
        Picasso.
                with(mainActivity).
                load(R.drawable.user_512).
                into(ivProfilePic1);

        return true;
    }

    @OnClick(R.id.ivProfilePic2)
    public void OnClickChooseProfileImage2(){
        mainActivity.setCurrentProfileImageView(ConstantRegistry.SECOND_PROFILE_IMAGE_VIEW);
        mainActivity.showProfilePicChoice();
    }

    @OnLongClick(R.id.ivProfilePic2)
    public boolean OnLongClickChooseProfileImage2(){
        Picasso.
                with(mainActivity).
                load(R.drawable.user_512).
                into(ivProfilePic2);

        return true;
    }

    @OnClick(R.id.ivProfilePic3)
    public void OnClickChooseProfileImage3(){
        mainActivity.setCurrentProfileImageView(ConstantRegistry.THIRD_PROFILE_IMAGE_VIEW);
        mainActivity.showProfilePicChoice();
    }

    @OnLongClick(R.id.ivProfilePic3)
    public boolean OnLongClickChooseProfileImage3(){
        Picasso.
                with(mainActivity).
                load(R.drawable.user_512).
                into(ivProfilePic3);

        return true;
    }

    @OnClick(R.id.ivProfilePic4)
    public void OnClickChooseProfileImage4(){
        mainActivity.setCurrentProfileImageView(ConstantRegistry.FOURTH_PROFILE_IMAGE_VIEW);
        mainActivity.showProfilePicChoice();
    }

    @OnLongClick(R.id.ivProfilePic4)
    public boolean OnLongClickChooseProfileImage4(){
        Picasso.
                with(mainActivity).
                load(R.drawable.user_512).
                into(ivProfilePic4);

        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MainProfilePicSettingsEvent event) {
        Picasso.
                with(mainActivity).
                load(event.getMainProfilePicURI()).
                error(R.drawable.user_512).
                into(ivMainProfilePic);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ProfilePic1SettingsEvent event) {
        Picasso.
                with(mainActivity).
                load(event.getProfilePicURI()).
                error(R.drawable.user_512).
                into(ivProfilePic1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ProfilePic2SettingsEvent event) {
        Picasso.
                with(mainActivity).
                load(event.getProfilePicURI()).
                error(R.drawable.user_512).
                into(ivProfilePic2);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ProfilePic3SettingsEvent event) {
        Picasso.
                with(mainActivity).
                load(event.getProfilePicURI()).
                error(R.drawable.user_512).
                into(ivProfilePic3);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ProfilePic4SettingsEvent event) {
        Picasso.
                with(mainActivity).
                load(event.getProfilePicURI()).
                error(R.drawable.user_512).
                into(ivProfilePic4);
    }
}