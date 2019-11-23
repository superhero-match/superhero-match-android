package nl.mwsoft.www.superheromatch.viewLayer.main.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.UserProfileSettingsNotificationsFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.UserProfileSettingsSuggestionsFragment;

public class UserProfileSettingsViewPagerAdapter extends FragmentPagerAdapter {

    public UserProfileSettingsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new UserProfileSettingsSuggestionsFragment();
        } else if (position == 1) {
            fragment = new UserProfileSettingsNotificationsFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            // set title here if needed
        } else if (position == 1) {
            // set title here if needed
        }
        return title;
    }
}