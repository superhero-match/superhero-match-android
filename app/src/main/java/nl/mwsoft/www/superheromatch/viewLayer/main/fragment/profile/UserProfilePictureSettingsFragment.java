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
package nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.event.MainProfilePicSettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.ProfilePic1SettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.ProfilePic2SettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.ProfilePic3SettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.ProfilePic4SettingsEvent;
import nl.mwsoft.www.superheromatch.modelLayer.helper.recyclerView.RecyclerTouchListener;
import nl.mwsoft.www.superheromatch.modelLayer.helper.recyclerView.RecyclerViewClickListener;
import nl.mwsoft.www.superheromatch.modelLayer.model.ProfilePicture;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.UserProfilePicturesAdapter;

public class UserProfilePictureSettingsFragment extends Fragment {

    @BindView(R.id.rvProfilePictures)
    RecyclerView rvProfilePictures;
    private UserProfilePicturesAdapter userProfilePicturesAdapter;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private ArrayList<ProfilePicture> profilePictures;
    private static final String PROFILE_PICTURES = "profile_pictures";

    public static UserProfilePictureSettingsFragment newInstance(ArrayList<ProfilePicture> profilePictures) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(PROFILE_PICTURES, profilePictures);
        UserProfilePictureSettingsFragment fragment = new UserProfilePictureSettingsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_pictures_settings, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity.hideBottomNavigation();

        Bundle arguments = getArguments();
        if(arguments == null){
            Toast.makeText(mainActivity,R.string.smth_went_wrong, Toast.LENGTH_SHORT).show();

            return;
        }

        profilePictures = arguments.getParcelableArrayList(PROFILE_PICTURES);
        if(profilePictures == null){
            Toast.makeText(mainActivity, R.string.smth_went_wrong,Toast.LENGTH_SHORT).show();

            return;
        }

        userProfilePicturesAdapter = new UserProfilePicturesAdapter(profilePictures, mainActivity);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        rvProfilePictures.setLayoutManager(mLayoutManager);
        rvProfilePictures.setItemAnimator(new DefaultItemAnimator());
        rvProfilePictures.setAdapter(userProfilePicturesAdapter);
        rvProfilePictures.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(),
                rvProfilePictures, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mainActivity.showBottomNavigation();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MainProfilePicSettingsEvent event) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ProfilePic1SettingsEvent event) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ProfilePic2SettingsEvent event) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ProfilePic3SettingsEvent event) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ProfilePic4SettingsEvent event) {

    }
}