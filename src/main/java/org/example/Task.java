package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;


public class Task {
    private int id;
    private String title;
    private boolean completed;
    private static final ObjectMapper mapper = new ObjectMapper();

    public Task() {}

    public Task(int id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
