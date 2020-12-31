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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class UserProfileSettingsSuggestionsFragment extends Fragment {

    public static final String TAG = UserProfileSettingsSuggestionsFragment.class.getName();

    @BindView(R.id.tvUserProfileSettingsSuggestionsLookingForDesc)
    TextView tvUserProfileSettingsSuggestionsLookingForDesc;
    @BindView(R.id.tvUserProfileSettingsSuggestionsAgeRangeDesc)
    TextView tvUserProfileSettingsSuggestionsAgeRangeDesc;
    @BindView(R.id.tvUserProfileSettingsSuggestionsDistanceDesc)
    TextView tvUserProfileSettingsSuggestionsDistanceDesc;

    @BindView(R.id.btnMale)
    Button btnMale;
    @BindView(R.id.btnFemale)
    Button btnFemale;
    @BindView(R.id.btnBoth)
    Button btnBoth;
    @BindView(R.id.tvAgeRangeMin)
    TextView tvAgeRangeMin;
    @BindView(R.id.tvAgeRangeMax)
    TextView tvAgeRangeMax;
    @BindView(R.id.rsUserProfileSettingsSuggestionsAgeRange)
    CrystalRangeSeekbar rsUserProfileSettingsSuggestionsAgeRange;
    @BindView(R.id.tvDistance)
    TextView tvDistance;
    @BindView(R.id.sbDistance)
    SeekBar sbDistance;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private boolean maleIsSelected = false;
    private boolean femaleIsSelected = false;
    private boolean bothIsSelected = false;

    public static UserProfileSettingsSuggestionsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        UserProfileSettingsSuggestionsFragment fragment = new UserProfileSettingsSuggestionsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile_settings_suggestions, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch (mainActivity.getUserLookingForGender()) {
            case ConstantRegistry.MALE:
                btnMaleOnClick();
                break;
            case ConstantRegistry.FEMALE:
                btnFemaleOnClick();
                break;
            case ConstantRegistry.BOTH:
                btnBothOnClick();
                break;
            default:
                Log.d(TAG, "mainActivity.getUserLookingForGender() returned incorrect result: " + mainActivity.getUserLookingForGender());
        }

        tvUserProfileSettingsSuggestionsLookingForDesc.setTypeface(tvUserProfileSettingsSuggestionsLookingForDesc.getTypeface(), Typeface.BOLD);
        tvUserProfileSettingsSuggestionsAgeRangeDesc.setTypeface(tvUserProfileSettingsSuggestionsAgeRangeDesc.getTypeface(), Typeface.BOLD);
        tvUserProfileSettingsSuggestionsDistanceDesc.setTypeface(tvUserProfileSettingsSuggestionsDistanceDesc.getTypeface(), Typeface.BOLD);

        rsUserProfileSettingsSuggestionsAgeRange.setMinStartValue(mainActivity.getUserLookingForMinAge());
        rsUserProfileSettingsSuggestionsAgeRange.setMaxStartValue(mainActivity.getUserLookingForMaxAge());
        rsUserProfileSettingsSuggestionsAgeRange.apply();

        tvAgeRangeMin.setText(String.valueOf(mainActivity.getUserLookingForMinAge()));
        tvAgeRangeMax.setText(String.valueOf(mainActivity.getUserLookingForMaxAge()));

        rsUserProfileSettingsSuggestionsAgeRange.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvAgeRangeMin.setText(String.valueOf(minValue));
                tvAgeRangeMax.setText(String.valueOf(maxValue));

                mainActivity.updateLookingForAgeRange(minValue.intValue(), maxValue.intValue());
            }
        });

        rsUserProfileSettingsSuggestionsAgeRange.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                tvAgeRangeMin.setText(String.valueOf(minValue));
                tvAgeRangeMax.setText(String.valueOf(maxValue));

                mainActivity.updateLookingForAgeRange(minValue.intValue(), maxValue.intValue());
            }
        });

        String maxDistance =
                mainActivity.getUserMaxDistance() != 0 ?
                        getString(
                                R.string.selected_distance,
                                mainActivity.getUserMaxDistance(),
                                mainActivity.getUserDistanceUnit()
                        ) :
                        getString(
                                R.string.default_distance,
                                mainActivity.getUserDistanceUnit()
                        );

        tvDistance.setText(maxDistance);

        sbDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 100) {
                    tvDistance.setText(
                            getString(
                                    R.string.selected_distance,
                                    progress,
                                    "+ " + mainActivity.getUserDistanceUnit()
                            )
                    );
                } else {
                    tvDistance.setText(
                            getString(
                                    R.string.selected_distance,
                                    progress,
                                    mainActivity.getUserDistanceUnit()
                            )
                    );
                }

                mainActivity.updateUserLookingForMaxDistance(progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mainActivity.updateUserProfile();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @OnClick(R.id.btnMale)
    public void btnMaleOnClickListener() {
        btnMaleOnClick();
        mainActivity.updateUserLookingForGender(ConstantRegistry.MALE);
    }

    private void btnMaleOnClick() {
        if (!maleIsSelected) {
            maleIsSelected = true;
            btnMale.setBackgroundResource(R.drawable.my_button);
            btnMale.setTextColor(getResources().getColor(R.color.colorWhite));

            if (femaleIsSelected) {
                femaleIsSelected = false;
                btnFemale.setBackgroundResource(R.drawable.my_gender_button);
                btnFemale.setTextColor(getResources().getColor(R.color.colorBlack));
            }

            if (bothIsSelected) {
                bothIsSelected = false;
                btnBoth.setBackgroundResource(R.drawable.my_gender_button);
                btnBoth.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        } else {
            maleIsSelected = false;
            btnMale.setBackgroundResource(R.drawable.my_gender_button);
            btnMale.setTextColor(getResources().getColor(R.color.colorBlack));
        }
    }

    @OnClick(R.id.btnFemale)
    public void btnFemaleOnClickListener() {
        btnFemaleOnClick();
        mainActivity.updateUserLookingForGender(ConstantRegistry.FEMALE);
    }

    private void btnFemaleOnClick() {
        if (!femaleIsSelected) {
            femaleIsSelected = true;
            btnFemale.setBackgroundResource(R.drawable.my_button);
            btnFemale.setTextColor(getResources().getColor(R.color.colorWhite));

            if (maleIsSelected) {
                maleIsSelected = false;
                btnMale.setBackgroundResource(R.drawable.my_gender_button);
                btnMale.setTextColor(getResources().getColor(R.color.colorBlack));
            }

            if (bothIsSelected) {
                bothIsSelected = false;
                btnBoth.setBackgroundResource(R.drawable.my_gender_button);
                btnBoth.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        } else {
            femaleIsSelected = false;
            btnFemale.setBackgroundResource(R.drawable.my_gender_button);
            btnFemale.setTextColor(getResources().getColor(R.color.colorBlack));
        }
    }

    @OnClick(R.id.btnBoth)
    public void btnBothOnClickListener() {
        btnBothOnClick();
        mainActivity.updateUserLookingForGender(ConstantRegistry.BOTH);
    }

    private void btnBothOnClick() {
        if (!bothIsSelected) {
            bothIsSelected = true;
            btnBoth.setBackgroundResource(R.drawable.my_button);
            btnBoth.setTextColor(getResources().getColor(R.color.colorWhite));

            if (maleIsSelected) {
                maleIsSelected = false;
                btnMale.setBackgroundResource(R.drawable.my_gender_button);
                btnMale.setTextColor(getResources().getColor(R.color.colorBlack));
            }

            if (femaleIsSelected) {
                femaleIsSelected = false;
                btnFemale.setBackgroundResource(R.drawable.my_gender_button);
                btnFemale.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        } else {
            bothIsSelected = false;
            btnBoth.setBackgroundResource(R.drawable.my_gender_button);
            btnBoth.setTextColor(getResources().getColor(R.color.colorBlack));
        }
    }

}