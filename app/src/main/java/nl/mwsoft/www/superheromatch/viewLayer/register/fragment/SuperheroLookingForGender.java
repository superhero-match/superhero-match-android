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

public class SuperheroLookingForGender extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.ivSuperHeroLookingForGenderMale)
    ImageView ivSuperHeroLookingForGenderMale;
    @BindView(R.id.ivSuperHeroLookingForGenderFemale)
    ImageView ivSuperHeroLookingForGenderFemale;
    @BindView(R.id.ivSuperHeroLookingForGenderBoth)
    ImageView ivSuperHeroLookingForGenderBoth;

    private RegisterActivity registerActivity;

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

    @OnClick(R.id.ivSuperHeroLookingForGenderMale)
    public void maleListener(){
        registerActivity.setLookingForGender(ConstantRegistry.MALE);
        ivSuperHeroLookingForGenderMale.setImageResource(R.drawable.male_256);
        ivSuperHeroLookingForGenderFemale.setImageResource(R.drawable.female_inactive_256);
        ivSuperHeroLookingForGenderBoth.setImageResource(R.drawable.both_inactive_256);
    }

    @OnClick(R.id.ivSuperHeroLookingForGenderFemale)
    public void femaleListener(){
        registerActivity.setLookingForGender(ConstantRegistry.FEMALE);
        ivSuperHeroLookingForGenderMale.setImageResource(R.drawable.male_inactive_256);
        ivSuperHeroLookingForGenderFemale.setImageResource(R.drawable.female_256);
        ivSuperHeroLookingForGenderBoth.setImageResource(R.drawable.both_inactive_256);
    }

    @OnClick(R.id.ivSuperHeroLookingForGenderBoth)
    public void bothListener(){
        registerActivity.setLookingForGender(ConstantRegistry.BOTH);
        ivSuperHeroLookingForGenderMale.setImageResource(R.drawable.male_inactive_256);
        ivSuperHeroLookingForGenderFemale.setImageResource(R.drawable.female_inactive_256);
        ivSuperHeroLookingForGenderBoth.setImageResource(R.drawable.both_256);
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