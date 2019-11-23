package nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class UserProfileSettingsDiscoverFragment extends Fragment {

    @BindView(R.id.swUserProfileSettingsDiscover)
    Switch swUserProfileSettingsDiscover;
    @BindView(R.id.swUserProfileSettingsDiscoverCountry)
    Switch swUserProfileSettingsDiscoverCountry;
    private Unbinder unbinder;
    private MainActivity mainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile_settings_discover, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swUserProfileSettingsDiscover.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(mainActivity,"Updated to Show me in Discover",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(mainActivity,"Updated to Don't Show me in Discover",Toast.LENGTH_LONG).show();
                }
            }
        });

        swUserProfileSettingsDiscoverCountry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(mainActivity,"Updated to Show me in only suggestions in my country",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(mainActivity,"Updated to Don't Show me all suggestions",Toast.LENGTH_LONG).show();
                }
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

}