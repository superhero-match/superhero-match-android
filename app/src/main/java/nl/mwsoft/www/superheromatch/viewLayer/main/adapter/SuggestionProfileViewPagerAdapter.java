package nl.mwsoft.www.superheromatch.viewLayer.main.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import nl.mwsoft.www.superheromatch.modelLayer.model.Superhero;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.suggestions.SuggestionProfileImageFragment;

public class SuggestionProfileViewPagerAdapter extends FragmentPagerAdapter {

    private Superhero suggestion;

    public SuggestionProfileViewPagerAdapter(FragmentManager fm, Superhero suggestion) {
        super(fm);
        this.suggestion = suggestion;
    }

    @Override
    public Fragment getItem(int position) {
        String profilePic = "";

        if (position == 0 || (this.suggestion.getProfilePicsUrls() == null)){
            profilePic = this.suggestion.getMainProfilePicUrl();
        }

        if (this.suggestion.getProfilePicsUrls() != null) {
            profilePic = this.suggestion.getProfilePicsUrls().get(position);
        }

        return SuggestionProfileImageFragment.newInstance(
                profilePic,
                this.suggestion.getSuperheroName(),
                this.suggestion.getAge(),
                this.suggestion.getCity(),
                this.suggestion.getSuperpower()
        );
    }

    @Override
    public int getCount() {
        if (this.suggestion.getProfilePicsUrls() != null) {
            return this.suggestion.getProfilePicsUrls().size();
        }

        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}