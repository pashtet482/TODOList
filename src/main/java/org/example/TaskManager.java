package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class TaskManager {
    private TaskManager(){
        throw new UnsupportedOperationException("Utility class should not be instantiated!");
    }
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String FOLDER_PATH = "TODO/";

    public static void addTask(Task task){
        try{

            File file = new File(FOLDER_PATH + task.getTitle() +"_"+ task.getId() + ".json");
            mapper.writeValue(file, task);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
