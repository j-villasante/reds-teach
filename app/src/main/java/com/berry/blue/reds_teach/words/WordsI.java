package com.berry.blue.reds_teach.words;

import com.berry.blue.reds_teach.fires.Word;

import java.util.List;

public interface WordsI {
    interface Fragment {
        void onDataObtained(List<Word> words);
        void onEditItemClick(Word word);
        void showMessage(String errorMessage);
    }
}
