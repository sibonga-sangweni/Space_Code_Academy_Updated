package com.example.spacecodeacademy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecodeacademy.utils.SoundManager;

public class HomeActivity extends AppCompatActivity {

    // Planet Layouts
    private LinearLayout mercuryCard, venusCard, marsCard, jupiterCard, saturnCard, earthCard;
    private TextView mercuryProgressText, venusProgressText, marsProgressText;
    private TextView jupiterProgressText, saturnProgressText, earthProgressText;
    private ProgressBar mercuryProgressBar, venusProgressBar, marsProgressBar;
    private ProgressBar jupiterProgressBar, saturnProgressBar, earthProgressBar;
    private Button leaderboardButton, backButton;

    // Planet names and topics
    private final String[][] planetData = {
            {"mercury", "UI Design"},
            {"venus", "Activities & Lifecycles"},
            {"mars", "Intents & Navigation"},
            {"jupiter", "Data Storage"},
            {"saturn", "RecyclerView"},
            {"earth", "OS Architecture"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userName = prefs.getString("user_name", "Cadet");

        TextView welcomeText = findViewById(R.id.welcomeText);
        if (welcomeText != null) {
            welcomeText.setText("Welcome back, " + userName + "!");
        }

        // Initialize views
        initializeViews();

        // Update all planet progress
        updateAllProgress();

        // Initialize back button
        backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> {
                SoundManager.playClick(this);
                goToDashboard();
            });
        }

        leaderboardButton = findViewById(R.id.leaderboardButton);
        if (leaderboardButton != null) {
            leaderboardButton.setOnClickListener(v -> {
                SoundManager.playClick(this);
                Intent intent = new Intent(HomeActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            });
        }

        SoundManager.startBackgroundMusic(this);
        setClickListeners();
    }

    private void initializeViews() {
        // Planet Layouts
        mercuryCard = findViewById(R.id.mercuryCard);
        venusCard = findViewById(R.id.venusCard);
        marsCard = findViewById(R.id.marsCard);
        jupiterCard = findViewById(R.id.jupiterCard);
        saturnCard = findViewById(R.id.saturnCard);
        earthCard = findViewById(R.id.earthCard);

        // Progress Text Views
        mercuryProgressText = findViewById(R.id.mercuryProgress);
        venusProgressText = findViewById(R.id.venusProgress);
        marsProgressText = findViewById(R.id.marsProgress);
        jupiterProgressText = findViewById(R.id.jupiterProgress);
        saturnProgressText = findViewById(R.id.saturnProgress);
        earthProgressText = findViewById(R.id.earthProgress);

        // Progress Bars
        mercuryProgressBar = findViewById(R.id.mercuryProgressBar);
        venusProgressBar = findViewById(R.id.venusProgressBar);
        marsProgressBar = findViewById(R.id.marsProgressBar);
        jupiterProgressBar = findViewById(R.id.jupiterProgressBar);
        saturnProgressBar = findViewById(R.id.saturnProgressBar);
        earthProgressBar = findViewById(R.id.earthProgressBar);
    }

    private void updateAllProgress() {
        // Update each planet's progress
        updatePlanetProgress("mercury", mercuryProgressText, mercuryProgressBar, "UI Design");
        updatePlanetProgress("venus", venusProgressText, venusProgressBar, "Activities & Lifecycles");
        updatePlanetProgress("mars", marsProgressText, marsProgressBar, "Intents & Navigation");
        updatePlanetProgress("jupiter", jupiterProgressText, jupiterProgressBar, "Data Storage");
        updatePlanetProgress("saturn", saturnProgressText, saturnProgressBar, "RecyclerView");
        updatePlanetProgress("earth", earthProgressText, earthProgressBar, "OS Architecture");
    }

