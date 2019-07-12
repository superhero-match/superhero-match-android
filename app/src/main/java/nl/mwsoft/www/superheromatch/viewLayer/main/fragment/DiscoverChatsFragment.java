package nl.mwsoft.www.superheromatch.viewLayer.main.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
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
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.DiscoverChatsAdapter;

public class DiscoverChatsFragment  extends Fragment {

    @BindView(R.id.rvDiscoverChats)
    RecyclerView rvDiscoverChats;
    private DiscoverChatsAdapter discoverChatsAdapter;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private ArrayList<Chat> matchChats;
    private static final String MATCHES_CHATS = "matches_chats";

    public static DiscoverChatsFragment newInstance(ArrayList<Chat> matchChats) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(MATCHES_CHATS,matchChats);
        DiscoverChatsFragment fragment = new DiscoverChatsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover_chats, container, false);
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

        discoverChatsAdapter = new DiscoverChatsAdapter(matchChats);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        rvDiscoverChats.setLayoutManager(mLayoutManager);
        rvDiscoverChats.setItemAnimator(new DefaultItemAnimator());
        rvDiscoverChats.setAdapter(discoverChatsAdapter);
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