package com.berry.blue.reds_teach.words.dialog;


public class DeleteWordDialog extends WordsDialog  {
    @Override
    void handlePositiveDialogClicks() {
        if (word != null) wordsControl.remove(word.key);
    }

    @Override
    protected void onSetViewContents() {
        editText.setText(word.name);
        editText.setFocusable(false);
    }

    @Override
    String getTitle() {
        return "Eliminar palabra";
    }

    @Override
    String getConfirmButtonText() {
        return "Eliminar";
    }
}
