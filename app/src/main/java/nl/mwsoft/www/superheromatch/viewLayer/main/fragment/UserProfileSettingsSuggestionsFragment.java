package nl.mwsoft.www.superheromatch.viewLayer.main.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class UserProfileSettingsSuggestionsFragment  extends Fragment {

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile_settings_suggestions, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TO-DO: check user is looking for gender and set the corresponding button to active.
        btnFemale.callOnClick();

        tvUserProfileSettingsSuggestionsLookingForDesc.setTypeface(tvUserProfileSettingsSuggestionsLookingForDesc.getTypeface(), Typeface.BOLD);
        tvUserProfileSettingsSuggestionsAgeRangeDesc.setTypeface(tvUserProfileSettingsSuggestionsAgeRangeDesc.getTypeface(), Typeface.BOLD);
        tvUserProfileSettingsSuggestionsDistanceDesc.setTypeface(tvUserProfileSettingsSuggestionsDistanceDesc.getTypeface(), Typeface.BOLD);

        rsUserProfileSettingsSuggestionsAgeRange.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvAgeRangeMin.setText(String.valueOf(minValue));
                tvAgeRangeMax.setText(String.valueOf(maxValue));
            }
        });

        rsUserProfileSettingsSuggestionsAgeRange.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                tvAgeRangeMin.setText(String.valueOf(minValue));
                tvAgeRangeMax.setText(String.valueOf(maxValue));
            }
        });

        tvDistance.setText(getString(R.string.default_distance, "km"));

        sbDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress == 100){
                    tvDistance.setText(getString(R.string.selected_distance, progress, "+ km"));
                }else{
                    tvDistance.setText(getString(R.string.selected_distance, progress, "km"));
                }
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
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @OnClick(R.id.btnMale)
    public void btnMaleOnClickListener(){
        if(!maleIsSelected){
            maleIsSelected = true;
            btnMale.setBackgroundResource(R.drawable.my_button);
            btnMale.setTextColor(getResources().getColor(R.color.colorWhite));

            if(femaleIsSelected){
                femaleIsSelected = false;
                btnFemale.setBackgroundResource(R.drawable.my_gender_button);
                btnFemale.setTextColor(getResources().getColor(R.color.colorBlack));
            }

            if(bothIsSelected){
                bothIsSelected = false;
                btnBoth.setBackgroundResource(R.drawable.my_gender_button);
                btnBoth.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        }else{
            maleIsSelected = false;
            btnMale.setBackgroundResource(R.drawable.my_gender_button);
            btnMale.setTextColor(getResources().getColor(R.color.colorBlack));
        }
    }

    @OnClick(R.id.btnFemale)
    public void btnFemaleOnClickListener(){
        if(!femaleIsSelected){
            femaleIsSelected = true;
            btnFemale.setBackgroundResource(R.drawable.my_button);
            btnFemale.setTextColor(getResources().getColor(R.color.colorWhite));

            if(maleIsSelected){
                maleIsSelected = false;
                btnMale.setBackgroundResource(R.drawable.my_gender_button);
                btnMale.setTextColor(getResources().getColor(R.color.colorBlack));
            }

            if(bothIsSelected){
                bothIsSelected = false;
                btnBoth.setBackgroundResource(R.drawable.my_gender_button);
                btnBoth.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        }else{
            femaleIsSelected = false;
            btnFemale.setBackgroundResource(R.drawable.my_gender_button);
            btnFemale.setTextColor(getResources().getColor(R.color.colorBlack));
        }
    }

    @OnClick(R.id.btnBoth)
    public void btnBothOnClickListener(){
        if(!bothIsSelected){
            bothIsSelected = true;
            btnBoth.setBackgroundResource(R.drawable.my_button);
            btnBoth.setTextColor(getResources().getColor(R.color.colorWhite));

            if(maleIsSelected){
                maleIsSelected = false;
                btnMale.setBackgroundResource(R.drawable.my_gender_button);
                btnMale.setTextColor(getResources().getColor(R.color.colorBlack));
            }

            if(femaleIsSelected){
                femaleIsSelected = false;
                btnFemale.setBackgroundResource(R.drawable.my_gender_button);
                btnFemale.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        }else{
            bothIsSelected = false;
            btnBoth.setBackgroundResource(R.drawable.my_gender_button);
            btnBoth.setTextColor(getResources().getColor(R.color.colorBlack));
        }
    }

}