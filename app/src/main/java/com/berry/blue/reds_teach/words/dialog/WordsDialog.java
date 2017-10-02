package com.berry.blue.reds_teach.words.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.berry.blue.reds_teach.R;
import com.berry.blue.reds_teach.fires.Word;
import com.berry.blue.reds_teach.words.WordsControl;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class WordsDialog extends DialogFragment{
    @BindView(R.id.new_word_edit_text) EditText editText;

    public enum TYPE {
        ADD_WORD, MODIFY_WORD, DELETE_WORD
    }

    protected Word word;
    protected WordsControl wordsControl;

    public static WordsDialog newInstance() {
        WordsDialog dialog = new AddWordDialog();
        dialog.initialize(null);
        return dialog;
    }

    public static WordsDialog newInstance(TYPE type, Word word) {
        WordsDialog dialog;
        switch (type) {
            case MODIFY_WORD:
                dialog = new ModifyWordDialog();
                break;
            case ADD_WORD:
                dialog = new AddWordDialog();
                break;
            case DELETE_WORD:
                dialog = new DeleteWordDialog();
                break;
            default:
                dialog = new AddWordDialog();
        }
        dialog.initialize(word);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();
        View view = activity.getLayoutInflater().inflate(R.layout.word_dialog, null);
        ButterKnife.bind(this, view);

        if (word != null) onSetViewContents();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view)
                .setTitle(getTitle())
                .setPositiveButton(getConfirmButtonText(), (dialog, which) -> this.handlePositiveDialogClicks())
                .setNegativeButton("Cancelar", null);
        return builder.create();
    }

    private void initialize(Word word) {
        this.word = word;
        this.wordsControl = WordsControl.instance();
    }

    protected void onSetViewContents() {
        editText.setText("");
    }

    abstract String getTitle();

    abstract void handlePositiveDialogClicks();

    abstract String getConfirmButtonText();
}
