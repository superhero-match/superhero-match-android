package nl.mwsoft.www.superheromatch.viewLayer.main.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.UserProfilePicturesAdapter;

public class UserProfileFragment extends Fragment {

//    @BindView(R.id.vpUserProfile)
//    ViewPager vpUserProfile;
//    private UserProfileViewPagerAdapter userProfileViewPagerAdapter;
//    @BindView(R.id.tlUserProfile)
//    TabLayout tlUserProfile;
    @BindView(R.id.ivUserProfilePic)
    ImageView ivUserProfilePic;
    @BindView(R.id.ivUserProfileSettings)
    ImageView ivUserProfileSettings;
    @BindView(R.id.ivUserProfileEdit)
    ImageView ivUserProfileEdit;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserAgeGender)
    TextView tvUserAgeGender;
    @BindView(R.id.ivUserProfileDislike)
    ImageView ivUserProfileDislike;
    @BindView(R.id.ivUserProfileLike)
    ImageView ivUserProfileLike;
    @BindView(R.id.tvUserProfileDetailsSuperPower)
    TextView tvUserProfileDetailsSuperPower;
    @BindView(R.id.tvUserProfileDetailsSuperPowerDesc)
    TextView tvUserProfileDetailsSuperPowerDesc;
    @BindView(R.id.rvUserProfilePictures)
    RecyclerView rvUserProfilePictures;
    private UserProfilePicturesAdapter userProfilePicturesAdapter;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private User user;
    private static final String USER = "user";

    public static UserProfileFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(USER, user);
        UserProfileFragment fragment = new UserProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if(arguments != null){
            user = arguments.getParcelable(USER);
            if(user == null){
                user = new User();
                Toast.makeText(mainActivity,"Something went wrong!",Toast.LENGTH_SHORT).show();
            }else {
                loadUserProfileDetails(user);
            }
        }else{
            user = new User();
            Toast.makeText(mainActivity,"Something went wrong!",Toast.LENGTH_SHORT).show();
        }
    }

    private void hideSettings() {
        // hide settings/edit
        if(ivUserProfileEdit.getVisibility() == View.VISIBLE){
            ivUserProfileEdit.setVisibility(View.GONE);
        }

        if(ivUserProfileSettings.getVisibility() == View.VISIBLE){
            ivUserProfileSettings.setVisibility(View.GONE);
        }

        // show like/dislike/etc
        if(ivUserProfileDislike.getVisibility() == View.GONE){
            ivUserProfileDislike.setVisibility(View.VISIBLE);
        }

        if(ivUserProfileLike.getVisibility() == View.GONE){
            ivUserProfileLike.setVisibility(View.VISIBLE);
        }
    }

    private void showSettings() {
        // show settings/edit
        if(ivUserProfileEdit.getVisibility() == View.GONE){
            ivUserProfileEdit.setVisibility(View.VISIBLE);
        }

        if(ivUserProfileSettings.getVisibility() == View.GONE){
            ivUserProfileSettings.setVisibility(View.VISIBLE);
        }

        // hide like/dislike/etc
        if(ivUserProfileDislike.getVisibility() == View.VISIBLE){
            ivUserProfileDislike.setVisibility(View.GONE);
        }

        if(ivUserProfileLike.getVisibility() == View.VISIBLE){
            ivUserProfileLike.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    private void loadUserProfileDetails(User user) {
        Glide
                .with(mainActivity)
                .load(R.drawable.test)
                .apply(RequestOptions.circleCropTransform())
                .into(ivUserProfilePic);
        configureUserName(user);
        configureUserAgeAndGender(user);
        configureUserControls(user);
        configureUserSuperPower(user);
        loadUserProfilePictures(user);
    }

    private void configureUserSuperPower(User user) {
        tvUserProfileDetailsSuperPower.setTypeface(tvUserProfileDetailsSuperPower.getTypeface(), Typeface.BOLD);
        tvUserProfileDetailsSuperPowerDesc.setText(user.getSuperPower());
    }

    private void configureUserAgeAndGender(User user) {
        tvUserAgeGender.setText(String.valueOf(user.getAge()).concat(", ").concat(user.getGender() == 1 ? "Male" : "Female"));
    }

    private void configureUserName(User user) {
        tvUserName.setTypeface(tvUserName.getTypeface(), Typeface.BOLD);
        tvUserName.setText(user.getName());
    }

    private void configureUserControls(User user) {
        if(user.getUserID().equals(mainActivity.getUserId())){
            showSettings();
        }else{
            hideSettings();
        }
    }

    private void loadUserProfilePictures(User user) {
        userProfilePicturesAdapter = new UserProfilePicturesAdapter(user, mainActivity);
        StaggeredGridLayoutManager mLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvUserProfilePictures.setLayoutManager(mLayoutManager);
        rvUserProfilePictures.setItemAnimator(new DefaultItemAnimator());
        rvUserProfilePictures.setAdapter(userProfilePicturesAdapter);
    }

    @OnClick(R.id.ivUserProfileSettings)
    public void onUserProfileSettingsClickListener(){
        mainActivity.loadSuggestionUserProfileSettingsFragment(mainActivity.createMockUser());
    }

    @OnClick(R.id.ivUserProfileEdit)
    public void onUserProfileEditClickListener(){
        mainActivity.loadSuggestionUserProfileEditFragment();
    }

    @OnClick(R.id.ivUserProfilePic)
    public void onUserProfilePicClickListener(){
        mainActivity.loadBackStackFragment(ImageDetailFragment.newInstance(user.getMainProfilePicUrl()));
    }

    @OnClick(R.id.ivUserProfileLike)
    public void onUserProfileLike(){
        // TO-DO: make request to server to process like and to check if there is match,
        // and then remove the user from the list and return to the suggestions list.
        Toast.makeText(mainActivity, "Like", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.ivUserProfileDislike)
    public void onUserProfileDislike(){
        // TO-DO: make request to server to process dislike,
        // and then remove the user from the list and return to the suggestions list.
        Toast.makeText(mainActivity, "Dislike", Toast.LENGTH_SHORT).show();
    }
}