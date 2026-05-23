package com.example.spacecodeacademy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView scoreText, xpText, totalXPText;
    private Button backHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int score = getIntent().getIntExtra("score", 0);

        scoreText = findViewById(R.id.scoreText);
        xpText = findViewById(R.id.xpText);
        totalXPText = findViewById(R.id.totalXPText);
        backHomeBtn = findViewById(R.id.backHomeBtn);

        SharedPreferences prefs = getSharedPreferences("xp", MODE_PRIVATE);
        int totalXP = prefs.getInt("totalXP", 0);

        scoreText.setText("Score: " + score + "/" + (score > 0 ? "30" : "0"));
        xpText.setText("+" + score + " XP Earned!");
        totalXPText.setText("Total XP: " + totalXP);

        backHomeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}