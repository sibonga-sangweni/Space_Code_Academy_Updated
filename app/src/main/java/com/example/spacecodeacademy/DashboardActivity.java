package com.example.spacecodeacademy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.spacecodeacademy.database.UserDatabaseHelper;
import android.webkit.WebView;
import com.example.spacecodeacademy.utils.SoundManager;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {

    // UI Components
    private CircleImageView profileImage;
    private TextView userName, userEmail, totalXPText, totalXPBigText, levelText, levelBigText;
    private TextView levelProgressText, overallProgressText, progressPercentText;
    private TextView streakText, quizzesCompletedText;
    private android.widget.ProgressBar levelProgressBar, overallProgressBar;
    private Button continueButton;
    private CardView continueCard;
    private WebView youtubeWebView;

    // Bottom Navigation
    private LinearLayout leaderboardButton, topicsButton, signOutButton;

    // Continue Learning Section
    private TextView continueTopicText, continueLessonText;
    private String currentTopic, currentLesson;
    private int currentLessonIndex;

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
        updateContinueLearning();
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

        continueButton = findViewById(R.id.continueButton);
        continueCard = findViewById(R.id.continueCard);

        // Bottom Navigation
        leaderboardButton = findViewById(R.id.leaderboardButton);
        topicsButton = findViewById(R.id.topicsButton);
        signOutButton = findViewById(R.id.signOutButton);

        // Continue Learning text views
        continueTopicText = findViewById(R.id.continueTopicText);
        continueLessonText = findViewById(R.id.continueLessonText);
    }

    private void setUserInfo() {
        userName.setText(username);
        userEmail.setText("android@learner.com");
        profileImage.setImageResource(R.drawable.default_avatar);
    }

    private void loadUserStats() {
        try {
            UserDatabaseHelper.UserStats stats = dbHelper.getUserStats(databaseUserId);
            int totalXP = stats.totalXP;
            int level = stats.level;
            int quizzesCompleted = stats.quizzesCompleted;

            totalXPText.setText(totalXP + " XP");
            totalXPBigText.setText(String.valueOf(totalXP));
            levelText.setText(String.valueOf(level));
            levelBigText.setText(String.valueOf(level));
            quizzesCompletedText.setText(String.valueOf(quizzesCompleted));

            // Calculate XP progress within current level
            int xpForCurrentLevel = getXPForLevel(level);
            int xpForNextLevel = getXPForLevel(level + 1);
            int xpInCurrentLevel = totalXP - xpForCurrentLevel;
            int xpNeeded = xpForNextLevel - xpForCurrentLevel;

            // Calculate percentage (0-100)
            int percentProgress = (xpInCurrentLevel * 100) / xpNeeded;

            // Cap at 100 for the progress bar
            levelProgressBar.setProgress(Math.min(percentProgress, 100));

            // Always show as /100 (cap at 100)
            int displayXP = Math.min(xpInCurrentLevel, 100);
            levelProgressText.setText(displayXP + " / 100 XP to next level");

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

    private int getXPForLevel(int level) {
        switch (level) {
            case 1: return 0;
            case 2: return 100;
            case 3: return 300;
            case 4: return 600;
            case 5: return 1000;
            case 6: return 1500;
            case 7: return 2100;
            case 8: return 2800;
            case 9: return 3600;
            case 10: return 4500;
            default: return 4500 + ((level - 10) * 500);
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

        int completedLessons = 0;

        String[] topics = {"UI Design", "Activities & Lifecycles", "Intents & Navigation",
                "Data Storage", "RecyclerView", "OS Architecture"};

        String[][] lessonTitles = {
                {"Lesson 1: Introduction to Views", "Lesson 2: Layout Types", "Lesson 3: XML vs Programmatic UI", "Lesson 4: UI Components", "Lesson 5: Best Practices & Material Design"},
                {"Lesson 1: What is an Activity?", "Lesson 2: Activity Lifecycle Methods", "Lesson 3: Managing State", "Lesson 4: Activity Communication & Results", "Lesson 5: Fragments"},
                {"Lesson 1: Explicit Intents", "Lesson 2: Implicit Intents", "Lesson 3: Passing Data with Intents", "Lesson 4: Navigation Component Basics", "Lesson 5: Deep Linking & Best Practices"},
                {"Lesson 1: SharedPreferences", "Lesson 2: Internal Storage", "Lesson 3: External Storage", "Lesson 4: SQLite Database Basics", "Lesson 5: Advanced SQLite & Room"},
                {"Lesson 1: Introduction to RecyclerView", "Lesson 2: Creating Adapters and ViewHolders", "Lesson 3: Layout Managers", "Lesson 4: Item Decorations & Animations", "Lesson 5: Advanced Features"},
                {"Lesson 1: Android Software Stack", "Lesson 2: Linux Kernel & Hardware Abstraction", "Lesson 3: Android Runtime (Dalvik vs ART)", "Lesson 4: Application Framework", "Lesson 5: Security Model & Permissions"}
        };

        for (int i = 0; i < topics.length; i++) {
            for (int j = 0; j < lessonTitles[i].length; j++) {
                String key = topics[i] + "_" + lessonTitles[i][j];
                if (lessonPrefs.getBoolean(key, false)) {
                    completedLessons++;
                }
            }
        }

        prefs.edit().putInt("completed_lessons", completedLessons).apply();

        int progressPercent = (completedLessons * 100) / totalLessons;
        overallProgressBar.setProgress(progressPercent);
        overallProgressText.setText(completedLessons + " / " + totalLessons + " lessons completed");
        progressPercentText.setText(progressPercent + "%");
    }

    private void updateContinueLearning() {
        SharedPreferences lessonPrefs = getSharedPreferences("lesson_progress", MODE_PRIVATE);

        String[] topics = {"UI Design", "Activities & Lifecycles", "Intents & Navigation",
                "Data Storage", "RecyclerView", "OS Architecture"};

        String[][] lessonTitles = {
                {"Lesson 1: Introduction to Views", "Lesson 2: Layout Types", "Lesson 3: XML vs Programmatic UI", "Lesson 4: UI Components", "Lesson 5: Best Practices & Material Design"},
                {"Lesson 1: What is an Activity?", "Lesson 2: Activity Lifecycle Methods", "Lesson 3: Managing State", "Lesson 4: Activity Communication & Results", "Lesson 5: Fragments"},
                {"Lesson 1: Explicit Intents", "Lesson 2: Implicit Intents", "Lesson 3: Passing Data with Intents", "Lesson 4: Navigation Component Basics", "Lesson 5: Deep Linking & Best Practices"},
                {"Lesson 1: SharedPreferences", "Lesson 2: Internal Storage", "Lesson 3: External Storage", "Lesson 4: SQLite Database Basics", "Lesson 5: Advanced SQLite & Room"},
                {"Lesson 1: Introduction to RecyclerView", "Lesson 2: Creating Adapters and ViewHolders", "Lesson 3: Layout Managers", "Lesson 4: Item Decorations & Animations", "Lesson 5: Advanced Features"},
                {"Lesson 1: Android Software Stack", "Lesson 2: Linux Kernel & Hardware Abstraction", "Lesson 3: Android Runtime (Dalvik vs ART)", "Lesson 4: Application Framework", "Lesson 5: Security Model & Permissions"}
        };

        // Find the first incomplete lesson
        boolean found = false;
        for (int i = 0; i < topics.length; i++) {
            for (int j = 0; j < lessonTitles[i].length; j++) {
                String key = topics[i] + "_" + lessonTitles[i][j];
                boolean isCompleted = lessonPrefs.getBoolean(key, false);

                if (!isCompleted) {
                    currentTopic = topics[i];
                    currentLesson = lessonTitles[i][j];
                    currentLessonIndex = j;
                    found = true;
                    break;
                }
            }
            if (found) break;
        }

        // If all lessons are completed
        if (!found) {
            currentTopic = "Complete!";
            currentLesson = "You've mastered all lessons!";
        }

        updateContinueLearningUI();
    }

    private void updateContinueLearningUI() {
        if (continueTopicText != null && continueLessonText != null) {
            String emoji = getTopicEmoji(currentTopic);
            continueTopicText.setText(emoji + " " + currentTopic);
            continueLessonText.setText(currentLesson);
        }
    }

    private String getTopicEmoji(String topic) {
        switch (topic) {
            case "UI Design": return "📱";
            case "Activities & Lifecycles": return "🔄";
            case "Intents & Navigation": return "🧭";
            case "Data Storage": return "💾";
            case "RecyclerView": return "📋";
            case "OS Architecture": return "🏛️";
            default: return "🎉";
        }
    }

    private void setupClickListeners() {
        // Continue Button
        continueButton.setOnClickListener(v -> {
            SoundManager.playClick(this);

            if (currentTopic.equals("Complete!")) {
                Toast.makeText(this, "🎉 Congratulations! You've mastered everything! 🎉", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DashboardActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                return;
            }

            Intent intent = new Intent(DashboardActivity.this, LessonActivity.class);
            intent.putExtra("topic", currentTopic);
            intent.putExtra("lessonTitle", currentLesson);
            intent.putExtra("lessonIndex", currentLessonIndex);
            startActivity(intent);
        });

        // Continue Card
        continueCard.setOnClickListener(v -> {
            SoundManager.playClick(this);

            if (currentTopic.equals("Complete!")) {
                Toast.makeText(this, "🎉 Congratulations! You've mastered everything! 🎉", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DashboardActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                return;
            }

            Intent intent = new Intent(DashboardActivity.this, LessonActivity.class);
            intent.putExtra("topic", currentTopic);
            intent.putExtra("lessonTitle", currentLesson);
            intent.putExtra("lessonIndex", currentLessonIndex);
            startActivity(intent);
        });

        // Leaderboard Button (Bottom Navigation)
        leaderboardButton.setOnClickListener(v -> {
            SoundManager.playClick(this);
            Intent intent = new Intent(DashboardActivity.this, LeaderboardActivity.class);
            startActivity(intent);
        });

        // Topics Button (Bottom Navigation) - Goes to HomeActivity (Roadmap)
        topicsButton.setOnClickListener(v -> {
            SoundManager.playClick(this);
            Intent intent = new Intent(DashboardActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        // Sign Out Button (Bottom Navigation)
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
//        SoundManager.resumeBackgroundMusic();
        loadUserStats();
        updateContinueLearning();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        SoundManager.pauseBackgroundMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SoundManager.stopBackgroundMusic();
    }
}