package nl.mwsoft.www.superheromatch.viewLayer.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.helper.image.ImageCircleTransformUtil;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.fragment.ImageDetailFragment;

public class UserProfilePicturesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private User user;
    private Context context;
    private MainActivity mainActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivUserProfilePictureItem;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            mainActivity = (MainActivity) context;
            ivUserProfilePictureItem = (ImageView) view.findViewById(R.id.ivUserProfilePictureItem);
            ivUserProfilePictureItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.loadBackStackFragment(ImageDetailFragment.newInstance(user.getProfilePicsUrls().get(getAdapterPosition())));
                }
            });
        }
    }

    public class MyNewViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivUserProfilePictureNewItem;

        public MyNewViewHolder(View view) {
            super(view);
            context = view.getContext();
            mainActivity = (MainActivity) context;
            ivUserProfilePictureNewItem = (ImageView) view.findViewById(R.id.ivUserProfilePictureNewItem);
            ivUserProfilePictureNewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mainActivity.accessFilesPermissionIsGranted()){
                        mainActivity.showProfilePicChoice();
                    }else{
                        mainActivity.requestPermissionWriteExternalStorage();
                    }
                }
            });
        }
    }

    public UserProfilePicturesAdapter(User user, MainActivity mainActivity) {
        this.user = user;
        this.mainActivity = mainActivity;
    }

    @Override
    public int getItemViewType(int position) {
        if(user.getUserID() == mainActivity.getUserId()){
            if(position == 0){
                return 1;
            }else{
                return 2;
            }
        }else {
            return 2;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == 2){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_profile_pictures_item, parent, false);
            return new UserProfilePicturesAdapter.MyViewHolder(itemView);
        }else{
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_profile_pictures_new_item, parent, false);
            return new UserProfilePicturesAdapter.MyNewViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String userProfilePictureUrl = user.getProfilePicsUrls().get(position);
        if(holder.getItemViewType() == 2){
            MyViewHolder myViewHolder = (MyViewHolder)holder;
            Picasso.with(context).load(R.drawable.user_512).into(myViewHolder.ivUserProfilePictureItem);
        }else{
            MyNewViewHolder myNewViewHolder = (MyNewViewHolder)holder;
            Picasso.with(context).load(R.drawable.plus_256).into(myNewViewHolder.ivUserProfilePictureNewItem);
        }
    }

    @Override
    public int getItemCount() {
        return user.getProfilePicsUrls().size();
    }
}
