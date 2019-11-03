package nl.mwsoft.www.superheromatch.viewLayer.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class UserProfileEditFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.etSuperPowerEdit)
    EditText etSuperPowerEdit;
    @BindView(R.id.btnKm)
    Button btnKm;
    @BindView(R.id.btnMi)
    Button btnMi;


    private MainActivity mainActivity;

    public static UserProfileEditFragment newInstance() {
        Bundle args = new Bundle();
        UserProfileEditFragment fragment = new UserProfileEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile_edit, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etSuperPowerEdit.setText(mainActivity.getUserSuperPower());
        etSuperPowerEdit.addTextChangedListener(etSuperPowerWatcher);

        switch (mainActivity.getUserDistanceUnit()){
            case ConstantRegistry.KILOMETERS:
                renderOnKMClicked();
                break;
            case ConstantRegistry.MILES:
                renderOnMIClicked();
                break;
        }
    }

    private TextWatcher etSuperPowerWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s != null && s.toString().length() > 0) {
                mainActivity.updateUserSuperPower(s.toString().trim());
            }
        }
    };

    @OnClick(R.id.btnKm)
    public void kilometersListener() {
        renderOnKMClicked();
        mainActivity.updateUserDistanceUnit(ConstantRegistry.KILOMETERS);
    }

    private void renderOnKMClicked() {
        btnKm.setBackgroundResource(R.drawable.my_button);
        btnKm.setTextColor(getResources().getColor(R.color.colorWhite));

        btnMi.setBackgroundResource(R.drawable.my_gender_button);
        btnMi.setTextColor(getResources().getColor(R.color.colorBlack));
    }

    @OnClick(R.id.btnMi)
    public void milesListener() {
        renderOnMIClicked();
        mainActivity.updateUserDistanceUnit(ConstantRegistry.MILES);
    }

    private void renderOnMIClicked() {
        btnMi.setBackgroundResource(R.drawable.my_button);
        btnMi.setTextColor(getResources().getColor(R.color.colorWhite));

        btnKm.setBackgroundResource(R.drawable.my_gender_button);
        btnKm.setTextColor(getResources().getColor(R.color.colorBlack));
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
        mainActivity = (MainActivity)context;
    }

}

