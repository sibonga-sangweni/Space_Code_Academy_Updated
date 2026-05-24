package com.example.spacecodeacademy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.spacecodeacademy.database.UserDatabaseHelper;
import com.example.spacecodeacademy.utils.SoundManager;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {

    // UI Components
    private CircleImageView profileImage;
    private TextView userName, userEmail, totalXPText, totalXPBigText, levelText, levelBigText;
    private TextView levelProgressText, overallProgressText, progressPercentText;
    private TextView streakText, quizzesCompletedText, heartsCount;
    private android.widget.ProgressBar levelProgressBar, overallProgressBar;
    private Button continueButton, leaderboardButton, signOutButton;
    private CardView continueCard;

    // Database
    private UserDatabaseHelper dbHelper;
    private long databaseUserId;
    private String username;

    // SharedPreferences
    private SharedPreferences prefs;
    private SharedPreferences streakPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SoundManager.startBackgroundMusic(this);

        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        streakPrefs = getSharedPreferences("streak", MODE_PRIVATE);
        dbHelper = new UserDatabaseHelper(this);

        // Get user session info
        SharedPreferences sessionPrefs = getSharedPreferences("user_session", MODE_PRIVATE);
        databaseUserId = sessionPrefs.getLong("database_user_id", -1);
        username = sessionPrefs.getString("username", "Cadet");

        // If no user logged in, go back to login
        if (databaseUserId == -1) {
            goToLogin();
            return;
        }

        initializeViews();
        setUserInfo();
        loadUserStats();
        setupClickListeners();
    }

    private void initializeViews() {
        profileImage = findViewById(R.id.profileImage);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        totalXPText = findViewById(R.id.totalXPText);
        totalXPBigText = findViewById(R.id.totalXPBigText);
        levelText = findViewById(R.id.levelText);
        levelBigText = findViewById(R.id.levelBigText);
        levelProgressBar = findViewById(R.id.levelProgressBar);
        levelProgressText = findViewById(R.id.levelProgressText);
        overallProgressBar = findViewById(R.id.overallProgressBar);
        overallProgressText = findViewById(R.id.overallProgressText);
        progressPercentText = findViewById(R.id.progressPercentText);
        streakText = findViewById(R.id.streakText);
        quizzesCompletedText = findViewById(R.id.quizzesCompletedText);
        heartsCount = findViewById(R.id.heartsCount);
        continueButton = findViewById(R.id.continueButton);
        leaderboardButton = findViewById(R.id.leaderboardButton);
        signOutButton = findViewById(R.id.signOutButton);
        continueCard = findViewById(R.id.continueCard);
    }

    private void setUserInfo() {
        userName.setText(username);
        userEmail.setText("android@learner.com");
        profileImage.setImageResource(R.drawable.default_avatar);
        heartsCount.setText("3");
    }

    private void loadUserStats() {
        try {
            UserDatabaseHelper.UserStats stats = dbHelper.getUserStats(databaseUserId);
            int totalXP = stats.totalXP;
            int level = stats.level;
            int quizzesCompleted = stats.quizzesCompleted;
            int currentLevelXP = totalXP % 100;

            totalXPText.setText(totalXP + " XP");
            totalXPBigText.setText(String.valueOf(totalXP));
            levelText.setText(String.valueOf(level));
            levelBigText.setText(String.valueOf(level));
            quizzesCompletedText.setText(String.valueOf(quizzesCompleted));
            levelProgressBar.setProgress(currentLevelXP);
            levelProgressText.setText(currentLevelXP + " / 100 XP to next level");

            updateDailyStreak();
            calculateOverallProgress();

        } catch (Exception e) {
            totalXPText.setText("0 XP");
            totalXPBigText.setText("0");
            levelText.setText("1");
            levelBigText.setText("1");
            levelProgressBar.setProgress(0);
            levelProgressText.setText("0 / 100 XP to next level");
        }
    }

    private void updateDailyStreak() {
        long today = System.currentTimeMillis() / (1000 * 60 * 60 * 24);
        long lastLogin = streakPrefs.getLong("last_login_date", 0);
        int currentStreak = streakPrefs.getInt("daily_streak", 0);

        if (lastLogin == today) {
            streakText.setText(String.valueOf(currentStreak));
        } else if (lastLogin == today - 1) {
            currentStreak++;
            streakPrefs.edit().putInt("daily_streak", currentStreak).apply();
            streakText.setText(String.valueOf(currentStreak));

            if (currentStreak % 7 == 0) {
                int bonusXP = 50;
                dbHelper.updateUserScore(databaseUserId, bonusXP);
                Toast.makeText(this, "🌟 " + currentStreak + " day streak! +" + bonusXP + " XP!", Toast.LENGTH_LONG).show();
                SoundManager.playXPGain(this);
                loadUserStats();
            }
        } else {
            currentStreak = 1;
            streakPrefs.edit().putInt("daily_streak", currentStreak).apply();
            streakText.setText(String.valueOf(currentStreak));
        }

        streakPrefs.edit().putLong("last_login_date", today).apply();

        boolean dailyBonusClaimed = streakPrefs.getBoolean("daily_bonus_" + today, false);
        if (!dailyBonusClaimed) {
            dbHelper.updateUserScore(databaseUserId, 10);
            streakPrefs.edit().putBoolean("daily_bonus_" + today, true).apply();
            Toast.makeText(this, "Daily Login Bonus: +10 XP!", Toast.LENGTH_SHORT).show();
            loadUserStats();
        }
    }

    private void calculateOverallProgress() {
        int totalLessons = 30;
        SharedPreferences lessonPrefs = getSharedPreferences("lesson_progress", MODE_PRIVATE);

        // Count completed lessons
        int completedLessons = 0;
        String[] topics = {"UI Design", "Activities & Lifecycles", "Intents & Navigation",
                "Data Storage", "RecyclerView", "OS Architecture"};

        for (String topic : topics) {
            for (int i = 1; i <= 5; i++) {
                String lessonKey = topic + "_Lesson " + i + ": ";
                // This is simplified - you may need to match your actual lesson keys
            }
        }

        // Use saved count
        int savedCompleted = prefs.getInt("completed_lessons", 0);
        completedLessons = savedCompleted;

        int progressPercent = (completedLessons * 100) / totalLessons;
        overallProgressBar.setProgress(progressPercent);
        overallProgressText.setText(completedLessons + " / " + totalLessons + " lessons completed");
        progressPercentText.setText(progressPercent + "%");
    }

    private void setupClickListeners() {
        continueButton.setOnClickListener(v -> {
            SoundManager.playClick(this);
            Intent intent = new Intent(DashboardActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        continueCard.setOnClickListener(v -> {
            SoundManager.playClick(this);
            Intent intent = new Intent(DashboardActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        leaderboardButton.setOnClickListener(v -> {
            SoundManager.playClick(this);
            Intent intent = new Intent(DashboardActivity.this, LeaderboardActivity.class);
            startActivity(intent);
        });

        signOutButton.setOnClickListener(v -> {
            SoundManager.playClick(this);
            signOut();
        });
    }

    private void signOut() {
        SharedPreferences sessionPrefs = getSharedPreferences("user_session", MODE_PRIVATE);
        sessionPrefs.edit().clear().apply();

        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        goToLogin();
        Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show();
    }

    private void goToLogin() {
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundManager.resumeBackgroundMusic();
        loadUserStats();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SoundManager.pauseBackgroundMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SoundManager.stopBackgroundMusic();
    }
}