    private void updatePlanetProgress(String planetName, TextView progressText, ProgressBar progressBar, String topic) {
        SharedPreferences prefs = getSharedPreferences("lesson_progress", MODE_PRIVATE);

        // Get all lessons for this topic (5 lessons per topic)
        int totalLessons = 5;
        int completedLessons = 0;

        // Get lesson titles for this topic
        String[] lessons = getLessonTitlesForTopic(topic);

        for (String lesson : lessons) {
            String key = topic + "_" + lesson;
            if (prefs.getBoolean(key, false)) {
                completedLessons++;
            }
        }

        int percent = (completedLessons * 100) / totalLessons;

        if (progressText != null) {
            progressText.setText(percent + "%");
        }

        if (progressBar != null) {
            progressBar.setProgress(percent);
        }

        // Check if planet is fully completed
        if (completedLessons == totalLessons) {
            // Planet completed - can add special visual effect
            if (progressText != null) {
                progressText.setText("✅ 100%");
            }
        }
    }

    private String[] getLessonTitlesForTopic(String topic) {
        switch (topic) {
            case "UI Design":
                return new String[]{
                        "Lesson 1: Introduction to Views",
                        "Lesson 2: Layout Types",
                        "Lesson 3: XML vs Programmatic UI",
                        "Lesson 4: UI Components",
                        "Lesson 5: Best Practices & Material Design"
                };
            case "Activities & Lifecycles":
                return new String[]{
                        "Lesson 1: What is an Activity?",
                        "Lesson 2: Activity Lifecycle Methods",
                        "Lesson 3: Managing State",
                        "Lesson 4: Activity Communication & Results",
                        "Lesson 5: Fragments"
                };
            case "Intents & Navigation":
                return new String[]{
                        "Lesson 1: Explicit Intents",
                        "Lesson 2: Implicit Intents",
                        "Lesson 3: Passing Data with Intents",
                        "Lesson 4: Navigation Component Basics",
                        "Lesson 5: Deep Linking & Best Practices"
                };
            case "Data Storage":
                return new String[]{
                        "Lesson 1: SharedPreferences",
                        "Lesson 2: Internal Storage",
                        "Lesson 3: External Storage",
                        "Lesson 4: SQLite Database Basics",
                        "Lesson 5: Advanced SQLite & Room"
                };
            case "RecyclerView":
                return new String[]{
                        "Lesson 1: Introduction to RecyclerView",
                        "Lesson 2: Creating Adapters and ViewHolders",
                        "Lesson 3: Layout Managers",
                        "Lesson 4: Item Decorations & Animations",
                        "Lesson 5: Advanced Features"
                };
            case "OS Architecture":
                return new String[]{
                        "Lesson 1: Android Software Stack",
                        "Lesson 2: Linux Kernel & Hardware Abstraction",
                        "Lesson 3: Android Runtime (Dalvik vs ART)",
                        "Lesson 4: Application Framework",
                        "Lesson 5: Security Model & Permissions"
                };
            default:
                return new String[]{"Lesson 1", "Lesson 2", "Lesson 3", "Lesson 4", "Lesson 5"};
        }
    }

    private void setClickListeners() {
        if (mercuryCard != null) {
            mercuryCard.setOnClickListener(v -> openTopic("UI Design"));
        }
        if (venusCard != null) {
            venusCard.setOnClickListener(v -> openTopic("Activities & Lifecycles"));
        }
        if (marsCard != null) {
            marsCard.setOnClickListener(v -> openTopic("Intents & Navigation"));
        }
        if (jupiterCard != null) {
            jupiterCard.setOnClickListener(v -> openTopic("Data Storage"));
        }
        if (saturnCard != null) {
            saturnCard.setOnClickListener(v -> openTopic("RecyclerView"));
        }
        if (earthCard != null) {
            earthCard.setOnClickListener(v -> openTopic("OS Architecture"));
        }
    }

    private void openTopic(String topic) {
        SoundManager.playClick(this);
        Intent intent = new Intent(this, TopicActivity.class);
        intent.putExtra("topic", topic);
        startActivity(intent);
    }

    private void goToDashboard() {
        Intent intent = new Intent(HomeActivity.this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundManager.resumeBackgroundMusic();
        // Refresh progress when returning from a lesson
        updateAllProgress();
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