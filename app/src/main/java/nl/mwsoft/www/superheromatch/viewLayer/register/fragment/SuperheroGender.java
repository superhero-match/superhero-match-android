package nl.mwsoft.www.superheromatch.viewLayer.register.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    @BindView(R.id.ivSuperHeroGenderMale)
    ImageView ivSuperHeroGenderMale;
    @BindView(R.id.ivSuperHeroGenderFemale)
    ImageView ivSuperHeroGenderFemale;

    private RegisterActivity registerActivity;

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

    @OnClick(R.id.ivSuperHeroGenderMale)
    public void maleListener(){
        registerActivity.setGender(ConstantRegistry.MALE);
        ivSuperHeroGenderMale.setImageResource(R.drawable.male_256);
        ivSuperHeroGenderFemale.setImageResource(R.drawable.female_inactive_256);
    }

    @OnClick(R.id.ivSuperHeroGenderFemale)
    public void femaleListener(){
        registerActivity.setGender(ConstantRegistry.FEMALE);
        ivSuperHeroGenderMale.setImageResource(R.drawable.male_inactive_256);
        ivSuperHeroGenderFemale.setImageResource(R.drawable.female_256);
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