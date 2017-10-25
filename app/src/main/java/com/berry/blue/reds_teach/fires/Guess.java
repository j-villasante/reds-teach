package com.berry.blue.reds_teach.fires;

import java.util.ArrayList;
import java.util.List;

public class Guess {
    private List<Long> tries;
    public String word;

    public Guess() {}

    public Guess(String word) {
        this.word = word;
        this.tries = new ArrayList<>();
    }

    public void addTry(long time) {
        this.tries.add(time);
    }

    public String stringifyTries() {
        StringBuilder res = new StringBuilder();
        int i = 0;
        for (long tri : tries){
            if (i == 0) res.append(String.valueOf(tri));
            else res.append(" - ").append(String.valueOf(tri));
            i++;
        }
        return res.toString();
    }
}
