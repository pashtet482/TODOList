package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Task {
    private int id;
    private String title;
    private boolean completed;
    private static final ObjectMapper mapper = new ObjectMapper();

    public void setId(File folder){
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        for (File file : files) {
            try {
                Task task = mapper.readValue(file, Task.class);
                if (task.getId() > id) {
                    id = task.getId();
                }
            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла: " + file.getName());
            }
        }

        this.id = id + 1;
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
