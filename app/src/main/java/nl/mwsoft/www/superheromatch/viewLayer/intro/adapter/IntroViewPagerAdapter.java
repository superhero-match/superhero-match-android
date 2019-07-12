package nl.mwsoft.www.superheromatch.viewLayer.intro.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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

        if (position == 0) {
            fragment = new IntroOverviewFragment();
        } else if (position == 1) {
            fragment = new IntroFeaturesOneFragment();
        } else if (position == 2) {
            fragment = new IntroFeaturesTwoFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
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
        }

        return title;
    }
}
