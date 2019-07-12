package nl.mwsoft.www.superheromatch.viewLayer.main.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.SuggestionUserProfileViewPagerAdapter;

public class SuggestionUserProfileFragment extends Fragment {

    @BindView(R.id.vpSuggestionUserProfile)
    ViewPager vpSuggestionUserProfile;
    private SuggestionUserProfileViewPagerAdapter suggestionUserProfileViewPagerAdapter;
    @BindView(R.id.llDotsSuggestionUserProfile)
    LinearLayout llDotsSuggestionUserProfile;
    @BindView(R.id.tvSuggestionUserProfileDetailsSuperPowerDesc)
    TextView tvSuggestionUserProfileDetailsSuperPowerDesc;
    private ImageView[] dots;
    private Unbinder unbinder;
    private int currentPosition = 0;
    private User user;
    private MainActivity mainActivity;
    private static final String USER = "user";


    public static SuggestionUserProfileFragment newInstance(User user) {

        Bundle args = new Bundle();
        args.putParcelable(USER, user);
        SuggestionUserProfileFragment fragment = new SuggestionUserProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion_user_profile, container, false);
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
                attachUI();
                tvSuggestionUserProfileDetailsSuperPowerDesc.setText(user.getSuperPower());
            }
        }else{
            user = new User();
            Toast.makeText(mainActivity,"Something went wrong!",Toast.LENGTH_SHORT).show();
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

    private void attachUI() {
        configureViewPager();
        showDots(0);
        vpSuggestionUserProfile.addOnPageChangeListener(myOnPageChangeListener);
    }

    private void configureViewPager() {
        suggestionUserProfileViewPagerAdapter = new SuggestionUserProfileViewPagerAdapter(getChildFragmentManager(), user.getProfilePicsUrls());
        vpSuggestionUserProfile.setOffscreenPageLimit(user.getProfilePicsUrls().size());
        vpSuggestionUserProfile.setAdapter(suggestionUserProfileViewPagerAdapter);
    }

    private void showDots(int currentPosition){
        resetDots();
        showCurrentDots(currentPosition);
    }

    private void showCurrentDots(int currentPosition) {
        dots = new ImageView[user.getProfilePicsUrls().size()];

        for(int i = 0; i < user.getProfilePicsUrls().size(); i++){
            dots[i] = new ImageView(mainActivity);
            if(i == currentPosition){
                dots[i].setImageDrawable(ContextCompat.getDrawable(mainActivity, R.drawable.active_dots));
            }else{
                dots[i].setImageDrawable(ContextCompat.getDrawable(mainActivity,R.drawable.inactive_dots));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);

            llDotsSuggestionUserProfile.addView(dots[i],params);
        }
    }

    private void resetDots() {
        if(llDotsSuggestionUserProfile != null){
            llDotsSuggestionUserProfile.removeAllViews();
        }
    }

    private void runOnPageSelectedRoutine(int position) {
        currentPosition = position;
        showDots(position);
    }

    public ViewPager.OnPageChangeListener myOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            runOnPageSelectedRoutine(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

}
