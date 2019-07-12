package nl.mwsoft.www.superheromatch.viewLayer.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.NearbyAdapter;

public class NearbyFragment extends Fragment {

    @BindView(R.id.rvNearby)
    RecyclerView rvSuggestions;
    private NearbyAdapter nearbyAdapter;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private ArrayList<User> users;
    private static final String USERS = "users";
    private int currentPosition = 0;

    public static NearbyFragment newInstance(ArrayList<User> users) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(USERS, users);
        NearbyFragment fragment = new NearbyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if(arguments != null){
            users = arguments.getParcelableArrayList(USERS);
            if(users == null){
                users = new ArrayList<>();
            }
        }else{
            users = new ArrayList<>();
        }

        loadSuggestions(users);
    }

    private void loadSuggestions(ArrayList<User> users) {
        nearbyAdapter = new NearbyAdapter(users);
        StaggeredGridLayoutManager mLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvSuggestions.setLayoutManager(mLayoutManager);
        rvSuggestions.setItemAnimator(new DefaultItemAnimator());
        rvSuggestions.setAdapter(nearbyAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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