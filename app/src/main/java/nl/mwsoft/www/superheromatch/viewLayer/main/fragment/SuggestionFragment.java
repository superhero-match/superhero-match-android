package nl.mwsoft.www.superheromatch.viewLayer.main.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.Superhero;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.SuggestionProfileViewPagerAdapter;

public class SuggestionFragment extends Fragment {

    @BindView(R.id.vpSuggestionUserProfile)
    ViewPager vpSuggestionUserProfile;
    private SuggestionProfileViewPagerAdapter suggestionUserProfileViewPagerAdapter;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private Superhero superhero;
    private static final String SUPERHERO = "superhero";

    public static SuggestionFragment newInstance(Superhero superhero) {
        Bundle args = new Bundle();
        args.putParcelable(SUPERHERO, superhero);
        SuggestionFragment fragment = new SuggestionFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            superhero = arguments.getParcelable(SUPERHERO);

            if (superhero == null) {
                superhero = new Superhero();
                Toast.makeText(mainActivity, "Something went wrong!", Toast.LENGTH_SHORT).show();
            } else {
                configureViewPager(superhero);
            }
        } else {
            superhero = new Superhero();
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

    private void configureViewPager(Superhero suggestion) {
        suggestionUserProfileViewPagerAdapter = new SuggestionProfileViewPagerAdapter(
                getChildFragmentManager(),
                suggestion
        );

        vpSuggestionUserProfile.setOffscreenPageLimit(1);
        vpSuggestionUserProfile.setAdapter(suggestionUserProfileViewPagerAdapter);
    }
}