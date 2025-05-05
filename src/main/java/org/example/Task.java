package org.example;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Task {
    private int id;
    private String title;
    private boolean completed;
    private static int nextId = -1;

    public int getId() { return id; }

    public String getTitle() { return title; }

    public boolean isCompleted() { return completed; }

    public void setId(int id) { this.id = id; }

    public void setTitle(String title) { this.title = title; }

    public void setCompleted(boolean completed) { this.completed = completed; }

    public Task() {}

    private Task(int id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    @Contract("_ -> new")
    public static @NotNull Task createNew(String title) {
        if (nextId == -1) {
            nextId = TaskManager.getNextId();
        }
        return new Task(nextId++, title, false);
    }
}
