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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class UserProfileImageFragment extends Fragment {

    @BindView(R.id.ivUserProfilePic)
    ImageView ivUserProfilePic;
    @BindView(R.id.tvUserNameAge)
    TextView tvUserNameAge;
    @BindView(R.id.tvUserCity)
    TextView tvUserCity;
    @BindView(R.id.tvUserSuperPowerDesc)
    TextView tvUserSuperPowerDesc;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private static final String PIC_URL = "picUrl";
    private static final String PIC_POSITION = "picPosition";
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String CITY = "city";
    private static final String SUPERPOWER = "superpower";
    private String picUrl;
    private int picPosition;
    private String name;
    private int age;
    private String city;
    private String superpower;

    public static UserProfileImageFragment newInstance(
            String picUrl,
            int picPosition,
            String name,
            int age,
            String city,
            String superpower) {
        Bundle args = new Bundle();
        args.putString(PIC_URL, picUrl);
        args.putInt(PIC_POSITION, picPosition);
        args.putString(NAME, name);
        args.putInt(AGE, age);
        args.putString(CITY, city);
        args.putString(SUPERPOWER, superpower);
        UserProfileImageFragment fragment = new UserProfileImageFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile_image, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }

        picUrl = arguments.getString(PIC_URL);
        picPosition = arguments.getInt(PIC_POSITION);
        name = arguments.getString(NAME);
        age = arguments.getInt(AGE);
        city = arguments.getString(CITY);
        superpower = arguments.getString(SUPERPOWER);

        ivUserProfilePic.setClipToOutline(true);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.user_512)
                .error(R.drawable.user_512);

        Glide.with(mainActivity).
                load(picUrl).
                apply(options).
                into(ivUserProfilePic);

        tvUserNameAge.bringToFront();
        tvUserNameAge.setTypeface(tvUserNameAge.getTypeface(), Typeface.BOLD);
        tvUserNameAge.setText(
                getString(
                        R.string.name_age,
                        name,
                        age
                )
        );

        tvUserCity.setTypeface(tvUserCity.getTypeface(), Typeface.BOLD);
        tvUserCity.setText(city);

        tvUserSuperPowerDesc.setText(superpower);
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

    @OnClick(R.id.ivUserProfilePic)
    public void onUserProfileProfilePictureClickListener() {
        Toast.makeText(mainActivity, "On Tap Pic Position " + picPosition, Toast.LENGTH_LONG).show();
    }

    @OnLongClick(R.id.ivUserProfilePic)
    public boolean onUserProfileProfilePictureLongClickListener() {
        Toast.makeText(mainActivity, "On Tap And Hold Pic Position " + picPosition, Toast.LENGTH_LONG).show();

        return true;
    }
}
