package nl.mwsoft.www.superheromatch.viewLayer.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class ImageDetailFragment extends Fragment {

    @BindView(R.id.ivImageDetail)
    ImageView ivImageDetail;
    private Unbinder unbinder;
    private MainActivity mainActivity;
    private String imageUrl;
    private static final String IMAGE_URL = "image_url";

    public static ImageDetailFragment newInstance(String imageUrl) {
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, imageUrl);
        ImageDetailFragment fragment = new ImageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity.setToolbarStatusBarColorBlack();
        Bundle arguments = getArguments();
        if(arguments != null){
            imageUrl = arguments.getString(IMAGE_URL);
            if(imageUrl == null){
                Toast.makeText(mainActivity,"Something went wrong!",Toast.LENGTH_SHORT).show();
            }else {
                Picasso.with(mainActivity).load(R.drawable.user_512).into(ivImageDetail);
            }
        }else{
            Toast.makeText(mainActivity,"Something went wrong!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivity.setToolbarStatusBarColorPrimary();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }
}