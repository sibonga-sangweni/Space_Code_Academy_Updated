/*package com.example.spacecodeacademy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.example.spacecodeacademy.utils.SoundManager;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1001;

    // UI Components
    private CircleImageView profileImage;
    private TextView userName, userEmail, totalXPText, streakText, levelText;
    private ProgressBar levelProgressBar, overallProgressBar;
    private TextView levelProgressText, overallProgressText;
    private Button signOutButton, continueButton;
    private ImageView statsIcon, achievementsIcon;

    // Google Sign-In
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;

    // SharedPreferences
    private SharedPreferences prefs;
    private SharedPreferences xpPrefs;
    private SharedPreferences streakPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Start background music
        SoundManager.startBackgroundMusic(this);

        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        xpPrefs = getSharedPreferences("xp", MODE_PRIVATE);
        streakPrefs = getSharedPreferences("streak", MODE_PRIVATE);

        initializeViews();
        setupGoogleSignIn();
        checkSignInStatus();

        signOutButton.setOnClickListener(v -> signOut());
        continueButton.setOnClickListener(v -> {
            SoundManager.playClick(this);
            Intent intent = new Intent(DashboardActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initializeViews() {
        profileImage = findViewById(R.id.profileImage);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        totalXPText = findViewById(R.id.totalXPText);
        streakText = findViewById(R.id.streakText);
        levelText = findViewById(R.id.levelText);
        levelProgressBar = findViewById(R.id.levelProgressBar);
        overallProgressBar = findViewById(R.id.overallProgressBar);
        levelProgressText = findViewById(R.id.levelProgressText);
        overallProgressText = findViewById(R.id.overallProgressText);
        signOutButton = findViewById(R.id.signOutButton);
        continueButton = findViewById(R.id.continueButton);
        statsIcon = findViewById(R.id.statsIcon);
        achievementsIcon = findViewById(R.id.achievementsIcon);
    }

    private void setupGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void checkSignInStatus() {
        account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            // User is already signed in
            updateUserUI(account);
            loadUserStats();
        } else {
            // Show sign-in button or prompt
            signIn();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            // Signed in successfully
            updateUserUI(account);
            loadUserStats();

            // Save user info to SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user_id", account.getId());
            editor.putString("user_name", account.getDisplayName());
            editor.putString("user_email", account.getEmail());
            editor.apply();

            Toast.makeText(this, "Welcome " + account.getDisplayName() + "!", Toast.LENGTH_SHORT).show();

        } catch (ApiException e) {
            // Sign in failed
            Toast.makeText(this, "Sign in failed. Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    private void updateUserUI(GoogleSignInAccount account) {
        // Set user name and email
        userName.setText(account.getDisplayName());
        userEmail.setText(account.getEmail());

        // Load profile picture using Glide
        Uri photoUrl = account.getPhotoUrl();
        if (photoUrl != null) {
            Glide.with(this)
                    .load(photoUrl)
                    .placeholder(R.drawable.default_avatar)
                    .error(R.drawable.default_avatar)
                    .into(profileImage);
        } else {
            profileImage.setImageResource(R.drawable.default_avatar);
        }
    }

    private void loadUserStats() {
        // Load XP and calculate level
        int totalXP = xpPrefs.getInt("totalXP", 0);
        totalXPText.setText(totalXP + " XP");

        // Calculate level (each level requires 100 XP)
        int level = totalXP / 100;
        int xpForNextLevel = 100 - (totalXP % 100);
        int currentLevelXP = totalXP % 100;

        levelText.setText("Level " + level);
        levelProgressBar.setProgress(currentLevelXP);
        levelProgressText.setText(currentLevelXP + " / 100 XP to next level");

        // Load and update daily streak
        updateDailyStreak();

        // Calculate overall progress (based on completed lessons)
        calculateOverallProgress();
    }

    private void updateDailyStreak() {
        long today = System.currentTimeMillis() / (1000 * 60 * 60 * 24);
        long lastLogin = streakPrefs.getLong("last_login_date", 0);
        int currentStreak = streakPrefs.getInt("daily_streak", 0);

        if (lastLogin == today) {
            // Already logged in today
            streakText.setText("🔥 " + currentStreak + " days");
        } else if (lastLogin == today - 1) {
            // Consecutive day
            currentStreak++;
            streakPrefs.edit().putInt("daily_streak", currentStreak).apply();
            streakText.setText("🔥 " + currentStreak + " days");

            // Bonus XP for streak
            if (currentStreak % 7 == 0) {
                int bonusXP = 50;
                int totalXP = xpPrefs.getInt("totalXP", 0);
                xpPrefs.edit().putInt("totalXP", totalXP + bonusXP).apply();
                Toast.makeText(this, "🌟 " + currentStreak + " day streak! +" + bonusXP + " XP Bonus!", Toast.LENGTH_LONG).show();
                SoundManager.playXPGain(this);
                loadUserStats(); // Refresh stats
            }
        } else {
            // Streak broken
            currentStreak = 1;
            streakPrefs.edit().putInt("daily_streak", currentStreak).apply();
            streakText.setText("🔥 " + currentStreak + " days");
        }

        // Update last login date
        streakPrefs.edit().putLong("last_login_date", today).apply();

        // Add daily login bonus (10 XP)
        boolean dailyBonusClaimed = streakPrefs.getBoolean("daily_bonus_" + today, false);
        if (!dailyBonusClaimed) {
            int totalXP = xpPrefs.getInt("totalXP", 0);
            xpPrefs.edit().putInt("totalXP", totalXP + 10).apply();
            streakPrefs.edit().putBoolean("daily_bonus_" + today, true).apply();
            Toast.makeText(this, "Daily Login Bonus: +10 XP!", Toast.LENGTH_SHORT).show();
            loadUserStats(); // Refresh stats
        }
    }

    private void calculateOverallProgress() {
        SharedPreferences lessonPrefs = getSharedPreferences("lesson_progress", MODE_PRIVATE);

        // Total lessons = 6 topics × 5 lessons = 30 lessons
        int totalLessons = 30;
        int completedLessons = 0;

        // Count completed lessons from all topics
        String[] topics = {"UI Design", "Activities & Lifecycles", "Intents & Navigation",
                "Data Storage", "RecyclerView", "OS Architecture"};

        for (String topic : topics) {
            for (int i = 1; i <= 5; i++) {
                String lessonKey = topic + "_Lesson " + i + ": ";
                // Check various lesson name patterns
                SharedPreferences allPrefs = getSharedPreferences("lesson_progress", MODE_PRIVATE);
                // This is simplified - you may need to match your actual lesson keys
            }
        }

        // Alternative: Use a simpler approach - count from SharedPreferences
        // You can store total completed lessons count separately
        int savedCompleted = prefs.getInt("completed_lessons", 0);
        completedLessons = savedCompleted;

        int progressPercent = (completedLessons * 100) / totalLessons;
        overallProgressBar.setProgress(progressPercent);
        overallProgressText.setText(completedLessons + " / " + totalLessons + " lessons completed (" + progressPercent + "%)");
    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            // Clear SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            // Show sign-in again
            signIn();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundManager.resumeBackgroundMusic();
        loadUserStats(); // Refresh stats when returning to dashboard
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
}*/
package com.example.spacecodeacademy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.example.spacecodeacademy.utils.SoundManager;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1001;

    // UI Components
    private CircleImageView profileImage;
    private TextView userName, userEmail, totalXPText, streakText, levelText;
    private ProgressBar levelProgressBar, overallProgressBar;
    private TextView levelProgressText, overallProgressText;
    private Button signOutButton, continueButton;
    private ImageView statsIcon, achievementsIcon;
    private TextView signInStatusText;

    // Google Sign-In
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;
    private boolean isSignedIn = false;

    // SharedPreferences
    private SharedPreferences prefs;
    private SharedPreferences xpPrefs;
    private SharedPreferences streakPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Start background music
        SoundManager.startBackgroundMusic(this);

        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        xpPrefs = getSharedPreferences("xp", MODE_PRIVATE);
        streakPrefs = getSharedPreferences("streak", MODE_PRIVATE);

        initializeViews();
        setupGoogleSignIn();

        // Check if user is already signed in from previous session
        boolean wasSignedIn = prefs.getBoolean("is_signed_in", false);
        String savedUserId = prefs.getString("user_id", "");

        if (wasSignedIn && !savedUserId.isEmpty()) {
            // Try to silently sign in
            silentSignIn();
        } else {
            // Start sign-in
            signIn();
        }

        signOutButton.setOnClickListener(v -> signOut());
        continueButton.setOnClickListener(v -> {
            if (isSignedIn) {
                SoundManager.playClick(this);
                Intent intent = new Intent(DashboardActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Please sign in first!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeViews() {
        profileImage = findViewById(R.id.profileImage);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        totalXPText = findViewById(R.id.totalXPText);
        streakText = findViewById(R.id.streakText);
        levelText = findViewById(R.id.levelText);
        levelProgressBar = findViewById(R.id.levelProgressBar);
        overallProgressBar = findViewById(R.id.overallProgressBar);
        levelProgressText = findViewById(R.id.levelProgressText);
        overallProgressText = findViewById(R.id.overallProgressText);
        signOutButton = findViewById(R.id.signOutButton);
        continueButton = findViewById(R.id.continueButton);
        statsIcon = findViewById(R.id.statsIcon);
        achievementsIcon = findViewById(R.id.achievementsIcon);
        signInStatusText = findViewById(R.id.signInStatusText);

        // Initially disable continue button until signed in
        continueButton.setEnabled(false);
        continueButton.setAlpha(0.5f);
    }

    private void setupGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void silentSignIn() {
        mGoogleSignInClient.silentSignIn()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        handleSignInResult(task);
                    } else {
                        // Silent sign-in failed, need user to sign in manually
                        signIn();
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            // Signed in successfully
            isSignedIn = true;

            // Save user info to SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user_id", account.getId());
            editor.putString("user_name", account.getDisplayName());
            editor.putString("user_email", account.getEmail());
            editor.putBoolean("is_signed_in", true);
            editor.apply();

            updateUserUI(account);
            loadUserStats();

            // Enable continue button
            continueButton.setEnabled(true);
            continueButton.setAlpha(1.0f);

            if (signInStatusText != null) {
                signInStatusText.setText("✅ Signed in as " + account.getDisplayName());
                signInStatusText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            }

            Toast.makeText(this, "Welcome " + account.getDisplayName() + "!", Toast.LENGTH_SHORT).show();

        } catch (ApiException e) {
            // Sign in failed
            isSignedIn = false;
            continueButton.setEnabled(false);
            continueButton.setAlpha(0.5f);

            String errorMessage = getGoogleSignInErrorMessage(e.getStatusCode());
            Toast.makeText(this, "Sign in failed: " + errorMessage, Toast.LENGTH_LONG).show();

            if (signInStatusText != null) {
                signInStatusText.setText("❌ Sign in failed: " + errorMessage + "\nPlease try again");
                signInStatusText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        }
    }

    private String getGoogleSignInErrorMessage(int errorCode) {
        // Common Google Sign-In error codes
        switch (errorCode) {
            case 7:
                return "Network error - check your internet connection";
            case 8:
                return "Invalid account - please use a valid Google account";
            case 10:
                return "Developer error - app not properly configured";
            case 16:
                return "Sign in cancelled";
            case 17:
                return "Sign in failed - account issue";
            case 12500:
                return "Google Play Services error - please update";
            case 12501:
                return "Sign in cancelled by user";
            case 12502:
                return "Account not found";
            case 12503:
                return "Invalid request";
            default:
                return "Error code: " + errorCode + ". Please try again.";
        }
    }

    private void updateUserUI(GoogleSignInAccount account) {
        // Set user name and email
        userName.setText(account.getDisplayName());
        userEmail.setText(account.getEmail());

        // Load profile picture using Glide
        Uri photoUrl = account.getPhotoUrl();
        if (photoUrl != null) {
            try {
                Glide.with(this)
                        .load(photoUrl)
                        .placeholder(R.drawable.default_avatar)
                        .error(R.drawable.default_avatar)
                        .into(profileImage);
            } catch (Exception e) {
                profileImage.setImageResource(R.drawable.default_avatar);
            }
        } else {
            profileImage.setImageResource(R.drawable.default_avatar);
        }
    }

    private void loadUserStats() {
        // Load XP and calculate level
        int totalXP = xpPrefs.getInt("totalXP", 0);
        totalXPText.setText(totalXP + " XP");

        // Calculate level (each level requires 100 XP)
        int level = totalXP / 100;
        int currentLevelXP = totalXP % 100;

        levelText.setText("Level " + level);
        levelProgressBar.setProgress(currentLevelXP);
        levelProgressText.setText(currentLevelXP + " / 100 XP to next level");

        // Load and update daily streak
        updateDailyStreak();

        // Calculate overall progress
        calculateOverallProgress();
    }

    private void updateDailyStreak() {
        long today = System.currentTimeMillis() / (1000 * 60 * 60 * 24);
        long lastLogin = streakPrefs.getLong("last_login_date", 0);
        int currentStreak = streakPrefs.getInt("daily_streak", 0);

        if (lastLogin == today) {
            // Already logged in today
            streakText.setText("🔥 " + currentStreak + " days");
        } else if (lastLogin == today - 1) {
            // Consecutive day
            currentStreak++;
            streakPrefs.edit().putInt("daily_streak", currentStreak).apply();
            streakText.setText("🔥 " + currentStreak + " days");

            // Bonus XP for streak
            if (currentStreak % 7 == 0) {
                int bonusXP = 50;
                int totalXP = xpPrefs.getInt("totalXP", 0);
                xpPrefs.edit().putInt("totalXP", totalXP + bonusXP).apply();
                Toast.makeText(this, "🌟 " + currentStreak + " day streak! +" + bonusXP + " XP Bonus!", Toast.LENGTH_LONG).show();
                SoundManager.playXPGain(this);
                loadUserStats();
            }
        } else {
            // Streak broken
            currentStreak = 1;
            streakPrefs.edit().putInt("daily_streak", currentStreak).apply();
            streakText.setText("🔥 " + currentStreak + " days");
        }

        // Update last login date
        streakPrefs.edit().putLong("last_login_date", today).apply();

        // Add daily login bonus (10 XP)
        boolean dailyBonusClaimed = streakPrefs.getBoolean("daily_bonus_" + today, false);
        if (!dailyBonusClaimed) {
            int totalXP = xpPrefs.getInt("totalXP", 0);
            xpPrefs.edit().putInt("totalXP", totalXP + 10).apply();
            streakPrefs.edit().putBoolean("daily_bonus_" + today, true).apply();
            Toast.makeText(this, "Daily Login Bonus: +10 XP!", Toast.LENGTH_SHORT).show();
            loadUserStats();
        }
    }

    private void calculateOverallProgress() {
        int totalLessons = 30;
        int completedLessons = prefs.getInt("completed_lessons", 0);

        int progressPercent = (completedLessons * 100) / totalLessons;
        overallProgressBar.setProgress(progressPercent);
        overallProgressText.setText(completedLessons + " / " + totalLessons + " lessons completed (" + progressPercent + "%)");
    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            // Clear SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            isSignedIn = false;
            continueButton.setEnabled(false);
            continueButton.setAlpha(0.5f);

            // Reset UI
            userName.setText("Not signed in");
            userEmail.setText("Please sign in to continue");
            profileImage.setImageResource(R.drawable.default_avatar);

            if (signInStatusText != null) {
                signInStatusText.setText("⚠️ You have been signed out");
                signInStatusText.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            }

            Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show();

            // Show sign-in again
            signIn();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundManager.resumeBackgroundMusic();
        if (isSignedIn) {
            loadUserStats();
        }
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