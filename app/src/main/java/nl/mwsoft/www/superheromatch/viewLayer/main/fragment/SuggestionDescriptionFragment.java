package nl.mwsoft.www.superheromatch.viewLayer.main.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.Superhero;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class SuggestionDescriptionFragment extends Fragment {

    @BindView(R.id.ivSuggestionDescriptionClose)
    ImageView ivSuggestionDescriptionClose;
    @BindView(R.id.tvSuggestionDescriptionNameAge)
    TextView tvSuggestionDescriptionNameAge;
    @BindView(R.id.tvSuggestionDescriptionCity)
    TextView tvSuggestionDescriptionCity;
    @BindView(R.id.tvSuggestionDescriptionSuperpower)
    TextView tvSuggestionDescriptionSuperpower;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private Superhero superhero;
    private static final String SUPERHERO = "superhero";

    public static SuggestionDescriptionFragment newInstance(Superhero superhero) {
        Bundle args = new Bundle();
        args.putParcelable(SUPERHERO, superhero);
        SuggestionDescriptionFragment fragment = new SuggestionDescriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion_description, container, false);
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
                tvSuggestionDescriptionNameAge.setTypeface(tvSuggestionDescriptionNameAge.getTypeface(), Typeface.BOLD);
                tvSuggestionDescriptionNameAge.setText(
                        getString(
                                R.string.name_age,
                                superhero.getSuperheroName(),
                                superhero.getAge()
                        )
                );

                tvSuggestionDescriptionCity.setText(superhero.getCity());
                tvSuggestionDescriptionSuperpower.setText(superhero.getSuperpower());
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

    @OnClick(R.id.ivSuggestionDescriptionClose)
    public void onSuggestionDescriptionClose() {
        mainActivity.closeSuggestionDescriptionWindow();
    }
}
