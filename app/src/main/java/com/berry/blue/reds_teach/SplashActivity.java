package com.berry.blue.reds_teach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        updateUI(mAuth.getCurrentUser());
    }

    private void updateUI(FirebaseUser user) {
        Intent intent;
        if (user == null) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        this.finish();
    }
}
