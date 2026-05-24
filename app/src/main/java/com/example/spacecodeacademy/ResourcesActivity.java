package com.example.spacecodeacademy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.spacecodeacademy.models.ExternalResource;
import com.example.spacecodeacademy.utils.SoundManager;
import java.util.ArrayList;
import java.util.List;

public class ResourcesActivity extends AppCompatActivity {

    private LinearLayout resourcesContainer;
    private TextView topicTitle;
    private Button backButton;
    private String topicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        topicName = getIntent().getStringExtra("topic");

        resourcesContainer = findViewById(R.id.resourcesContainer);
        topicTitle = findViewById(R.id.topicTitle);
        backButton = findViewById(R.id.backButton);

        topicTitle.setText("📚 " + topicName + " - Resources");

        loadResources();

        backButton.setOnClickListener(v -> {
            SoundManager.playClick(this);
            finish();
        });
    }

    private void loadResources() {
        List<ExternalResource> resources = getResourcesForTopic(topicName);

        for (ExternalResource resource : resources) {
            addResourceCard(resource);
        }
    }

    private void addResourceCard(ExternalResource resource) {
        CardView card = new CardView(this);
        CardView.LayoutParams cardParams = new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(0, 0, 0, 30);
        card.setLayoutParams(cardParams);
        card.setRadius(16f);
        card.setCardElevation(4f);
        card.setContentPadding(20, 20, 20, 20);
        card.setCardBackgroundColor(0xFF1A237E);

        LinearLayout cardContent = new LinearLayout(this);
        cardContent.setOrientation(LinearLayout.VERTICAL);

        // Type badge
        TextView typeBadge = new TextView(this);
        typeBadge.setText(getTypeBadge(resource.getType()));
        typeBadge.setTextSize(12);
        typeBadge.setPadding(12, 6, 12, 6);
        typeBadge.setBackgroundColor(getTypeColor(resource.getType()));

        // Title
        TextView titleText = new TextView(this);
        titleText.setText(resource.getTitle());
        titleText.setTextSize(18);
        titleText.setTextColor(0xFFFFFFFF);
        titleText.setTypeface(null, android.graphics.Typeface.BOLD);
        titleText.setPadding(0, 15, 0, 10);

        // Description
        TextView descText = new TextView(this);
        descText.setText(resource.getDescription());
        descText.setTextSize(14);
        descText.setTextColor(0xFFB0BEC5);
        descText.setPadding(0, 0, 0, 15);

        // Open button
        Button openButton = new Button(this);
        openButton.setText("🔗 Open Resource");
        openButton.setBackgroundColor(0xFF4CAF50);
        openButton.setTextColor(0xFFFFFFFF);
        openButton.setPadding(20, 12, 20, 12);
        openButton.setOnClickListener(v -> {
            SoundManager.playClick(this);
            openWebPage(resource.getUrl());
        });

        cardContent.addView(typeBadge);
        cardContent.addView(titleText);
        cardContent.addView(descText);
        cardContent.addView(openButton);

        card.addView(cardContent);
        resourcesContainer.addView(card);
    }

    private String getTypeBadge(String type) {
        switch (type) {
            case "video": return "🎥 VIDEO TUTORIAL";
            case "documentation": return "📖 OFFICIAL DOCS";
            case "github": return "💻 GITHUB REPO";
            default: return "🔗 EXTERNAL LINK";
        }
    }

    private int getTypeColor(String type) {
        switch (type) {
            case "video": return 0xFFE65100;
            case "documentation": return 0xFF1565C0;
            case "github": return 0xFF2E7D32;
            default: return 0xFF4A148C;
        }
    }

    private void openWebPage(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Cannot open link", Toast.LENGTH_SHORT).show();
        }
    }

    private List<ExternalResource> getResourcesForTopic(String topic) {
        List<ExternalResource> resources = new ArrayList<>();

        switch (topic) {
            case "UI Design":
                resources.add(new ExternalResource(
                        "Android Views Documentation",
                        "https://developer.android.com/develop/ui/views",
                        "Complete guide to Android Views and UI components",
                        "documentation"
                ));
                resources.add(new ExternalResource(
                        "ConstraintLayout Tutorial (YouTube)",
                        "https://www.youtube.com/watch?v=ZXpOn2QxpK4",
                        "Learn ConstraintLayout in 30 minutes",
                        "video"
                ));
                resources.add(new ExternalResource(
                        "Material Design Guidelines",
                        "https://material.io/design",
                        "Official Material Design principles and components",
                        "documentation"
                ));
                resources.add(new ExternalResource(
                        "Responsive UI Design for Android",
                        "https://developer.android.com/guide/topics/ui/layout/responsive",
                        "Create layouts that work on all screen sizes",
                        "documentation"
                ));
                resources.add(new ExternalResource(
                        "Android UI Design Tutorial (Codelab)",
                        "https://developer.android.com/codelabs/basic-android-kotlin-compose-training-xml-layouts",
                        "Interactive tutorial for building UI",
                        "article"
                ));
                break;

            case "Activities & Lifecycles":
                resources.add(new ExternalResource(
                        "Activity Lifecycle Documentation",
                        "https://developer.android.com/guide/components/activities/activity-lifecycle",
                        "Official Android documentation on Activity lifecycle",
                        "documentation"
                ));
                resources.add(new ExternalResource(
                        "Understanding Activity Lifecycle (Video)",
                        "https://www.youtube.com/watch?v=J5B6P2yWkGQ",
                        "Visual explanation of all lifecycle methods",
                        "video"
                ));
                resources.add(new ExternalResource(
                        "Saving UI States with ViewModel",
                        "https://developer.android.com/topic/libraries/architecture/viewmodel",
                        "Modern approach to state management",
                        "documentation"
                ));
                resources.add(new ExternalResource(
                        "Android Fragments Guide",
                        "https://developer.android.com/guide/fragments",
                        "Learn how to use Fragments effectively",
                        "documentation"
                ));
                resources.add(new ExternalResource(
                        "Activity Result API (Modern)",
                        "https://developer.android.com/training/basics/intents/result",
                        "New way to get results from activities",
                        "article"
                ));
                break;

            case "Intents & Navigation":
                resources.add(new ExternalResource(
                        "Intent Documentation",
                        "https://developer.android.com/guide/components/intents-filters",
                        "Official guide to Intents and Intent Filters",
                        "documentation"
                ));
                resources.add(new ExternalResource(
                        "Navigation Component Tutorial (Video)",
                        "https://www.youtube.com/watch?v=8i3JNP0Oi4M",
                        "Complete Navigation Component tutorial",
                        "video"
                ));
                resources.add(new ExternalResource(
                        "Android Deep Linking",
                        "https://developer.android.com/training/app-links/deep-linking",
                        "How to handle deep links in your app",
                        "documentation"
                ));
                resources.add(new ExternalResource(
                        "Navigation Component Codelab",
                        "https://developer.android.com/codelabs/android-navigation",
                        "Hands-on tutorial for Navigation",
                        "article"
                ));
                resources.add(new ExternalResource(
                        "Android Intents Example (GitHub)",
                        "https://github.com/android/architecture-samples",
                        "Sample code for Intents and Navigation",
                        "github"
                ));
                break;

            case "Data Storage":
                resources.add(new ExternalResource(
                        "Data Storage Options Overview",
                        "https://developer.android.com/training/data-storage",
                        "Complete guide to all storage options",
                        "documentation"
                ));
                resources.add(new ExternalResource(
                        "SQLite Database Tutorial (Video)",
                        "https://www.youtube.com/watch?v=cp2j6jv47-w",
                        "Step-by-step SQLite tutorial",
                        "video"
                ));
                resources.add(new ExternalResource(
                        "Room Library Documentation",
                        "https://developer.android.com/training/data-storage/room",
                        "Modern database solution for Android",
                        "documentation"
                ));
                resources.add(new ExternalResource(
                        "SharedPreferences Best Practices",
                        "https://medium.com/androiddevelopers/sharedpreferences-best-practices-4a20116bc7d",
                        "Article on using SharedPreferences correctly",
                        "article"
                ));
                resources.add(new ExternalResource(
                        "Working with Files on Android",
                        "https://developer.android.com/training/data-storage/files",
                        "Save and read files on Android",
                        "documentation"
                ));
                break;

            case "RecyclerView":
                resources.add(new ExternalResource(
                        "RecyclerView Documentation",
                        "https://developer.android.com/guide/topics/ui/layout/recyclerview",
                        "Official RecyclerView guide",
                        "documentation"
                ));
                resources.add(new ExternalResource(
                        "RecyclerView Tutorial (Video Series)",
                        "https://www.youtube.com/watch?v=H0jXpP4_gLw",
                        "Complete RecyclerView tutorial playlist",
                        "video"
                ));
                resources.add(new ExternalResource(
                        "ItemTouchHelper for Swipe/Drag",
                        "https://developer.android.com/reference/androidx/recyclerview/widget/ItemTouchHelper",
                        "Add swipe to delete and drag to reorder",
                        "documentation"
                ));
                resources.add(new ExternalResource(
                        "RecyclerView Animations Tutorial",
                        "https://medium.com/androiddevelopers/recyclerview-animations-part-1-how-animations-work-3b126c4ac44a",
                        "Master RecyclerView animations",
                        "article"
                ));
                resources.add(new ExternalResource(
                        "Advanced RecyclerView Examples",
                        "https://github.com/nisrulz/recyclerview-essentials",
                        "GitHub repo with RecyclerView examples",
                        "github"
                ));
                break;

            case "OS Architecture":
                resources.add(new ExternalResource(
                        "Android Architecture Overview",
                        "https://developer.android.com/guide/platform",
                        "Understanding Android's platform architecture",
                        "documentation"
                ));
                resources.add(new ExternalResource(
                        "Android Security Overview",
                        "https://source.android.com/docs/security",
                        "How Android keeps apps and users safe",
                        "documentation"
                ));
                resources.add(new ExternalResource(
                        "ART Runtime Explained (Video)",
                        "https://www.youtube.com/watch?v=E6RvO7C6rVA",
                        "Understanding Android Runtime (ART)",
                        "video"
                ));
                resources.add(new ExternalResource(
                        "Android Open Source Project (AOSP)",
                        "https://source.android.com/",
                        "Explore Android's open source code",
                        "github"
                ));
                resources.add(new ExternalResource(
                        "Android Security Best Practices",
                        "https://developer.android.com/topic/security/best-practices",
                        "Secure your Android apps",
                        "documentation"
                ));
                break;

            default:
                resources.add(new ExternalResource(
                        "Android Developer Documentation",
                        "https://developer.android.com/docs",
                        "Official Android documentation",
                        "documentation"
                ));
                break;
        }

        return resources;
    }
}