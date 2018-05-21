package com.berry.blue.reds_teach.settings;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.berry.blue.reds_teach.R;
import com.berry.blue.reds_teach.RedDB;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCategoryDialog extends DialogFragment {
    @BindView(R.id.new_word_edit_text) EditText editText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();
        View view = activity.getLayoutInflater().inflate(R.layout.word_dialog, null);
        ButterKnife.bind(this, view);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view)
                .setTitle("Agregar categoria")
                .setPositiveButton("Agregar", (dialog, which) -> this.addCategory())
                .setNegativeButton("Cancelar", null);
        return builder.create();
    }

    private void addCategory() {
        RedDB.instance().getReference("categories").push().setValue(editText.getText().toString());
    }
}
