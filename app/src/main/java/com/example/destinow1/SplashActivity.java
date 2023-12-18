package com.example.destinow1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        // Delay for 3 seconds (3000 milliseconds)
        int splashScreenDuration = 3000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create an Intent to start the LoginActivity
                Intent loginIntent = new Intent(SplashActivity.this, Login.class);
                startActivity(loginIntent);

                // Do not finish the SplashActivity here
            }
        }, splashScreenDuration);
    }
}
