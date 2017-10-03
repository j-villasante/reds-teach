/*
 * Most of the methods of this class were copied from the NfcLibrary project
 * and its permission notice is thereby included.
 * NdefWriteImpl.java
 * NfcLibrary project.
 *
 * Created by : Daneo van Overloop - 17/6/2014.
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 AppFoundry. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */

package com.berry.blue.reds_teach.nfcUtils;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.util.Log;

import com.berry.blue.reds_teach.exceptions.InsufficientCapacityException;
import com.berry.blue.reds_teach.exceptions.ReadOnlyTagException;
import com.berry.blue.reds_teach.exceptions.TagNotPresentException;
import com.berry.blue.reds_teach.interfaces.activities.Main;

import java.io.IOException;

public class TagControl {
    private String key;
    private Main view;
    private String TAG = getClass().getSimpleName();

    private TagControl() {}

    private static TagControl instance;
    public static TagControl instance() {
        if (instance == null) instance = new TagControl();
        return instance;
    }

    public TagControl setView(Main view) {
        this.view = view;
        return this;
    }

    public TagControl setKey(String key) {
        this.key = key;
        return this;
    }

    private boolean writeToNdef(NdefMessage message, Ndef ndef) throws ReadOnlyTagException, InsufficientCapacityException, FormatException {
        if (message == null || ndef == null) {
            return false;
        }

        int size = message.getByteArrayLength();

        try {
            ndef.connect();
            if (!ndef.isWritable()) {
                throw new ReadOnlyTagException();
            }
            if (ndef.getMaxSize() < size) {
                throw new InsufficientCapacityException();
            }
            ndef.writeNdefMessage(message);
            Log.i(TAG, "Wrote using ndef.");
            return true;
        } catch (IOException e) {
            Log.w(TAG, "IOException occurred", e);
        } finally {
            if (ndef.isConnected()) {
                try {
                    ndef.close();
                } catch (IOException e) {
                    Log.v(TAG, "IOException occurred at closing.", e);
                }
            }
        }
        return false;
    }

    private boolean writeToNdefFormatable(NdefMessage message, NdefFormatable ndefFormatable) throws FormatException {
        if (ndefFormatable == null || message == null) {
            return false;
        }

        try {
            ndefFormatable.connect();
            ndefFormatable.format(message);
            Log.i(TAG, "Wrote using ndef formatable.");
            return true;
        } catch (TagLostException e) {
            Log.d(TAG, "We lost our tag !", e);
        } catch (IOException e) {
            Log.w(TAG, "IOException occured", e);
        } catch (FormatException e) {
            Log.w(TAG, "Message is malformed occurred", e);
            throw e;
        } finally {
            if (ndefFormatable.isConnected()) {
                try {
                    ndefFormatable.close();
                } catch (IOException e) {
                    Log.w(TAG, "IOException occurred at closing.", e);
                }
            }
        }

        return false;
    }

    private Tag retrieveTagFromIntent(Intent i) throws TagNotPresentException {
        Tag tag = i.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            throw new TagNotPresentException();
        }
        return tag;
    }

    public void writeKeyToTagFromIntent(Intent intent) {
        if (this.key == null){
            Log.e(TAG, "Key has not been set.");
            return;
        }
        try {
            final NdefMessage ndefMessage = new NdefMessage(NdefRecord.createTextRecord("en", this.key));
            final Tag tag = retrieveTagFromIntent(intent);

            Ndef ndef = Ndef.get(tag);
            NdefFormatable formatable = NdefFormatable.get(tag);
            boolean result = writeToNdef(ndefMessage, ndef) || writeToNdefFormatable(ndefMessage, formatable);
            view.dismissDialog();
            if (result) view.showMessage("Se asignó la palabra correctamente al tag");
        } catch (FormatException | ReadOnlyTagException | TagNotPresentException | InsufficientCapacityException e) {
            e.printStackTrace();
        }
    }
}
