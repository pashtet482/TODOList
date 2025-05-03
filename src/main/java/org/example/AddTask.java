package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import static java.lang.System.*;

public final class AddTask {
    private AddTask() {
        throw new UnsupportedOperationException("Utility class should not be instantiated!");
    }

    public static void addTask(){
        Task task = new Task();
        Scanner scanner = new Scanner(System.in);
        out.println("Введите заголовок: ");
        String title = scanner.nextLine();
        task.setId(1);
        task.setTitle(title);
        task.setCompleted(false);

        ObjectMapper mapper = new ObjectMapper();

        try {
            out.println("Введите название файла: ");
            String fileName = scanner.nextLine();
            mapper.writeValue(new File("src/main/resources/TODO/" + fileName + ".json"), task);
            out.println("JSON-файл создан!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
