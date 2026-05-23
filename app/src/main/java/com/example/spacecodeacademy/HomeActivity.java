package com.example.spacecodeacademy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {

    CardView uiDesignCard, lifecycleCard, intentsCard, dataStorageCard, recyclerViewCard, osArchCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        Intent intent = new Intent(this, TopicActivity.class);
        intent.putExtra("topic", topic);
        startActivity(intent);
    }
}