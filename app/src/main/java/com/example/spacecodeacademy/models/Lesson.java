package com.example.spacecodeacademy.models;

public class Lesson {
    private String title;
    private String content;
    private String codeExample;

    public Lesson(String title, String content, String codeExample) {
        this.title = title;
        this.content = content;
        this.codeExample = codeExample;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCodeExample() {
        return codeExample;
    }
}