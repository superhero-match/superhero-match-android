package nl.mwsoft.www.superheromatch.viewLayer.main.fragment.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.event.SuperheroProfilePicEvent;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.adapter.UserProfilePicturesAdapter;

public class UserPicturesFragment extends Fragment {

    @BindView(R.id.rvUserProfilePictures)
    RecyclerView rvUserProfilePictures;
    private UserProfilePicturesAdapter userProfilePicturesAdapter;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private User user;
    private static final String USER = "user";

    public static UserPicturesFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(USER, user);
        UserPicturesFragment fragment = new UserPicturesFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_profile_pictures, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if(arguments != null){
            user = arguments.getParcelable(USER);
            loadUserProfilePictures(user);
        }else{
            Toast.makeText(mainActivity, "Something went wrong!", Toast.LENGTH_LONG).show();
        }
    }

    private void loadUserProfilePictures(User user) {
        userProfilePicturesAdapter = new UserProfilePicturesAdapter(user, mainActivity);
        StaggeredGridLayoutManager mLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvUserProfilePictures.setLayoutManager(mLayoutManager);
        rvUserProfilePictures.setItemAnimator(new DefaultItemAnimator());
        rvUserProfilePictures.setAdapter(userProfilePicturesAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SuperheroProfilePicEvent event) {
        Toast.makeText(mainActivity, event.getProfilePicURI().toString(), Toast.LENGTH_SHORT).show();
    }
}