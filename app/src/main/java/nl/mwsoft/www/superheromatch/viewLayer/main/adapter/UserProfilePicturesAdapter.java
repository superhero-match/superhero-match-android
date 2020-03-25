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
package nl.mwsoft.www.superheromatch.viewLayer.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.ProfilePicture;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class UserProfilePicturesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ProfilePicture> profilePictures;
    private Context context;
    private MainActivity mainActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfilePictureItem;
        public ImageView ivProfilePictureEdit;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            mainActivity = (MainActivity) context;
            ivProfilePictureItem = (ImageView) view.findViewById(R.id.ivProfilePictureItem);
            ivProfilePictureEdit = (ImageView) view.findViewById(R.id.ivProfilePictureEdit);
            ivProfilePictureEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TO-DO: choose new picture at specific position
                }
            });
        }
    }

    public UserProfilePicturesAdapter(ArrayList<ProfilePicture> profilePictures, MainActivity mainActivity) {
        this.profilePictures = profilePictures;
        this.mainActivity = mainActivity;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_profile_picture_settings, parent, false);

        return new UserProfilePicturesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProfilePicture userProfilePicture = profilePictures.get(position);
        MyViewHolder myViewHolder = (MyViewHolder)holder;
        Picasso.with(context).load(userProfilePicture.getProfilePicUrl()).into(myViewHolder.ivProfilePictureItem);
    }

    @Override
    public int getItemCount() {
        return profilePictures.size();
    }
}
