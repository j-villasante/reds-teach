package com.berry.blue.reds_teach.share;

import com.berry.blue.reds_teach.RedDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class ShareControl {
    private static ShareControl instance;

    private DatabaseReference reference;
    private ShareI.ExporterI view;

    private ShareControl() {
        this.reference = RedDB.instance().getReference("games");
    }

    static ShareControl instance() {
        if (instance == null) instance = new ShareControl();
        return instance;
    }

    ShareControl setView(ShareI.ExporterI exporter) {
        this.view = exporter;
        return this;
    }

    void getStringifiedGames() {
        this.reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                view.share(processSnapshotToArray(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                view.showMessage(databaseError.getMessage());
            }
        });
    }

    private List<String[]> processSnapshotToArray(DataSnapshot dataSnapshot) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"prueba", "fecha", "palabra", "numero", "cantidad de intentos", "intento 1", "intento 2", "intento 3", "intento 4", "intento 5"});
        int i = 1;
        for (DataSnapshot gameSnap : dataSnapshot.getChildren()) {

            int j = 1;
            for (DataSnapshot guessSnap : gameSnap.child("guesses").getChildren()) {
                String[] game = new String[10];
                game[0] = String.valueOf(i);
                game[1] = (String) gameSnap.child("date").getValue();
                game[2] = (String) guessSnap.child("word").getValue();
                game[3] = String.valueOf(j);

                int k = 0;
                for (int l = 4; l >= 0; l--) {
                    DataSnapshot triSnap = guessSnap.child(String.valueOf(l));
                    if (triSnap.exists()) {
                        DataSnapshot elapsedSnap = triSnap.child("elapsedTime");
                        if (elapsedSnap.exists()) game[k] = String.valueOf(elapsedSnap.getValue());
                        k++;
                    }
                }

                game[4] = String.valueOf(k);

                j++;
                data.add(game);
            }
            i++;
        }
        return data;
    }
}
