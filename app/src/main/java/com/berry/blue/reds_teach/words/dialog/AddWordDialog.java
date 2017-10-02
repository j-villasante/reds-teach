package com.berry.blue.reds_teach.words.dialog;

public class AddWordDialog extends WordsDialog {
    @Override
    String getTitle() {
        return "Agregar palabra";
    }

    @Override
    void handlePositiveDialogClicks() {
        wordsControl.add(editText.getText().toString());
    }

    @Override
    String getConfirmButtonText() {
        return "Agregar";
    }
}
