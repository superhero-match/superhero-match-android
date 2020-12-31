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
package nl.mwsoft.www.superheromatch.viewLayer.dialog.matchDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
        if (arguments == null) {
            return dialog;
        }

        superhero = arguments.getParcelable(SUPERHERO);
        if (superhero == null) {
            return dialog;
        }

        ivItIsAMatchProfilePic.setClipToOutline(true);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.user_512)
                .error(R.drawable.user_512);

        Glide.with(mainActivity).
                load(superhero.getMainProfilePicUrl()).
                apply(options).
                into(ivItIsAMatchProfilePic);

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