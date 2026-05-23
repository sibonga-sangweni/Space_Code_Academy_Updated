package com.example.spacecodeacademy.models;

public class Question {
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String type;
    private String correctAnswer;

    // Constructor for MCQ (with 4 options)
    public Question(String question, String option1, String option2,
                        String option3, String option4, String type, String correctAnswer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.type = type;
        this.correctAnswer = correctAnswer;
    }

    // Constructor for True/False (simpler)
    public Question(String question, String option1, String option2,
                        String type, String correctAnswer) {
        this(question, option1, option2, "", "", type, correctAnswer);
    }

    // Constructor for Fill in the Blank
    public Question(String question, String type, String correctAnswer) {
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