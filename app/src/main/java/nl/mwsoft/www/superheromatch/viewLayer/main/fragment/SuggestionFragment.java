package nl.mwsoft.www.superheromatch.viewLayer.main.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class SuggestionFragment extends Fragment {

    @BindView(R.id.ivSuggestionsMainProfilePic)
    ImageView ivSuggestionsMainProfilePic;
    @BindView(R.id.tvSuggestionsUsernameAge)
    TextView tvSuggestionsUsernameAge;
    @BindView(R.id.tvSuggestionsCityCountry)
    TextView tvSuggestionsCityCountry;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private User user;
    private static final String USER = "user";

    public static SuggestionFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(USER, user);
        SuggestionFragment fragment = new SuggestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.suggestions_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if(arguments != null){
            user = arguments.getParcelable(USER);
            if(user == null){
                user = new User();
                Toast.makeText(mainActivity,"Something went wrong!",Toast.LENGTH_SHORT).show();
            }else {
                tvSuggestionsUsernameAge.setText(user.getName().concat(", ").concat(String.valueOf(user.getAge())));
                tvSuggestionsCityCountry.setText(user.getCity().concat(", ").concat(user.getCountry()));
            }
        }else{
            user = new User();
            Toast.makeText(mainActivity,"Something went wrong!",Toast.LENGTH_SHORT).show();
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
}