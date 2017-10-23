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

    public static WordsControl instance() {
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
                    Word word = wordSnap.getValue(Word.class);
                    if (word != null) word.key = wordSnap.getKey();
                    words.add(word);
                }
                view.onDataObtained(words);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                view.showMessage(databaseError.getMessage());
            }
        });
    }

    public void add(String word) {
        this.reference
                .push()
                .setValue(new Word(word))
                .addOnCompleteListener(task -> view.showMessage("La palabra se agregó correctamente."));
    }

    public void modify(String word, String key) {
        this.reference
                .child(key)
                .child("name")
                .setValue(word)
                .addOnCompleteListener(task -> view.showMessage("La palabra se modificó correctamente."));
    }

    public void remove(String key) {
        this.reference
                .child(key)
                .removeValue()
                .addOnCompleteListener(task -> view.showMessage("La palabra se eliminó correctamente."));
    }
}
