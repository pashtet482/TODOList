package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class TaskManager {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final File FOLDER_PATH = new File("TODO/");
    private static final Logger LOGGER = Logger.getLogger(TaskManager.class.getName());
    private static final String FOLDER_IS_EMPTY = "Папка пуста";

    private TaskManager(){
        throw new UnsupportedOperationException("Utility class should not be instantiated!");
    }

    private static void ensureFolderExists() {
        if (!FOLDER_PATH.exists()) {
            FOLDER_PATH.mkdirs();
        }
    }

    public static void saveNewTask(@NotNull Task task){
        ensureFolderExists();

        String safeTitle = task.getTitle().replaceAll("[^a-zA-Z0-9\\-_]", "_");
        File file = new File(FOLDER_PATH, safeTitle + "_" + task.getId() + ".json");

        try {
            MAPPER.writeValue(file, task);
        } catch (IOException e){
            LOGGER.log(Level.SEVERE, "Ошибка при сохранении задачи", e);
        }
    }

    @NotNull
    public static Integer getNextId(){
        File[] files = FOLDER_PATH.listFiles();
        int maxID = 0;

        if(files != null){
            for(File file : files){
                String fileName = file.getName();
                String fileNameNumberOnly = fileName.replaceAll("\\D+", "");
                if (!fileNameNumberOnly.isEmpty()) {
                    int num = Integer.parseInt(fileNameNumberOnly);
                    if (num > maxID) maxID = num;
                }
            }
            return maxID + 1;
        }else {
            LOGGER.info(FOLDER_IS_EMPTY);
            return 0;
        }
    }

    @Nullable
    public static Task readTaskById(int selectedTaskId){
        ensureFolderExists();

        File[] files = FOLDER_PATH.listFiles();

        if(files != null){
            for (File file : files){
                if(file.getName().contains("_" + selectedTaskId + ".json")){
                    try {
                        return MAPPER.readValue(file, Task.class);
                    } catch (IOException e){
                        LOGGER.log(Level.SEVERE, "Ошибка при чтении файла", e);
                        return null;
                    }
                }
            }
        }else {
            LOGGER.info(FOLDER_IS_EMPTY);
        }
        return null;
    }

    @NotNull
    public static List<String> viewAllTasks(){
        ensureFolderExists();

        File[] files = FOLDER_PATH.listFiles();
        if (files == null) {
            LOGGER.warning(FOLDER_IS_EMPTY);
            return Collections.emptyList();
        }

        List<String> fileNames = new ArrayList<>();
        for (File file : files){
            fileNames.add(file.getName());
        }
        return fileNames;
    }
}
