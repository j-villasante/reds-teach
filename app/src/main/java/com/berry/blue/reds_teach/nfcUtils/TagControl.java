package com.berry.blue.reds_teach.nfcUtils;

public class TagControl {
    private String key;

    private TagControl() {}

    private static TagControl instance;
    public static TagControl instance() {
        if (instance == null) instance = new TagControl();
        return instance;
    }

    public TagControl setKey(String key) {
        this.key = key;
        return this;
    }

    public void writeToTag() {

    }
}
