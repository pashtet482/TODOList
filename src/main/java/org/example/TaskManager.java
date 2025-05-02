package org.example;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    List<Task> taskList = new ArrayList<>();
    Task task = new Task();

    public void addTask(String title){
        task.setTitle(title);
    }

    public void listTasks(List<Task> taskList){
        for(Task title: taskList){
            System.out.println(title);
        }
    }
}
