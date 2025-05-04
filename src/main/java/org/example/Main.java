package org.example;


import static java.lang.System.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        while (true){
            Scanner scanner = new Scanner(in);
            out.println(" ");
            out.println("Выберите действие:");
            out.println("-------------------");
            out.println("1. Добавить новую задачу");
            out.println("2. Посмотреть список текщих задач");
            out.println("3. Удалить задачу");
            out.println("4. Выход");

            out.println("Выберите действие: ");
            int a = scanner.nextInt();

            switch (a){
                case 1:
                    TaskManager.addTask();
                    break;
                case 2:
                    out.println("Текущие задачи: ");

                    out.println("Введите номер задачи которую необходимо открыть: ");
                    
                    break;
                case 3:
                    out.println(3);
                    break;
                case 4:
                    exit(0);
                    break;
                default:
                    out.println("Долбаеб");
            }
        }
    }
}