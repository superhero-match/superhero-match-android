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
package nl.mwsoft.www.superheromatch.viewLayer.intro.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.viewLayer.intro.fragment.IntroFeaturesOneFragment;
import nl.mwsoft.www.superheromatch.viewLayer.intro.fragment.IntroFeaturesTwoFragment;
import nl.mwsoft.www.superheromatch.viewLayer.intro.fragment.IntroOverviewFragment;

public class IntroViewPagerAdapter extends FragmentPagerAdapter {

    public IntroViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new IntroOverviewFragment();
                break;
            case 1:
                fragment = new IntroFeaturesOneFragment();
                break;
            case 2:
                fragment = new IntroFeaturesTwoFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return ConstantRegistry.INTRO_FRAGMENTS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                //title = ;
                break;
            case 1:
                //title = ;
                break;
            case 2:
                //title = ;
                break;
        }

        return title;
    }
}
