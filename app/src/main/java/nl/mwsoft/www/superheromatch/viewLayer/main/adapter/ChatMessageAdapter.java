package nl.mwsoft.www.superheromatch.viewLayer.main.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.Message;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class ChatMessageAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Message> messageList;
    private Context context;
    private MainActivity mainActivity;

    public class TextMessageViewHolder extends RecyclerView.ViewHolder {
        public TextView tvChatMessageSenderName;
        public TextView tvChatMessageText;
        public TextView tvChatMessageCreated;

        public TextMessageViewHolder(View view) {
            super(view);
            context = view.getContext();
            tvChatMessageSenderName = (TextView) view.findViewById(R.id.tvChatMessageSenderName);
            tvChatMessageText = (TextView) view.findViewById(R.id.tvChatMessageText);
            tvChatMessageCreated = (TextView) view.findViewById(R.id.tvChatMessageCreated);
        }
    }

    public class ImageMessageViewHolder extends RecyclerView.ViewHolder {

        public TextView tvChatMessageImageSenderName;
        public TextView tvChatMessageImageCreated;
        public ImageView ivChatMessageImage;

        public ImageMessageViewHolder(View view) {
            super(view);
            context = view.getContext();
            tvChatMessageImageSenderName = (TextView) view.findViewById(R.id.tvChatMessageImageSenderName);
            ivChatMessageImage = (ImageView) view.findViewById(R.id.ivChatMessageImage);
            tvChatMessageImageCreated = (TextView) view.findViewById(R.id.tvChatMessageImageCreated);
        }
    }


    public ChatMessageAdapter(ArrayList<Message> messageList, MainActivity mainActivity) {
        this.messageList = messageList;
        this.mainActivity = mainActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        context = parent.getContext();
        if(viewType == 1){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_item, parent, false);
            return new ChatMessageAdapter.TextMessageViewHolder(itemView);
        }else if(viewType == 2){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_image_item, parent, false);
            return new ChatMessageAdapter.ImageMessageViewHolder(itemView);
        }else if(viewType == 3){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_sender_item, parent, false);
            return new ChatMessageAdapter.TextMessageViewHolder(itemView);
        }else{
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_image_sender_item, parent, false);
            return new ChatMessageAdapter.ImageMessageViewHolder(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messageList.get(position).getMessageSenderId().equals(mainActivity.getUserId())){
            return 3;
        }else{
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        String date = getMessageCreated(message);
        TextMessageViewHolder textMessageViewHolder = (TextMessageViewHolder) holder;
        if(!message.getMessageSenderId().equals(mainActivity.getUserId())){
            if(textMessageViewHolder.tvChatMessageSenderName.getVisibility() == View.GONE){
                textMessageViewHolder.tvChatMessageSenderName.setVisibility(View.VISIBLE);
            }
            textMessageViewHolder.tvChatMessageSenderName
                    .setTypeface(textMessageViewHolder.tvChatMessageSenderName.getTypeface(), Typeface.BOLD);
            textMessageViewHolder.tvChatMessageSenderName
                    .setText(mainActivity.getContactNameById(context, message.getMessageSenderId()));
            textMessageViewHolder.tvChatMessageText.setText(message.getMessageText());
            textMessageViewHolder.tvChatMessageCreated.setText(date);
        }else{
            textMessageViewHolder.tvChatMessageSenderName
                    .setTypeface(textMessageViewHolder.tvChatMessageSenderName.getTypeface(), Typeface.BOLD);
            textMessageViewHolder.tvChatMessageText.setText(message.getMessageText());
            textMessageViewHolder.tvChatMessageCreated.setText(date);
        }

        // after the message has been read by the user, mark it as has been read
        // TO-DO: mainActivity.updateMessageHasBeenReadByMessageId(message.getMessageId(), context);
    }

    @NonNull
    private String getMessageCreated(Message message) {
        String messageCreated = "";
        String[] parts;
        String part1;
        String part2;
        String date = "";
        if(message.getMessageCreated() != null && !message.getMessageCreated().equals("")){
            messageCreated = message.getMessageCreated();
            parts = messageCreated.split(" ");
            part1 = parts[0]; // 2017-05-27
            part2 = parts[1]; // 12:05:41
            String[] dateParts = part1.split("-");
            String[] currDate = mainActivity.getDateTime().split(" ");
            String[] currDateParts = currDate[0].split("-");
            if(Integer.parseInt(currDateParts[2]) != Integer.parseInt(dateParts[2])){
                date = part1;
            }else{
                String[] newParts = part2.split(":");
                date = newParts[0].concat(":").concat(newParts[1]);
            }
        }else{
            messageCreated = mainActivity.getDateTime();
            parts = messageCreated.split(" ");
            part1 = parts[0]; // 2017-05-27
            part2 = parts[1]; // 12:05:41
            String[] newParts = part2.split(":");
            date = newParts[0].concat(":").concat(newParts[1]);
        }
        return date;
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}

