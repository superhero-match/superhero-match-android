package nl.mwsoft.www.superheromatch.viewLayer.main.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.helper.image.ImageCircleTransformUtil;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.MyViewHolder> {

    private List<User> users;
    private Context context;
    private MainActivity mainActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivNearbyMainProfilePic;
        public TextView tvNearbyUsernameAge;
        public TextView tvNearbyCityCountry;
        public RelativeLayout rlNearbyItem;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            mainActivity = (MainActivity)context;
            ivNearbyMainProfilePic = (ImageView) view.findViewById(R.id.ivNearbyMainProfilePic);
            tvNearbyUsernameAge = (TextView) view.findViewById(R.id.tvNearbyUsernameAge);
            tvNearbyCityCountry = (TextView) view.findViewById(R.id.tvNearbyCityCountry);
            rlNearbyItem = (RelativeLayout) view.findViewById(R.id.rlNearbyItem);
            rlNearbyItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.loadSuggestionUserProfileFragment(users.get(getAdapterPosition()));
                }
            });

            ivNearbyMainProfilePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.loadSuggestionUserProfileFragment(users.get(getAdapterPosition()));
                }
            });
        }
    }

    public NearbyAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public NearbyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nearby_item, parent, false);

        return new NearbyAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NearbyAdapter.MyViewHolder holder, int position) {
        User user = users.get(position);
        Picasso.with(context).load(R.drawable.user_512).transform(new ImageCircleTransformUtil()).into(holder.ivNearbyMainProfilePic);
        holder.tvNearbyUsernameAge.setTypeface(holder.tvNearbyUsernameAge.getTypeface(), Typeface.BOLD);
        holder.tvNearbyUsernameAge.setText(user.getName().concat(", ").concat(String.valueOf(user.getAge())));
        holder.tvNearbyCityCountry.setText(user.getCity().concat(", ").concat(user.getCountry()));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}

