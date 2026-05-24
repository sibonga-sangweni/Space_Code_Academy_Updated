package com.example.spacecodeacademy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spacecodeacademy.adapters.LessonAdapter;
import com.example.spacecodeacademy.models.Lesson;
import java.util.ArrayList;
import java.util.List;
import com.example.spacecodeacademy.utils.SoundManager;

public class TopicActivity extends AppCompatActivity {

    private Button topicResourcesButton;
    private TextView topicTitle;
    private RecyclerView lessonsRecyclerView;
    private LessonAdapter lessonAdapter;
    private String topicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        topicName = getIntent().getStringExtra("topic");

        setupToolbar();
        initializeViews();
        setupRecyclerView();

        topicResourcesButton.setOnClickListener(v -> {
            SoundManager.playClick(this);
            Intent intent = new Intent(TopicActivity.this, ResourcesActivity.class);
            intent.putExtra("topic", topicName);
            startActivity(intent);
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(topicName);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initializeViews() {
        topicTitle = findViewById(R.id.topicTitle);
        lessonsRecyclerView = findViewById(R.id.lessonsRecyclerView);
        topicTitle.setText(topicName);
        topicResourcesButton = findViewById(R.id.topicResourcesButton);
    }

    private void setupRecyclerView() {
        List<Lesson> lessons = getLessonsForTopic(topicName);
        lessonAdapter = new LessonAdapter(lessons, (position, lesson) -> {
            // Check if previous lessons are completed
            if (position > 0) {
                SharedPreferences prefs = getSharedPreferences("lesson_progress", MODE_PRIVATE);
                String prevLessonKey = topicName + "_" + lessons.get(position - 1).getTitle();
                boolean prevCompleted = prefs.getBoolean(prevLessonKey, false);

                if (!prevCompleted) {
                    // Show dialog or toast that previous lesson must be completed
                    android.widget.Toast.makeText(this,
                            "Please complete the previous lesson first!",
                            android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            SoundManager.playClick(this);
            Intent intent = new Intent(TopicActivity.this, LessonActivity.class);
            intent.putExtra("topic", topicName);
            intent.putExtra("lessonTitle", lesson.getTitle());
            intent.putExtra("lessonIndex", position);
            startActivity(intent);
        });
        lessonsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lessonsRecyclerView.setAdapter(lessonAdapter);
    }

    private List<Lesson> getLessonsForTopic(String topic) {
        List<Lesson> lessons = new ArrayList<>();

        switch(topic) {
            case "UI Design":
                lessons.add(new Lesson("Lesson 1: Introduction to Views",
                        "Learn what Views are, the View hierarchy, and basic View types.",
                        "Understanding Views and ViewGroups"));
                lessons.add(new Lesson("Lesson 2: Layout Types",
                        "Explore LinearLayout, ConstraintLayout, RelativeLayout, and when to use each.",
                        "Mastering different layout containers"));
                lessons.add(new Lesson("Lesson 3: XML vs Programmatic UI",
                        "Compare XML layout design vs programmatic UI creation.",
                        "Choosing the right approach for your needs"));
                lessons.add(new Lesson("Lesson 4: UI Components",
                        "Deep dive into Buttons, TextViews, EditTexts, ImageViews and more.",
                        "Working with common UI elements"));
                lessons.add(new Lesson("Lesson 5: Best Practices & Material Design",
                        "Learn Material Design principles and UI best practices for Android.",
                        "Creating professional, user-friendly interfaces"));
                break;

            case "Activities & Lifecycles":
                lessons.add(new Lesson("Lesson 1: What is an Activity?",
                        "Understand Activities as the building blocks of Android screens.",
                        "Creating and managing basic Activities"));
                lessons.add(new Lesson("Lesson 2: Activity Lifecycle Methods",
                        "Master all 7 lifecycle methods: onCreate, onStart, onResume, etc.",
                        "Understanding when each method is called"));
                lessons.add(new Lesson("Lesson 3: Managing State",
                        "Save and restore UI state using onSaveInstanceState and Bundles.",
                        "Handling configuration changes like screen rotation"));
                lessons.add(new Lesson("Lesson 4: Activity Communication & Results",
                        "Start activities for results and communicate between activities.",
                        "Using startActivityForResult and modern alternatives"));
                lessons.add(new Lesson("Lesson 5: Fragments",
                        "Introduction to Fragments and Activity-Fragment communication.",
                        "Creating reusable UI components"));
                break;

            case "Intents & Navigation":
                lessons.add(new Lesson("Lesson 1: Explicit Intents",
                        "Navigate directly to specific activities within your app.",
                        "Starting activities by class name"));
                lessons.add(new Lesson("Lesson 2: Implicit Intents",
                        "Let Android find the right app to handle your request.",
                        "Opening web pages, making calls, sharing content"));
                lessons.add(new Lesson("Lesson 3: Passing Data with Intents",
                        "Send data between activities using Intent extras.",
                        "Using putExtra and getIntent methods"));
                lessons.add(new Lesson("Lesson 4: Navigation Component Basics",
                        "Implement modern navigation using Navigation Component.",
                        "Creating navigation graphs and safe args"));
                lessons.add(new Lesson("Lesson 5: Deep Linking & Best Practices",
                        "Handle deep links and follow navigation best practices.",
                        "Making your app navigable from external sources"));
                break;

            case "Data Storage":
                lessons.add(new Lesson("Lesson 1: SharedPreferences",
                        "Store simple key-value pairs for user preferences.",
                        "Saving and retrieving primitive data types"));
                lessons.add(new Lesson("Lesson 2: Internal Storage",
                        "Save private files directly on device storage.",
                        "Reading and writing files privately"));
                lessons.add(new Lesson("Lesson 3: External Storage",
                        "Store files on shared external storage like SD cards.",
                        "Managing permissions and file access"));
                lessons.add(new Lesson("Lesson 4: SQLite Database Basics",
                        "Create and use SQLite databases for structured data.",
                        "CRUD operations with SQLiteOpenHelper"));
                lessons.add(new Lesson("Lesson 5: Advanced SQLite & Room",
                        "Master complex queries and learn the Room persistence library.",
                        "Database optimization and best practices"));
                break;

            case "RecyclerView":
                lessons.add(new Lesson("Lesson 1: Introduction to RecyclerView",
                        "Understand what RecyclerView is and why it's efficient.",
                        "Setting up your first RecyclerView"));
                lessons.add(new Lesson("Lesson 2: Creating Adapters and ViewHolders",
                        "Implement custom adapters and ViewHolders.",
                        "Binding data to RecyclerView items"));
                lessons.add(new Lesson("Lesson 3: Layout Managers",
                        "Use LinearLayoutManager, GridLayoutManager, and StaggeredGridLayoutManager.",
                        "Creating different list layouts"));
                lessons.add(new Lesson("Lesson 4: Item Decorations & Animations",
                        "Add dividers, spacing, and animations to your lists.",
                        "Making your RecyclerView visually appealing"));
                lessons.add(new Lesson("Lesson 5: Advanced Features",
                        "Implement swipe to delete, drag and drop, and endless scrolling.",
                        "Professional RecyclerView implementations"));
                break;

            case "OS Architecture":
                lessons.add(new Lesson("Lesson 1: Android Software Stack",
                        "Overview of Android's layered architecture.",
                        "Understanding how Android is structured"));
                lessons.add(new Lesson("Lesson 2: Linux Kernel & Hardware Abstraction",
                        "Deep dive into the kernel layer and HAL.",
                        "How Android interacts with hardware"));
                lessons.add(new Lesson("Lesson 3: Android Runtime (Dalvik vs ART)",
                        "Learn about Dalvik, ART, and how Android executes code.",
                        "JIT vs AOT compilation"));
                lessons.add(new Lesson("Lesson 4: Application Framework",
                        "Explore the framework layers and system services.",
                        "Understanding Activity Manager, Package Manager, etc."));
                lessons.add(new Lesson("Lesson 5: Security Model & Permissions",
                        "Android's security features and permission system.",
                        "Protecting user data and system resources"));
                break;
        }

        return lessons;
    }
}