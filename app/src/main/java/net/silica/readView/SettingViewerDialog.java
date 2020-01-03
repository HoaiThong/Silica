package net.silica.readView;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import net.silica.R;

public class SettingViewerDialog extends DialogFragment {
    private Activity mActivity;
    private FragmentManager fragmentManager;
    private View view;

    public SettingViewerDialog(Activity mActivity, FragmentManager fragmentManager) {
        this.mActivity = mActivity;
        this.fragmentManager=fragmentManager;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.frag_manga, null);
        builder.setPositiveButton(getString(R.string.xac_nhan), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        builder.setView(view);
        return builder.create();
    }

    public void show() {
        this.show(fragmentManager, "dialog");
        this.setCancelable(false);
    }
}