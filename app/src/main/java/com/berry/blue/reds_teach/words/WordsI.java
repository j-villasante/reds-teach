package com.berry.blue.reds_teach.words;

import com.berry.blue.reds_teach.fires.Word;

import java.util.List;

public interface WordsI {
    interface Fragment {
        void onDataObtained(List<Word> words);
        void onCategoryData(List<String> categories);
        void onEditItemClick(Word word);
        void onDeleteItemClick(Word word);
        void onNfcItemClick(String key);
        void onChangeCategoryClick(String key);
        void showMessage(String errorMessage);
    }
}
