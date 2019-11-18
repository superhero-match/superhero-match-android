package nl.mwsoft.www.superheromatch.viewLayer.main.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class SuggestionProfileImageFragment extends Fragment {

    @BindView(R.id.ivSuggestionProfilePic)
    ImageView ivSuggestionProfilePic;
    @BindView(R.id.tvSuggestionNameAge)
    TextView tvSuggestionNameAge;
    @BindView(R.id.tvSuggestionCity)
    TextView tvSuggestionCity;
    @BindView(R.id.tvSuggestionSuperPowerDesc)
    TextView tvSuggestionSuperPowerDesc;
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

    public static SuggestionProfileImageFragment newInstance(String picUrl, String name, int age, String city, String superpower) {
        Bundle args = new Bundle();
        args.putString(PIC_URL, picUrl);
        args.putString(NAME, name);
        args.putInt(AGE, age);
        args.putString(CITY, city);
        args.putString(SUPERPOWER, superpower);
        SuggestionProfileImageFragment fragment = new SuggestionProfileImageFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion_profile_image, container, false);
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

        ivSuggestionProfilePic.setClipToOutline(true);

        switch (picUrl) {
            case "test":
                Glide.with(mainActivity)
                        .load(R.drawable.test)
                        .into(ivSuggestionProfilePic);
                break;
            case "test1":
                Glide.with(mainActivity)
                        .load(R.drawable.test1)
                        .into(ivSuggestionProfilePic);
                break;
            case "test2":
                Glide.with(mainActivity)
                        .load(R.drawable.test2)
                        .into(ivSuggestionProfilePic);
                break;
            case "test3":
                Glide.with(mainActivity)
                        .load(R.drawable.test3)
                        .into(ivSuggestionProfilePic);
                break;
            case "test4":
                Glide.with(mainActivity)
                        .load(R.drawable.test4)
                        .into(ivSuggestionProfilePic);
                break;
            case "test5":
                Glide.with(mainActivity)
                        .load(R.drawable.test5)
                        .into(ivSuggestionProfilePic);
                break;
            case "test6":
                Glide.with(mainActivity)
                        .load(R.drawable.test6)
                        .into(ivSuggestionProfilePic);
                break;
            case "test7":
                Glide.with(mainActivity)
                        .load(R.drawable.test7)
                        .into(ivSuggestionProfilePic);
                break;
            case "test8":
                Glide.with(mainActivity)
                        .load(R.drawable.test8)
                        .into(ivSuggestionProfilePic);
                break;
            case "test9":
                Glide.with(mainActivity)
                        .load(R.drawable.test9)
                        .into(ivSuggestionProfilePic);
                break;
            case "test10":
                Glide.with(mainActivity)
                        .load(R.drawable.test10)
                        .into(ivSuggestionProfilePic);
                break;
            case "test11":
                Glide.with(mainActivity)
                        .load(R.drawable.test11)
                        .into(ivSuggestionProfilePic);
                break;
            case "test12":
                Glide.with(mainActivity)
                        .load(R.drawable.test12)
                        .into(ivSuggestionProfilePic);
                break;
            default:
                Glide.with(mainActivity)
                        .load(R.drawable.test)
                        .into(ivSuggestionProfilePic);
        }

        tvSuggestionNameAge.bringToFront();
        tvSuggestionNameAge.setTypeface(tvSuggestionNameAge.getTypeface(), Typeface.BOLD);
        tvSuggestionNameAge.setText(
                getString(
                        R.string.name_age,
                        name,
                        age
                )
        );

        tvSuggestionCity.setTypeface(tvSuggestionCity.getTypeface(), Typeface.BOLD);
        tvSuggestionCity.setText(city);

        tvSuggestionSuperPowerDesc.setText(superpower);
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