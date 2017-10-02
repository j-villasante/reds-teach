package com.berry.blue.reds_teach.words.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.berry.blue.reds_teach.R;

public class AddWordDialog extends DialogFragment{
    private EditText editText;

    public static AddWordDialog instance() {
        return new AddWordDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_word_dialog, null);
        editText = view.findViewById(R.id.new_word_edit_text);
        builder.setView(view)
                .setPositiveButton("Agregar", this::addWord)
                .setNegativeButton("Cancelar", (dialog, id) -> dialog.cancel());
        return builder.create();
    }

    public void addWord(DialogInterface dialog, int id) {
        Log.i("Test", editText.getText().toString());
    }
}
