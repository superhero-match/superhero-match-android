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
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.event.ImageMessageEvent;
import nl.mwsoft.www.superheromatch.modelLayer.event.TextMessageEvent;
import nl.mwsoft.www.superheromatch.modelLayer.model.Chat;
import nl.mwsoft.www.superheromatch.modelLayer.model.Message;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.ChatMessageAdapter;

public class ChatFragment extends Fragment {

    private MainActivity mainActivity;
    @BindView(R.id.rvChat)
    RecyclerView rvChat;
    @BindView(R.id.etMessage)
    EditText etMessage;
    @BindView(R.id.ivSendMessage)
    ImageView ivSendMessage;
    private Unbinder unbinder;
    private Chat chat;
    private ArrayList<Message> messages;
    private ChatMessageAdapter chatMessageAdapter;
    public static final String CHAT = "chat";
    public static final String MESSAGES = "messages";


    public static ChatFragment newInstance(Chat chat, ArrayList<Message> messages) {

        Bundle args = new Bundle();
        args.putParcelable(CHAT, chat);
        args.putParcelableArrayList(MESSAGES, messages);
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivity.showBottomNavigation();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity.hideBottomNavigation();
        Bundle arguments = getArguments();

        if (arguments == null) {
            return;
        }

        chat = arguments.getParcelable(CHAT);
        messages = arguments.getParcelableArrayList(MESSAGES);

        chatMessageAdapter = new ChatMessageAdapter(messages, mainActivity);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        rvChat.setLayoutManager(mLayoutManager);
        rvChat.setItemAnimator(new DefaultItemAnimator());
        rvChat.setAdapter(chatMessageAdapter);

        mainActivity.setCurrentChat(chat);

        if (messages.size() > 0) {
            scrollToBottom();
        }
    }

    private void scrollToBottom() {
        rvChat.scrollToPosition(chatMessageAdapter.getItemCount() - 1);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @OnClick(R.id.ivSendMessage)
    public void sendMessageClickListener() {
        String message = etMessage.getText().toString().trim();
        etMessage.setText("");
        mainActivity.sendMessageClickListener(message, chat.getMatchedUserId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TextMessageEvent event) {
        messages = event.getMessages();
        chatMessageAdapter.notifyDataSetChanged();
        scrollToBottom();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ImageMessageEvent event) {
        messages = event.getMessages();
        chatMessageAdapter.notifyDataSetChanged();
        scrollToBottom();
    }
}