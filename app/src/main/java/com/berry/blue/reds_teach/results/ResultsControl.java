package com.berry.blue.reds_teach.results;

import com.berry.blue.reds_teach.RedDB;
import com.berry.blue.reds_teach.fires.Game;
import com.berry.blue.reds_teach.fires.Guess;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ResultsControl {
    private static ResultsControl instance;

    private DatabaseReference reference;

    private ResultsControl() {
        this.reference = RedDB.instance().getReference("games");
    }

    static ResultsControl instance() {
        if (instance == null) instance = new ResultsControl();
        return instance;
    }

    void getResultsGames(Results.Fragment view) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snaps = dataSnapshot.getChildren();
                List<Game> games = new ArrayList<>();
                for (DataSnapshot snap : snaps) {
                    Game game = snap.getValue(Game.class);
                    game.setKey(snap.getKey());
                    if (game != null) games.add(game);
                }
                Collections.reverse(games);
                view.onDataObtained(games);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                view.showMessage(databaseError.getMessage());
            }
        });
    }

    void getGameDetail(String key, Results.Fragment view) {
        reference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Game game = dataSnapshot.getValue(Game.class);
                List<Guess> guesses = new ArrayList<>();
                DataSnapshot guessesSnap = dataSnapshot.child("guesses");

                for (DataSnapshot guessSnap : guessesSnap.getChildren()) {
                    Guess guess = new Guess((String) guessSnap.child("word").getValue());
                    int i = 0;
                    DataSnapshot triSnap = guessSnap.child("0");
                    while (triSnap.exists()) {
                        DataSnapshot elapsedSnap = triSnap.child("elapsedTime");
                        if (elapsedSnap.exists()) guess.addTry((long) elapsedSnap.getValue());
                        i++;
                        triSnap = guessSnap.child(String.valueOf(i));
                    }
                    guesses.add(guess);
                }
                view.showDetail(game, guesses);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                view.showMessage(databaseError.getMessage());
            }
        });
    }
}
