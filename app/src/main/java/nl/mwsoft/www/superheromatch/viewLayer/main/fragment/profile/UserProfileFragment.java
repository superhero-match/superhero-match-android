/*
  Copyright (C) 2019 - 2021 MWSOFT
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
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.UserProfileViewPagerAdapter;

public class UserProfileFragment extends Fragment {
    @BindView(R.id.vpUserProfile)
    ViewPager vpUserProfile;
    @BindView(R.id.ivUserProfileSettings)
    ImageView ivUserProfileSettings;
    @BindView(R.id.ivUserProfileAddNewPic)
    ImageView ivUserProfileAddNewPic;
    @BindView(R.id.ivUserProfileEdit)
    ImageView ivUserProfileEdit;
    @BindView(R.id.ivUserProfileInformation)
    ImageView ivUserProfileInformation;
    private UserProfileViewPagerAdapter userProfileViewPagerAdapter;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private Superhero superhero;
    private static final String SUPERHERO = "superhero";

    public static UserProfileFragment newInstance(Superhero superhero) {
        Bundle args = new Bundle();
        args.putParcelable(SUPERHERO, superhero);
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
        userProfileViewPagerAdapter = new UserProfileViewPagerAdapter(
                getChildFragmentManager(),
                superhero
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

    @OnClick(R.id.ivUserProfileInformation)
    public void onUserProfileInformationClickListener() {
        mainActivity.showPopupProfilePictureInformation();
    }

    @OnClick(R.id.ivUserProfileAddNewPic)
    public void onUserProfileAddNewProfilePictureClickListener() {
        mainActivity.setProfile(superhero);
        mainActivity.setAddingNewProfilePicture(true);
        mainActivity.showProfilePicChoice();
    }
}
