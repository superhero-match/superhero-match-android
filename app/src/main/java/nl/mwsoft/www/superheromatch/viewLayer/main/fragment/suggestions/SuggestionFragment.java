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
package nl.mwsoft.www.superheromatch.viewLayer.main.fragment.suggestions;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.Superhero;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.SuggestionProfileViewPagerAdapter;

public class SuggestionFragment extends Fragment {

    @BindView(R.id.vpSuggestionUserProfile)
    ViewPager vpSuggestionUserProfile;
    @BindView(R.id.ivSuggestionDislike)
    ImageView ivSuggestionDislike;
    @BindView(R.id.ivSuggestionLike)
    ImageView ivSuggestionLike;
    @BindView(R.id.ivSuperPowerIconSuggestion)
    ImageView ivSuperPowerIconSuggestion;
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

    @OnClick(R.id.ivSuggestionLike)
    public void onSuggestionLike(){
        mainActivity.onSuggestionLike();
    }

    @OnClick(R.id.ivSuggestionDislike)
    public void onSuggestionDislike(){
        mainActivity.onSuggestionDislike();
    }

    @OnClick(R.id.ivSuperPowerIconSuggestion)
    public void onSuperPowerIconSuggestion(){
        mainActivity.onSuggestionSuperpowerIcon();
    }
}