package com.example.spacecodeacademy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.spacecodeacademy.utils.SoundManager;

public class HomeActivity extends AppCompatActivity {

    CardView uiDesignCard, lifecycleCard, intentsCard, dataStorageCard, recyclerViewCard, osArchCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userName = prefs.getString("user_name", "Cadet");

        // Display welcome message
        TextView welcomeText = findViewById(R.id.welcomeText);
        if (welcomeText != null) {
            welcomeText.setText("Welcome back, " + userName + "!");
        }
        SoundManager.startBackgroundMusic(this);

        initializeViews();
        setClickListeners();
    }

    private void initializeViews() {
        uiDesignCard = findViewById(R.id.uiDesignCard);
        lifecycleCard = findViewById(R.id.lifecycleCard);
        intentsCard = findViewById(R.id.intentsCard);
        dataStorageCard = findViewById(R.id.dataStorageCard);
        recyclerViewCard = findViewById(R.id.recyclerViewCard);
        osArchCard = findViewById(R.id.osArchCard);
    }

    private void setClickListeners() {
        uiDesignCard.setOnClickListener(v -> openTopic("UI Design"));
        lifecycleCard.setOnClickListener(v -> openTopic("Activities & Lifecycles"));
        intentsCard.setOnClickListener(v -> openTopic("Intents & Navigation"));
        dataStorageCard.setOnClickListener(v -> openTopic("Data Storage"));
        recyclerViewCard.setOnClickListener(v -> openTopic("RecyclerView"));
        osArchCard.setOnClickListener(v -> openTopic("OS Architecture"));
    }

    private void openTopic(String topic) {
        SoundManager.playClick(this);  // ADD THIS LINE
        Intent intent = new Intent(this, TopicActivity.class);
        intent.putExtra("topic", topic);
        startActivity(intent);
    }
    @Override
    protected void onPause() {
        super.onPause();
        SoundManager.pauseBackgroundMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundManager.resumeBackgroundMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SoundManager.stopBackgroundMusic();
    }

}