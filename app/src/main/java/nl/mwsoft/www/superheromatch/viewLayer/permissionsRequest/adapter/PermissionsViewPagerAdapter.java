package nl.mwsoft.www.superheromatch.viewLayer.permissionsRequest.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import nl.mwsoft.www.superheromatch.viewLayer.permissionsRequest.fragment.AccessFilesFragment;

public class PermissionsViewPagerAdapter extends FragmentPagerAdapter {

    public PermissionsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
         if (position == 0) {
            fragment = new AccessFilesFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            //title = ;
        }

        return title;
    }
}

