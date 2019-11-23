package nl.mwsoft.www.superheromatch.viewLayer.main.fragment.matches;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.Chat;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.MatchesChatsAdapter;

public class MatchesChatsFragment extends Fragment {

    @BindView(R.id.rvMatchesChats)
    RecyclerView rvMatchesChats;
    private MatchesChatsAdapter matchesChatsAdapter;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private ArrayList<Chat> matchChats;
    private static final String MATCHES_CHATS = "matches_chats";

    public static MatchesChatsFragment newInstance(ArrayList<Chat> matchChats) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(MATCHES_CHATS,matchChats);
        MatchesChatsFragment fragment = new MatchesChatsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matches_chats, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments != null){
            matchChats = arguments.getParcelableArrayList(MATCHES_CHATS);
            if(matchChats == null){
                matchChats = new ArrayList<>();
            }
        }else{
            matchChats = new ArrayList<>();
        }

        matchesChatsAdapter = new MatchesChatsAdapter(matchChats);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        rvMatchesChats.setLayoutManager(mLayoutManager);
        rvMatchesChats.setItemAnimator(new DefaultItemAnimator());
        rvMatchesChats.setAdapter(matchesChatsAdapter);
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