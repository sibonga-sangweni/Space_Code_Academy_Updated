package com.example.spacecodeacademy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecodeacademy.database.UserDatabaseHelper;
import com.example.spacecodeacademy.utils.SoundManager;

public class ResultActivity extends AppCompatActivity {

    private TextView scoreText, xpText, totalXPText, congratsMessage, levelBadge;
    private Button backHomeBtn;
    private UserDatabaseHelper dbHelper;
    private long databaseUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int score = getIntent().getIntExtra("score", 0);
        SoundManager.playXPGain(this);

        SharedPreferences sessionPrefs = getSharedPreferences("user_session", MODE_PRIVATE);
        databaseUserId = sessionPrefs.getLong("database_user_id", -1);
        dbHelper = new UserDatabaseHelper(this);

        scoreText = findViewById(R.id.scoreText);
        xpText = findViewById(R.id.xpText);
        totalXPText = findViewById(R.id.totalXPText);
        backHomeBtn = findViewById(R.id.backHomeBtn);
        congratsMessage = findViewById(R.id.congratsMessage);
        levelBadge = findViewById(R.id.levelBadge);

        // Get stats from database
        UserDatabaseHelper.UserStats stats = dbHelper.getUserStats(databaseUserId);
        int totalXP = stats.totalXP;
        int level = stats.level;

        // Set dynamic congratulation message based on score
        String congrats = getCongratsMessage(score);
        congratsMessage.setText(congrats);

        scoreText.setText(score + "/50");
        xpText.setText("+" + score + " XP");
        totalXPText.setText(totalXP + " XP");
        levelBadge.setText("Lv " + level);

        backHomeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private String getCongratsMessage(int score) {
        if (score >= 25) {
            return "🌟 Outstanding! You're a true Android Master! 🌟";
        } else if (score >= 20) {
            return "🎉 Excellent work! Keep up the great momentum! 🎉";
        } else if (score >= 15) {
            return "👍 Good job! Review the lesson to aim for perfection! 👍";
        } else if (score >= 10) {
            return "📚 Nice try! Watch the videos and try again! 📚";
        } else {
            return "💪 Don't give up! Review the resources and try again! 💪";
        }
    }
}