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
package nl.mwsoft.www.superheromatch.viewLayer.main.fragment.suggestions;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class SuggestionProfileImageFragment extends Fragment {

    @BindView(R.id.ivSuggestionProfilePic)
    ImageView ivSuggestionProfilePic;
    @BindView(R.id.tvSuggestionNameAge)
    TextView tvSuggestionNameAge;
    @BindView(R.id.tvSuggestionCity)
    TextView tvSuggestionCity;
    @BindView(R.id.tvSuggestionSuperPowerDesc)
    TextView tvSuggestionSuperPowerDesc;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private static final String PIC_URL = "picUrl";
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String CITY = "city";
    private static final String SUPERPOWER = "superpower";
    private String picUrl;
    private String name;
    private int age;
    private String city;
    private String superpower;

    public static SuggestionProfileImageFragment newInstance(String picUrl, String name, int age, String city, String superpower) {
        Bundle args = new Bundle();
        args.putString(PIC_URL, picUrl);
        args.putString(NAME, name);
        args.putInt(AGE, age);
        args.putString(CITY, city);
        args.putString(SUPERPOWER, superpower);
        SuggestionProfileImageFragment fragment = new SuggestionProfileImageFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion_profile_image, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments == null) {
            Toast.makeText(mainActivity, R.string.smth_went_wrong, Toast.LENGTH_LONG).show();

            return;
        }

        picUrl = arguments.getString(PIC_URL);
        name = arguments.getString(NAME);
        age = arguments.getInt(AGE);
        city = arguments.getString(CITY);
        superpower = arguments.getString(SUPERPOWER);


        ivSuggestionProfilePic.setClipToOutline(true);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.user_512)
                .error(R.drawable.user_512);

        Glide.with(mainActivity).
                load(picUrl).
                apply(options).
                into(ivSuggestionProfilePic);

        tvSuggestionNameAge.bringToFront();
        tvSuggestionNameAge.setTypeface(tvSuggestionNameAge.getTypeface(), Typeface.BOLD);
        tvSuggestionNameAge.setText(
                getString(
                        R.string.name_age,
                        name,
                        age
                )
        );

        tvSuggestionCity.setTypeface(tvSuggestionCity.getTypeface(), Typeface.BOLD);
        tvSuggestionCity.setText(city);

        tvSuggestionSuperPowerDesc.setText(superpower);
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