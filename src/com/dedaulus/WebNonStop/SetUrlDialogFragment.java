package com.dedaulus.WebNonStop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created with IntelliJ IDEA.
 * User: dedaulus
 * Date: 20.01.13
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 */
public class SetUrlDialogFragment extends DialogFragment {
    public interface SetUrlDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }

    public interface SetUrlDialogInitializer {
        public String getUrl();
    }

    SetUrlDialogListener listener;
    String url;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (SetUrlDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SetUrlDialogListener");
        }

        try {
            SetUrlDialogInitializer initializer = (SetUrlDialogInitializer)activity;
            url = initializer.getUrl();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SetUrlDialogInitializer");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dlg_set_url, null);
        builder.setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogPositiveClick(SetUrlDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SetUrlDialogFragment.this.getDialog().cancel();
                    }
                });
        EditText editText = (EditText)view.findViewById(R.id.url);
        editText.setText(url);

        return builder.create();
    }
}
