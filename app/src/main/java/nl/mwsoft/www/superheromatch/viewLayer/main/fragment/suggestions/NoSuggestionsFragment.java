package nl.mwsoft.www.superheromatch.viewLayer.main.fragment.suggestions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import nl.mwsoft.www.superheromatch.R;

public class NoSuggestionsFragment extends Fragment {

    public static NoSuggestionsFragment newInstance() {
        Bundle args = new Bundle();
        
        NoSuggestionsFragment fragment = new NoSuggestionsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_suggestion, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
