package storage;

import model.Task;
import java.util.*;

public class TaskStorage {
    private static List<Task> tasks = new ArrayList<>();
    private static int currentId = 1;

    public static List<Task> getAll() {
        return tasks;
    }
    public static boolean delete(int id) {
        return tasks.removeIf(task -> task.getId() == id);
    }
    public static Task add(String title, String priority, String dueDate)
    {
        Task task = new Task(currentId++, title, priority, dueDate);
        tasks.add(task);
        return task;
    }
}
