/*
  Copyright (C) 2019 - 2020 MWSOFT
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
import nl.mwsoft.www.superheromatch.modelLayer.helper.recyclerView.RecyclerTouchListener;
import nl.mwsoft.www.superheromatch.modelLayer.helper.recyclerView.RecyclerViewClickListener;
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
        args.putParcelableArrayList(MATCHES_CHATS, matchChats);
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
        if (arguments != null) {
            matchChats = arguments.getParcelableArrayList(MATCHES_CHATS);
            if (matchChats == null) {
                matchChats = new ArrayList<>();
            }
        } else {
            matchChats = new ArrayList<>();
        }

        matchesChatsAdapter = new MatchesChatsAdapter(matchChats);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        rvMatchesChats.setLayoutManager(mLayoutManager);
        rvMatchesChats.setItemAnimator(new DefaultItemAnimator());
        rvMatchesChats.setAdapter(matchesChatsAdapter);
        rvMatchesChats.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(),
                rvMatchesChats, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                mainActivity.showDialogDeleteMatch(
                        matchChats.get(position).getChatId(),
                        position,
                        matchChats,
                        matchesChatsAdapter
                );
            }
        }));
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