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
import com.example.spacecodeacademy.models.Question;
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
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int hearts = 3;
    private boolean isAnswerLocked = false;  // Prevents multiple answer submissions

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
                questions.add(new Question(
                        "Which class is the parent of all Android UI components?",
                        "View",
                        "Activity",
                        "Fragment",
                        "Layout",
                        "mcq",
                        "View"
                ));
                questions.add(new Question(
                        "Which method is used to find a View by its ID?",
                        "getView()",
                        "findView()",
                        "findViewById()",
                        "locateView()",
                        "mcq",
                        "findViewById()"
                ));
                questions.add(new Question(
                        "TextView is used for user input in Android.",
                        "True",
                        "False",
                        "tf",
                        "False"
                ));
                questions.add(new Question(
                        "The method ________ is used to set text on a TextView.",
                        "fill",
                        "setText"
                ));
                questions.add(new Question(
                        "Every visible UI component in Android extends the ________ class.",
                        "fill",
                        "View"
                ));
            } else if (lessonName != null && lessonName.contains("Layout Types")) {
                questions.add(new Question(
                        "Which layout arranges children in a single row or column?",
                        "ConstraintLayout",
                        "LinearLayout",
                        "RelativeLayout",
                        "FrameLayout",
                        "mcq",
                        "LinearLayout"
                ));
                questions.add(new Question(
                        "LinearLayout with orientation='horizontal' arranges views ________.",
                        "Vertically",
                        "Diagonally",
                        "Horizontally",
                        "In a grid",
                        "mcq",
                        "Horizontally"
                ));
                questions.add(new Question(
                        "For better performance, you should use ConstraintLayout to avoid nested layouts.",
                        "True",
                        "False",
                        "tf",
                        "True"
                ));
                questions.add(new Question(
                        "In LinearLayout, ________ controls how space is distributed.",
                        "fill",
                        "layout_weight"
                ));
            } else {
                questions.add(new Question(
                        "Which approach separates design from logic?",
                        "Programmatic UI",
                        "XML Layouts",
                        "Both",
                        "Neither",
                        "mcq",
                        "XML Layouts"
                ));
                questions.add(new Question(
                        "Dynamic Views can only be created in XML, not in code.",
                        "True",
                        "False",
                        "tf",
                        "False"
                ));
                questions.add(new Question(
                        "The method ________ is used to set an XML layout to an Activity.",
                        "fill",
                        "setContentView"
                ));
            }
        } else if (topic.equals("Activities & Lifecycles")) {
            if (lessonName != null && lessonName.contains("What is an Activity")) {
                questions.add(new Question(
                        "What is an Activity in Android?",
                        "A background service",
                        "A single screen with UI",
                        "A database helper",
                        "A network request",
                        "mcq",
                        "A single screen with UI"
                ));
                questions.add(new Question(
                        "Every Activity must be declared in ________.",
                        "build.gradle",
                        "strings.xml",
                        "AndroidManifest.xml",
                        "activity_main.xml",
                        "mcq",
                        "AndroidManifest.xml"
                ));
                questions.add(new Question(
                        "An Activity can exist without being declared in the manifest.",
                        "True",
                        "False",
                        "tf",
                        "False"
                ));
                questions.add(new Question(
                        "Which class should you extend when creating an Activity?",
                        "fill",
                        "AppCompatActivity"
                ));
            } else if (lessonName != null && lessonName.contains("Activity Lifecycle Methods")) {
                questions.add(new Question(
                        "Which method is called first when an Activity is created?",
                        "onStart()",
                        "onResume()",
                        "onCreate()",
                        "onInit()",
                        "mcq",
                        "onCreate()"
                ));
                questions.add(new Question(
                        "Which lifecycle method is called when Activity becomes visible?",
                        "onCreate()",
                        "onStart()",
                        "onResume()",
                        "onPause()",
                        "mcq",
                        "onStart()"
                ));
                questions.add(new Question(
                        "onResume() is called when the Activity starts interacting with the user.",
                        "True",
                        "False",
                        "tf",
                        "True"
                ));
                questions.add(new Question(
                        "When the Activity is no longer visible, ________ is called.",
                        "fill",
                        "onStop"
                ));
            } else {
                questions.add(new Question(
                        "Which method saves Activity state before destruction?",
                        "onSaveInstanceState()",
                        "onStop()",
                        "onDestroy()",
                        "onPause()",
                        "mcq",
                        "onSaveInstanceState()"
                ));
                questions.add(new Question(
                        "Screen rotation causes Activity to be destroyed and recreated.",
                        "True",
                        "False",
                        "tf",
                        "True"
                ));
                questions.add(new Question(
                        "The ________ object is used to save and restore Activity state.",
                        "fill",
                        "Bundle"
                ));
            }
        } else if (topic.equals("Intents & Navigation")) {
            questions.add(new Question(
                    "Which intent type specifies exactly which component to start?",
                    "Implicit Intent",
                    "Explicit Intent",
                    "Broadcast Intent",
                    "Sticky Intent",
                    "mcq",
                    "Explicit Intent"
            ));
            questions.add(new Question(
                    "Which method is used to start a new Activity?",
                    "launchActivity()",
                    "startActivity()",
                    "beginActivity()",
                    "openActivity()",
                    "mcq",
                    "startActivity()"
            ));
            questions.add(new Question(
                    "Implicit Intents require you to specify the exact component class name.",
                    "True",
                    "False",
                    "tf",
                    "False"
            ));
            questions.add(new Question(
                    "To pass data to another Activity, use the ________ method on an Intent.",
                    "fill",
                    "putExtra"
            ));
        } else if (topic.equals("Data Storage")) {
            questions.add(new Question(
                    "Which storage method is best for saving user preferences?",
                    "SQLite",
                    "Internal Storage",
                    "SharedPreferences",
                    "External Storage",
                    "mcq",
                    "SharedPreferences"
            ));
            questions.add(new Question(
                    "SharedPreferences stores data as ________ pairs.",
                    "Key-Value",
                    "Table-Row",
                    "File-Content",
                    "Object-Property",
                    "mcq",
                    "Key-Value"
            ));
            questions.add(new Question(
                    "apply() is synchronous and blocks the UI thread.",
                    "True",
                    "False",
                    "tf",
                    "False"
            ));
        } else if (topic.equals("RecyclerView")) {
            questions.add(new Question(
                    "Which component provides data to RecyclerView?",
                    "LayoutManager",
                    "ViewHolder",
                    "Adapter",
                    "ItemDecoration",
                    "mcq",
                    "Adapter"
            ));
            questions.add(new Question(
                    "Which class creates and recycles views in RecyclerView?",
                    "Adapter",
                    "ViewHolder",
                    "LayoutManager",
                    "Recycler",
                    "mcq",
                    "ViewHolder"
            ));
            questions.add(new Question(
                    "RecyclerView is less efficient than ListView for large datasets.",
                    "True",
                    "False",
                    "tf",
                    "False"
            ));
        } else if (topic.equals("OS Architecture")) {
            questions.add(new Question(
                    "What is at the bottom of the Android software stack?",
                    "Applications",
                    "Framework",
                    "Libraries",
                    "Linux Kernel",
                    "mcq",
                    "Linux Kernel"
            ));
            questions.add(new Question(
                    "What does DVM stand for?",
                    "Dalvik Virtual Machine",
                    "Digital Virtual Machine",
                    "Dynamic VM",
                    "Device VM",
                    "mcq",
                    "Dalvik Virtual Machine"
            ));
            questions.add(new Question(
                    "ART (Android Runtime) replaced Dalvik for better performance.",
                    "True",
                    "False",
                    "tf",
                    "True"
            ));
        }
    }

    private void displayQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            finishQuiz();
            return;
        }

        // Reset answer lock for new question
        isAnswerLocked = false;

        // Hide all input types
        mcqGroup.setVisibility(View.GONE);
        tfGroup.setVisibility(View.GONE);
        fillBlankLayout.setVisibility(View.GONE);
        feedbackCard.setVisibility(View.GONE);

        // Enable the next button again
        nextBtn.setEnabled(true);
        nextBtn.setText("Check Answer");

        Question q = questions.get(currentQuestionIndex);
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

    private void showMultipleChoice(Question q) {
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
    }

    private void showFillBlank() {
        fillBlankLayout.setVisibility(View.VISIBLE);
        fillBlankAnswer.setText("");
    }

    private void checkAnswer() {
        // Prevent checking if already locked
        if (isAnswerLocked) {
            Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
            return;
        }

        Question q = questions.get(currentQuestionIndex);
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
                isCorrect = userAnswer.equalsIgnoreCase(q.getCorrectAnswer());
                break;
        }

        // Lock answer checking while processing
        isAnswerLocked = true;

        if (isCorrect) {
            // CORRECT ANSWER - Move to next question
            score += 10;
            showFeedback(true, "✅ Correct! +10 XP", getExplanation(q));
            updateScoreDisplay();

            // Disable the button while showing feedback
            nextBtn.setEnabled(false);

            // Move to next question after delay
            new Handler().postDelayed(() -> {
                currentQuestionIndex++;
                displayQuestion();
            }, 2000);
        } else {
            // WRONG ANSWER - Stay on same question, lose a heart
            hearts--;
            updateHeartsDisplay();
            showFeedback(false, "❌ Wrong! Try again! -1 Heart", getExplanation(q));

            // Shake animation for wrong answer
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            questionText.startAnimation(shake);

            // Check if game over
            if (hearts == 0) {
                nextBtn.setEnabled(false);
                new Handler().postDelayed(() -> {
                    Toast.makeText(this, "Game Over! No hearts left.", Toast.LENGTH_LONG).show();
                    finish();
                }, 2000);
            } else {
                // Unlock after a short delay so user can try again
                new Handler().postDelayed(() -> {
                    isAnswerLocked = false;
                    nextBtn.setEnabled(true);

                    // Clear the selected answer so user can try again
                    if (q.getType().equals("mcq")) {
                        mcqGroup.clearCheck();
                    } else if (q.getType().equals("tf")) {
                        tfGroup.clearCheck();
                    } else if (q.getType().equals("fill")) {
                        fillBlankAnswer.setText("");
                    }

                    // Show a hint to try again
                    Toast.makeText(this, "Try again! Choose the correct answer.", Toast.LENGTH_SHORT).show();
                }, 2000);
            }
        }

        updateScoreDisplay();
    }

    private String getExplanation(Question q) {
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
        } else if (question.contains("LinearLayout") && question.contains("orientation")) {
            return "Horizontal orientation places views side by side horizontally.";
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
            case "fill": return "✏️ Fill in the Blank";
            default: return "❓ Question";
        }
    }

    private void updateHeartsDisplay() {
        StringBuilder heartsStr = new StringBuilder();
        for (int i = 0; i < hearts; i++) {
            heartsStr.append("❤️ ");
        }
        // Add empty hearts for lost ones
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

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }
}