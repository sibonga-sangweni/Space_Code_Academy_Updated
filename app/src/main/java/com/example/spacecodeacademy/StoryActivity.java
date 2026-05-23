package com.example.spacecodeacademy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        TextView storyText = findViewById(R.id.storyText);
        String story = "The galaxy's code systems have failed...\n\n" +
                "The Android Universe is in chaos! Apps crash, layouts break, " +
                "and data disappears into the void.\n\n" +
                "You are the final Cadet capable of restoring programming knowledge. " +
                "Travel across planets and rebuild the digital universe.\n\n" +
                "Your mission: Master Android Development and save the galaxy!";

        storyText.setText(story);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(StoryActivity.this, HomeActivity.class));
            finish();
        }, 5000);
    }
}