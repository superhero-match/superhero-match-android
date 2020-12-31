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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.UserProfileSettingsViewPagerAdapter;

public class UserProfileSettingsFragment extends Fragment {

    @BindView(R.id.vpUserProfileSettings)
    ViewPager vpUserProfileSettings;
    private UserProfileSettingsViewPagerAdapter userProfileSettingsViewPagerAdapter;
    @BindView(R.id.tlUserProfileSettings)
    TabLayout tlUserProfileSettings;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private User user;
    private static final String USER = "user";

    public static UserProfileSettingsFragment newInstance(User user) {

        Bundle args = new Bundle();
        args.putParcelable(USER, user);
        UserProfileSettingsFragment fragment = new UserProfileSettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile_settings, container, false);
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

    private void configureViewPager() {
        // set up ViewPager
        vpUserProfileSettings.addOnPageChangeListener(myOnPageChangeListener);
        userProfileSettingsViewPagerAdapter = new UserProfileSettingsViewPagerAdapter(getChildFragmentManager());
        vpUserProfileSettings.setOffscreenPageLimit(1);
        vpUserProfileSettings.setAdapter(userProfileSettingsViewPagerAdapter);
        tlUserProfileSettings.setupWithViewPager(vpUserProfileSettings);
    }

    private void configureTabIcons(){
        tlUserProfileSettings.getTabAt(0).setIcon(R.drawable.settings_suggestions_128);
        tlUserProfileSettings.getTabAt(1).setIcon(R.drawable.settings_notifications_128);
    }

    public ViewPager.OnPageChangeListener myOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void attachUI() {
        configureViewPager();
        configureTabIcons();
    }


}