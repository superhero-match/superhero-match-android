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
package nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class UserProfileSettingsNotificationsFragment extends Fragment {

    @BindView(R.id.tvUserProfileSettingsNotificationsMessagesDesc)
    TextView tvUserProfileSettingsNotificationsMessagesDesc;
    @BindView(R.id.swUserProfileSettingsNotificationsMessages)
    Switch swUserProfileSettingsNotificationsMessages;

    @BindView(R.id.tvUserProfileSettingsNotificationsMatchesDesc)
    TextView tvUserProfileSettingsNotificationsMatchesDesc;
    @BindView(R.id.swUserProfileSettingsNotificationsMatches)
    Switch swUserProfileSettingsNotificationsMatches;

    @BindView(R.id.tvUserProfileSettingsNotificationsLikesDesc)
    TextView tvUserProfileSettingsNotificationsLikesDesc;
    @BindView(R.id.swUserProfileSettingsNotificationsLikes)
    Switch swUserProfileSettingsNotificationsLikes;

    private Unbinder unbinder;
    private MainActivity mainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile_settings_notifications, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvUserProfileSettingsNotificationsMessagesDesc.setTypeface(tvUserProfileSettingsNotificationsMessagesDesc.getTypeface(), Typeface.BOLD);
        tvUserProfileSettingsNotificationsMatchesDesc.setTypeface(tvUserProfileSettingsNotificationsMatchesDesc.getTypeface(), Typeface.BOLD);
        tvUserProfileSettingsNotificationsLikesDesc.setTypeface(tvUserProfileSettingsNotificationsLikesDesc.getTypeface(), Typeface.BOLD);

        swUserProfileSettingsNotificationsMessages.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(mainActivity,"Updated to Show message notifications",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(mainActivity,"Updated to Don't Show message notifications",Toast.LENGTH_LONG).show();
                }
            }
        });

        swUserProfileSettingsNotificationsMatches.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(mainActivity,"Updated to Show matches notifications",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(mainActivity,"Updated to Don't Show matches notifications",Toast.LENGTH_LONG).show();
                }
            }
        });

        swUserProfileSettingsNotificationsLikes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(mainActivity,"Updated to Show likes notifications",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(mainActivity,"Updated to Don't Show likes notifications",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

}