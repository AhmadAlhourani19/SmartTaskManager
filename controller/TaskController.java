package controller;

import storage.TaskStorage;
import utils.JsonUtil;

public class TaskController {

    public static String handleGetAllTasks() {
        return JsonUtil.taskListToJson(TaskStorage.getAll());
    }

    public static String handleCreateTask(String title, String priority, String dueDate) {
        return JsonUtil.taskToJson(TaskStorage.add(title, priority, dueDate));
    }

    public static boolean handleDeleteTask(int id) {
        return TaskStorage.delete(id);
    }
}
