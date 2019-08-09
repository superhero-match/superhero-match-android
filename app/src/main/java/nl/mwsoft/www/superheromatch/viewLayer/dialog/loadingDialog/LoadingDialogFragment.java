package nl.mwsoft.www.superheromatch.viewLayer.dialog.loadingDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.fragment.app.DialogFragment;

import nl.mwsoft.www.superheromatch.R;

public class LoadingDialogFragment  extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        return dialog;
    }
}
