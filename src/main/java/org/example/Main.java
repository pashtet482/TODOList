package org.example;


import static java.lang.System.*;

import java.util.List;
import java.util.Scanner;


public class Main {
    private static final Scanner SCANNER = new Scanner(in);
    private static final String TASK_NOT_FOUND = "Задача не найдена";
    private static final String INVALID_TASK_NUMBER = "Ошибка: введите корректный номер задачи";
    private static boolean running = true;

    public static void main(String[] args) {
        while (running) {
            out.println("\nВыберите действие:");
            out.println("-------------------");
            out.println("1. Добавить новую задачу");
            out.println("2. Посмотреть список задач");
            out.println("3. Пометить задачу как выполненную");
            out.println("4. Удалить задачу");
            out.println("5. Выход");

            out.print("Выберите действие: ");

            int choice;
            try {
                choice = Integer.parseInt(SCANNER.nextLine());
                handleUserChoice(choice);
            } catch (NumberFormatException e) {
                out.println("Ошибка: введите число от 1 до 5");
            }
        }
    }

    private static void handleUserChoice(int userChoice) {
        switch (userChoice) {
            case 1 -> handleAddTask();
            case 2 -> handleViewTasks();
            case 3 -> handleCompleteTask();
            case 4 -> handleDeleteTask();
            case 5 -> {
                out.println("Выход из программы...");
                running = false;
            }
            default -> out.println("Неверный ввод. Введите число от 1 до 4");
        }
    }

    private static void handleViewTasks(){
        List<Task> tasks = TaskManager.viewAllTasks();
        out.println("\nТекущие задачи:");
        if (tasks.isEmpty()) {
            out.println("Нет сохранённых задач");
        }

        for (Task task : tasks) {
            String status = task.isCompleted() ? "[✔]" : "[✘]";
            out.println(task.getId() + ". " + task.getTitle() + " " + status);
        }
    }

    private static void handleAddTask() {
        out.print("Введите задачу: ");
        String title = SCANNER.nextLine();
        if (title.trim().isEmpty()) {
            out.println("Ошибка: название задачи не может быть пустым");
            return;
        }
        Task task = Task.createNew(title);
        TaskManager.saveNewTask(task);
        out.println("Задача успешно добавлена!");
    }

    private static void handleDeleteTask() {
        handleViewTasks();
        out.print("\nВведите номер задачи для удаления: ");
        try {
            int selectedTaskId = Integer.parseInt(SCANNER.nextLine());
            Task selectedTask = TaskManager.readTaskById(selectedTaskId);
            if (selectedTask == null) {
                out.println(TASK_NOT_FOUND);
                return;
            }
            TaskManager.deleteTaskById(selectedTaskId);
            out.println("Задача успешно удалена");
        } catch (NumberFormatException e) {
            out.println(INVALID_TASK_NUMBER);
        }
    }

    private static void handleCompleteTask(){
        handleViewTasks();
        out.print("\nВведите номер задачи которую вы выполнили: ");
        try {
            int selectedTaskId = Integer.parseInt(SCANNER.nextLine());
            Task selectedTask = TaskManager.readTaskById(selectedTaskId);
            if (selectedTask == null) {
                out.println(TASK_NOT_FOUND);
                return;
            }
            TaskManager.completeTask(selectedTaskId);
            out.println("Задача успешно выполенена");
        } catch (NumberFormatException e) {
            out.println(INVALID_TASK_NUMBER);
        }
    }
}