package org.example;


import static java.lang.System.*;

import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(in);

        while (true){
            out.println(" ");
            out.println("Выберите действие:");
            out.println("-------------------");
            out.println("1. Добавить новую задачу");
            out.println("2. Посмотреть список задач и открыть конкретную задачу");
            out.println("3. Удалить задачу");
            out.println("4. Выход");

            out.println("Выберите действие: ");
            int a = scanner.nextInt();

            switch (a){
                case 1:
                    out.println("Введите задачу: ");
                    String title = scanner.nextLine();
                    Task task = Task.createNew(title, false);
                    TaskManager.saveNewTask(task);
                    break;
                case 2:
                    out.println("Текущие задачи: ");
                    // TODO Сдеалть красивый вывод
                    List<String> tasks = TaskManager.viewAllTasks();
                    for (String name : tasks) {
                        out.println(name);
                    }

                    out.println("Введите номер задачи которую необходимо открыть: ");
                    int selectedTaskId = scanner.nextInt();
                    Task selectedTask = TaskManager.readTaskById(selectedTaskId);

                    out.println(selectedTask != null ? selectedTask.toString() : "Задача не найдена");

                    break;
                case 3:
                    // TODO: удалить задачу
                    break;
                case 4:
                    exit(0);
                    break;
                default:
                    out.println("Неверный ввод");
            }
        }
    }
}