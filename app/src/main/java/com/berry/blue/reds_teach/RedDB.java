package com.berry.blue.reds_teach;

import com.google.firebase.database.FirebaseDatabase;

public class RedDB {
    private static FirebaseDatabase database;

    private static void setup() {
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        database.getReference("words").keepSynced(true);
    }

    public static FirebaseDatabase instance() {
        if (database == null) setup();
        return database;
    }
}

