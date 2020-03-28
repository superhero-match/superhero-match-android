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
package nl.mwsoft.www.superheromatch.viewLayer.main.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;

import nl.mwsoft.www.superheromatch.modelLayer.helper.compare.ProfilePicturePositionComparator;
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
            Collections.sort(this.suggestion.getProfilePictures(), new ProfilePicturePositionComparator());

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