package nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.Superhero;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.ProfileViewPagerAdapter;

public class SuggestionProfileFragment extends Fragment {
    @BindView(R.id.vpSuggestionProfile)
    ViewPager vpSuggestionProfile;
    private ProfileViewPagerAdapter profileViewPagerAdapter;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private Superhero superhero;
    private static final String SUPERHERO = "superhero";

    public static SuggestionProfileFragment newInstance(Superhero superhero) {
        Bundle args = new Bundle();
        args.putParcelable(SUPERHERO, superhero);
        SuggestionProfileFragment fragment = new SuggestionProfileFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments == null) {
            Toast.makeText(mainActivity, R.string.smth_went_wrong, Toast.LENGTH_SHORT).show();

            return;
        }

        superhero = arguments.getParcelable(SUPERHERO);
        if (superhero == null) {
            Toast.makeText(mainActivity, R.string.smth_went_wrong, Toast.LENGTH_SHORT).show();

            return;
        }

        configureViewPager(superhero);
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

    private void configureViewPager(Superhero superhero) {
        profileViewPagerAdapter = new ProfileViewPagerAdapter(
                getChildFragmentManager(),
                superhero
        );

        vpSuggestionProfile.setOffscreenPageLimit(1);
        vpSuggestionProfile.setAdapter(profileViewPagerAdapter);
    }
}
