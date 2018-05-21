package com.berry.blue.reds_teach.settings;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.berry.blue.reds_teach.RedDB;
import com.berry.blue.reds_teach.fires.Category;

import java.util.List;


public class DeleteCategoryDialog extends DialogFragment {
    private List<Category> categories;
    private CharSequence[] charSequences;

    public void initialize(List<Category> categories) {
        this.categories = categories;
        this.charSequences = new CharSequence[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            this.charSequences[i] = categories.get(i).name;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Eliminar categoria")
            .setItems(charSequences, (dialog, which) -> {
                RedDB.instance().getReference("categories/" + categories.get(which).key).removeValue();
            });
        return builder.create();
    }
}
