package nl.mwsoft.www.superheromatch.viewLayer.dialog.matchDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.model.Superhero;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class MatchDialog extends DialogFragment {

    private ImageView ivItIsAMatchProfilePic;
    private ImageView ivItIsAMatchMessage;
    private ImageView ivItIsAMatchOk;
    private TextView tvItIsAMatch;
    private MainActivity mainActivity;
    private static final String SUPERHERO = "superhero";
    private Superhero superhero;

    public static MatchDialog newInstance(Superhero superhero) {
        Bundle args = new Bundle();
        args.putParcelable(SUPERHERO, superhero);
        MatchDialog fragment = new MatchDialog();
        fragment.setArguments(args);

        return fragment;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return  inflater.inflate(R.layout.fragment_it_is_a_match, container, false);
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_it_is_a_match, null);

        ivItIsAMatchProfilePic = (ImageView) view.findViewById(R.id.ivItIsAMatchProfilePic);

        ivItIsAMatchMessage = (ImageView) view.findViewById(R.id.ivItIsAMatchMessage);
        ivItIsAMatchMessage.setOnClickListener(onClickItIsAMatchMessage);

        ivItIsAMatchOk = (ImageView) view.findViewById(R.id.ivItIsAMatchOk);
        ivItIsAMatchOk.setOnClickListener(onClickItIsAMatchOk);

        tvItIsAMatch = (TextView) view.findViewById(R.id.tvItIsAMatch);


        dialog.setContentView(view);

        Bundle arguments = getArguments();
        if (arguments != null) {
            superhero = arguments.getParcelable(SUPERHERO);
        } else {
            return dialog;
        }

        ivItIsAMatchProfilePic.setClipToOutline(true);

        switch (superhero.getMainProfilePicUrl()) {
            case "test":
                Glide.with(mainActivity)
                        .load(R.drawable.test)
                        .into(ivItIsAMatchProfilePic);
                break;
            case "test1":
                Glide.with(mainActivity)
                        .load(R.drawable.test1)
                        .into(ivItIsAMatchProfilePic);
                break;
            case "test2":
                Glide.with(mainActivity)
                        .load(R.drawable.test2)
                        .into(ivItIsAMatchProfilePic);
                break;
            case "test3":
                Glide.with(mainActivity)
                        .load(R.drawable.test3)
                        .into(ivItIsAMatchProfilePic);
                break;
            case "test4":
                Glide.with(mainActivity)
                        .load(R.drawable.test4)
                        .into(ivItIsAMatchProfilePic);
                break;
            case "test5":
                Glide.with(mainActivity)
                        .load(R.drawable.test5)
                        .into(ivItIsAMatchProfilePic);
                break;
            case "test6":
                Glide.with(mainActivity)
                        .load(R.drawable.test6)
                        .into(ivItIsAMatchProfilePic);
                break;
            case "test7":
                Glide.with(mainActivity)
                        .load(R.drawable.test7)
                        .into(ivItIsAMatchProfilePic);
                break;
            case "test8":
                Glide.with(mainActivity)
                        .load(R.drawable.test8)
                        .into(ivItIsAMatchProfilePic);
                break;
            case "test9":
                Glide.with(mainActivity)
                        .load(R.drawable.test9)
                        .into(ivItIsAMatchProfilePic);
                break;
            case "test10":
                Glide.with(mainActivity)
                        .load(R.drawable.test10)
                        .into(ivItIsAMatchProfilePic);
                break;
            case "test11":
                Glide.with(mainActivity)
                        .load(R.drawable.test11)
                        .into(ivItIsAMatchProfilePic);
                break;
            case "test12":
                Glide.with(mainActivity)
                        .load(R.drawable.test12)
                        .into(ivItIsAMatchProfilePic);
                break;
        }

        tvItIsAMatch.bringToFront();
        tvItIsAMatch.setTypeface(tvItIsAMatch.getTypeface(), Typeface.BOLD);

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    public View.OnClickListener onClickItIsAMatchMessage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mainActivity.closeMatchDialog();
            mainActivity.loadNextSuggestion();
            mainActivity.openChatsFragment();
        }
    };

    public View.OnClickListener onClickItIsAMatchOk = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mainActivity.closeMatchDialog();
            mainActivity.loadNextSuggestion();
        }
    };
}