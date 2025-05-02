package org.example;

public class Task {
    private int id;
    private String title;
    private boolean completed;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    public boolean isCompleted(){
        return completed;
    }
}
