package com.example.calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView textSelectedDate, textExercise;
    SharedPreferences sharedPreferences;
    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        textSelectedDate = findViewById(R.id.textSelectedDate);
        textExercise = findViewById(R.id.textExercise);

        sharedPreferences = getSharedPreferences("WorkoutPrefs", MODE_PRIVATE);

        // On date change
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = String.format(Locale.getDefault(), "%02d-%02d-%04d", dayOfMonth, month + 1, year);
            textSelectedDate.setText("Selected Date: " + selectedDate);
            String exercise = sharedPreferences.getString(selectedDate, null);

            if (exercise != null) {
                textExercise.setText("Workout: " + exercise);
            } else {
                textExercise.setText("Workout: -");
            }

            // Prompt to add/edit
            showAddWorkoutDialog(selectedDate);
        });
    }

    private void showAddWorkoutDialog(String date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Workout for " + date);

        final EditText input = new EditText(this);
        input.setHint("Enter workout (e.g., Chest + Triceps)");
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String workout = input.getText().toString();
            if (!workout.isEmpty()) {
                sharedPreferences.edit().putString(date, workout).apply();
                textExercise.setText("Workout: " + workout);
                Toast.makeText(this, "Workout Saved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Workout not saved (empty)", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
