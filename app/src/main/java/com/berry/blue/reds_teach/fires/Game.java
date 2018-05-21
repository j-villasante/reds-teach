package com.berry.blue.reds_teach.fires;

public class Game {
    public String key;
    public String date;
    public long type;

    public Game() {}

    public String getSimpleDate() {
        return date;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        if (this.type == 1) {
            return "Buscar";
        } else if (this.type == 2) {
            return "Aprender";
        } else {
            return "Unknown";
        }
    }
}