package nl.mwsoft.www.superheromatch.viewLayer.main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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

public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.MyViewHolder> {

    private List<User> users;
    private Context context;
    private MainActivity mainActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivSuggestionsMainProfilePic;
        public TextView tvSuggestionsUsernameAge;
        public TextView tvSuggestionsCityCountry;
        public RelativeLayout rlSuggestionsItem;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            mainActivity = (MainActivity)context;
            ivSuggestionsMainProfilePic = (ImageView) view.findViewById(R.id.ivSuggestionsMainProfilePic);
            tvSuggestionsUsernameAge = (TextView) view.findViewById(R.id.tvSuggestionsUsernameAge);
            tvSuggestionsCityCountry = (TextView) view.findViewById(R.id.tvSuggestionsCityCountry);
            rlSuggestionsItem = (RelativeLayout) view.findViewById(R.id.rlSuggestionsItem);
            rlSuggestionsItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.loadSuggestionUserProfileFragment(users.get(getAdapterPosition()));
                }
            });

            ivSuggestionsMainProfilePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.loadSuggestionUserProfileFragment(users.get(getAdapterPosition()));
                }
            });
        }
    }

    public SuggestionsAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public SuggestionsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggestions_item, parent, false);

        return new SuggestionsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SuggestionsAdapter.MyViewHolder holder, int position) {
        User user = users.get(position);
        Picasso.with(context).load(R.drawable.user_512).transform(new ImageCircleTransformUtil()).into(holder.ivSuggestionsMainProfilePic);
        holder.tvSuggestionsUsernameAge.setTypeface(holder.tvSuggestionsUsernameAge.getTypeface(), Typeface.BOLD);
        holder.tvSuggestionsUsernameAge.setText(user.getName().concat(", ").concat(String.valueOf(user.getAge())));
        holder.tvSuggestionsCityCountry.setText(user.getCity().concat(", ").concat(user.getCountry()));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}

