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
    private ArrayList<Message> messages;
    private ChatMessageAdapter chatMessageAdapter;
    public static final String MESSAGES = "messages";


    public static ChatFragment newInstance(ArrayList<Message> messages) {

        Bundle args = new Bundle();
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
        if(arguments != null){
            messages = arguments.getParcelableArrayList(MESSAGES);
        }else{
            messages = new ArrayList<>();
        }

        chatMessageAdapter = new ChatMessageAdapter(messages, mainActivity);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        rvChat.setLayoutManager(mLayoutManager);
        rvChat.setItemAnimator(new DefaultItemAnimator());
        rvChat.setAdapter(chatMessageAdapter);

        if(messages.size() > 0){
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
    public void sendMessageClickListener(){
        String message = etMessage.getText().toString().trim();
        etMessage.setText("");
        mainActivity.sendMessageClickListener(message);
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