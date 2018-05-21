package com.berry.blue.reds_teach.words.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.berry.blue.reds_teach.fires.Category;
import com.berry.blue.reds_teach.words.WordsControl;

import java.util.List;

public class ModifyCategoryDialog extends DialogFragment {
    private List<String> categories;
    private CharSequence[] charSequences;
    private String key;

    public void initialize(List<String> categories, String key) {
        this.categories = categories;
        this.key = key;
        this.charSequences = new CharSequence[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            this.charSequences[i] = categories.get(i);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Modificar categoria")
                .setItems(charSequences, (dialog, which) -> {
                    WordsControl.instance().modifyCategory(key, categories.get(which));
                });
        return builder.create();
    }
}
