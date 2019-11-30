package nl.mwsoft.www.superheromatch.viewLayer.main.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.UserProfileImageFragment;

public class UserProfileViewPagerAdapter extends FragmentPagerAdapter {

    private User user;

    public UserProfileViewPagerAdapter(FragmentManager fm, User user) {
        super(fm);
        this.user = user;
    }

    @Override
    public Fragment getItem(int position) {
        String profilePic = "";

        if (position == 0 || (this.user.getProfilePicsUrls() == null)){
            profilePic = this.user.getMainProfilePicUrl();
        }

        if (this.user.getProfilePicsUrls() != null) {
            profilePic = this.user.getProfilePicsUrls().get(position);
        }

        return UserProfileImageFragment.newInstance(
                profilePic,
                this.user.getSuperHeroName(),
                this.user.getAge(),
                this.user.getCity(),
                this.user.getSuperPower()
        );
    }

    @Override
    public int getCount() {
        if (this.user.getProfilePicsUrls() != null) {
            return this.user.getProfilePicsUrls().size();
        }

        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}