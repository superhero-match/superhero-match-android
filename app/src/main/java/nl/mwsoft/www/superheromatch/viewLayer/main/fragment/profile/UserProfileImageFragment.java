package nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class UserProfileImageFragment  extends Fragment {

    @BindView(R.id.ivUserProfilePic)
    ImageView ivUserProfilePic;
    @BindView(R.id.tvUserNameAge)
    TextView tvUserNameAge;
    @BindView(R.id.tvUserCity)
    TextView tvUserCity;
    @BindView(R.id.tvUserSuperPowerDesc)
    TextView tvUserSuperPowerDesc;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private static final String PIC_URL = "picUrl";
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String CITY = "city";
    private static final String SUPERPOWER = "superpower";
    private String picUrl;
    private String name;
    private int age;
    private String city;
    private String superpower;

    public static UserProfileImageFragment newInstance(String picUrl, String name, int age, String city, String superpower) {
        Bundle args = new Bundle();
        args.putString(PIC_URL, picUrl);
        args.putString(NAME, name);
        args.putInt(AGE, age);
        args.putString(CITY, city);
        args.putString(SUPERPOWER, superpower);
        UserProfileImageFragment fragment = new UserProfileImageFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile_image, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            picUrl = arguments.getString(PIC_URL);
            name = arguments.getString(NAME);
            age = arguments.getInt(AGE);
            city = arguments.getString(CITY);
            superpower = arguments.getString(SUPERPOWER);
            if (picUrl == null) {
                picUrl = "";
            }
        } else {
            return;
        }

        ivUserProfilePic.setClipToOutline(true);

        switch (picUrl) {
            case "test":
                Glide.with(mainActivity)
                        .load(R.drawable.test)
                        .into(ivUserProfilePic);
                break;
            case "test5":
                Glide.with(mainActivity)
                        .load(R.drawable.test5)
                        .into(ivUserProfilePic);
                break;
            case "test2":
                Glide.with(mainActivity)
                        .load(R.drawable.test2)
                        .into(ivUserProfilePic);
                break;
            case "test3":
                Glide.with(mainActivity)
                        .load(R.drawable.test3)
                        .into(ivUserProfilePic);
                break;
            case "test7":
                Glide.with(mainActivity)
                        .load(R.drawable.test7)
                        .into(ivUserProfilePic);
                break;
        }

        tvUserNameAge.bringToFront();
        tvUserNameAge.setTypeface(tvUserNameAge.getTypeface(), Typeface.BOLD);
        tvUserNameAge.setText(
                getString(
                        R.string.name_age,
                        name,
                        age
                )
        );

        tvUserCity.setTypeface(tvUserCity.getTypeface(), Typeface.BOLD);
        tvUserCity.setText(city);

        tvUserSuperPowerDesc.setText(superpower);
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
