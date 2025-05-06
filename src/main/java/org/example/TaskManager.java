package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TaskManager {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final File FOLDER_PATH = new File("TODO/");
    private static final Logger LOGGER = Logger.getLogger(TaskManager.class.getName());
    private static final String FOLDER_IS_EMPTY = "Папка пуста";
    private static final String FILE_NOT_FOUND = "Файл не найден";
    private static final String TASK_FILENAME_FORMAT = ".+_%d\\.json";
    private static final Pattern TASK_ID_PATTERN = Pattern.compile("_(\\d+)\\.json$");
    private static final Pattern REPLACE_PATTERN = Pattern.compile("[^\\p{L}0-9\\-_]");

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

        String safeTitle = task.getTitle().replaceAll(REPLACE_PATTERN.toString(), "_");
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

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                Matcher matcher = TASK_ID_PATTERN.matcher(fileName);
                if (matcher.find()) {
                    int num = Integer.parseInt(matcher.group(1));
                    if (num > maxID) maxID = num;
                }
            }
            return ++maxID;
        } else {
            LOGGER.info(FOLDER_IS_EMPTY);
            return 1;
        }
    }

    @Nullable
    public static Task readTaskById(int selectedTaskId){
        try {
            File selectedFile = findTaskByID(selectedTaskId);
            if (selectedFile == null){
                LOGGER.log(Level.WARNING, FILE_NOT_FOUND);
                return null;
            }
            return MAPPER.readValue(selectedFile, Task.class);
        } catch (IllegalArgumentException | IOException e){
            LOGGER.log(Level.SEVERE, "Ошибка при чтении файла", e);
            return null;
        }
    }

    public static void deleteTaskById(int selectedTaskId) {
        try {
            File selectedFile = findTaskByID(selectedTaskId);
            if (selectedFile == null){
                LOGGER.log(Level.WARNING, FILE_NOT_FOUND);
                return;
            }
            Files.delete(selectedFile.toPath());
        } catch (IllegalArgumentException | IOException e) {
            LOGGER.log(Level.SEVERE, e, () -> "Ошибка удаления файла");
        }
    }


    @NotNull
    public static List<Task> viewAllTasks(){
        ensureFolderExists();
        File[] files = FOLDER_PATH.listFiles();

        if (files == null) {
            LOGGER.warning(FOLDER_IS_EMPTY);
            return Collections.emptyList();
        }

        List<Task> tasks = new ArrayList<>();
        for (File file : files) {
            try {
                Task task = MAPPER.readValue(file, Task.class);
                tasks.add(task);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, e, () -> "Ошибка при чтении задачи из файла: " + file.getName());
            }
        }

        tasks.sort(Comparator.comparingInt(Task::getId));
        return tasks;
    }

    public static void completeTask(int selectedTaskId){
        try {
            File selectedFile = findTaskByID(selectedTaskId);
            if (selectedFile == null){
                LOGGER.log(Level.WARNING, FILE_NOT_FOUND);
                return;
            }
            Task task = MAPPER.readValue(selectedFile, Task.class);
            task.setCompleted(true);
            MAPPER.writeValue(selectedFile, task);
        } catch (IllegalArgumentException | IOException e) {
            LOGGER.log(Level.SEVERE, e, () -> "Ошибка изменения файла");
        }
    }

    @Nullable
    private static File findTaskByID(int selectedTaskId){
        ensureFolderExists();
        if (selectedTaskId <= 0) {
            throw new IllegalArgumentException("ID задачи должен быть положительным");
        }

        File[] files = FOLDER_PATH.listFiles();

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                if (fileName.matches(String.format(TASK_FILENAME_FORMAT, selectedTaskId))) {
                    return file;
                }
            }
        } else {
            LOGGER.warning(FOLDER_IS_EMPTY);
            return null;
        }
        return null;
    }
}
