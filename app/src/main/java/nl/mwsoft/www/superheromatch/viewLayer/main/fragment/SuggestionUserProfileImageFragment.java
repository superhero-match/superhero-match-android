package nl.mwsoft.www.superheromatch.viewLayer.main.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class SuggestionUserProfileImageFragment extends Fragment {

    @BindView(R.id.ivSuggestionUserProfilePic)
    ImageView ivSuggestionUserProfilePic;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private static final String PIC_URL = "picUrl";
    private String picUrl;

    public static SuggestionUserProfileImageFragment newInstance(String picUrl) {

        Bundle args = new Bundle();
        args.putString(PIC_URL, picUrl);
        SuggestionUserProfileImageFragment fragment = new SuggestionUserProfileImageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion_user_profile_image, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if(arguments != null){
            picUrl = arguments.getString(PIC_URL);
            if(picUrl == null){
                picUrl = "";
            }
        }else{
            picUrl = "";
        }

        Picasso.with(mainActivity).load(R.drawable.user_512).into(ivSuggestionUserProfilePic);
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