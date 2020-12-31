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
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class ProfileDetailsFragment extends Fragment {

    @BindView(R.id.tvUserProfileDetailsSuperPower)
    TextView tvUserProfileDetailsAbout;
    @BindView(R.id.tvUserProfileDetailsSuperPowerDesc)
    TextView tvUserProfileDetailsAboutDesc;

    private Unbinder unbinder;
    private MainActivity mainActivity;
    private User user;
    private final static String USER = "user";

    public static ProfileDetailsFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(USER, user);
        ProfileDetailsFragment fragment = new ProfileDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments != null){
            user = arguments.getParcelable(USER);
            loadUserProfileDetails(user);
        }else{
            Toast.makeText(mainActivity, "Something went wrong!", Toast.LENGTH_LONG).show();
        }
    }

    private void loadUserProfileDetails(User user) {
        tvUserProfileDetailsAbout.setTypeface(tvUserProfileDetailsAbout.getTypeface(), Typeface.BOLD);
        tvUserProfileDetailsAboutDesc.setText(user.getSuperPower());
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