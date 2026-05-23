package com.example.spacecodeacademy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class LessonActivity extends AppCompatActivity {

    private TextView lessonTitle, learningObjectives, keyConcepts, detailedExplanation;
    private TextView codeExample, proTip, progressText;
    private ProgressBar lessonProgress;
    private Button testButton;
    private CardView diagramCard;
    private ImageView diagramImage;

    private String topic, lessonName;
    private int lessonIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        topic = getIntent().getStringExtra("topic");
        lessonName = getIntent().getStringExtra("lessonTitle");
        lessonIndex = getIntent().getIntExtra("lessonIndex", 0);

        initializeViews();
        loadLessonContent();
        updateProgress();

        testButton.setOnClickListener(v -> {
            Intent intent = new Intent(LessonActivity.this, QuizActivity.class);
            intent.putExtra("topic", topic);
            intent.putExtra("lessonName", lessonName);
            startActivity(intent);
        });
    }

    private void initializeViews() {
        lessonTitle = findViewById(R.id.lessonTitle);
        learningObjectives = findViewById(R.id.learningObjectives);
        keyConcepts = findViewById(R.id.keyConcepts);
        detailedExplanation = findViewById(R.id.detailedExplanation);
        codeExample = findViewById(R.id.codeExample);
        proTip = findViewById(R.id.proTip);
        testButton = findViewById(R.id.testButton);
        lessonProgress = findViewById(R.id.lessonProgress);
        progressText = findViewById(R.id.progressText);
        diagramCard = findViewById(R.id.diagramCard);
        diagramImage = findViewById(R.id.diagramImage);
    }

    private void loadLessonContent() {
        lessonTitle.setText(lessonName);

        if (topic.equals("UI Design")) {
            if (lessonName.contains("Introduction to Views")) {
                loadUIDesignLesson1();
            } else if (lessonName.contains("Layout Types")) {
                loadUIDesignLesson2();
            } else if (lessonName.contains("XML vs Programmatic")) {
                loadUIDesignLesson3();
            } else if (lessonName.contains("UI Components")) {
                loadUIDesignLesson4();
            } else if (lessonName.contains("Best Practices")) {
                loadUIDesignLesson5();
            }
        } else if (topic.equals("Activities & Lifecycles")) {
            if (lessonIndex == 0) {
                loadLifecycleLesson1();
            } else if (lessonIndex == 1) {
                loadLifecycleLesson2();
            } else if (lessonIndex == 2) {
                loadLifecycleLesson3();
            } else if (lessonIndex == 3) {
                loadLifecycleLesson4();
            } else if (lessonIndex == 4) {
                loadLifecycleLesson5();
            }
        } else if (topic.equals("Intents & Navigation")) {
            if (lessonIndex == 0) {
                loadIntentsLesson1();
            } else if (lessonIndex == 1) {
                loadIntentsLesson2();
            } else if (lessonIndex == 2) {
                loadIntentsLesson3();
            } else if (lessonIndex == 3) {
                loadIntentsLesson4();
            } else if (lessonIndex == 4) {
                loadIntentsLesson5();
            }
        } else if (topic.equals("Data Storage")) {
            if (lessonIndex == 0) {
                loadStorageLesson1();
            } else if (lessonIndex == 1) {
                loadStorageLesson2();
            } else if (lessonIndex == 2) {
                loadStorageLesson3();
            } else if (lessonIndex == 3) {
                loadStorageLesson4();
            } else if (lessonIndex == 4) {
                loadStorageLesson5();
            }
        } else if (topic.equals("RecyclerView")) {
            if (lessonIndex == 0) {
                loadRecyclerViewLesson1();
            } else if (lessonIndex == 1) {
                loadRecyclerViewLesson2();
            } else if (lessonIndex == 2) {
                loadRecyclerViewLesson3();
            } else if (lessonIndex == 3) {
                loadRecyclerViewLesson4();
            } else if (lessonIndex == 4) {
                loadRecyclerViewLesson5();
            }
        } else if (topic.equals("OS Architecture")) {
            if (lessonIndex == 0) {
                loadOSLesson1();
            } else if (lessonIndex == 1) {
                loadOSLesson2();
            } else if (lessonIndex == 2) {
                loadOSLesson3();
            } else if (lessonIndex == 3) {
                loadOSLesson4();
            } else if (lessonIndex == 4) {
                loadOSLesson5();
            }
        }
    }

    // ==================== UI DESIGN LESSONS ====================

    private void loadUIDesignLesson1() {
        learningObjectives.setText("• Understand what Views are in Android\n• Learn about different View types\n• Know how to create and manipulate Views\n• Understand the View hierarchy");

        keyConcepts.setText("View • ViewGroup • TextView • Button • ImageView • EditText • findViewById()");

        detailedExplanation.setText("In Android, a View is the basic building block of UI. Every visible UI component (buttons, text fields, images, etc.) is a View. Views are organized in a tree structure called the View Hierarchy, where ViewGroups act as containers for other Views.\n\n" +
                "Common View types:\n" +
                "• TextView - Displays text to the user\n" +
                "• EditText - Allows user input\n" +
                "• Button - Clickable element that performs an action\n" +
                "• ImageView - Displays images\n\n" +
                "To find a View in your code, use findViewById(R.id.view_id). This returns a reference to the View defined in your XML layout.");

        codeExample.setText("// Finding a View in Java\nTextView myText = findViewById(R.id.welcomeText);\nmyText.setText(\"Hello Android!\");\n\n// Creating a Button programmatically\nButton myButton = new Button(this);\nmyButton.setText(\"Click Me\");\nmyButton.setOnClickListener(v -> {\n    Toast.makeText(this, \"Clicked!\", Toast.LENGTH_SHORT).show();\n});");

        proTip.setText("Always give your Views meaningful IDs in XML (e.g., 'loginButton' instead of 'button1'). This makes your code more readable and maintainable.");
    }

    private void loadUIDesignLesson2() {
        learningObjectives.setText("• Understand different Layout types\n• Learn when to use each Layout\n• Master LinearLayout and ConstraintLayout\n• Create responsive layouts");

        keyConcepts.setText("LinearLayout • ConstraintLayout • RelativeLayout • FrameLayout • TableLayout • orientation • layout_weight");

        detailedExplanation.setText("Layouts are ViewGroups that arrange child views on screen:\n\n" +
                "📱 LinearLayout: Arranges children in a single row (horizontal) or column (vertical). Perfect for simple, linear structures.\n" +
                "   - android:orientation=\"vertical\" or \"horizontal\"\n" +
                "   - Use layout_weight to distribute space proportionally\n\n" +
                "🎯 ConstraintLayout: Most flexible layout. You create relationships (constraints) between views and the parent container.\n" +
                "   - Better performance than nested layouts\n" +
                "   - Visual editor in Android Studio\n" +
                "   - app:layout_constraintTop_toTopOf=\"parent\"\n\n" +
                "📦 Other layouts: FrameLayout (single child overlay), RelativeLayout (position relative to siblings), GridLayout (grid arrangement)");

        codeExample.setText("<!-- LinearLayout Example -->\n<LinearLayout\n    android:orientation=\"vertical\"\n    android:layout_width=\"match_parent\"\n    android:layout_height=\"wrap_content\">\n    \n    <Button\n        android:layout_width=\"match_parent\"\n        android:layout_height=\"wrap_content\"\n        android:text=\"Button 1\"/>\n        \n</LinearLayout>\n\n<!-- ConstraintLayout Example -->\n<Button\n    android:id=\"@+id/button\"\n    android:layout_width=\"wrap_content\"\n    android:layout_height=\"wrap_content\"\n    app:layout_constraintTop_toTopOf=\"parent\"\n    app:layout_constraintLeft_toLeftOf=\"parent\"\n    app:layout_constraintRight_toRightOf=\"parent\"/>");

        proTip.setText("Use ConstraintLayout for complex layouts - it creates a flat view hierarchy (no nested layouts), which improves performance!");
    }

    private void loadUIDesignLesson3() {
        learningObjectives.setText("• Understand XML vs Programmatic UI\n• Know when to use each approach\n• Create dynamic UIs at runtime\n• Best practices for UI design");

        keyConcepts.setText("XML Layouts • Programmatic UI • Inflater • Dynamic Views • Separation of Concerns");

        detailedExplanation.setText("Two ways to create UI in Android:\n\n" +
                "📄 XML Layouts (Recommended for static UI):\n" +
                "• Declarative approach - define UI in XML files\n" +
                "• Separation of design from logic\n" +
                "• Visual editor support\n" +
                "• Easier to maintain\n" +
                "• Better for responsive designs\n\n" +
                "💻 Programmatic UI (For dynamic content):\n" +
                "• Create Views in Java/Kotlin code\n" +
                "• Add/remove Views at runtime\n" +
                "• Useful for dynamic lists\n" +
                "• More flexible but harder to maintain\n\n" +
                "Most apps use XML for structure and programmatic for dynamic content.");

        codeExample.setText("// Setting XML layout (in Activity's onCreate)\nsetContentView(R.layout.activity_main);\n\n// Programmatic creation\nButton dynamicButton = new Button(this);\ndynamicButton.setText(\"I was created in code!\");\ndynamicButton.setLayoutParams(new LinearLayout.LayoutParams(\n    LinearLayout.LayoutParams.MATCH_PARENT,\n    LinearLayout.LayoutParams.WRAP_CONTENT\n));\n\n// Adding to layout\nLinearLayout container = findViewById(R.id.container);\ncontainer.addView(dynamicButton);");

        proTip.setText("Start with XML layouts for structure, then add programmatic Views for dynamic content. This gives you the best of both worlds!");
    }

    private void loadUIDesignLesson4() {
        learningObjectives.setText("• Master Buttons and click handling\n• Work with TextViews and EditTexts\n• Display images with ImageView\n• Create interactive UI components");

        keyConcepts.setText("Button • TextView • EditText • ImageView • onClickListener • TextWatcher");

        detailedExplanation.setText("Common UI Components:\n\n" +
                "🔘 Button:\n" +
                "• Clickable element that performs actions\n" +
                "• Set onClickListener to handle clicks\n" +
                "• Can have custom backgrounds and states\n\n" +
                "📝 TextView:\n" +
                "• Displays text to the user\n" +
                "• Can be formatted with HTML, spans\n" +
                "• AutoLink for web/email/phone detection\n\n" +
                "✏️ EditText:\n" +
                "• Allows user text input\n" +
                "• Set inputType for numbers, email, password\n" +
                "• TextWatcher for real-time input monitoring\n\n" +
                "🖼️ ImageView:\n" +
                "• Displays images from resources or network\n" +
                "• ScaleType controls image scaling\n" +
                "• Can load SVGs, drawables, bitmaps");

        codeExample.setText("// Button click handling\nButton myButton = findViewById(R.id.myButton);\nmyButton.setOnClickListener(v -> {\n    Toast.makeText(this, \"Clicked!\", Toast.LENGTH_SHORT).show();\n});\n\n// EditText with TextWatcher\nEditText editText = findViewById(R.id.editText);\neditText.addTextChangedListener(new TextWatcher() {\n    @Override\n    public void afterTextChanged(Editable s) {\n        // Called after text changes\n    }\n});\n\n// Loading image\nImageView imageView = findViewById(R.id.myImage);\nimageView.setImageResource(R.drawable.my_image);\nimageView.setScaleType(ImageView.ScaleType.CENTER_CROP);");

        proTip.setText("Use Vector Drawables instead of PNGs when possible. They scale perfectly on all screen densities and reduce APK size!");
    }

    private void loadUIDesignLesson5() {
        learningObjectives.setText("• Learn Material Design principles\n• Implement Material Components\n• Follow Android design guidelines\n• Create responsive layouts for all screen sizes");

        keyConcepts.setText("Material Design • Elevation • Ripple Effect • Theming • Responsive Design • Dark Mode");

        detailedExplanation.setText("Material Design Best Practices:\n\n" +
                "🎨 Material Design Principles:\n" +
                "• Material is the metaphor (paper and ink)\n" +
                "• Bold, graphic, intentional (visual hierarchy)\n" +
                "• Motion provides meaning (animations)\n\n" +
                "📐 Key Components:\n" +
                "• MaterialButton - with ripples and elevation\n" +
                "• MaterialCardView - cards with shadows\n" +
                "• BottomNavigationView - bottom tabs\n" +
                "• TextInputLayout - floating labels\n\n" +
                "🎯 Responsive Design:\n" +
                "• Use ConstraintLayout for flexible layouts\n" +
                "• Provide alternative layouts for tablets\n" +
                "• Use dp for dimensions, sp for text\n" +
                "• Support dark mode with themes\n\n" +
                "✅ Do's and Don'ts:\n" +
                "• DO: Use consistent spacing (8dp grid)\n" +
                "• DON'T: Hardcode colors (use themes)\n" +
                "• DO: Provide visual feedback for touches\n" +
                "• DON'T: Block the UI thread");

        codeExample.setText("// Apply Material Theme\n<style name=\"AppTheme\" parent=\"Theme.MaterialComponents.DayNight\">\n    <item name=\"colorPrimary\">@color/purple_500</item>\n    <item name=\"colorSecondary\">@color/teal_200</item>\n</style>\n\n// MaterialButton\n<com.google.android.material.button.MaterialButton\n    android:layout_width=\"match_parent\"\n    android:layout_height=\"wrap_content\"\n    android:text=\"Material Button\"\n    app:icon=\"@drawable/ic_android\"/>\n\n// TextInputLayout\n<com.google.android.material.textfield.TextInputLayout\n    style=\"@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox\">\n    <com.google.android.material.textfield.TextInputEditText\n        android:hint=\"Enter email\"/>\n</com.google.android.material.textfield.TextInputLayout>");

        proTip.setText("Always test your layouts on different screen sizes using Android Studio's layout validation tool. Use both light and dark themes!");
    }

    // ==================== ACTIVITIES & LIFECYCLES LESSONS ====================

    private void loadLifecycleLesson1() {
        learningObjectives.setText("• Understand what an Activity is\n• Learn the role of Activities in Android\n• Know how to create an Activity\n• Understand Activity lifecycle basics");

        keyConcepts.setText("Activity • Screen • UI • Entry Point • AppCompatActivity • User Interface");

        detailedExplanation.setText("An Activity is a single, focused screen with a user interface. It's the entry point for user interaction with your app.\n\n" +
                "🎯 Key points about Activities:\n" +
                "• Each screen in your app is an Activity\n" +
                "• Activities manage user interaction\n" +
                "• Activities have a lifecycle (born, run, pause, die)\n" +
                "• One Activity is marked as MAIN/LAUNCHER (first screen)\n\n" +
                "📱 Example: In a messaging app:\n" +
                "• Activity 1: Contact list screen\n" +
                "• Activity 2: Chat screen\n" +
                "• Activity 3: Settings screen\n\n" +
                "All Activities must be declared in AndroidManifest.xml");

        codeExample.setText("// Basic Activity in Android\npublic class MainActivity extends AppCompatActivity {\n    \n    @Override\n    protected void onCreate(Bundle savedInstanceState) {\n        super.onCreate(savedInstanceState);\n        setContentView(R.layout.activity_main);\n        \n        // Initialize your UI components here\n        Button button = findViewById(R.id.myButton);\n        button.setOnClickListener(v -> {\n            // Handle button click\n        });\n    }\n}\n\n<!-- Manifest declaration -->\n<activity android:name=\".MainActivity\">\n    <intent-filter>\n        <action android:name=\"android.intent.action.MAIN\"/>\n        <category android:name=\"android.intent.category.LAUNCHER\"/>\n    </intent-filter>\n</activity>");

        proTip.setText("Always extend AppCompatActivity (not just Activity) for modern Android features like Toolbar support and backwards compatibility.");
    }

    private void loadLifecycleLesson2() {
        learningObjectives.setText("• Master all 7 lifecycle methods\n• Understand when each method is called\n• Know what to do in each method\n• Handle configuration changes");

        keyConcepts.setText("onCreate • onStart • onResume • onPause • onStop • onRestart • onDestroy • Lifecycle States");

        detailedExplanation.setText("Activity Lifecycle Methods (in order):\n\n" +
                "1️⃣ onCreate() - Called when Activity is first created\n" +
                "   • Initialize UI, set click listeners, bind data\n" +
                "   • Called only once\n\n" +
                "2️⃣ onStart() - Activity becomes visible to user\n" +
                "   • Activity is now visible but not interactive yet\n\n" +
                "3️⃣ onResume() - Activity starts interacting with user\n" +
                "   • Activity is in foreground, user can interact\n" +
                "   • Start animations, open cameras, etc.\n\n" +
                "4️⃣ onPause() - Another Activity is coming to foreground\n" +
                "   • Save unsaved data, stop animations\n" +
                "   • Called when leaving Activity (but still visible)\n\n" +
                "5️⃣ onStop() - Activity no longer visible\n" +
                "   • Release resources not needed while hidden\n\n" +
                "6️⃣ onRestart() - Activity was stopped, now restarting\n" +
                "   • Goes to onStart() after this\n\n" +
                "7️⃣ onDestroy() - Activity is being destroyed\n" +
                "   • Clean up all resources");

        codeExample.setText("public class LifecycleActivity extends AppCompatActivity {\n    \n    @Override\n    protected void onCreate(Bundle savedInstanceState) {\n        super.onCreate(savedInstanceState);\n        setContentView(R.layout.activity_main);\n        Log.d(\"Lifecycle\", \"onCreate called\");\n    }\n    \n    @Override\n    protected void onStart() {\n        super.onStart();\n        Log.d(\"Lifecycle\", \"Activity is now visible\");\n    }\n    \n    @Override\n    protected void onResume() {\n        super.onResume();\n        Log.d(\"Lifecycle\", \"Activity is ready for user input\");\n    }\n    \n    @Override\n    protected void onPause() {\n        super.onPause();\n        Log.d(\"Lifecycle\", \"Activity is pausing, save data here\");\n    }\n    \n    @Override\n    protected void onStop() {\n        super.onStop();\n        Log.d(\"Lifecycle\", \"Activity is no longer visible\");\n    }\n    \n    @Override\n    protected void onDestroy() {\n        super.onDestroy();\n        Log.d(\"Lifecycle\", \"Activity is being destroyed\");\n    }\n}");

        proTip.setText("Use Log.d() in each lifecycle method to understand when they're called. This helps debug unexpected behavior in your app!");
    }

    private void loadLifecycleLesson3() {
        learningObjectives.setText("• Save UI state using onSaveInstanceState\n• Restore state in onCreate/onRestoreInstanceState\n• Handle screen rotations\n• Manage configuration changes");

        keyConcepts.setText("Bundle • onSaveInstanceState • onRestoreInstanceState • State Persistence • Configuration Changes");

        detailedExplanation.setText("When configuration changes (like screen rotation), Android destroys and recreates your Activity. To preserve data:\n\n" +
                "💾 Saving State:\n" +
                "• onSaveInstanceState() is called before the Activity is destroyed\n" +
                "• Use Bundle to store key-value pairs\n" +
                "• Bundle survives configuration changes\n\n" +
                "🔄 Restoring State:\n" +
                "• Option 1: Check savedInstanceState in onCreate()\n" +
                "• Option 2: Override onRestoreInstanceState()\n\n" +
                "📦 What to save:\n" +
                "• User input (EditText content)\n" +
                "• Checkbox/RadioButton states\n" +
                "• Scroll positions\n" +
                "• Game scores\n" +
                "• Anything that would frustrate users to lose");

        codeExample.setText("public class SaveStateActivity extends AppCompatActivity {\n    \n    private int score = 0;\n    private EditText userInput;\n    \n    @Override\n    protected void onCreate(Bundle savedInstanceState) {\n        super.onCreate(savedInstanceState);\n        setContentView(R.layout.activity_main);\n        \n        userInput = findViewById(R.id.userInput);\n        \n        // Restore state if available\n        if (savedInstanceState != null) {\n            score = savedInstanceState.getInt(\"score\", 0);\n            String text = savedInstanceState.getString(\"user_text\", \"\");\n            userInput.setText(text);\n        }\n    }\n    \n    @Override\n    protected void onSaveInstanceState(Bundle outState) {\n        super.onSaveInstanceState(outState);\n        // Save data before Activity is destroyed\n        outState.putInt(\"score\", score);\n        outState.putString(\"user_text\", userInput.getText().toString());\n    }\n    \n    @Override\n    protected void onRestoreInstanceState(Bundle savedInstanceState) {\n        super.onRestoreInstanceState(savedInstanceState);\n        // Alternative way to restore\n        score = savedInstanceState.getInt(\"score\", 0);\n    }\n}");

        proTip.setText("Always call super.onSaveInstanceState() first! Android automatically saves View states (like EditText text) if they have IDs.");
    }

    private void loadLifecycleLesson4() {
        learningObjectives.setText("• Start activities for results\n• Communicate between activities\n• Use modern Activity Result API\n• Handle callback data");

        keyConcepts.setText("startActivityForResult • onActivityResult • Activity Result API • Callbacks");

        detailedExplanation.setText("Activity Communication:\n\n" +
                "When you need data back from another activity:\n\n" +
                "📱 Traditional Way (deprecated in API 30):\n" +
                "• startActivityForResult()\n" +
                "• onActivityResult()\n\n" +
                "🚀 Modern Way (Activity Result API):\n" +
                "• ActivityResultLauncher\n" +
                "• Register before create, then launch\n\n" +
                "🔄 Returning Data:\n" +
                "• Use setResult() in the child activity\n" +
                "• Pass data back via Intent\n\n" +
                "Example: Selecting a contact, taking a photo, picking a file");

        codeExample.setText("// Modern Activity Result API\nprivate ActivityResultLauncher<Intent> someActivityResultLauncher;\n\n@Override\nprotected void onCreate(Bundle savedInstanceState) {\n    super.onCreate(savedInstanceState);\n    \n    someActivityResultLauncher = registerForActivityResult(\n        new ActivityResultContracts.StartActivityForResult(),\n        result -> {\n            if (result.getResultCode() == RESULT_OK) {\n                Intent data = result.getData();\n                String result = data.getStringExtra(\"result_key\");\n                // Handle the result\n            }\n        }\n    );\n}\n\n// Launch the activity\nIntent intent = new Intent(this, ChildActivity.class);\nsomeActivityResultLauncher.launch(intent);\n\n// In ChildActivity - send result back\nIntent resultIntent = new Intent();\nresultIntent.putExtra(\"result_key\", \"some data\");\nsetResult(RESULT_OK, resultIntent);\nfinish();");

        proTip.setText("Use the new Activity Result API for all new projects. It's type-safe and works well with ViewModels!");
    }

    private void loadLifecycleLesson5() {
        learningObjectives.setText("• Understand Fragments\n• Create and manage Fragments\n• Communicate between Activity and Fragment\n• Implement Fragment transactions");

        keyConcepts.setText("Fragment • FragmentManager • FragmentTransaction • Fragment lifecycle");

        detailedExplanation.setText("Fragments:\n\n" +
                "Fragments are reusable UI components that have their own lifecycle.\n\n" +
                "📱 Why Fragments?\n" +
                "• Reusable across multiple activities\n" +
                "• Support tablet layouts (master-detail)\n" +
                "• Better separation of concerns\n\n" +
                "🔄 Fragment Lifecycle:\n" +
                "• Similar to Activity but additional methods\n" +
                "• onAttach(), onCreateView(), onViewCreated()\n\n" +
                "📦 Adding Fragments:\n" +
                "• Statically in XML\n" +
                "• Dynamically using FragmentManager\n\n" +
                "💬 Communication:\n" +
                "• Use interfaces for Activity-Fragment communication\n" +
                "• Shared ViewModel for complex data sharing");

        codeExample.setText("// Creating a Fragment\npublic class MyFragment extends Fragment {\n    @Override\n    public View onCreateView(LayoutInflater inflater, ViewGroup container,\n                             Bundle savedInstanceState) {\n        return inflater.inflate(R.layout.fragment_my, container, false);\n    }\n    \n    @Override\n    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {\n        super.onViewCreated(view, savedInstanceState);\n        // Initialize views\n    }\n}\n\n// Adding Fragment dynamically\nFragmentManager fragmentManager = getSupportFragmentManager();\nFragmentTransaction transaction = fragmentManager.beginTransaction();\ntransaction.replace(R.id.fragment_container, new MyFragment());\ntransaction.addToBackStack(null);\ntransaction.commit();\n\n// Activity-Fragment communication (interface)\npublic interface OnDataPass {\n    void onDataPass(String data);\n}\n\n// Fragment calls activity\n((OnDataPass) getActivity()).onDataPass(\"some data\");");

        proTip.setText("Use the Navigation Component with Fragments - it handles Fragment transactions automatically and visualizes navigation flow!");
    }

    // ==================== INTENTS & NAVIGATION LESSONS ====================

    private void loadIntentsLesson1() {
        learningObjectives.setText("• Understand Explicit Intents\n• Navigate between Activities\n• Pass data between screens\n• Start Activities for results");

        keyConcepts.setText("Explicit Intent • Intent Constructor • putExtra() • getIntent() • Data Passing");

        detailedExplanation.setText("Explicit Intents specify exactly which component to start. You name the target Activity class directly.\n\n" +
                "🚀 Basic Navigation:\n" +
                "Intent intent = new Intent(this, TargetActivity.class);\n" +
                "startActivity(intent);\n\n" +
                "📤 Passing Data:\n" +
                "• Use putExtra(String key, value)\n" +
                "• Supports String, int, boolean, float, double, arrays, etc.\n" +
                "• Get data with getIntent().getStringExtra(\"key\")\n\n" +
                "🔄 Getting Results:\n" +
                "• Use startActivityForResult() (deprecated)\n" +
                "• Modern way: registerForActivityResult()");

        codeExample.setText("// In FirstActivity.java - Starting SecondActivity\npublic void goToSecondActivity(View view) {\n    // Create Explicit Intent\n    Intent intent = new Intent(FirstActivity.this, SecondActivity.class);\n    \n    // Add data to pass\n    intent.putExtra(\"username\", \"JohnDoe\");\n    intent.putExtra(\"score\", 100);\n    intent.putExtra(\"isPremium\", true);\n    \n    startActivity(intent);\n}\n\n// In SecondActivity.java - Receiving data\n@Override\nprotected void onCreate(Bundle savedInstanceState) {\n    super.onCreate(savedInstanceState);\n    setContentView(R.layout.activity_second);\n    \n    // Get passed data\n    Intent intent = getIntent();\n    String username = intent.getStringExtra(\"username\");\n    int score = intent.getIntExtra(\"score\", 0);\n    boolean isPremium = intent.getBooleanExtra(\"isPremium\", false);\n    \n    TextView textView = findViewById(R.id.displayText);\n    textView.setText(\"Welcome \" + username + \"! Score: \" + score);\n}");

        proTip.setText("Use consistent key names for extras across your app. Consider creating a Constants class with public static final String keys.");
    }

    private void loadIntentsLesson2() {
        learningObjectives.setText("• Understand Implicit Intents\n• Open web pages, make calls, send emails\n• Let Android find the right app\n• Handle cases where no app exists");

        keyConcepts.setText("Implicit Intent • ACTION_VIEW • ACTION_DIAL • ACTION_SEND • Intent Chooser • Data URI");

        detailedExplanation.setText("Implicit Intents don't specify a component name. Instead, they declare an action to perform, and Android finds the appropriate app.\n\n" +
                "🌐 Common Actions:\n" +
                "• ACTION_VIEW - Open a URL or file\n" +
                "• ACTION_DIAL - Open dialer with a number\n" +
                "• ACTION_CALL - Directly call a number (needs permission)\n" +
                "• ACTION_SEND - Share content\n" +
                "• ACTION_WEB_SEARCH - Search the web\n\n" +
                "🔧 How it works:\n" +
                "1. Create Intent with action\n" +
                "2. Set data (URI) if needed\n" +
                "3. Android matches Intent to apps with Intent Filters\n" +
                "4. User chooses or system picks default\n\n" +
                "⚠️ Always check if there's an app to handle your Intent!");

        codeExample.setText("// Open a webpage\npublic void openWebpage(View view) {\n    String url = \"https://developer.android.com\";\n    Intent intent = new Intent(Intent.ACTION_VIEW);\n    intent.setData(Uri.parse(url));\n    \n    // Safety check - ensure app exists\n    if (intent.resolveActivity(getPackageManager()) != null) {\n        startActivity(intent);\n    } else {\n        Toast.makeText(this, \"No browser installed\", Toast.LENGTH_SHORT).show();\n    }\n}\n\n// Make a phone call (requires permission)\npublic void makePhoneCall(View view) {\n    String number = \"1234567890\";\n    Intent intent = new Intent(Intent.ACTION_DIAL);\n    intent.setData(Uri.parse(\"tel:\" + number));\n    startActivity(intent);\n}\n\n// Share text\npublic void shareText(View view) {\n    Intent intent = new Intent(Intent.ACTION_SEND);\n    intent.setType(\"text/plain\");\n    intent.putExtra(Intent.EXTRA_TEXT, \"Check out this awesome app!\");\n    \n    // Create chooser for better UX\n    Intent chooser = Intent.createChooser(intent, \"Share via\");\n    startActivity(chooser);\n}\n\n// Open location in maps\npublic void openMap(View view) {\n    Uri location = Uri.parse(\"geo:0,0?q=Durban,South+Africa\");\n    Intent intent = new Intent(Intent.ACTION_VIEW, location);\n    startActivity(intent);\n}");

        proTip.setText("Always use Intent.createChooser() for ACTION_SEND - it provides a better user experience and prevents crashes if no apps exist.");
    }

    private void loadIntentsLesson3() {
        learningObjectives.setText("• Pass complex data between activities\n• Use Serializable and Parcelable\n• Handle different data types\n• Best practices for data passing");

        keyConcepts.setText("putExtra • Bundle • Serializable • Parcelable • Data Transfer");

        detailedExplanation.setText("Passing different types of data:\n\n" +
                "📦 Basic Types:\n" +
                "• Strings, integers, booleans, floats, doubles\n" +
                "• Arrays of primitives\n" +
                "• ArrayList of Strings\n\n" +
                "🔧 Complex Objects (2 approaches):\n" +
                "• Serializable - Simpler but slower\n" +
                "• Parcelable - More complex but faster (Android recommended)\n\n" +
                "💡 Best Practices:\n" +
                "• Keep passed data minimal\n" +
                "• Use ViewModel for configuration changes\n" +
                "• Consider using a shared repository for large data");

        codeExample.setText("// Passing Serializable object\npublic class User implements Serializable {\n    String name;\n    int age;\n}\n\nUser user = new User();\nuser.name = \"John\";\nuser.age = 25;\nintent.putExtra(\"user\", user);\n\n// Receiving\nUser user = (User) getIntent().getSerializableExtra(\"user\");\n\n// Passing Parcelable object (Android recommended)\npublic class Person implements Parcelable {\n    String name;\n    int age;\n    \n    protected Person(Parcel in) {\n        name = in.readString();\n        age = in.readInt();\n    }\n    \n    @Override\n    public void writeToParcel(Parcel dest, int flags) {\n        dest.writeString(name);\n        dest.writeInt(age);\n    }\n}\n\n// Using Bundle for multiple values\nBundle bundle = new Bundle();\nbundle.putString(\"name\", \"John\");\nbundle.putInt(\"age\", 25);\nbundle.putBoolean(\"active\", true);\nintent.putExtras(bundle);");

        proTip.setText("Use Parcelable instead of Serializable for better performance. Android Studio can generate Parcelable code automatically!");
    }

    private void loadIntentsLesson4() {
        learningObjectives.setText("• Understand Navigation Component\n• Create navigation graphs\n• SafeArgs for type-safe data passing\n• Handle up/back navigation");

        keyConcepts.setText("Navigation Component • NavController • NavGraph • SafeArgs • NavigationUI");

        detailedExplanation.setText("The Navigation Component simplifies implementing navigation between screens:\n\n" +
                "🗺️ Key Concepts:\n" +
                "• Navigation Graph - Visual representation of all navigation paths\n" +
                "• NavHostFragment - Container for navigation\n" +
                "• NavController - Manages navigation within NavHost\n" +
                "• Destinations - Screens in your navigation graph\n\n" +
                "✅ Benefits:\n" +
                "• Automatic up/back button handling\n" +
                "• SafeArgs for type-safe data passing\n" +
                "• Deep linking support\n" +
                "• Visual navigation editor\n" +
                "• Consistent navigation behavior");

        codeExample.setText("// 1. Create navigation graph (res/navigation/nav_graph.xml)\n<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<navigation xmlns:android=\"http://schemas.android.com/apk/res/android\"\n    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n    android:id=\"@+id/nav_graph\"\n    app:startDestination=\"@id/homeFragment\">\n    \n    <fragment\n        android:id=\"@+id/homeFragment\"\n        android:name=\".HomeFragment\"\n        android:label=\"Home\">\n        <action\n            android:id=\"@+id/action_home_to_detail\"\n            app:destination=\"@id/detailFragment\" />\n    </fragment>\n</navigation>\n\n// 2. Add NavHostFragment in activity layout\n<fragment\n    android:id=\"@+id/nav_host_fragment\"\n    android:name=\"androidx.navigation.fragment.NavHostFragment\"\n    app:navGraph=\"@navigation/nav_graph\"\n    app:defaultNavHost=\"true\"\n    android:layout_width=\"match_parent\"\n    android:layout_height=\"match_parent\" />\n\n// 3. Navigate in code\nNavController navController = Navigation.findNavController(view);\nnavController.navigate(R.id.action_home_to_detail);");

        proTip.setText("Add SafeArgs plugin to your project for compile-time safety when passing data between destinations.");
    }

    private void loadIntentsLesson5() {
        learningObjectives.setText("• Implement deep linking\n• Handle incoming links\n• Best practices for navigation\n• Test navigation flows");

        keyConcepts.setText("Deep Link • App Link • Navigation Testing • Back Stack Management");

        detailedExplanation.setText("Advanced Navigation Features:\n\n" +
                "🔗 Deep Links:\n" +
                "• Allow external apps/web to open specific screens\n" +
                "• Define in manifest or navigation graph\n" +
                "• Handle incoming intents in your Activity\n\n" +
                "📱 App Links (Android 6.0+):\n" +
                "• Verify domain ownership\n" +
                "• Auto-open your app without disambiguation dialog\n\n" +
                "🧪 Testing Navigation:\n" +
                "• TestNavController for unit tests\n" +
                "• Navigation testing library\n\n" +
                "📚 Best Practices:\n" +
                "• Manage back stack properly\n" +
                "• Use ViewModel for shared data\n" +
                "• Handle deeplink edge cases");

        codeExample.setText("// Deep link in manifest\n<activity android:name=\".DetailActivity\">\n    <intent-filter>\n        <action android:name=\"android.intent.action.VIEW\" />\n        <category android:name=\"android.intent.category.DEFAULT\" />\n        <category android:name=\"android.intent.category.BROWSABLE\" />\n        <data android:scheme=\"https\"\n              android:host=\"myapp.com\"\n              android:pathPrefix=\"/detail\" />\n    </intent-filter>\n</activity>\n\n// Handling deep link in Activity\n@Override\nprotected void onCreate(Bundle savedInstanceState) {\n    super.onCreate(savedInstanceState);\n    handleDeepLink(getIntent());\n}\n\nprivate void handleDeepLink(Intent intent) {\n    Uri data = intent.getData();\n    if (data != null) {\n        String id = data.getQueryParameter(\"id\");\n        // Navigate to appropriate screen with the id\n    }\n}\n\n// Navigation best practice - clear top\nNavOptions navOptions = new NavOptions.Builder()\n    .setPopUpTo(R.id.homeFragment, true)\n    .build();\nnavController.navigate(R.id.action_to_detail, null, navOptions);");

        proTip.setText("Test your deep links using ADB: adb shell am start -W -a android.intent.action.VIEW -d \"https://myapp.com/detail?id=123\" your.package.name");
    }

    // ==================== DATA STORAGE LESSONS ====================

    private void loadStorageLesson1() {
        learningObjectives.setText("• Understand SharedPreferences\n• Save user settings and preferences\n• Read and write key-value pairs\n• Best practices for preferences");

        keyConcepts.setText("SharedPreferences • SharedPreferences.Editor • apply() • commit() • MODE_PRIVATE");

        detailedExplanation.setText("SharedPreferences is ideal for storing small amounts of primitive data like user preferences and settings:\n\n" +
                "💾 Features:\n" +
                "• Stores data as key-value pairs\n" +
                "• Data persists across app restarts\n" +
                "• Simple API\n" +
                "• Automatically saved to XML file\n\n" +
                "📦 Data types supported:\n" +
                "• String\n" +
                "• int, long, float\n" +
                "• boolean\n\n" +
                "⚠️ Limitations:\n" +
                "• NOT for large amounts of data\n" +
                "• NOT for structured data\n" +
                "• Performance degrades with many entries");

        codeExample.setText("// Getting SharedPreferences instance\nSharedPreferences prefs = getSharedPreferences(\"MyAppPrefs\", MODE_PRIVATE);\n\n// Writing data\nSharedPreferences.Editor editor = prefs.edit();\neditor.putString(\"username\", \"JohnDoe\");\neditor.putInt(\"highScore\", 1000);\neditor.putBoolean(\"soundEnabled\", true);\neditor.apply();  // Async write (preferred)\n\n// Reading data\nString username = prefs.getString(\"username\", \"defaultUser\");\nint highScore = prefs.getInt(\"highScore\", 0);\nboolean soundEnabled = prefs.getBoolean(\"soundEnabled\", true);\n\n// Remove a single value\neditor.remove(\"username\").apply();\n\n// Clear all values\neditor.clear().apply();");

        proTip.setText("Use apply() instead of commit() whenever possible. apply() is asynchronous and doesn't block the UI thread.");
    }

    private void loadStorageLesson2() {
        learningObjectives.setText("• Save files to internal storage\n• Read files from internal storage\n• Understand file permissions\n• Best practices for file I/O");

        keyConcepts.setText("Internal Storage • FileOutputStream • FileInputStream • openFileOutput • fileList");

        detailedExplanation.setText("Internal Storage is private storage for your app:\n\n" +
                "📁 Features:\n" +
                "• Always available\n" +
                "• Private to your app (other apps can't access)\n" +
                "• Files deleted when app is uninstalled\n" +
                "• No permissions required\n\n" +
                "📂 Useful methods:\n" +
                "• openFileOutput() - Write a file\n" +
                "• openFileInput() - Read a file\n" +
                "• fileList() - List all files\n" +
                "• deleteFile() - Delete a file\n\n" +
                "💡 Use for:\n" +
                "• User data that shouldn't be shared\n" +
                "• Cache files\n" +
                "• Temporary data");

        codeExample.setText("// Writing to internal storage\npublic void saveToInternalStorage(String fileName, String content) {\n    try (FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE)) {\n        fos.write(content.getBytes());\n        Toast.makeText(this, \"Saved\", Toast.LENGTH_SHORT).show();\n    } catch (IOException e) {\n        e.printStackTrace();\n    }\n}\n\n// Reading from internal storage\npublic String readFromInternalStorage(String fileName) {\n    StringBuilder content = new StringBuilder();\n    try (FileInputStream fis = openFileInput(fileName)) {\n        InputStreamReader isr = new InputStreamReader(fis);\n        BufferedReader br = new BufferedReader(isr);\n        String line;\n        while ((line = br.readLine()) != null) {\n            content.append(line);\n        }\n    } catch (IOException e) {\n        e.printStackTrace();\n    }\n    return content.toString();\n}\n\n// List all internal files\npublic void listInternalFiles() {\n    String[] files = fileList();\n    for (String file : files) {\n        Log.d(\"Files\", \"File: \" + file);\n    }\n}\n\n// Delete a file\ndeleteFile(\"myfile.txt\");");

        proTip.setText("Always use try-with-resources (API 19+) for automatic file closing to prevent memory leaks.");
    }

    private void loadStorageLesson3() {
        learningObjectives.setText("• Save files to external storage\n• Handle storage permissions\n• Check storage availability\n• Best practices for external storage");

        keyConcepts.setText("External Storage • Permissions • WRITE_EXTERNAL_STORAGE • READ_EXTERNAL_STORAGE • MediaStore");

        detailedExplanation.setText("External Storage (SD card) requires special handling:\n\n" +
                "💾 Features:\n" +
                "• May not always be available\n" +
                "• Can be accessed by other apps (with permission)\n" +
                "• Files may remain after uninstall\n" +
                "• Requires permission declaration\n\n" +
                "⚠️ Android 10+ (API 29) changes:\n" +
                "• Scoped storage restricts access\n" +
                "• Use MediaStore API for media files\n" +
                "• Request MANAGE_EXTERNAL_STORAGE for broad access\n\n" +
                "🔒 Permissions needed:\n" +
                "• WRITE_EXTERNAL_STORAGE (dangerous permission)\n" +
                "• READ_EXTERNAL_STORAGE");

        codeExample.setText("// Check external storage availability\npublic boolean isExternalStorageWritable() {\n    String state = Environment.getExternalStorageState();\n    return Environment.MEDIA_MOUNTED.equals(state);\n}\n\n// Save to external storage (public directory)\npublic void saveToExternalStorage(String fileName, String content) {\n    if (isExternalStorageWritable()) {\n        File file = new File(Environment.getExternalStoragePublicDirectory(\n            Environment.DIRECTORY_DOCUMENTS), fileName);\n        try (FileWriter fw = new FileWriter(file)) {\n            fw.write(content);\n            Toast.makeText(this, \"Saved to \" + file.getPath(), Toast.LENGTH_SHORT).show();\n        } catch (IOException e) {\n            e.printStackTrace();\n        }\n    } else {\n        Toast.makeText(this, \"External storage not available\", Toast.LENGTH_SHORT).show();\n    }\n}\n\n// Save to app-specific external storage (no permission needed)\npublic void saveToAppSpecificStorage(String fileName, String content) {\n    File file = new File(getExternalFilesDir(null), fileName);\n    try (FileWriter fw = new FileWriter(file)) {\n        fw.write(content);\n    } catch (IOException e) {\n        e.printStackTrace();\n    }\n}\n\n// Request permissions (Android 6.0+)\nif (ContextCompat.checkSelfPermission(this, \n    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {\n    ActivityCompat.requestPermissions(this,\n        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},\n        REQUEST_CODE);\n}");

        proTip.setText("Use app-specific external storage (getExternalFilesDir()) when possible - it doesn't require permissions and is automatically deleted when the app is uninstalled.");
    }

    private void loadStorageLesson4() {
        learningObjectives.setText("• Understand SQLite databases\n• Create and manage databases\n• Perform CRUD operations\n• Use SQLiteOpenHelper");

        keyConcepts.setText("SQLite • SQLiteOpenHelper • onCreate • onUpgrade • CRUD • ContentValues");

        detailedExplanation.setText("SQLite is a lightweight, embedded relational database:\n\n" +
                "📊 Why SQLite?\n" +
                "• Zero configuration\n" +
                "• Portable - single file database\n" +
                "• Transactional - ACID compliant\n" +
                "• Standard SQL syntax\n" +
                "• Built into Android\n\n" +
                "🏗️ Database Architecture:\n" +
                "1. SQLiteOpenHelper - Manages DB creation and versioning\n" +
                "2. onCreate() - Called when DB first created\n" +
                "3. onUpgrade() - Called when DB version changes\n" +
                "4. SQLiteDatabase - Main interface\n\n" +
                "📝 CRUD Operations:\n" +
                "• Create (INSERT)\n" +
                "• Read (SELECT)\n" +
                "• Update (UPDATE)\n" +
                "• Delete (DELETE)");

        codeExample.setText("// Database Helper\npublic class DatabaseHelper extends SQLiteOpenHelper {\n    \n    private static final String DATABASE_NAME = \"myapp.db\";\n    private static final int DATABASE_VERSION = 1;\n    \n    public DatabaseHelper(Context context) {\n        super(context, DATABASE_NAME, null, DATABASE_VERSION);\n    }\n    \n    @Override\n    public void onCreate(SQLiteDatabase db) {\n        String createTable = \"CREATE TABLE users (\" +\n                \"id INTEGER PRIMARY KEY AUTOINCREMENT,\" +\n                \"name TEXT NOT NULL,\" +\n                \"email TEXT UNIQUE,\" +\n                \"age INTEGER)\";\n        db.execSQL(createTable);\n    }\n    \n    @Override\n    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {\n        db.execSQL(\"DROP TABLE IF EXISTS users\");\n        onCreate(db);\n    }\n}\n\n// Insert data\npublic long insertUser(String name, String email, int age) {\n    SQLiteDatabase db = dbHelper.getWritableDatabase();\n    ContentValues values = new ContentValues();\n    values.put(\"name\", name);\n    values.put(\"email\", email);\n    values.put(\"age\", age);\n    long newRowId = db.insert(\"users\", null, values);\n    db.close();\n    return newRowId;\n}\n\n// Query data\npublic Cursor getAllUsers() {\n    SQLiteDatabase db = dbHelper.getReadableDatabase();\n    return db.query(\"users\", null, null, null, null, null, null);\n}\n\n// Update data\npublic int updateUser(int id, String newName) {\n    SQLiteDatabase db = dbHelper.getWritableDatabase();\n    ContentValues values = new ContentValues();\n    values.put(\"name\", newName);\n    return db.update(\"users\", values, \"id = ?\", new String[]{String.valueOf(id)});\n}\n\n// Delete data\npublic int deleteUser(int id) {\n    SQLiteDatabase db = dbHelper.getWritableDatabase();\n    return db.delete(\"users\", \"id = ?\", new String[]{String.valueOf(id)});\n}");

        proTip.setText("Always close your Cursors and Database connections to prevent memory leaks. Use try-catch-finally or try-with-resources.");
    }

    private void loadStorageLesson5() {
        learningObjectives.setText("• Master advanced SQLite queries\n• Understand Room Persistence Library\n• Migrate from SQLite to Room\n• Database best practices");

        keyConcepts.setText("Room • DAO • Entity • TypeConverter • Migration • Coroutines");

        detailedExplanation.setText("Advanced Database Concepts:\n\n" +
                "📈 Advanced SQLite:\n" +
                "• JOIN operations across tables\n" +
                "• INDEX for performance\n" +
                "• Transactions for batch operations\n" +
                "• Raw queries with rawQuery()\n\n" +
                "🏠 Room Library (Recommended):\n" +
                "• Compile-time SQL verification\n" +
                "• LiveData/Flow integration\n" +
                "• Coroutines support\n" +
                "• Automatic migrations\n\n" +
                "🏗️ Room Components:\n" +
                "• @Entity - Defines table structure\n" +
                "• @Dao - Data Access Object (CRUD methods)\n" +
                "• @Database - Database holder\n\n" +
                "✅ Best Practices:\n" +
                "• Use indices on frequently queried columns\n" +
                "• Run database operations on background threads\n" +
                "• Use transactions for multiple operations\n" +
                "• Version your database schema");

        codeExample.setText("// Room Entity\n@Entity(tableName = \"users\")\npublic class User {\n    @PrimaryKey(autoGenerate = true)\n    public int id;\n    \n    @ColumnInfo(name = \"full_name\")\n    public String name;\n    \n    public String email;\n    public int age;\n}\n\n// Room DAO\n@Dao\npublic interface UserDao {\n    @Insert(onConflict = OnConflictStrategy.REPLACE)\n    void insert(User user);\n    \n    @Update\n    void update(User user);\n    \n    @Delete\n    void delete(User user);\n    \n    @Query(\"SELECT * FROM users WHERE age > :minAge\")\n    LiveData<List<User>> getUsersOlderThan(int minAge);\n    \n    @Query(\"SELECT * FROM users WHERE name LIKE '%' || :search || '%'\")\n    List<User> searchUsers(String search);\n    \n    @Query(\"DELETE FROM users WHERE age < :maxAge\")\n    int deleteOldUsers(int maxAge);\n}\n\n// Room Database\n@Database(entities = {User.class}, version = 1)\npublic abstract class AppDatabase extends RoomDatabase {\n    public abstract UserDao userDao();\n}\n\n// Using Room\nAppDatabase db = Room.databaseBuilder(getApplicationContext(),\n    AppDatabase.class, \"myapp.db\").build();\n\n// Insert on background thread\nnew Thread(() -> {\n    User user = new User();\n    user.name = \"John\";\n    user.email = \"john@example.com\";\n    db.userDao().insert(user);\n}).start();\n\n// Advanced query with JOIN\n@Query(\"SELECT users.* FROM users \" +\n       \"INNER JOIN orders ON users.id = orders.user_id \" +\n       \"WHERE orders.total > 100\")\nLiveData<List<User>> getUsersWithLargeOrders();\n\n// Transaction example\n@Transaction\n@Query(\"SELECT * FROM users\")\npublic List<UserAndOrders> getUsersWithOrders();");

        proTip.setText("Use Room instead of raw SQLite - it provides compile-time verification, removes boilerplate code, and integrates seamlessly with LiveData and Coroutines!");
    }

    // ==================== RECYCLERVIEW LESSONS ====================

    private void loadRecyclerViewLesson1() {
        learningObjectives.setText("• Understand RecyclerView\n• Know why RecyclerView is efficient\n• Set up basic RecyclerView\n• Understand view recycling");

        keyConcepts.setText("RecyclerView • View Recycling • Performance • Large Datasets");

        detailedExplanation.setText("RecyclerView is a flexible view for displaying large data sets efficiently:\n\n" +
                "🎯 Why RecyclerView?\n" +
                "• Recycles views - only creates visible items\n" +
                "• Better performance than ListView\n" +
                "• Separates concerns (Layout, Data, Display)\n" +
                "• Built-in animations\n\n" +
                "📦 Components:\n" +
                "• RecyclerView - The container\n" +
                "• Adapter - Provides data and creates views\n" +
                "• ViewHolder - Holds references to views\n" +
                "• LayoutManager - Arranges items\n\n" +
                "🔄 How Recycling Works:\n" +
                "• When an item scrolls off-screen, its View is recycled\n" +
                "• Recycled View is reused for the new item\n" +
                "• Saves memory and improves scrolling performance");

        codeExample.setText("<!-- RecyclerView in XML -->\n<androidx.recyclerview.widget.RecyclerView\n    android:id=\"@+id/recyclerView\"\n    android:layout_width=\"match_parent\"\n    android:layout_height=\"match_parent\"\n    app:layoutManager=\"androidx.recyclerview.widget.LinearLayoutManager\" />\n\n// Basic setup in Activity\nRecyclerView recyclerView = findViewById(R.id.recyclerView);\nrecyclerView.setLayoutManager(new LinearLayoutManager(this));\nMyAdapter adapter = new MyAdapter(dataList);\nrecyclerView.setAdapter(adapter);\n\n// Item layout (item_layout.xml)\n<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n    android:layout_width=\"match_parent\"\n    android:layout_height=\"wrap_content\"\n    android:padding=\"16dp\">\n    \n    <TextView\n        android:id=\"@+id/itemText\"\n        android:layout_width=\"wrap_content\"\n        android:layout_height=\"wrap_content\"\n        android:textSize=\"16sp\"/>\n</LinearLayout>");

        proTip.setText("RecyclerView is the standard for displaying lists in Android. Always use it instead of ListView for new projects!");
    }

    private void loadRecyclerViewLesson2() {
        learningObjectives.setText("• Create custom adapters\n• Implement ViewHolder pattern\n• Bind data to views\n• Handle item click events");

        keyConcepts.setText("Adapter • ViewHolder • onBindViewHolder • onCreateViewHolder • getItemCount");

        detailedExplanation.setText("Adapter and ViewHolder are the core of RecyclerView:\n\n" +
                "🏗️ Adapter Responsibilities:\n" +
                "• onCreateViewHolder() - Create new ViewHolder instances\n" +
                "• onBindViewHolder() - Bind data to views\n" +
                "• getItemCount() - Return total number of items\n\n" +
                "📦 ViewHolder Pattern:\n" +
                "• Holds references to views in an item\n" +
                "• Prevents expensive findViewById() calls\n" +
                "• Significantly improves scrolling performance\n\n" +
                "🔘 Handling Clicks:\n" +
                "• Set click listeners in ViewHolder\n" +
                "• Use interface callbacks to communicate clicks\n" +
                "• Pass position to listener");

        codeExample.setText("public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {\n    \n    private List<String> data;\n    private OnItemClickListener listener;\n    \n    public interface OnItemClickListener {\n        void onItemClick(int position);\n    }\n    \n    public MyAdapter(List<String> data, OnItemClickListener listener) {\n        this.data = data;\n        this.listener = listener;\n    }\n    \n    @NonNull\n    @Override\n    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {\n        View view = LayoutInflater.from(parent.getContext())\n            .inflate(R.layout.item_layout, parent, false);\n        return new ViewHolder(view);\n    }\n    \n    @Override\n    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {\n        holder.bind(data.get(position), position, listener);\n    }\n    \n    @Override\n    public int getItemCount() {\n        return data.size();\n    }\n    \n    static class ViewHolder extends RecyclerView.ViewHolder {\n        TextView textView;\n        \n        ViewHolder(View itemView) {\n            super(itemView);\n            textView = itemView.findViewById(R.id.itemText);\n        }\n        \n        void bind(String item, int position, OnItemClickListener listener) {\n            textView.setText(item);\n            itemView.setOnClickListener(v -> listener.onItemClick(position));\n        }\n    }\n}\n\n// Usage\nMyAdapter adapter = new MyAdapter(dataList, position -> {\n    // Handle click at position\n    Toast.makeText(this, \"Clicked: \" + dataList.get(position), Toast.LENGTH_SHORT).show();\n});\nrecyclerView.setAdapter(adapter);");

        proTip.setText("Always use ViewHolder pattern - it's automatically required by RecyclerView and prevents findViewById() calls while scrolling.");
    }

    private void loadRecyclerViewLesson3() {
        learningObjectives.setText("• Use different LayoutManagers\n• Implement GridLayoutManager\n• Create staggered grid layouts\n• Change layout dynamically");

        keyConcepts.setText("LinearLayoutManager • GridLayoutManager • StaggeredGridLayoutManager • Layout Switching");

        detailedExplanation.setText("LayoutManagers control how items are arranged:\n\n" +
                "📱 LinearLayoutManager:\n" +
                "• Displays items in a single row or column\n" +
                "• setOrientation() for VERTICAL or HORIZONTAL\n" +
                "• setReverseLayout() to reverse order\n\n" +
                "🗂️ GridLayoutManager:\n" +
                "• Displays items in a grid pattern\n" +
                "• Specify number of columns/rows\n" +
                "• Support for headers/footers\n\n" +
                "📐 StaggeredGridLayoutManager:\n" +
                "• Items with different heights/widths\n" +
                "• Pinterest-style layout\n" +
                "• Can be vertical or horizontal\n\n" +
                "🔄 Dynamic Layout Switching:\n" +
                "• Change LayoutManager at runtime\n" +
                "• Maintain scroll position with onSaveInstanceState");

        codeExample.setText("// LinearLayoutManager - Vertical list\nLinearLayoutManager linearManager = new LinearLayoutManager(this);\nlinearManager.setOrientation(LinearLayoutManager.VERTICAL);\nrecyclerView.setLayoutManager(linearManager);\n\n// LinearLayoutManager - Horizontal list\nLinearLayoutManager horizontalManager = new LinearLayoutManager(this);\nhorizontalManager.setOrientation(LinearLayoutManager.HORIZONTAL);\nrecyclerView.setLayoutManager(horizontalManager);\n\n// GridLayoutManager - 2 columns\nGridLayoutManager gridManager = new GridLayoutManager(this, 2);\nrecyclerView.setLayoutManager(gridManager);\n\n// GridLayoutManager with span size for different items\ngridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {\n    @Override\n    public int getSpanSize(int position) {\n        // Make item at position 0 take full width\n        return position == 0 ? 2 : 1;\n    }\n});\n\n// StaggeredGridLayoutManager\nStaggeredGridLayoutManager staggeredManager = \n    new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);\nrecyclerView.setLayoutManager(staggeredManager);\n\n// Switch layout at runtime\nButton switchButton = findViewById(R.id.switchLayout);\nboolean isGrid = false;\nswitchButton.setOnClickListener(v -> {\n    if (isGrid) {\n        recyclerView.setLayoutManager(new LinearLayoutManager(this));\n    } else {\n        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));\n    }\n    isGrid = !isGrid;\n});");

        proTip.setText("Save and restore LayoutManager state when switching between layouts to keep user's scroll position!");
    }

    private void loadRecyclerViewLesson4() {
        learningObjectives.setText("• Add item decorations\n• Implement item animations\n• Customize item dividers\n• Add spacing between items");

        keyConcepts.setText("ItemDecoration • DividerItemDecoration • Animation • DefaultItemAnimator");

        detailedExplanation.setText("Enhance RecyclerView with decorations and animations:\n\n" +
                "🎨 ItemDecorations:\n" +
                "• Add dividers between items\n" +
                "• Add spacing/margins\n" +
                "• Draw custom overlays\n" +
                "• Implement custom decoration by extending ItemDecoration\n\n" +
                "✨ Animations:\n" +
                "• DefaultItemAnimator provides basic animations\n" +
                "• Custom animators for add/remove/move\n" +
                "• Animate appearance of items\n" +
                "• Cross-fade, slide, fade-in effects\n\n" +
                "📏 Spacing:\n" +
                "• Use MarginItemDecoration for consistent spacing\n" +
                "• Handle edge cases (first/last item)\n" +
                "• Responsive spacing for different layouts");

        codeExample.setText("// Built-in divider\nDividerItemDecoration divider = new DividerItemDecoration(this,\n    DividerItemDecoration.VERTICAL);\ndivider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));\nrecyclerView.addItemDecoration(divider);\n\n// Custom spacing decorator\npublic class SpacingDecoration extends RecyclerView.ItemDecoration {\n    private int spacing;\n    \n    public SpacingDecoration(int spacing) {\n        this.spacing = spacing;\n    }\n    \n    @Override\n    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,\n                               RecyclerView.State state) {\n        int position = parent.getChildAdapterPosition(view);\n        \n        // Add spacing except for last item\n        if (position != parent.getAdapter().getItemCount() - 1) {\n            outRect.bottom = spacing;\n        }\n        \n        // Add left and right spacing for GridLayout\n        if (parent.getLayoutManager() instanceof GridLayoutManager) {\n            outRect.left = spacing;\n            outRect.right = spacing;\n            outRect.top = spacing;\n        }\n    }\n}\n\n// Apply spacing\nrecyclerView.addItemDecoration(new SpacingDecoration(16));\n\n// Custom animations\nRecyclerView.ItemAnimator animator = new DefaultItemAnimator();\nanimator.setAddDuration(300);\nanimator.setRemoveDuration(300);\nanimator.setMoveDuration(300);\nrecyclerView.setItemAnimator(animator);\n\n// Animate adapter changes\nadapter.notifyItemInserted(position);\nadapter.notifyItemRemoved(position);\nadapter.notifyItemMoved(fromPos, toPos);\n\n// Custom item animation with LayoutTransition\nTransition transition = new AutoTransition();\ntransition.setDuration(300);\nLayoutTransition layoutTransition = new LayoutTransition();\nlayoutTransition.setAnimator(LayoutTransition.CHANGE_APPEARING, transition);\nrecyclerView.setLayoutTransition(layoutTransition);");

        proTip.setText("Use notifyItemInserted() and notifyItemRemoved() instead of notifyDataSetChanged() - they provide better animations and performance!");
    }

    private void loadRecyclerViewLesson5() {
        learningObjectives.setText("• Implement swipe to delete\n• Add drag and drop reordering\n• Create endless scrolling\n• Handle different view types");

        keyConcepts.setText("ItemTouchHelper • Swipe to Delete • Drag and Drop • Pagination • Multiple View Types");

        detailedExplanation.setText("Advanced RecyclerView Features:\n\n" +
                "🔀 Drag and Drop:\n" +
                "• ItemTouchHelper for drag/swipe\n" +
                "• SimpleCallback for callbacks\n" +
                "• Update adapter data after drag\n\n" +
                "🗑️ Swipe to Delete:\n" +
                "• Swipe left/right to delete\n" +
                "• Undo functionality\n" +
                "• Background color change on swipe\n\n" +
                "📄 Endless/Pagination Scrolling:\n" +
                "• Detect scroll end\n" +
                "• Load more data from network/database\n" +
                "• Show loading indicator\n\n" +
                "🎯 Multiple View Types:\n" +
                "• Different layouts for different items\n" +
                "• getItemViewType() method\n" +
                "• Headers, footers, loading states");

        codeExample.setText("// Swipe to delete and drag to reorder\nItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(\n    ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END,\n    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {\n    \n    @Override\n    public boolean onMove(@NonNull RecyclerView recyclerView, \n                         @NonNull RecyclerView.ViewHolder dragged,\n                         @NonNull RecyclerView.ViewHolder target) {\n        int from = dragged.getAdapterPosition();\n        int to = target.getAdapterPosition();\n        adapter.moveItem(from, to);\n        return true;\n    }\n    \n    @Override\n    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {\n        int position = viewHolder.getAdapterPosition();\n        adapter.removeItem(position);\n        \n        // Show undo snackbar\n        Snackbar.make(recyclerView, \"Item deleted\", Snackbar.LENGTH_LONG)\n            .setAction(\"Undo\", v -> adapter.restoreItem(position))\n            .show();\n    }\n    \n    @Override\n    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {\n        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {\n            viewHolder.itemView.setAlpha(0.7f);\n        }\n        super.onSelectedChanged(viewHolder, actionState);\n    }\n    \n    @Override\n    public void clearView(@NonNull RecyclerView recyclerView, \n                         @NonNull RecyclerView.ViewHolder viewHolder) {\n        super.clearView(recyclerView, viewHolder);\n        viewHolder.itemView.setAlpha(1.0f);\n    }\n};\n\nItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);\nitemTouchHelper.attachToRecyclerView(recyclerView);\n\n// Endless scrolling\nrecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {\n    @Override\n    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {\n        super.onScrolled(recyclerView, dx, dy);\n        \n        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();\n        int visibleItemCount = layoutManager.getChildCount();\n        int totalItemCount = layoutManager.getItemCount();\n        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();\n        \n        if (!isLoading && !isLastPage) {\n            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount\n                && firstVisibleItemPosition >= 0) {\n                loadMoreData();\n            }\n        }\n    }\n});\n\n// Multiple view types\n@Override\npublic int getItemViewType(int position) {\n    if (position == 0) return TYPE_HEADER;\n    if (isLoading && position == getItemCount() - 1) return TYPE_LOADING;\n    return TYPE_NORMAL;\n}\n\n@Override\npublic RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {\n    if (viewType == TYPE_HEADER) {\n        View view = LayoutInflater.from(parent.getContext())\n            .inflate(R.layout.header_layout, parent, false);\n        return new HeaderViewHolder(view);\n    } else if (viewType == TYPE_LOADING) {\n        View view = LayoutInflater.from(parent.getContext())\n            .inflate(R.layout.loading_layout, parent, false);\n        return new LoadingViewHolder(view);\n    } else {\n        View view = LayoutInflater.from(parent.getContext())\n            .inflate(R.layout.item_layout, parent, false);\n        return new NormalViewHolder(view);\n    }\n}");

        proTip.setText("For swipe to delete, always provide an Undo option - users appreciate being able to recover accidentally deleted items!");
    }

    // ==================== OS ARCHITECTURE LESSONS ====================

    private void loadOSLesson1() {
        learningObjectives.setText("• Understand Android software stack\n• Learn the layered architecture\n• Know each layer's responsibility\n• See how layers interact");

        keyConcepts.setText("Linux Kernel • Libraries • Android Runtime • Framework • Applications");

        detailedExplanation.setText("Android has a layered architecture:\n\n" +
                "🏗️ 5 Layers (Bottom to Top):\n\n" +
                "1️⃣ Linux Kernel Layer:\n" +
                "• Hardware abstraction\n" +
                "• Memory management\n" +
                "• Process management\n" +
                "• Device drivers\n\n" +
                "2️⃣ Hardware Abstraction Layer (HAL):\n" +
                "• Standard interfaces for hardware\n" +
                "• Camera, audio, sensors, etc.\n\n" +
                "3️⃣ Libraries & Android Runtime:\n" +
                "• C/C++ libraries (OpenGL, SQLite, WebKit)\n" +
                "• Dalvik/ART virtual machine\n\n" +
                "4️⃣ Application Framework:\n" +
                "• Activity Manager, Content Providers\n" +
                "• View System, Notification Manager\n\n" +
                "5️⃣ Applications Layer:\n" +
                "• Your apps and system apps\n" +
                "• Contacts, Phone, Browser");

        codeExample.setText("┌─────────────────────────────────────────┐\n│           APPLICATIONS                    │\n│  (Your App, Phone, Contacts, Browser)     │\n├─────────────────────────────────────────┤\n│         APPLICATION FRAMEWORK            │\n│  (Activity Mgr, Content Providers,       │\n│   View System, Notification Mgr)         │\n├─────────────────────────────────────────┤\n│     LIBRARIES       │   ANDROID RUNTIME  │\n│  (OpenGL, SQLite,   │   (Dalvik/ART)     │\n│   WebKit, SSL)      │   Core Libraries   │\n├─────────────────────────────────────────┤\n│           LINUX KERNEL                    │\n│  (Drivers, Memory, Process Management)   │\n└─────────────────────────────────────────┘\n\n// Each layer has specific responsibilities\n// Upper layers depend on lower layers");

        proTip.setText("Understanding Android architecture helps you build better apps and debug issues more effectively!");
    }

    private void loadOSLesson2() {
        learningObjectives.setText("• Understand Linux Kernel role\n• Learn hardware abstraction\n• Explore device drivers\n• Understand kernel services");

        keyConcepts.setText("Linux Kernel • HAL • Device Drivers • Memory Management • Process Scheduling");

        detailedExplanation.setText("The Linux Kernel is the foundation of Android:\n\n" +
                "⚙️ Core Services:\n" +
                "• Process Management - scheduling and lifecycle\n" +
                "• Memory Management - buddy system, slabs, PMEM\n" +
                "• Device Drivers - camera, Bluetooth, Wi-Fi, audio\n" +
                "• Power Management - wakelocks, suspend/resume\n" +
                "• Security - SELinux, Tomoyo, SMACK\n\n" +
                "🔧 Hardware Abstraction Layer (HAL):\n" +
                "• Standard interfaces for hardware vendors\n" +
                "• Camera HAL, Audio HAL, Sensors HAL\n" +
                "• Allows Android to run on different hardware\n\n" +
                "📊 Memory Management:\n" +
                "• Buddy allocator for physical pages\n" +
                "• Slab allocator for small objects\n" +
                "• OOM (Out of Memory) killer");

        codeExample.setText("// Kernel version check\n$ adb shell cat /proc/version\nLinux version 4.14.xxx\n\n// Memory info\n$ adb shell cat /proc/meminfo\nMemTotal:        5829148 kB\nMemFree:          124568 kB\n\n// CPU info\n$ adb shell cat /proc/cpuinfo\nProcessor       : AArch64\n\n// Kernel modules\n$ adb shell ls /system/lib/modules/\n\n// Wakelocks (power management)\n$ adb shell cat /sys/power/wake_lock\n\n// SELinux status\n$ adb shell getenforce\nEnforcing\n\n// How Android uses kernel features:\n// - Processes run in isolated sandboxes\n// - Each app has unique UID\n// - Permissions enforced at kernel level");

        proTip.setText("The kernel is why Android is secure - each app runs as a separate Linux user, providing isolation between apps.");
    }

    private void loadOSLesson3() {
        learningObjectives.setText("• Understand Dalvik Virtual Machine\n• Learn about ART (Android Runtime)\n• Compare JIT vs AOT compilation\n• Know the evolution");

        keyConcepts.setText("Dalvik • ART • JIT • AOT • DEX • Bytecode");

        detailedExplanation.setText("Android Runtime Evolution:\n\n" +
                "📱 Dalvik (Android 1.0 - 4.4):\n" +
                "• Just-In-Time (JIT) compilation\n" +
                "• Compiled code on-the-fly\n" +
                "• Used DEX bytecode\n" +
                "• Lower memory footprint\n\n" +
                "🚀 ART (Android 5.0+):\n" +
                "• Initially used Ahead-Of-Time (AOT)\n" +
                "• Pre-compiles at install time\n" +
                "• Better performance\n" +
                "• Larger install size\n\n" +
                "⚡ Modern ART (Android 7.0+):\n" +
                "• Hybrid approach\n" +
                "• JIT + AOT together\n" +
                "• Profile-guided optimization\n" +
                "• Best of both worlds\n\n" +
                "📦 DEX Files:\n" +
                "• Java → .class → .dex\n" +
                "• Optimized for mobile devices");

        codeExample.setText("// DEX compilation process\n.java file → javac → .class files → dx/d8 → .dex\n\n// Run on Dalvik (historical)\n$ dalvikvm -cp app.dex MainClass\n\n// Run on ART\n$ app_process /system/bin MainClass\n\n// Check runtime\n$ adb shell getprop persist.sys.dalvik.vm.lib\nlibart.so  // libdvm.so for Dalvik\n\n// ART optimizations:\n// - AOT compilation at install time\n// - Profile-guided optimization\n// - Better garbage collection\n// - Improved debugging support\n\n// Multi-dex support (when method count > 65535)\nandroid {\n    defaultConfig {\n        multiDexEnabled true\n    }\n}\n\n// DEX optimization flags\n--compiler-filter=speed  // Maximum performance\n--compiler-filter=space  // Smaller size\n--compiler-filter=balanced  // Balance of both");

        proTip.setText("Modern ART's hybrid JIT/AOT approach gives you fast app startup (JIT) and good long-term performance (AOT) - best of both worlds!");
    }

    private void loadOSLesson4() {
        learningObjectives.setText("• Understand Application Framework\n• Learn key framework services\n• Know how components interact\n• Use system services effectively");

        keyConcepts.setText("Activity Manager • Content Provider • View System • Notification Manager • Package Manager");

        detailedExplanation.setText("The Application Framework provides services to your app:\n\n" +
                "🏛️ Key Framework Components:\n\n" +
                "📱 Activity Manager:\n" +
                "• Manages activity lifecycle\n" +
                "• Controls back stack\n" +
                "• Handles process death\n\n" +
                "📦 Content Providers:\n" +
                "• Manage shared data\n" +
                "• Implement CRUD operations\n" +
                "• Data access abstraction\n\n" +
                "🎨 View System:\n" +
                "• Manages UI components\n" +
                "• Handles layout and rendering\n" +
                "• Touch event distribution\n\n" +
                "🔔 Notification Manager:\n" +
                "• Display notifications\n" +
                "• Manage notification channels\n" +
                "• Heads-up notifications\n\n" +
                "📦 Package Manager:\n" +
                "• Information about installed apps\n" +
                "• Permission management\n" +
                "• Intent resolution");

        codeExample.setText("// Using Activity Manager\nActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);\nList<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();\n\n// Using Package Manager\nPackageManager pm = getPackageManager();\nIntent intent = new Intent(Intent.ACTION_VIEW);\nList<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);\n\n// Using Notification Manager\nNotificationManager notificationManager = getSystemService(NotificationManager.class);\nNotificationChannel channel = new NotificationChannel(\"channel_id\", \"Channel Name\",\n    NotificationManager.IMPORTANCE_HIGH);\nnotificationManager.createNotificationChannel(channel);\n\n// Framework service architecture\n// Your App → Framework Service → System Server → Kernel\n// Example: startActivity() → Activity Manager Service → Zygote → New Process\n\n// Binder IPC (Inter-Process Communication)\n// Framework services use Binder for communication\nIBinder binder = ServiceManager.getService(\"activity\");\nIActivityManager am = IActivityManager.Stub.asInterface(binder);");

        proTip.setText("Use system services through getSystemService() - they handle complex system interactions so you don't have to!");
    }

    private void loadOSLesson5() {
        learningObjectives.setText("• Understand Android security model\n• Learn permission system\n• Explore app sandboxing\n• Implement security best practices");

        keyConcepts.setText("Sandbox • Permissions • SELinux • Signature Verification • Data Encryption");

        detailedExplanation.setText("Android Security Features:\n\n" +
                "🔒 App Sandbox:\n" +
                "• Each app runs in separate Linux process\n" +
                "• Unique UID per app\n" +
                "• Files isolated by default\n" +
                "• Cannot access other apps' data\n\n" +
                "📜 Permission System:\n" +
                "• Normal permissions (auto-granted)\n" +
                "• Dangerous permissions (runtime)\n" +
                "• Signature permissions\n" +
                "• Permission groups\n\n" +
                "🛡️ SELinux:\n" +
                "• Mandatory Access Control\n" +
                "• Enforcing mode prevents violations\n" +
                "• Policy-based security\n\n" +
                "🔐 Security Best Practices:\n" +
                "• Use ProGuard/R8 for obfuscation\n" +
                "• Encrypt sensitive data\n" +
                "• Validate all inputs\n" +
                "• Use HTTPS for network\n" +
                "• Don't log sensitive info");

        codeExample.setText("<!-- Declare permissions in manifest -->\n<uses-permission android:name=\"android.permission.INTERNET\" />\n<uses-permission android:name=\"android.permission.CAMERA\" />\n\n<!-- Request dangerous permission at runtime (Android 6.0+) -->\nif (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)\n    != PackageManager.PERMISSION_GRANTED) {\n    \n    ActivityCompat.requestPermissions(this,\n        new String[]{Manifest.permission.CAMERA},\n        REQUEST_CODE);\n}\n\n// Handle permission result\n@Override\npublic void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {\n    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {\n        // Permission granted\n    }\n}\n\n// Security best practices in code\n// 1. Don't log sensitive data\n// Bad: Log.d(\"Password\", password);\n// Good: Log.d(\"Password\", \"[REDACTED]\");\n\n// 2. Encrypt sensitive data\nSharedPreferences prefs = getSharedPreferences(\"secure_prefs\", MODE_PRIVATE);\nSharedPreferences.Editor editor = prefs.edit();\n// Use EncryptedSharedPreferences for sensitive data\n\n// 3. Network security\n<network-security-config>\n    <domain-config cleartextTrafficPermitted=\"false\">\n        <domain includeSubdomains=\"true\">api.example.com</domain>\n    </domain-config>\n</network-security-config>\n\n// 4. Input validation\npublic void processInput(String userInput) {\n    if (userInput == null || userInput.isEmpty()) return;\n    // Sanitize input before using\n    String sanitized = userInput.replaceAll(\"[^a-zA-Z0-9]\", \"\");\n}");

        proTip.setText("Always request the minimum permissions your app needs. Users are more likely to install apps that don't ask for unnecessary permissions!");
    }

    private void updateProgress() {
        SharedPreferences prefs = getSharedPreferences("lesson_progress", MODE_PRIVATE);
        String key = topic + "_" + lessonName;
        boolean completed = prefs.getBoolean(key, false);

        if (completed) {
            lessonProgress.setProgress(100);
            progressText.setText("100% Complete - ✅ Mastered!");
            testButton.setText("📝 Review Test");
        } else {
            lessonProgress.setProgress(50);
            progressText.setText("50% Complete - Take test to master!");
        }
    }
}