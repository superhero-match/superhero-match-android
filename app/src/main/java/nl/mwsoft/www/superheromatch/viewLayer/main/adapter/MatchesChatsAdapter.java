package nl.mwsoft.www.superheromatch.viewLayer.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.Chat;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.matches.ChatFragment;

public class MatchesChatsAdapter extends RecyclerView.Adapter<MatchesChatsAdapter.MyViewHolder> {

    private List<Chat> matchChats;
    private Context context;
    private MainActivity mainActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileMatchesChats;
        public TextView tvUserNameMatchesChats;
        public TextView tvLastActivityMessageMatchesChats;
        public TextView tvLastActivityDateMatchesChats;
        public TextView tvUnreadMsgMatchesChat;
        public RelativeLayout rlMatchesChats;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            mainActivity = (MainActivity)context;
            rlMatchesChats = (RelativeLayout) view.findViewById(R.id.rlMatchesChats);
            ivProfileMatchesChats = (ImageView) view.findViewById(R.id.ivProfileMatchesChats);
            tvUserNameMatchesChats = (TextView) view.findViewById(R.id.tvUserNameMatchesChats);
            tvLastActivityMessageMatchesChats = (TextView) view.findViewById(R.id.tvLastActivityMessageMatchesChats);
            tvLastActivityDateMatchesChats = (TextView) view.findViewById(R.id.tvLastActivityDateMatchesChats);
            tvUnreadMsgMatchesChat = (TextView) view.findViewById(R.id.tvUnreadMsgMatchesChat);

            rlMatchesChats.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.loadBackStackFragment(ChatFragment.newInstance(mainActivity.getMessages()));
                }
            });
        }
    }

    public MatchesChatsAdapter(List<Chat> matchChats) {
        this.matchChats = matchChats;
    }

    @Override
    public MatchesChatsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.matches_chats_list_item, parent, false);

        return new MatchesChatsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MatchesChatsAdapter.MyViewHolder holder, int position) {
        Chat matchChat = matchChats.get(position);
        holder.tvUserNameMatchesChats.setText(matchChat.getUserName());
        holder.tvLastActivityDateMatchesChats.setText(matchChat.getLastActivityDate());
        holder.tvLastActivityMessageMatchesChats.setText(matchChat.getLastActivityMessage());
        if(matchChat.getUnreadMessageCount() > 0){
            if(holder.tvUnreadMsgMatchesChat.getVisibility() == View.GONE){
                holder.tvUnreadMsgMatchesChat.setVisibility(View.VISIBLE);
            }
            holder.tvUnreadMsgMatchesChat.setText(String.valueOf(matchChat.getUnreadMessageCount()));
        }else{
            if(holder.tvUnreadMsgMatchesChat.getVisibility() == View.VISIBLE){
                holder.tvUnreadMsgMatchesChat.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return matchChats.size();
    }
}

