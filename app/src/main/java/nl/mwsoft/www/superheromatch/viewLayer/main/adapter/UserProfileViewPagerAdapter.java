package nl.mwsoft.www.superheromatch.viewLayer.main.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.ProfileDetailsFragment;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.UserPicturesFragment;

public class UserProfileViewPagerAdapter extends FragmentPagerAdapter {

    private User user;

    public UserProfileViewPagerAdapter(FragmentManager fm, User user) {
        super(fm);
        this.user = user;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = ProfileDetailsFragment.newInstance(this.user);
        }
        else if (position == 1) {
            fragment = UserPicturesFragment.newInstance(this.user);
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
        }
        else if (position == 1) {
            // set title here if needed
        }
        return title;
    }
}