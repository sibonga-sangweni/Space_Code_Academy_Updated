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
import com.example.spacecodeacademy.utils.SoundManager;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    // UI Components
    private TextView quizTopic, questionText, heartsText, scoreText, questionCounter;
    private TextView questionTypeIndicator;
    private ProgressBar quizProgress;
    private RadioGroup mcqGroup, tfGroup;
    private RadioButton option1, option2, option3, option4, trueOption, falseOption;
    private LinearLayout fillBlankLayout;
    private EditText fillBlankAnswer;
    private Button nextBtn;
    private CardView feedbackCard;
    private TextView feedbackIcon, feedbackMessage, explanationMessage;

    // Quiz Data
    private String topic, lessonName;
    private List<QuizQuestion> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int hearts = 3;
    private boolean isAnswerLocked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        topic = getIntent().getStringExtra("topic");
        lessonName = getIntent().getStringExtra("lessonName");

        initializeViews();
        loadQuestions();
        displayQuestion();

        nextBtn.setOnClickListener(v -> checkAnswer());
    }

    private void initializeViews() {
        quizTopic = findViewById(R.id.quizTopic);
        questionText = findViewById(R.id.questionText);
        heartsText = findViewById(R.id.heartsText);
        scoreText = findViewById(R.id.scoreText);
        questionCounter = findViewById(R.id.questionCounter);
        questionTypeIndicator = findViewById(R.id.questionTypeIndicator);
        quizProgress = findViewById(R.id.quizProgress);

        mcqGroup = findViewById(R.id.mcqGroup);
        tfGroup = findViewById(R.id.tfGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        trueOption = findViewById(R.id.trueOption);
        falseOption = findViewById(R.id.falseOption);

        fillBlankLayout = findViewById(R.id.fillBlankLayout);
        fillBlankAnswer = findViewById(R.id.fillBlankAnswer);

        nextBtn = findViewById(R.id.nextBtn);
        feedbackCard = findViewById(R.id.feedbackCard);
        feedbackIcon = findViewById(R.id.feedbackIcon);
        feedbackMessage = findViewById(R.id.feedbackMessage);
        explanationMessage = findViewById(R.id.explanationMessage);

        quizTopic.setText(topic + " - " + lessonName);
    }

    private void loadQuestions() {
        questions = new ArrayList<>();

        if (topic.equals("UI Design")) {
            if (lessonName != null && lessonName.contains("Introduction to Views")) {
                // Multiple Choice
                questions.add(new QuizQuestion(
                        "Which class is the parent of all Android UI components?",
                        "View",
                        "Activity",
                        "Fragment",
                        "Layout",
                        "mcq",
                        "View"
                ));
                questions.add(new QuizQuestion(
                        "Which method is used to find a View by its ID?",
                        "getView()",
                        "findView()",
                        "findViewById()",
                        "locateView()",
                        "mcq",
                        "findViewById()"
                ));
                // True/False
                questions.add(new QuizQuestion(
                        "TextView is used for user input in Android.",
                        "True",
                        "False",
                        "tf",
                        "False"
                ));
                // Fill in the Blanks
                questions.add(new QuizQuestion(
                        "The method ________ is used to set text on a TextView.",
                        "fill",
                        "setText"
                ));
                questions.add(new QuizQuestion(
                        "Every visible UI component in Android extends the ________ class.",
                        "fill",
                        "View"
                ));
            } else if (lessonName != null && lessonName.contains("Layout Types")) {
                questions.add(new QuizQuestion(
                        "Which layout arranges children in a single row or column?",
                        "ConstraintLayout",
                        "LinearLayout",
                        "RelativeLayout",
                        "FrameLayout",
                        "mcq",
                        "LinearLayout"
                ));
                questions.add(new QuizQuestion(
                        "LinearLayout with orientation='horizontal' arranges views horizontally.",
                        "True",
                        "False",
                        "tf",
                        "True"
                ));
                questions.add(new QuizQuestion(
                        "For better performance, you should use ConstraintLayout to avoid nested layouts.",
                        "True",
                        "False",
                        "tf",
                        "True"
                ));
                questions.add(new QuizQuestion(
                        "In LinearLayout, ________ controls how space is distributed among children.",
                        "fill",
                        "layout_weight"
                ));
                questions.add(new QuizQuestion(
                        "________ Layout is best for complex UI with flat view hierarchy.",
                        "fill",
                        "ConstraintLayout"
                ));
            } else {
                questions.add(new QuizQuestion(
                        "Which approach separates design from logic?",
                        "Programmatic UI",
                        "XML Layouts",
                        "Both",
                        "Neither",
                        "mcq",
                        "XML Layouts"
                ));
                questions.add(new QuizQuestion(
                        "Dynamic Views can only be created in XML, not in code.",
                        "True",
                        "False",
                        "tf",
                        "False"
                ));
                questions.add(new QuizQuestion(
                        "The method ________ is used to set an XML layout to an Activity.",
                        "fill",
                        "setContentView"
                ));
            }
        } else if (topic.equals("Activities & Lifecycles")) {
            if (lessonName != null && lessonName.contains("What is an Activity")) {
                questions.add(new QuizQuestion(
                        "What is an Activity in Android?",
                        "A background service",
                        "A single screen with UI",
                        "A database helper",
                        "A network request",
                        "mcq",
                        "A single screen with UI"
                ));
                questions.add(new QuizQuestion(
                        "Every Activity must be declared in ________.",
                        "fill",
                        "AndroidManifest.xml"
                ));
                questions.add(new QuizQuestion(
                        "An Activity can exist without being declared in the manifest.",
                        "True",
                        "False",
                        "tf",
                        "False"
                ));
                questions.add(new QuizQuestion(
                        "Which class should you extend when creating an Activity?",
                        "fill",
                        "AppCompatActivity"
                ));
            } else if (lessonName != null && lessonName.contains("Activity Lifecycle Methods")) {
                questions.add(new QuizQuestion(
                        "Which method is called first when an Activity is created?",
                        "onStart()",
                        "onResume()",
                        "onCreate()",
                        "onInit()",
                        "mcq",
                        "onCreate()"
                ));
                questions.add(new QuizQuestion(
                        "Which lifecycle method is called when Activity becomes visible?",
                        "onCreate()",
                        "onStart()",
                        "onResume()",
                        "onPause()",
                        "mcq",
                        "onStart()"
                ));
                questions.add(new QuizQuestion(
                        "onResume() is called when the Activity starts interacting with the user.",
                        "True",
                        "False",
                        "tf",
                        "True"
                ));
                questions.add(new QuizQuestion(
                        "When the Activity is no longer visible, ________ is called.",
                        "fill",
                        "onStop"
                ));
                questions.add(new QuizQuestion(
                        "The method ________ is called before an Activity is destroyed.",
                        "fill",
                        "onDestroy"
                ));
            } else {
                questions.add(new QuizQuestion(
                        "Which method saves Activity state before destruction?",
                        "onSaveInstanceState()",
                        "onStop()",
                        "onDestroy()",
                        "onPause()",
                        "mcq",
                        "onSaveInstanceState()"
                ));
                questions.add(new QuizQuestion(
                        "Screen rotation causes Activity to be destroyed and recreated.",
                        "True",
                        "False",
                        "tf",
                        "True"
                ));
                questions.add(new QuizQuestion(
                        "The ________ object is used to save and restore Activity state.",
                        "fill",
                        "Bundle"
                ));
            }
        } else if (topic.equals("Intents & Navigation")) {
            questions.add(new QuizQuestion(
                    "Which intent type specifies exactly which component to start?",
                    "Implicit Intent",
                    "Explicit Intent",
                    "Broadcast Intent",
                    "Sticky Intent",
                    "mcq",
                    "Explicit Intent"
            ));
            questions.add(new QuizQuestion(
                    "Which method is used to start a new Activity?",
                    "launchActivity()",
                    "startActivity()",
                    "beginActivity()",
                    "openActivity()",
                    "mcq",
                    "startActivity()"
            ));
            questions.add(new QuizQuestion(
                    "Implicit Intents require you to specify the exact component class name.",
                    "True",
                    "False",
                    "tf",
                    "False"
            ));
            questions.add(new QuizQuestion(
                    "To pass data to another Activity, use the ________ method on an Intent.",
                    "fill",
                    "putExtra"
            ));
            questions.add(new QuizQuestion(
                    "The ________ class is used to create explicit intents.",
                    "fill",
                    "Intent"
            ));
        } else if (topic.equals("Data Storage")) {
            questions.add(new QuizQuestion(
                    "Which storage method is best for saving user preferences?",
                    "SQLite",
                    "Internal Storage",
                    "SharedPreferences",
                    "External Storage",
                    "mcq",
                    "SharedPreferences"
            ));
            questions.add(new QuizQuestion(
                    "SharedPreferences stores data as ________ pairs.",
                    "fill",
                    "Key-Value"
            ));
            questions.add(new QuizQuestion(
                    "apply() is synchronous and blocks the UI thread.",
                    "True",
                    "False",
                    "tf",
                    "False"
            ));
            questions.add(new QuizQuestion(
                    "To write to SharedPreferences, you need a ________ object.",
                    "fill",
                    "Editor"
            ));
        } else if (topic.equals("RecyclerView")) {
            questions.add(new QuizQuestion(
                    "Which component provides data to RecyclerView?",
                    "LayoutManager",
                    "ViewHolder",
                    "Adapter",
                    "ItemDecoration",
                    "mcq",
                    "Adapter"
            ));
            questions.add(new QuizQuestion(
                    "Which class creates and recycles views in RecyclerView?",
                    "Adapter",
                    "ViewHolder",
                    "LayoutManager",
                    "Recycler",
                    "mcq",
                    "ViewHolder"
            ));
            questions.add(new QuizQuestion(
                    "RecyclerView is less efficient than ListView for large datasets.",
                    "True",
                    "False",
                    "tf",
                    "False"
            ));
            questions.add(new QuizQuestion(
                    "The ________ controls how items are arranged in RecyclerView.",
                    "fill",
                    "LayoutManager"
            ));
        } else if (topic.equals("OS Architecture")) {
            questions.add(new QuizQuestion(
                    "What is at the bottom of the Android software stack?",
                    "Applications",
                    "Framework",
                    "Libraries",
                    "Linux Kernel",
                    "mcq",
                    "Linux Kernel"
            ));
            questions.add(new QuizQuestion(
                    "What does DVM stand for?",
                    "Dalvik Virtual Machine",
                    "Digital Virtual Machine",
                    "Dynamic VM",
                    "Device VM",
                    "mcq",
                    "Dalvik Virtual Machine"
            ));
            questions.add(new QuizQuestion(
                    "ART (Android Runtime) replaced Dalvik for better performance.",
                    "True",
                    "False",
                    "tf",
                    "True"
            ));
            questions.add(new QuizQuestion(
                    "Android applications run in their own ________ process.",
                    "fill",
                    "Linux"
            ));
        }
    }

    private void displayQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            finishQuiz();
            return;
        }

        isAnswerLocked = false;

        // Hide all input types
        mcqGroup.setVisibility(View.GONE);
        tfGroup.setVisibility(View.GONE);
        fillBlankLayout.setVisibility(View.GONE);
        feedbackCard.setVisibility(View.GONE);

        QuizQuestion q = questions.get(currentQuestionIndex);
        questionText.setText(q.getQuestion());
        questionTypeIndicator.setText(getQuestionTypeDisplay(q.getType()));

        // Update progress
        int progress = (currentQuestionIndex * 100) / questions.size();
        quizProgress.setProgress(progress);
        questionCounter.setText("Question " + (currentQuestionIndex + 1) + "/" + questions.size());

        // Show appropriate input type
        switch (q.getType()) {
            case "mcq":
                showMultipleChoice(q);
                break;
            case "tf":
                showTrueFalse();
                break;
            case "fill":
                showFillBlank();
                break;
        }

        updateHeartsDisplay();
        updateScoreDisplay();
    }

    private void showMultipleChoice(QuizQuestion q) {
        mcqGroup.setVisibility(View.VISIBLE);
        mcqGroup.clearCheck();

        option1.setText(q.getOption1());
        option2.setText(q.getOption2());
        option3.setText(q.getOption3());
        option4.setText(q.getOption4());
    }

    private void showTrueFalse() {
        tfGroup.setVisibility(View.VISIBLE);
        tfGroup.clearCheck();
        trueOption.setText("True");
        falseOption.setText("False");
    }

    private void showFillBlank() {
        fillBlankLayout.setVisibility(View.VISIBLE);
        fillBlankAnswer.setText("");
        fillBlankAnswer.setHint("Type your answer here...");
    }

    private void checkAnswer() {
        if (isAnswerLocked) {
            Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
            return;
        }

        QuizQuestion q = questions.get(currentQuestionIndex);
        boolean isCorrect = false;
        String userAnswer = "";

        switch (q.getType()) {
            case "mcq":
                int selectedId = mcqGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(this, "Please select an answer!", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton selected = findViewById(selectedId);
                userAnswer = selected.getText().toString();
                isCorrect = userAnswer.equals(q.getCorrectAnswer());
                break;

            case "tf":
                int tfSelected = tfGroup.getCheckedRadioButtonId();
                if (tfSelected == -1) {
                    Toast.makeText(this, "Please select True or False!", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton tfButton = findViewById(tfSelected);
                userAnswer = tfButton.getText().toString();
                isCorrect = userAnswer.equals(q.getCorrectAnswer());
                break;

            case "fill":
                userAnswer = fillBlankAnswer.getText().toString().trim();
                if (userAnswer.isEmpty()) {
                    Toast.makeText(this, "Please enter an answer!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Case-insensitive comparison for fill in the blank
                isCorrect = userAnswer.equalsIgnoreCase(q.getCorrectAnswer());
                break;
        }

        isAnswerLocked = true;

        if (isCorrect) {
            SoundManager.playSuccess(this);
            score += 10;
            showFeedback(true, "✅ Correct! +10 XP", getExplanation(q, userAnswer));
            updateScoreDisplay();
            nextBtn.setEnabled(false);

            new Handler().postDelayed(() -> {
                currentQuestionIndex++;
                displayQuestion();
                nextBtn.setEnabled(true);
            }, 1500);
        } else {
            SoundManager.playError(this);
            hearts--;
            updateHeartsDisplay();
            showFeedback(false, "❌ Wrong! The correct answer is: " + q.getCorrectAnswer(), getExplanation(q, userAnswer));

            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            questionText.startAnimation(shake);

            if (hearts == 0) {
                nextBtn.setEnabled(false);
                new Handler().postDelayed(() -> {
                    Toast.makeText(this, "Game Over! No hearts left.", Toast.LENGTH_LONG).show();
                    finish();
                }, 2000);
            } else {
                new Handler().postDelayed(() -> {
                    isAnswerLocked = false;
                    nextBtn.setEnabled(true);

                    // Clear previous selection
                    if (q.getType().equals("mcq")) {
                        mcqGroup.clearCheck();
                    } else if (q.getType().equals("tf")) {
                        tfGroup.clearCheck();
                    } else if (q.getType().equals("fill")) {
                        fillBlankAnswer.setText("");
                    }

                    Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
                }, 2000);
            }
        }

        updateScoreDisplay();
    }

    private String getExplanation(QuizQuestion q, String userAnswer) {
        String question = q.getQuestion();
        String correctAnswer = q.getCorrectAnswer();

        if (question.contains("parent of all Android UI components")) {
            return "All UI components (TextView, Button, ImageView, etc.) extend the View class.";
        } else if (question.contains("findViewById")) {
            return "findViewById(R.id.view_id) returns a reference to the View defined in your XML layout.";
        } else if (question.contains("Activity in Android")) {
            return "An Activity represents a single screen with a user interface.";
        } else if (question.contains("first when an Activity is created")) {
            return "onCreate() is the first lifecycle method called to initialize the Activity.";
        } else if (question.contains("onSaveInstanceState")) {
            return "onSaveInstanceState() saves UI state before the Activity is destroyed (e.g., screen rotation).";
        } else if (question.contains("TextView is used for user input")) {
            return "TextView displays text, but does NOT accept user input. EditText is used for user input.";
        } else if (question.contains("setText")) {
            return "The setText() method changes the text displayed by a TextView or Button.";
        } else if (question.contains("setContentView")) {
            return "setContentView(R.layout.activity_main) loads the XML layout into the Activity.";
        } else if (question.contains("AndroidManifest")) {
            return "All Activities must be declared in AndroidManifest.xml with an <activity> tag.";
        } else if (question.contains("putExtra")) {
            return "Use intent.putExtra(\"key\", value) to pass data to another Activity.";
        } else if (question.contains("SharedPreferences")) {
            return "SharedPreferences is best for storing simple key-value pairs like user settings.";
        } else {
            return "The correct answer is: " + correctAnswer;
        }
    }

    private void showFeedback(boolean isCorrect, String message, String explanation) {
        feedbackCard.setVisibility(View.VISIBLE);
        feedbackIcon.setText(isCorrect ? "✅" : "❌");
        feedbackMessage.setText(message);

        if (explanation != null && !explanation.isEmpty()) {
            explanationMessage.setVisibility(View.VISIBLE);
            explanationMessage.setText(explanation);
        } else {
            explanationMessage.setVisibility(View.GONE);
        }

        if (isCorrect) {
            feedbackCard.setCardBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            feedbackCard.setCardBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
        }
    }

    private String getQuestionTypeDisplay(String type) {
        switch (type) {
            case "mcq": return "📝 Multiple Choice Question";
            case "tf": return "✓ True/False Question";
            case "fill": return "✏️ Fill in the Blank Question";
            default: return "❓ Question";
        }
    }

    private void updateHeartsDisplay() {
        StringBuilder heartsStr = new StringBuilder();
        for (int i = 0; i < hearts; i++) {
            heartsStr.append("❤️ ");
        }
        for (int i = hearts; i < 3; i++) {
            heartsStr.append("🖤 ");
        }
        heartsText.setText(heartsStr.toString());
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

        SoundManager.playXPGain(this);

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }

    // QuizQuestion inner class
    static class QuizQuestion {
        private String question;
        private String option1, option2, option3, option4;
        private String type;
        private String correctAnswer;

        // Constructor for MCQ (with 4 options)
        public QuizQuestion(String question, String option1, String option2,
                            String option3, String option4, String type, String correctAnswer) {
            this.question = question;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
            this.type = type;
            this.correctAnswer = correctAnswer;
        }

        // Constructor for True/False (2 options)
        public QuizQuestion(String question, String option1, String option2,
                            String type, String correctAnswer) {
            this(question, option1, option2, "", "", type, correctAnswer);
        }

        // Constructor for Fill in the Blank (no options)
        public QuizQuestion(String question, String type, String correctAnswer) {
            this(question, "", "", "", "", type, correctAnswer);
        }

        public String getQuestion() { return question; }
        public String getOption1() { return option1; }
        public String getOption2() { return option2; }
        public String getOption3() { return option3; }
        public String getOption4() { return option4; }
        public String getType() { return type; }
        public String getCorrectAnswer() { return correctAnswer; }
    }
}