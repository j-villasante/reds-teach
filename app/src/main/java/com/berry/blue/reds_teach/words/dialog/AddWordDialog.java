package com.berry.blue.reds_teach.words.dialog;

public class AddWordDialog extends WordsDialog {
    @Override
    void handlePositiveDialogClicks() {
        wordsControl.addWord(editText.getText().toString());
    }

    @Override
    String getConfirmButtonText() {
        return "Agregar";
    }
}
