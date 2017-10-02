package com.berry.blue.reds_teach.words.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.berry.blue.reds_teach.R;
import com.berry.blue.reds_teach.interfaces.activities.Main;

public class NfcDialog extends DialogFragment{
    private Main parent;

    public NfcDialog setParent(Main parent) {
        this.parent = parent;
        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("NFC")
                .setMessage("Acerque el dispositivo al tag nfc para realizar la escritura.")
                .setNegativeButton(R.string.cancelar, null);
        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        parent.onDismissNfcDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        parent.onOpenNfcDialog();
    }
}
