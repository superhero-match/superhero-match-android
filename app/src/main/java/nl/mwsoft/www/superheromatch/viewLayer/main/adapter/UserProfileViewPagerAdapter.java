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

import nl.mwsoft.www.superheromatch.modelLayer.model.Superhero;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile.UserProfileImageFragment;

public class UserProfileViewPagerAdapter extends FragmentPagerAdapter {

    private Superhero superhero;

    public UserProfileViewPagerAdapter(FragmentManager fm, Superhero superhero) {
        super(fm);
        this.superhero = superhero;
    }

    @Override
    public Fragment getItem(int position) {
        String profilePic = "";

        if (position == 0 || (this.superhero.getProfilePictures() == null)){
            profilePic = this.superhero.getMainProfilePicUrl();
        }

        if (this.superhero.getProfilePictures() != null) {
            profilePic = this.superhero.getProfilePictures().get(position).getProfilePicUrl();
        }

        return UserProfileImageFragment.newInstance(
                profilePic,
                this.superhero.getSuperheroName(),
                this.superhero.getAge(),
                this.superhero.getCity(),
                this.superhero.getSuperpower()
        );
    }

    @Override
    public int getCount() {
        if (this.superhero.getProfilePictures() != null) {
            return this.superhero.getProfilePictures().size();
        }

        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}