package com.berry.blue.reds_teach.fires;

import com.google.firebase.database.Exclude;

public class Word {
    public String name;
    public String category;
    @Exclude
    public String key;

    public Word() {}

    public Word(String name) {
        this.name = name;
    }
}
