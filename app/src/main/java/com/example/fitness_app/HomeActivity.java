package com.example.fitness_app;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the email passed from MainActivity
        String userEmail = getIntent().getStringExtra("userEmail");

        // Create a TextView to display the welcome message
        TextView tv = new TextView(this);
        if (userEmail != null) {
            tv.setText("Welcome to the Fitness App, " + userEmail + "!");
        } else {
            tv.setText("Welcome to the Fitness App!");
        }

        tv.setTextSize(24);
        setContentView(tv); // Set the layout to show the welcome message
    }
}