package com.berry.blue.reds_teach.fires;

import java.util.List;

public class Guess {
    List<Integer> tries;
    String word;

    public String stringifyTries() {
        StringBuilder res = new StringBuilder();
        int i = 0;
        for (int tri : tries){
            if (i == 0) res.append(String.valueOf(tri));
            else res.append(" - ").append(String.valueOf(tri));
            i++;
        }
        return res.toString();
    }
}
