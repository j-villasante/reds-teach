package com.berry.blue.reds_teach.words;

import com.berry.blue.reds_teach.RedDB;
import com.berry.blue.reds_teach.fires.Word;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WordsControl {
    private static WordsControl instance;

    private DatabaseReference reference;
    private WordsI.Fragment view;

    static WordsControl instance() {
        if (instance == null) instance = new WordsControl();
        return instance;
    }

    public WordsControl setView(WordsI.Fragment view) {
        this.view = view;
        return this;
    }

    private WordsControl() {
        this.reference = RedDB.instance().getReference("words");
    }

    void getWords() {
        this.reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> wordSnaps = dataSnapshot.getChildren();
                List<Word> words = new ArrayList<>();
                for (DataSnapshot wordSnap: wordSnaps) {
                    words.add(wordSnap.getValue(Word.class));
                }
                view.onDataObtained(words);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                view.onError(databaseError.getMessage());
            }
        });
    }

    void addWord(String word) {
        this.reference.push().setValue(new Word(word))
                .addOnCompleteListener(task -> {

                });
    }
}
