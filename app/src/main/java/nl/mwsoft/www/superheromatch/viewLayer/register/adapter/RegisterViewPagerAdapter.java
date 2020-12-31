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
package nl.mwsoft.www.superheromatch.viewLayer.register.adapter;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import nl.mwsoft.www.superheromatch.viewLayer.register.fragment.SuperheroBirthday;
import nl.mwsoft.www.superheromatch.viewLayer.register.fragment.SuperheroDistanceUnit;
import nl.mwsoft.www.superheromatch.viewLayer.register.fragment.SuperheroGender;
import nl.mwsoft.www.superheromatch.viewLayer.register.fragment.SuperheroLookingForGender;
import nl.mwsoft.www.superheromatch.viewLayer.register.fragment.SuperheroName;
import nl.mwsoft.www.superheromatch.viewLayer.register.fragment.SuperheroProfilePic;
import nl.mwsoft.www.superheromatch.viewLayer.register.fragment.SuperheroSuperPower;

public class RegisterViewPagerAdapter extends FragmentPagerAdapter {

    public RegisterViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new SuperheroName();
        } else if (position == 1) {
            fragment = new SuperheroBirthday();
        } else if (position == 2) {
            fragment = new SuperheroDistanceUnit();
        } else if (position == 3) {
            fragment = new SuperheroGender();
        } else if (position == 4) {
            fragment = new SuperheroLookingForGender();
        } else if (position == 5) {
            fragment = new SuperheroSuperPower();
        } else if (position == 6) {
            fragment = new SuperheroProfilePic();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            //title = ;
        } else if (position == 1) {
            //title = ;
        } else if (position == 2) {
            //title = ;
        } else if (position == 3) {
            //title = ;
        } else if (position == 4) {
            //title = ;
        } else if (position == 5) {
            //title = ;
        }else if (position == 6) {
            //title = ;
        }

        return title;
    }
}

