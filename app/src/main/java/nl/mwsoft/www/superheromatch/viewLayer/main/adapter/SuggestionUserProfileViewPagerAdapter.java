package nl.mwsoft.www.superheromatch.viewLayer.main.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.SuggestionUserProfileImageFragment;

public class SuggestionUserProfileViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> profilePics = new ArrayList<>();

    public SuggestionUserProfileViewPagerAdapter(FragmentManager fm, ArrayList<String> profilePics) {
        super(fm);
        this.profilePics = profilePics;
    }

    @Override
    public Fragment getItem(int position) {
        return SuggestionUserProfileImageFragment.newInstance(this.profilePics.get(position));
    }

    @Override
    public int getCount() {
        return this.profilePics.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        return title;
    }
}