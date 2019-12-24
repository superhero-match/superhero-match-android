package nl.mwsoft.www.superheromatch.viewLayer.main.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import nl.mwsoft.www.superheromatch.modelLayer.model.ProfilePicture;
import nl.mwsoft.www.superheromatch.modelLayer.model.Superhero;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.suggestions.SuggestionProfileImageFragment;

public class SuggestionProfileViewPagerAdapter extends FragmentPagerAdapter {

    private Superhero suggestion;
    private  ArrayList<String> profilePictures;

    public SuggestionProfileViewPagerAdapter(FragmentManager fm, Superhero suggestion) {
        super(fm);
        this.suggestion = suggestion;
        this.profilePictures = new ArrayList<>();
        this.profilePictures.add(this.suggestion.getMainProfilePicUrl());
        if (this.suggestion.getProfilePictures() != null) {
            for(ProfilePicture profilePicture : this.suggestion.getProfilePictures()){
                this.profilePictures.add(profilePicture.getProfilePicUrl());
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        String profilePic = this.profilePictures.get(position);

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
        return this.profilePictures.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}