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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

public class SuperheroDistanceUnit  extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.btnDistanceUnitKm)
    Button btnDistanceUnitKm;
    @BindView(R.id.btnDistanceUnitMi)
    Button btnDistanceUnitMi;

    private RegisterActivity registerActivity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration_distance_unit, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.btnDistanceUnitKm)
    public void kilometersListener() {
        renderOnKMClicked();
        registerActivity.setDistanceUnit(ConstantRegistry.KILOMETERS);
    }

    private void renderOnKMClicked() {
        btnDistanceUnitKm.setBackgroundResource(R.drawable.my_button);
        btnDistanceUnitKm.setTextColor(getResources().getColor(R.color.colorWhite));

        btnDistanceUnitMi.setBackgroundResource(R.drawable.my_gender_button);
        btnDistanceUnitMi.setTextColor(getResources().getColor(R.color.colorBlack));
    }

    @OnClick(R.id.btnDistanceUnitMi)
    public void milesListener() {
        renderOnMICliecked();
        registerActivity.setDistanceUnit(ConstantRegistry.MILES);
    }

    private void renderOnMICliecked() {
        btnDistanceUnitMi.setBackgroundResource(R.drawable.my_button);
        btnDistanceUnitMi.setTextColor(getResources().getColor(R.color.colorWhite));

        btnDistanceUnitKm.setBackgroundResource(R.drawable.my_gender_button);
        btnDistanceUnitKm.setTextColor(getResources().getColor(R.color.colorBlack));
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