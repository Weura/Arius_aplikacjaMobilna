package com.example.pizzeria;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Use Handler to delay the transition to the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent to start the main activity (LoginActivity or MainActivity)
                Intent intent = new Intent(WelcomeActivity.this, NavigationLoggedUser.class);
                startActivity(intent);
                finish(); // Finish the welcome activity so it's removed from the stack
            }
        }, 500); // 3000ms (3 seconds) delay for the splash screen
    }
}
