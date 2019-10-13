package nl.mwsoft.www.superheromatch.viewLayer.main.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.model.Superhero;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.modelLayer.network.suggestions.Suggestions;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class SuggestionFragment extends Fragment {

    @BindView(R.id.ivSuggestionProfilePic)
    ImageView ivSuggestionProfilePic;
    @BindView(R.id.tvSuggestionName)
    TextView tvSuggestionName;
    @BindView(R.id.tvSuggestionAgeGender)
    TextView tvSuggestionAgeGender;
    @BindView(R.id.tvSuggestionSuperPowerDesc)
    TextView tvSuggestionSuperPowerDesc;
    @BindView(R.id.ivSuggestionDislike)
    ImageView ivSuggestionDislike;
    @BindView(R.id.ivSuggestionLike)
    ImageView ivSuggestionLike;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private Superhero superhero;
    private static final String SUPERHERO = "superhero";

    public static SuggestionFragment newInstance(Superhero superhero) {
        Bundle args = new Bundle();
        args.putParcelable(SUPERHERO, superhero);
        SuggestionFragment fragment = new SuggestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            superhero = arguments.getParcelable(SUPERHERO);
            if (superhero == null) {
                superhero = new Superhero();
                Toast.makeText(mainActivity, "Something went wrong!", Toast.LENGTH_SHORT).show();
            } else {
                Glide.with(mainActivity)
                        .load(R.drawable.test)
                        .into(ivSuggestionProfilePic);

                tvSuggestionName.setTypeface(tvSuggestionName.getTypeface(), Typeface.BOLD);
                tvSuggestionName.setText(superhero.getSuperheroName());

                switch (superhero.getGender()) {
                    case ConstantRegistry.MALE:
                        tvSuggestionAgeGender.setText(getString(
                                R.string.male_age,
                                superhero.getAge())
                        );
                        break;
                    case ConstantRegistry.FEMALE:
                        tvSuggestionAgeGender.setText(getString(
                                R.string.female_age,
                                superhero.getAge())
                        );
                        break;
                }

                tvSuggestionSuperPowerDesc.setText(superhero.getSuperpower());
            }
        } else {
            superhero = new Superhero();
            Toast.makeText(mainActivity, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
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

    @OnClick(R.id.ivSuggestionLike)
    public void onSuggestionLike(){
        // TO-DO: make request to server to process like and to check if there is match,
        // and then remove the user from the list and return to the suggestions list.
        Toast.makeText(mainActivity, "Like", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.ivSuggestionDislike)
    public void onSuggestionDislike(){
        // TO-DO: make request to server to process dislike,
        // and then remove the user from the list and return to the suggestions list.
        Toast.makeText(mainActivity, "Dislike", Toast.LENGTH_SHORT).show();
    }
}