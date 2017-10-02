package com.berry.blue.reds_teach.words.dialog;

public class ModifyWordDialog extends WordsDialog  {
    @Override
    void handlePositiveDialogClicks() {
        if (word != null) wordsControl.modifyWord(editText.getText().toString(), word.key);
    }

    @Override
    protected void setEditTextContent() {
        editText.setText(word.name);
    }

    @Override
    String getConfirmButtonText() {
        return null;
    }
}
