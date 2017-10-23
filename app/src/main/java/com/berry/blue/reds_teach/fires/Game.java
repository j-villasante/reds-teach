package com.berry.blue.reds_teach.fires;

import android.nfc.FormatException;

import com.google.firebase.database.IgnoreExtraProperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Game {
    public String key;
    public String date;
    public int type;
    private List<Guess> guesses;

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.ENGLISH);

    public Game() {}

    private Date getDate() {
        Date date = null;
        try {
            date = format.parse(this.date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getSimpleDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.ENGLISH);
        return dateFormat.format(getDate());
    }

    public Game setKey(String key) {
        this.key = key;
        return this;
    }
}
