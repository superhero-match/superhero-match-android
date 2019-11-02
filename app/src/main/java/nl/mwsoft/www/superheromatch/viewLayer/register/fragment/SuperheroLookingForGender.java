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

public class SuperheroLookingForGender extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.btnSuperHeroLookingForGenderMale)
    Button btnSuperHeroLookingForGenderMale;
    @BindView(R.id.btnSuperHeroLookingForGenderFemale)
    Button btnSuperHeroLookingForGenderFemale;
    @BindView(R.id.btnSuperHeroLookingForGenderBoth)
    Button btnSuperHeroLookingForGenderBoth;
    private RegisterActivity registerActivity;
    private boolean maleIsSelected = false;
    private boolean femaleIsSelected = false;
    private boolean bothIsSelected = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_superhero_looking_for_gender, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.btnSuperHeroLookingForGenderMale)
    public void maleListener(){
        registerActivity.setLookingForGender(ConstantRegistry.MALE);
        btnMaleOnClick();
    }

    private void btnMaleOnClick() {
        if (!maleIsSelected) {
            maleIsSelected = true;
            btnSuperHeroLookingForGenderMale.setBackgroundResource(R.drawable.my_button);
            btnSuperHeroLookingForGenderMale.setTextColor(getResources().getColor(R.color.colorWhite));

            if (femaleIsSelected) {
                femaleIsSelected = false;
                btnSuperHeroLookingForGenderFemale.setBackgroundResource(R.drawable.my_gender_button);
                btnSuperHeroLookingForGenderFemale.setTextColor(getResources().getColor(R.color.colorBlack));
            }

            if (bothIsSelected) {
                bothIsSelected = false;
                btnSuperHeroLookingForGenderBoth.setBackgroundResource(R.drawable.my_gender_button);
                btnSuperHeroLookingForGenderBoth.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        } else {
            maleIsSelected = false;
            btnSuperHeroLookingForGenderMale.setBackgroundResource(R.drawable.my_gender_button);
            btnSuperHeroLookingForGenderMale.setTextColor(getResources().getColor(R.color.colorBlack));
        }
    }

    @OnClick(R.id.btnSuperHeroLookingForGenderFemale)
    public void femaleListener(){
        registerActivity.setLookingForGender(ConstantRegistry.FEMALE);
        btnFemaleOnClick();
    }

    private void btnFemaleOnClick() {
        if (!femaleIsSelected) {
            femaleIsSelected = true;
            btnSuperHeroLookingForGenderFemale.setBackgroundResource(R.drawable.my_button);
            btnSuperHeroLookingForGenderFemale.setTextColor(getResources().getColor(R.color.colorWhite));

            if (maleIsSelected) {
                maleIsSelected = false;
                btnSuperHeroLookingForGenderMale.setBackgroundResource(R.drawable.my_gender_button);
                btnSuperHeroLookingForGenderMale.setTextColor(getResources().getColor(R.color.colorBlack));
            }

            if (bothIsSelected) {
                bothIsSelected = false;
                btnSuperHeroLookingForGenderBoth.setBackgroundResource(R.drawable.my_gender_button);
                btnSuperHeroLookingForGenderBoth.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        } else {
            femaleIsSelected = false;
            btnSuperHeroLookingForGenderFemale.setBackgroundResource(R.drawable.my_gender_button);
            btnSuperHeroLookingForGenderFemale.setTextColor(getResources().getColor(R.color.colorBlack));
        }
    }

    @OnClick(R.id.btnSuperHeroLookingForGenderBoth)
    public void bothListener(){
        registerActivity.setLookingForGender(ConstantRegistry.BOTH);
        btnBothOnClick();
    }

    private void btnBothOnClick() {
        if (!bothIsSelected) {
            bothIsSelected = true;
            btnSuperHeroLookingForGenderBoth.setBackgroundResource(R.drawable.my_button);
            btnSuperHeroLookingForGenderBoth.setTextColor(getResources().getColor(R.color.colorWhite));

            if (maleIsSelected) {
                maleIsSelected = false;
                btnSuperHeroLookingForGenderMale.setBackgroundResource(R.drawable.my_gender_button);
                btnSuperHeroLookingForGenderMale.setTextColor(getResources().getColor(R.color.colorBlack));
            }

            if (femaleIsSelected) {
                femaleIsSelected = false;
                btnSuperHeroLookingForGenderFemale.setBackgroundResource(R.drawable.my_gender_button);
                btnSuperHeroLookingForGenderFemale.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        } else {
            bothIsSelected = false;
            btnSuperHeroLookingForGenderBoth.setBackgroundResource(R.drawable.my_gender_button);
            btnSuperHeroLookingForGenderBoth.setTextColor(getResources().getColor(R.color.colorBlack));
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