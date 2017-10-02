package com.berry.blue.reds_teach.interfaces.activities;

import android.support.v4.app.DialogFragment;

public interface Main extends GenericActivity{
    void showDialog(DialogFragment dialog);
    void onDismissNfcDialog();
    void onOpenNfcDialog();
}
