/*
  Copyright (C) 2019 - 2021 MWSOFT
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
package nl.mwsoft.www.superheromatch.viewLayer.register.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.event.SuperheroProfilePicEvent;
import nl.mwsoft.www.superheromatch.viewLayer.register.activity.RegisterActivity;

public class SuperheroProfilePic extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.ivSuperHeroProfilePic)
    ImageView ivSuperHeroProfilePic;
    private RegisterActivity registerActivity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_superhero_profile_pic, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.ivSuperHeroProfilePic)
    public void profilePicListener(){
        if(registerActivity.accessFilesPermissionIsGranted()){
            registerActivity.showProfilePicChoice();
        }

        registerActivity.requestPermissionWriteExternalStorage();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        registerActivity = (RegisterActivity)context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SuperheroProfilePicEvent event) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.user_512)
                .circleCrop()
                .error(R.drawable.user_512);

        Glide.with(registerActivity).
                load(event.getProfilePicURI()).
                apply(options).
                into(ivSuperHeroProfilePic);
    }
}