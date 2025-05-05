# Task Manager

A simple Java-based task manager that allows you to create, view, update status, and delete tasks stored as JSON files.

## Features

- Create new tasks
- View all saved tasks
- Mark tasks as completed
- Delete tasks by ID
- All tasks are stored as individual JSON files in the `TODO/` directory

## Task Structure

Each task is represented as a JSON file with the following fields:

```json
{
  "id": 1,
  "title": "My Task",
  "completed": false
}
```

## Requirements

- Java 21 or higher
- Jackson (for JSON serialization)

## How It Works

- Tasks are saved in the `TODO/` directory.
- Filenames are formatted as: `title_id.json` (e.g., `BuyMilk_3.json`)
- IDs are automatically incremented based on existing files.

## Running

You can use any IDE or build tool to compile and run the application. Example entry point:

```java
public static void main(String[] args) {
    Task newTask = new Task(TaskManager.getNextId(), "Buy groceries");
    TaskManager.saveNewTask(newTask);

    TaskManager.completeTask(newTask.getId());

    List<Task> tasks = TaskManager.viewAllTasks();
    tasks.forEach(System.out::println);

    TaskManager.deleteTaskById(newTask.getId());
}
```

## Notes

- The application ensures the `TODO/` folder exists before working with files.
- Basic error handling is implemented with logging.
