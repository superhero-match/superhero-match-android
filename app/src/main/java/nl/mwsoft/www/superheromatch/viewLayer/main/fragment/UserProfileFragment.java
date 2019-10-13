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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class UserProfileFragment extends Fragment {

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
    @BindView(R.id.tvUserProfileDetailsSuperPowerDesc)
    TextView tvUserProfileDetailsSuperPowerDesc;
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
                loadUserProfileDetails(user);
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

    private void loadUserProfileDetails(User user) {
        Glide.with(mainActivity)
                .load(R.drawable.test)
                .into(ivUserProfilePic);
        configureUserName(user);
        configureUserAgeAndGender(user);
        configureUserSuperPower(user);
    }

    private void configureUserSuperPower(User user) {
        tvUserProfileDetailsSuperPowerDesc.setText(user.getSuperPower());
    }

    private void configureUserAgeAndGender(User user) {
        tvUserAgeGender.setText(String.valueOf(user.getAge()).concat(", ").concat(user.getGender() == 1 ? "Male" : "Female"));
    }

    private void configureUserName(User user) {
        tvUserName.setTypeface(tvUserName.getTypeface(), Typeface.BOLD);
        tvUserName.setText(user.getName());
    }

    @OnClick(R.id.ivUserProfileSettings)
    public void onUserProfileSettingsClickListener() {
        mainActivity.loadSuggestionUserProfileSettingsFragment(mainActivity.createMockUser());
    }

    @OnClick(R.id.ivUserProfileEdit)
    public void onUserProfileEditClickListener() {
        mainActivity.loadSuggestionUserProfileEditFragment();
    }

    @OnClick(R.id.ivUserProfilePic)
    public void onUserProfilePicClickListener() {
        mainActivity.loadBackStackFragment(ImageDetailFragment.newInstance(user.getMainProfilePicUrl()));
    }
}