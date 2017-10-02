package com.berry.blue.reds_teach.words.dialog;

public class ModifyWordDialog extends WordsDialog  {
    @Override
    void handlePositiveDialogClicks() {
        if (word != null) wordsControl.modify(editText.getText().toString(), word.key);
    }

    @Override
    protected void onSetViewContents() {
        editText.setText(word.name);
    }

    @Override
    String getTitle() {
        return "Modificar palabra";
    }

    @Override
    String getConfirmButtonText() {
        return "Modificar";
    }
}
