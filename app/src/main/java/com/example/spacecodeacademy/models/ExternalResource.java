package com.example.spacecodeacademy.models;

public class ExternalResource {
    private String title;
    private String url;
    private String description;
    private String type; // "article", "video", "documentation", "github"

    public ExternalResource(String title, String url, String description, String type) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.type = type;
    }

    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public String getDescription() { return description; }
    public String getType() { return type; }

    public int getIconResource() {
        switch (type) {
            case "video": return android.R.drawable.ic_media_play;
            case "documentation": return android.R.drawable.ic_menu_info_details;
            case "github": return android.R.drawable.ic_menu_share;
            default: return android.R.drawable.ic_menu_view;
        }
    }
}