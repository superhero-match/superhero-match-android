package nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile;

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
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.Superhero;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.SuggestionProfileViewPagerAdapter;
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.UserProfileViewPagerAdapter;

public class UserProfileFragment extends Fragment {

    @BindView(R.id.vpUserProfile)
    ViewPager vpUserProfile;
    private UserProfileViewPagerAdapter userProfileViewPagerAdapter;
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
        if (arguments != null) {
            user = arguments.getParcelable(USER);
            if (user == null) {
                user = new User();
                Toast.makeText(mainActivity, "Something went wrong!", Toast.LENGTH_SHORT).show();
            } else {
                configureViewPager(user);
            }
        } else {
            user = new User();
            Toast.makeText(mainActivity, "Something went wrong!", Toast.LENGTH_SHORT).show();
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

    private void configureViewPager(User user) {
        userProfileViewPagerAdapter = new UserProfileViewPagerAdapter(
                getChildFragmentManager(),
                user
        );

        vpUserProfile.setOffscreenPageLimit(1);
        vpUserProfile.setAdapter(userProfileViewPagerAdapter);
    }

    @OnClick(R.id.ivUserProfileSettings)
    public void onUserProfileSettingsClickListener() {
        mainActivity.loadSuggestionUserProfileSettingsFragment();
    }

    @OnClick(R.id.ivUserProfileEdit)
    public void onUserProfileEditClickListener() {
        mainActivity.loadSuggestionUserProfileEditFragment();
    }

    @OnClick(R.id.ivUserProfileImageGallery)
    public void onUserProfilePicClickListener() {
        mainActivity.loadImageDetailFragment(mainActivity.createMockUser());
    }
}