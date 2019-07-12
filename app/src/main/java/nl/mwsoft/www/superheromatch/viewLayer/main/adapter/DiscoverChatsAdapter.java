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
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.ChatFragment;

public class DiscoverChatsAdapter extends RecyclerView.Adapter<DiscoverChatsAdapter.MyViewHolder> {

    private List<Chat> matchChats;
    private Context context;
    private MainActivity mainActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileDiscoverChats;
        public TextView tvUserNameDiscoverChats;
        public TextView tvLastActivityMessageDiscoverChats;
        public TextView tvLastActivityDateDiscoverChats;
        public TextView tvUnreadMsgDiscoverChat;
        public RelativeLayout rlDiscoverChats;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            mainActivity = (MainActivity)context;
            rlDiscoverChats = (RelativeLayout) view.findViewById(R.id.rlDiscoverChats);
            ivProfileDiscoverChats = (ImageView) view.findViewById(R.id.ivProfileDiscoverChats);
            tvUserNameDiscoverChats = (TextView) view.findViewById(R.id.tvUserNameDiscoverChats);
            tvLastActivityMessageDiscoverChats = (TextView) view.findViewById(R.id.tvLastActivityMessageDiscoverChats);
            tvLastActivityDateDiscoverChats = (TextView) view.findViewById(R.id.tvLastActivityDateDiscoverChats);
            tvUnreadMsgDiscoverChat = (TextView) view.findViewById(R.id.tvUnreadMsgDiscoverChat);

            rlDiscoverChats.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.loadBackStackFragment(ChatFragment.newInstance(mainActivity.getMessages()));
                }
            });
        }
    }

    public DiscoverChatsAdapter(List<Chat> matchChats) {
        this.matchChats = matchChats;
    }

    @Override
    public DiscoverChatsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discover_chats_item, parent, false);

        return new DiscoverChatsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DiscoverChatsAdapter.MyViewHolder holder, int position) {
        Chat matchChat = matchChats.get(position);
        holder.tvUserNameDiscoverChats.setText(matchChat.getUserName());
        holder.tvLastActivityDateDiscoverChats.setText(matchChat.getLastActivityDate());
        holder.tvLastActivityMessageDiscoverChats.setText(matchChat.getLastActivityMessage());
        if(matchChat.getUnreadMessageCount() > 0){
            if(holder.tvUnreadMsgDiscoverChat.getVisibility() == View.GONE){
                holder.tvUnreadMsgDiscoverChat.setVisibility(View.VISIBLE);
            }
            holder.tvUnreadMsgDiscoverChat.setText(String.valueOf(matchChat.getUnreadMessageCount()));
        }else{
            if(holder.tvUnreadMsgDiscoverChat.getVisibility() == View.VISIBLE){
                holder.tvUnreadMsgDiscoverChat.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return matchChats.size();
    }
}

