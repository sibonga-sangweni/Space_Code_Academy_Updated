package com.example.spacecodeacademy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.spacecodeacademy.database.UserDatabaseHelper;
import com.example.spacecodeacademy.utils.SoundManager;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    // UI
    private TextView quizTopic, questionText, heartsText, scoreText,
            questionCounter, questionTypeIndicator;

    private ProgressBar quizProgress;

    private RadioGroup mcqGroup, tfGroup;

    private RadioButton option1, option2, option3, option4;
    private RadioButton trueOption, falseOption;

    private LinearLayout fillBlankLayout;

    private EditText fillBlankAnswer;

    private Button nextBtn;

    private CardView feedbackCard;

    private TextView feedbackIcon, feedbackMessage, explanationMessage;

    // Quiz
    private String topic, lessonName;

    private List<QuizQuestion> questions;

    private int currentQuestionIndex = 0;

    private int score = 0;

    private int hearts = 3;

    private boolean isAnswered = false;

    // Database
    private UserDatabaseHelper dbHelper;

    private long databaseUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        topic = getIntent().getStringExtra("topic");
        lessonName = getIntent().getStringExtra("lessonName");

        SharedPreferences sessionPrefs =
                getSharedPreferences("user_session", MODE_PRIVATE);

        databaseUserId =
                sessionPrefs.getLong("database_user_id", -1);

        dbHelper = new UserDatabaseHelper(this);

        initializeViews();

        loadQuestions();

        displayQuestion();

        nextBtn.setOnClickListener(v -> {

            if (!isAnswered) {
                checkAnswer();
            }

        });
    }

    private void initializeViews() {

        quizTopic = findViewById(R.id.quizTopic);

        questionText = findViewById(R.id.questionText);

        heartsText = findViewById(R.id.heartsText);

        scoreText = findViewById(R.id.scoreText);

        questionCounter = findViewById(R.id.questionCounter);

        questionTypeIndicator =
                findViewById(R.id.questionTypeIndicator);

        quizProgress = findViewById(R.id.quizProgress);

        mcqGroup = findViewById(R.id.mcqGroup);

        tfGroup = findViewById(R.id.tfGroup);

        option1 = findViewById(R.id.option1);

        option2 = findViewById(R.id.option2);

        option3 = findViewById(R.id.option3);

        option4 = findViewById(R.id.option4);

        trueOption = findViewById(R.id.trueOption);

        falseOption = findViewById(R.id.falseOption);

        fillBlankLayout =
                findViewById(R.id.fillBlankLayout);

        fillBlankAnswer =
                findViewById(R.id.fillBlankAnswer);

        nextBtn = findViewById(R.id.nextBtn);

        feedbackCard = findViewById(R.id.feedbackCard);

        feedbackIcon = findViewById(R.id.feedbackIcon);

        feedbackMessage =
                findViewById(R.id.feedbackMessage);

        explanationMessage =
                findViewById(R.id.explanationMessage);

        quizTopic.setText(topic + " - " + lessonName);
    }

    private void loadQuestions() {

        questions = new ArrayList<>();

        // ==================== UI DESIGN TOPIC ====================
        if (topic.equals("UI Design")) {

            // Lesson 1: Introduction to Views
            if (lessonName.contains("Introduction to Views")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which class is the parent of all Android UI components?",
                        "View",
                        "Activity",
                        "Fragment",
                        "Layout",
                        "View"
                ));
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which method is used to find a View by its ID?",
                        "getView()",
                        "findView()",
                        "findViewById()",
                        "locateView()",
                        "findViewById()"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "TextView is used for user input in Android.",
                        "False"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "The method ________ is used to set text on a TextView.",
                        "setText"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "Every visible UI component in Android extends the ________ class.",
                        "View"
                ));
            }
            // Lesson 2: Layout Types
            else if (lessonName.contains("Layout Types")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which layout arranges children in a single row or column?",
                        "ConstraintLayout",
                        "LinearLayout",
                        "RelativeLayout",
                        "FrameLayout",
                        "LinearLayout"
                ));
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which layout is best for complex UI with flat view hierarchy?",
                        "LinearLayout",
                        "RelativeLayout",
                        "FrameLayout",
                        "ConstraintLayout",
                        "ConstraintLayout"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "LinearLayout with orientation='horizontal' arranges views horizontally.",
                        "True"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "For better performance, you should use ConstraintLayout to avoid nested layouts.",
                        "True"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "In LinearLayout, ________ controls how space is distributed among children.",
                        "layout_weight"
                ));
            }
            // Lesson 3: XML vs Programmatic UI
            else if (lessonName.contains("XML vs Programmatic")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which approach separates design from logic?",
                        "Programmatic UI",
                        "XML Layouts",
                        "Both",
                        "Neither",
                        "XML Layouts"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "Dynamic Views can only be created in XML, not in code.",
                        "False"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "The method ________ is used to set an XML layout to an Activity.",
                        "setContentView"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "Creating views programmatically gives more ________ at runtime.",
                        "control"
                ));
            }
            // Lesson 4: UI Components
            else if (lessonName.contains("UI Components")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which component is used to display images?",
                        "TextView",
                        "EditText",
                        "Button",
                        "ImageView",
                        "ImageView"
                ));
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which component allows user text input?",
                        "TextView",
                        "EditText",
                        "Button",
                        "ImageView",
                        "EditText"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "Button is clickable and performs an action when clicked.",
                        "True"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "Use ________ to handle button clicks.",
                        "onClickListener"
                ));
            }
            // Lesson 5: Best Practices & Material Design
            else {
                questions.add(new QuizQuestion(
                        "mcq",
                        "What is the recommended spacing unit in Material Design?",
                        "4dp",
                        "8dp",
                        "16dp",
                        "32dp",
                        "8dp"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "Material Design uses paper and ink as its metaphor.",
                        "True"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "Use ________ for dimensions and ________ for text size.",
                        "dp/sp"
                ));
            }
        }

        // ==================== ACTIVITIES & LIFECYCLES TOPIC ====================
        else if (topic.equals("Activities & Lifecycles")) {

            // Lesson 1: What is an Activity
            if (lessonName.contains("What is an Activity")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "What is an Activity in Android?",
                        "A background service",
                        "A single screen with UI",
                        "A database helper",
                        "A network request",
                        "A single screen with UI"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "Every Activity must be declared in ________.",
                        "AndroidManifest.xml"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "An Activity can exist without being declared in the manifest.",
                        "False"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "Which class should you extend when creating an Activity?",
                        "AppCompatActivity"
                ));
            }
            // Lesson 2: Activity Lifecycle Methods
            else if (lessonName.contains("Activity Lifecycle Methods")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which method is called first when an Activity is created?",
                        "onStart()",
                        "onResume()",
                        "onCreate()",
                        "onInit()",
                        "onCreate()"
                ));
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which lifecycle method is called when Activity becomes visible?",
                        "onCreate()",
                        "onStart()",
                        "onResume()",
                        "onPause()",
                        "onStart()"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "onResume() is called when the Activity starts interacting with the user.",
                        "True"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "When the Activity is no longer visible, ________ is called.",
                        "onStop"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "The method ________ is called before an Activity is destroyed.",
                        "onDestroy"
                ));
            }
            // Lesson 3: Managing State
            else if (lessonName.contains("Managing State")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which method saves Activity state before destruction?",
                        "onSaveInstanceState()",
                        "onStop()",
                        "onDestroy()",
                        "onPause()",
                        "onSaveInstanceState()"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "Screen rotation causes Activity to be destroyed and recreated.",
                        "True"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "The ________ object is used to save and restore Activity state.",
                        "Bundle"
                ));
            }
            // Lesson 4: Activity Communication & Results
            else if (lessonName.contains("Activity Communication")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which method is used to start an activity for a result?",
                        "startActivity()",
                        "startActivityForResult()",
                        "launchActivity()",
                        "openActivity()",
                        "startActivityForResult()"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "Use ________ to send data back from an activity.",
                        "setResult"
                ));
            }
            // Lesson 5: Fragments
            else {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Fragments were introduced in which Android version?",
                        "Android 1.0",
                        "Android 2.0",
                        "Android 3.0",
                        "Android 4.0",
                        "Android 3.0"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "Fragments have their own lifecycle.",
                        "True"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "________ is used to add fragments dynamically.",
                        "FragmentTransaction"
                ));
            }
        }

        // ==================== INTENTS & NAVIGATION TOPIC ====================
        else if (topic.equals("Intents & Navigation")) {

            // Lesson 1: Explicit Intents
            if (lessonName.contains("Explicit Intents")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which intent type specifies exactly which component to start?",
                        "Implicit Intent",
                        "Explicit Intent",
                        "Broadcast Intent",
                        "Sticky Intent",
                        "Explicit Intent"
                ));
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which method is used to start a new Activity?",
                        "launchActivity()",
                        "startActivity()",
                        "beginActivity()",
                        "openActivity()",
                        "startActivity()"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "To pass data to another Activity, use the ________ method.",
                        "putExtra"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "The ________ class is used to create explicit intents.",
                        "Intent"
                ));
            }
            // Lesson 2: Implicit Intents
            else if (lessonName.contains("Implicit Intents")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which action is used to open a web page?",
                        "ACTION_DIAL",
                        "ACTION_CALL",
                        "ACTION_VIEW",
                        "ACTION_SEND",
                        "ACTION_VIEW"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "Implicit Intents require exact component names.",
                        "False"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "Always check if there's an app to handle your ________ Intent.",
                        "Implicit"
                ));
            }
            // Lesson 3: Passing Data with Intents
            else if (lessonName.contains("Passing Data")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which is recommended for passing complex objects?",
                        "Serializable",
                        "Parcelable",
                        "Both",
                        "Neither",
                        "Parcelable"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "Use ________ to pass multiple values in one Intent.",
                        "Bundle"
                ));
            }
            // Lesson 4: Navigation Component
            else if (lessonName.contains("Navigation Component")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "What is the container for navigation in Android?",
                        "NavController",
                        "NavHostFragment",
                        "NavGraph",
                        "NavDestination",
                        "NavHostFragment"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "The ________ manages navigation within a NavHost.",
                        "NavController"
                ));
            }
            // Lesson 5: Deep Linking
            else {
                questions.add(new QuizQuestion(
                        "tf",
                        "Deep links allow external apps to open specific screens.",
                        "True"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "Deep links are defined in ________ or navigation graph.",
                        "AndroidManifest.xml"
                ));
            }
        }

        // ==================== DATA STORAGE TOPIC ====================
        else if (topic.equals("Data Storage")) {

            // Lesson 1: SharedPreferences
            if (lessonName.contains("SharedPreferences")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which storage method is best for user preferences?",
                        "SQLite",
                        "Internal Storage",
                        "SharedPreferences",
                        "External Storage",
                        "SharedPreferences"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "apply() blocks the UI thread.",
                        "False"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "SharedPreferences uses ________ pairs.",
                        "Key-Value"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "To write to SharedPreferences you need an ________ object.",
                        "Editor"
                ));
            }
            // Lesson 2: Internal Storage
            else if (lessonName.contains("Internal Storage")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Internal storage files are ________ by other apps.",
                        "Accessible",
                        "Not accessible",
                        "Readable only",
                        "Writable only",
                        "Not accessible"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "Use ________ to write a file to internal storage.",
                        "openFileOutput"
                ));
            }
            // Lesson 3: External Storage
            else if (lessonName.contains("External Storage")) {
                questions.add(new QuizQuestion(
                        "tf",
                        "External storage may not always be available.",
                        "True"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "Android 10+ introduced ________ storage restrictions.",
                        "scoped"
                ));
            }
            // Lesson 4: SQLite Database Basics
            else if (lessonName.contains("SQLite Database Basics")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "What is SQLite?",
                        "A NoSQL database",
                        "A relational database",
                        "A file system",
                        "A cache system",
                        "A relational database"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "________ is used to manage database creation and versioning.",
                        "SQLiteOpenHelper"
                ));
            }
            // Lesson 5: Advanced SQLite & Room
            else {
                questions.add(new QuizQuestion(
                        "mcq",
                        "What does Room provide at compile-time?",
                        "SQL verification",
                        "Database backup",
                        "Cloud sync",
                        "Encryption",
                        "SQL verification"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "In Room, ________ defines the table structure.",
                        "Entity"
                ));
            }
        }

        // ==================== RECYCLERVIEW TOPIC ====================
        else if (topic.equals("RecyclerView")) {

            // Lesson 1: Introduction to RecyclerView
            if (lessonName.contains("Introduction to RecyclerView")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which component provides data to RecyclerView?",
                        "LayoutManager",
                        "ViewHolder",
                        "Adapter",
                        "Recycler",
                        "Adapter"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "RecyclerView recycles views to improve performance.",
                        "True"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "________ is the standard for displaying lists in Android.",
                        "RecyclerView"
                ));
            }
            // Lesson 2: Creating Adapters and ViewHolders
            else if (lessonName.contains("Creating Adapters")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which class creates and recycles views?",
                        "Adapter",
                        "ViewHolder",
                        "Recycler",
                        "LayoutManager",
                        "ViewHolder"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "The ________ pattern prevents expensive findViewById() calls.",
                        "ViewHolder"
                ));
            }
            // Lesson 3: Layout Managers
            else if (lessonName.contains("Layout Managers")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which LayoutManager creates a grid layout?",
                        "LinearLayoutManager",
                        "GridLayoutManager",
                        "StaggeredGridLayoutManager",
                        "All of the above",
                        "GridLayoutManager"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "The ________ controls how items are arranged.",
                        "LayoutManager"
                ));
            }
            // Lesson 4: Item Decorations & Animations
            else if (lessonName.contains("Item Decorations")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which class is used to add dividers between items?",
                        "ItemDecoration",
                        "DividerItemDecoration",
                        "SpacingDecoration",
                        "SeparatorDecoration",
                        "DividerItemDecoration"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "DefaultItemAnimator provides basic animations.",
                        "True"
                ));
            }
            // Lesson 5: Advanced Features
            else {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which class handles swipe and drag in RecyclerView?",
                        "ItemTouchHelper",
                        "SwipeHelper",
                        "DragHelper",
                        "GestureHelper",
                        "ItemTouchHelper"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "Always provide an ________ option for swipe to delete.",
                        "Undo"
                ));
            }
        }

        // ==================== OS ARCHITECTURE TOPIC ====================
        else if (topic.equals("OS Architecture")) {

            // Lesson 1: Android Software Stack
            if (lessonName.contains("Android Software Stack")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "What is at the bottom of the Android software stack?",
                        "Applications",
                        "Framework",
                        "Libraries",
                        "Linux Kernel",
                        "Linux Kernel"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "Android has ________ layers in its software stack.",
                        "5"
                ));
            }
            // Lesson 2: Linux Kernel & HAL
            else if (lessonName.contains("Linux Kernel")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "What does HAL stand for?",
                        "Hardware Access Layer",
                        "Hardware Abstraction Layer",
                        "High Abstraction Layer",
                        "Hardware Application Layer",
                        "Hardware Abstraction Layer"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "Each Android app runs as a separate Linux user.",
                        "True"
                ));
            }
            // Lesson 3: Dalvik vs ART
            else if (lessonName.contains("Dalvik") || lessonName.contains("ART")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "What does DVM stand for?",
                        "Dalvik Virtual Machine",
                        "Digital Virtual Machine",
                        "Dynamic Virtual Machine",
                        "Device Virtual Machine",
                        "Dalvik Virtual Machine"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "ART replaced Dalvik for better performance.",
                        "True"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "ART stands for Android ________.",
                        "Runtime"
                ));
            }
            // Lesson 4: Application Framework
            else if (lessonName.contains("Application Framework")) {
                questions.add(new QuizQuestion(
                        "mcq",
                        "Which manager controls the activity lifecycle?",
                        "Package Manager",
                        "Notification Manager",
                        "Activity Manager",
                        "Content Manager",
                        "Activity Manager"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "Use ________ to get system services.",
                        "getSystemService"
                ));
            }
            // Lesson 5: Security Model & Permissions
            else {
                questions.add(new QuizQuestion(
                        "mcq",
                        "What type of permission requires runtime approval?",
                        "Normal",
                        "Dangerous",
                        "Signature",
                        "All of the above",
                        "Dangerous"
                ));
                questions.add(new QuizQuestion(
                        "tf",
                        "You should always request the minimum permissions needed.",
                        "True"
                ));
                questions.add(new QuizQuestion(
                        "fill",
                        "Each app runs in its own ________ for security.",
                        "sandbox"
                ));
            }
        }

        // Default fallback questions if no match
        if (questions.isEmpty()) {
            questions.add(new QuizQuestion(
                    "mcq",
                    "What is Android?",
                    "An operating system",
                    "A programming language",
                    "A database",
                    "A web browser",
                    "An operating system"
            ));
            questions.add(new QuizQuestion(
                    "mcq",
                    "What language is primarily used for Android development?",
                    "Python",
                    "JavaScript",
                    "Java",
                    "C++",
                    "Java"
            ));
            questions.add(new QuizQuestion(
                    "tf",
                    "Android is developed by Google.",
                    "True"
            ));
            questions.add(new QuizQuestion(
                    "fill",
                    "Android Studio is the official ________ for Android.",
                    "IDE"
            ));
        }
    }

    private void displayQuestion() {

        if (currentQuestionIndex >= questions.size()) {
            finishQuiz();
            return;
        }

        isAnswered = false;

        nextBtn.setEnabled(true);

        feedbackCard.setVisibility(View.GONE);

        mcqGroup.clearCheck();

        tfGroup.clearCheck();

        fillBlankAnswer.setText("");

        mcqGroup.setVisibility(View.GONE);

        tfGroup.setVisibility(View.GONE);

        fillBlankLayout.setVisibility(View.GONE);

        QuizQuestion q = questions.get(currentQuestionIndex);

        questionText.setText(q.getQuestion());

        if (q.getType().equals("mcq")) {

            questionTypeIndicator.setText("📝 Multiple Choice");

            mcqGroup.setVisibility(View.VISIBLE);

            option1.setText(q.getOpt1());

            option2.setText(q.getOpt2());

            option3.setText(q.getOpt3());

            option4.setText(q.getOpt4());
        }

        else if (q.getType().equals("tf")) {

            questionTypeIndicator.setText("✓ True or False");

            tfGroup.setVisibility(View.VISIBLE);
        }

        else {

            questionTypeIndicator.setText("✏️ Fill in the Blank");

            fillBlankLayout.setVisibility(View.VISIBLE);
        }

        questionCounter.setText(
                "Question "
                        + (currentQuestionIndex + 1)
                        + "/"
                        + questions.size()
        );

        int progress =
                ((currentQuestionIndex + 1) * 100)
                        / questions.size();

        quizProgress.setProgress(progress);

        updateHeartsDisplay();

        updateScoreDisplay();
    }

    private void checkAnswer() {

        if (isAnswered) {
            return;
        }

        QuizQuestion q = questions.get(currentQuestionIndex);

        boolean isCorrect = false;

        String userAnswer = "";

        // MULTIPLE CHOICE
        if (q.getType().equals("mcq")) {

            int selectedId = mcqGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {

                Toast.makeText(
                        this,
                        "Please select an answer!",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            RadioButton selected = findViewById(selectedId);

            userAnswer = selected.getText().toString();

            isCorrect = userAnswer.trim()
                    .equalsIgnoreCase(q.getCorrectAnswer().trim());
        }

        // TRUE/FALSE
        else if (q.getType().equals("tf")) {

            int selectedId = tfGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {

                Toast.makeText(
                        this,
                        "Please select True or False!",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            if (selectedId == R.id.trueOption) {
                userAnswer = "True";
            } else if (selectedId == R.id.falseOption) {
                userAnswer = "False";
            }

            isCorrect = userAnswer.equalsIgnoreCase(q.getCorrectAnswer());
        }

        // FILL IN BLANK
        else {

            userAnswer = fillBlankAnswer.getText()
                    .toString()
                    .trim();

            if (userAnswer.isEmpty()) {

                Toast.makeText(
                        this,
                        "Please enter an answer!",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            isCorrect = userAnswer.equalsIgnoreCase(
                    q.getCorrectAnswer()
            );
        }

        isAnswered = true;

        nextBtn.setEnabled(false);

        // =========================
        // CORRECT ANSWER
        // =========================
        if (isCorrect) {

            SoundManager.playSuccess(this);

            score += 10;

            updateScoreDisplay();

            showFeedback(
                    true,
                    "✅ Correct! +10 XP",
                    getExplanation(q)
            );

            new Handler().postDelayed(() -> {

                currentQuestionIndex++;

                displayQuestion();

            }, 1500);
        }

        // =========================
        // WRONG ANSWER
        // =========================
        else {

            SoundManager.playError(this);

            hearts--;

            updateHeartsDisplay();

            showFeedback(
                    false,
                    "❌ Wrong! The correct answer is: " + q.getCorrectAnswer(),
                    getExplanation(q)
            );

            Animation shake = AnimationUtils.loadAnimation(
                    this,
                    R.anim.shake
            );

            questionText.startAnimation(shake);

            // GAME OVER
            if (hearts <= 0) {

                new Handler().postDelayed(() -> {

                    Toast.makeText(
                            this,
                            "💀 GAME OVER! No hearts left.",
                            Toast.LENGTH_LONG
                    ).show();

                    finish();

                }, 2000);
            }

            // TRY AGAIN - Stay on same question
            else {

                new Handler().postDelayed(() -> {

                    isAnswered = false;

                    nextBtn.setEnabled(true);

                    tfGroup.clearCheck();
                    mcqGroup.clearCheck();
                    fillBlankAnswer.setText("");

                    feedbackCard.setVisibility(View.GONE);

                }, 2000);
            }
        }
    }

    private void showFeedback(
            boolean correct,
            String message,
            String explanation
    ) {

        feedbackCard.setVisibility(View.VISIBLE);

        feedbackIcon.setText(correct ? "✅" : "❌");

        feedbackMessage.setText(message);

        explanationMessage.setText(explanation);

        explanationMessage.setVisibility(View.VISIBLE);
    }

    private String getExplanation(QuizQuestion q) {

        return "Correct answer: "
                + q.getCorrectAnswer();
    }

    private void updateHeartsDisplay() {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < hearts; i++) {
            builder.append("❤️ ");
        }

        for (int i = hearts; i < 3; i++) {
            builder.append("🖤 ");
        }

        heartsText.setText(builder.toString());
    }

    private void updateScoreDisplay() {

        scoreText.setText("Score: " + score);
    }

    private void finishQuiz() {
        SharedPreferences prefs = getSharedPreferences("lesson_progress", MODE_PRIVATE);
        String key = topic + "_" + lessonName;
        prefs.edit().putBoolean(key, true).apply();

        SharedPreferences xpPrefs = getSharedPreferences("xp", MODE_PRIVATE);
        int currentXP = xpPrefs.getInt("totalXP", 0);
        xpPrefs.edit().putInt("totalXP", currentXP + score).apply();

        if (databaseUserId != -1 && dbHelper != null) {
            dbHelper.updateUserScore(databaseUserId, score);
        }

        SoundManager.playXPGain(this);

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }

    static class QuizQuestion {

        private String type;
        private String question;
        private String opt1, opt2, opt3, opt4;
        private String correctAnswer;

        public QuizQuestion(
                String type,
                String question,
                String opt1,
                String opt2,
                String opt3,
                String opt4,
                String correctAnswer
        ) {
            this.type = type;
            this.question = question;
            this.opt1 = opt1;
            this.opt2 = opt2;
            this.opt3 = opt3;
            this.opt4 = opt4;
            this.correctAnswer = correctAnswer;
        }

        public QuizQuestion(
                String type,
                String question,
                String opt1,
                String opt2,
                String correctAnswer
        ) {
            this(type, question, opt1, opt2, "", "", correctAnswer);
        }

        public QuizQuestion(
                String type,
                String question,
                String correctAnswer
        ) {
            this(type, question, "", "", "", "", correctAnswer);
        }

        public String getType() { return type; }
        public String getQuestion() { return question; }
        public String getOpt1() { return opt1; }
        public String getOpt2() { return opt2; }
        public String getOpt3() { return opt3; }
        public String getOpt4() { return opt4; }
        public String getCorrectAnswer() { return correctAnswer; }
    }
}