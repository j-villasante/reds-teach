package com.berry.blue.reds_teach.words.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.berry.blue.reds_teach.R;
import com.berry.blue.reds_teach.fires.Word;
import com.berry.blue.reds_teach.words.WordsControl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordsDialog extends DialogFragment{
    @BindView(R.id.new_word_edit_text) EditText editText;

    public enum TYPE {
        ADD_WORD, MODIFY_WORD
    }

    private TYPE currentType;
    private Word word;
    private WordsControl wordsControl;

    public static WordsDialog newInstance() {
        return new WordsDialog();
    }

    public static WordsDialog newInstance(TYPE type, Word word) {
        WordsDialog dialog = new WordsDialog();
        dialog.initialize(type, word);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();
        View view = activity.getLayoutInflater().inflate(R.layout.new_word_dialog, null);
        ButterKnife.bind(this, view);

        if (word != null) editText.setText(word.name);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view)
                .setPositiveButton(getConfirmText(), (dialog, which) -> this.handlePositiveDialogClicks())
                .setNegativeButton("Cancelar", null);
        return builder.create();
    }

    public void handlePositiveDialogClicks() {
        switch (currentType) {
            case ADD_WORD:
                wordsControl.addWord(editText.getText().toString());
                break;
            case MODIFY_WORD:
                if (word != null) wordsControl.modifyWord(editText.getText().toString(), word.key);
                break;
            default:
                wordsControl.addWord(editText.getText().toString());
        }
    }

    private void initialize(TYPE type, Word word) {
        this.currentType = type;
        this.word = word;
        this.wordsControl = WordsControl.instance();
    }

    private String getConfirmText(){
        switch (currentType) {
            case ADD_WORD:
                return "Agregar";
            case MODIFY_WORD:
                return "Modificar";
            default:
                return "Agregar";
        }
    }
}
