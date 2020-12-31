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
package nl.mwsoft.www.superheromatch.viewLayer.register.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.viewLayer.register.activity.RegisterActivity;

public class SuperheroGender extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.btnSuperHeroGenderMale)
    Button btnSuperHeroGenderMale;
    @BindView(R.id.btnSuperHeroGenderFemale)
    Button btnSuperHeroGenderFemale;
    private RegisterActivity registerActivity;
    private boolean maleIsSelected = false;
    private boolean femaleIsSelected = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_superhero_gender, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.btnSuperHeroGenderMale)
    public void maleListener(){
        registerActivity.setGender(ConstantRegistry.MALE);
        btnMaleOnClick();
    }

    private void btnMaleOnClick() {
        if (!maleIsSelected) {
            maleIsSelected = true;
            btnSuperHeroGenderMale.setBackgroundResource(R.drawable.my_button);
            btnSuperHeroGenderMale.setTextColor(getResources().getColor(R.color.colorWhite));

            if (femaleIsSelected) {
                femaleIsSelected = false;
                btnSuperHeroGenderFemale.setBackgroundResource(R.drawable.my_gender_button);
                btnSuperHeroGenderFemale.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        } else {
            maleIsSelected = false;
            btnSuperHeroGenderMale.setBackgroundResource(R.drawable.my_gender_button);
            btnSuperHeroGenderMale.setTextColor(getResources().getColor(R.color.colorBlack));
        }
    }

    @OnClick(R.id.btnSuperHeroGenderFemale)
    public void femaleListener(){
        registerActivity.setGender(ConstantRegistry.FEMALE);
        btnFemaleOnClick();
    }

    private void btnFemaleOnClick() {
        if (!femaleIsSelected) {
            femaleIsSelected = true;
            btnSuperHeroGenderFemale.setBackgroundResource(R.drawable.my_button);
            btnSuperHeroGenderFemale.setTextColor(getResources().getColor(R.color.colorWhite));

            if (maleIsSelected) {
                maleIsSelected = false;
                btnSuperHeroGenderMale.setBackgroundResource(R.drawable.my_gender_button);
                btnSuperHeroGenderMale.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        } else {
            femaleIsSelected = false;
            btnSuperHeroGenderFemale.setBackgroundResource(R.drawable.my_gender_button);
            btnSuperHeroGenderFemale.setTextColor(getResources().getColor(R.color.colorBlack));
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
        registerActivity = (RegisterActivity)context;
    }

}