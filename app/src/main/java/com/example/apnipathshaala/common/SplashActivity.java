package com.example.apnipathshaala.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.apnipathshaala.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mFirebaseAuth = FirebaseAuth.getInstance();
        int SPLASH_SCREEN_TIME_OUT = 5000;
        new Handler().postDelayed(() -> { //This function will handle auto-login, if the user is logged-in before.
            mAuthStateListener = firebaseAuth -> {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();  //If the user is already Logged In, no need to sign in again.
                if (mFirebaseUser != null && mFirebaseUser.isEmailVerified()) {
                    //Toast.makeText(getApplicationContext(), "Welcome back", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SplashActivity.this, Homepage.class); //Start the application directly to the Dashboard.

                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                    finish(); //Finish the activity
                } else {
                    // User is signed out
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
            };
            mFirebaseAuth.addAuthStateListener(mAuthStateListener); //This function will trigger the AuthState Listener.
        }, SPLASH_SCREEN_TIME_OUT);
    }

    //This function is super important.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFirebaseAuth != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}