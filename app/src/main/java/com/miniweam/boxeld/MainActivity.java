package com.miniweam.boxeld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 3000;

    // Initialize Firebase Auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if user is signed in (non-null) and update UI accordingly.
                FirebaseUser currentUser = mAuth.getCurrentUser();

                //If user is authenticated, send user to DashboardActivity, else,
                // send user to FirstActivity to authenticate first.
                if (currentUser != null) {
                    startActivity(new Intent(MainActivity.this, MainActivity2.class));
                } else {
                    startActivity(new Intent(MainActivity.this, FirstActivity.class));
                }
                finish();
            }
        }, SPLASH_SCREEN);
    }
}



