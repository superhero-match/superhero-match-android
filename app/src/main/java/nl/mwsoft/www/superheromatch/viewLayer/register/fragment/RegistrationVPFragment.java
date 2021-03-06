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
package nl.mwsoft.www.superheromatch.viewLayer.register.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.viewLayer.register.activity.RegisterActivity;
import nl.mwsoft.www.superheromatch.viewLayer.register.adapter.RegisterViewPagerAdapter;

public class RegistrationVPFragment extends Fragment {

    @BindView(R.id.vpRegister)
    ViewPager vpRegister;
    private RegisterViewPagerAdapter registerViewPagerAdapter;
    @BindView(R.id.llDots)
    LinearLayout llDots;
    private ImageView[] dots;
    @BindView(R.id.btnPrevious)
    Button btnPrevious;
    @BindView(R.id.btnContinue)
    Button btnContinue;
    private Unbinder unbinder;
    private int currentPosition = 0;


    private RegisterActivity registerActivity;

    public static RegistrationVPFragment newInstance() {
        Bundle args = new Bundle();
        RegistrationVPFragment fragment = new RegistrationVPFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_vp, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadRegistrationViewPager();
    }

    private void loadRegistrationViewPager() {
        configureViewPager();
        showDots(0);
        vpRegister.addOnPageChangeListener(myOnPageChangeListener);
    }

    private void configureViewPager() {
        registerViewPagerAdapter = new RegisterViewPagerAdapter(getChildFragmentManager());
        vpRegister.setOffscreenPageLimit(0);
        vpRegister.setAdapter(registerViewPagerAdapter);
        vpRegister.setOnTouchListener(myOnTouchListener);
    }

    private void showDots(int currentPosition) {
        resetDots();
        showCurrentDots(currentPosition);
    }

    private void showCurrentDots(int currentPosition) {
        dots = new ImageView[7];

        for (int i = 0; i < 7; i++) {
            dots[i] = new ImageView(registerActivity);
            if (i == currentPosition) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(registerActivity, R.drawable.active_dots));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(registerActivity, R.drawable.inactive_dots));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);

            llDots.addView(dots[i], params);
        }
    }

    private void resetDots() {
        if (llDots != null) {
            llDots.removeAllViews();
        }
    }

    private void runOnPageSelectedRoutine(int position) {
        currentPosition = position;
        if (currentPosition > 0) {
            btnPrevious.setVisibility(View.VISIBLE);
        } else if (currentPosition == 0) {
            btnPrevious.setVisibility(View.GONE);
        }
        showDots(position);
    }

    public ViewPager.OnPageChangeListener myOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            runOnPageSelectedRoutine(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    public ViewPager.OnTouchListener myOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };

    @OnClick(R.id.btnPrevious)
    public void previousClickListener() {
        if (currentPosition >= 1) {
            currentPosition--;
            vpRegister.setCurrentItem(currentPosition);
        }
    }

    @OnClick(R.id.btnContinue)
    public void continueClickListener() {
        switch (currentPosition) {
            case ConstantRegistry.CURRENT_POSITION_SUPERHERO_NAME:
                if (registerActivity.superheroNameHasBeenChosen()) {
                    currentPosition++;
                    vpRegister.setCurrentItem(currentPosition);
                }

                break;
            case ConstantRegistry.CURRENT_POSITION_SUPERHERO_BIRTHDAY:
                if (registerActivity.superheroBirthdayHasBeenChosen()) {
                    currentPosition++;
                    vpRegister.setCurrentItem(currentPosition);
                }

                break;
            case ConstantRegistry.CURRENT_POSITION_DISTANCE_UNIT:
                if (registerActivity.superheroDistanceUnitHasBeenChosen()) {
                    currentPosition++;
                    vpRegister.setCurrentItem(currentPosition);
                }

                break;
            case ConstantRegistry.CURRENT_POSITION_SUPERHERO_GENDER:
                if (registerActivity.superheroGenderHasBeenChosen()) {
                    currentPosition++;
                    vpRegister.setCurrentItem(currentPosition);
                }

                break;
            case ConstantRegistry.CURRENT_POSITION_LOOKING_FOR_GENDER:
                if (registerActivity.superheroLookingForGenderHasBeenChosen()) {
                    currentPosition++;
                    vpRegister.setCurrentItem(currentPosition);
                }

                break;
            case ConstantRegistry.CURRENT_POSITION_SUPERHERO_SUPERPOWER:
                if (registerActivity.superheroSuperpowerHasBeenChosen()) {
                    currentPosition++;
                    vpRegister.setCurrentItem(currentPosition);
                }

                break;
            case ConstantRegistry.CURRENT_POSITION_SUPERHERO_PROFILE_PICTURE:
                if (registerActivity.superheroProfilePictureHasBeenChosen()) {
                    registerActivity.registerUser();
                }

                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        registerActivity = (RegisterActivity) context;
    }

}

