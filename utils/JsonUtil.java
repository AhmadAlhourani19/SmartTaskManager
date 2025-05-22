package utils;

import model.Task;
import java.util.List;

public class JsonUtil {
    public static String taskListToJson(List<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(taskToJson(tasks.get(i)));
            if (i < tasks.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private static String escape(String text) {
        return text.replace("\"", "\\\"");
    }
    public static String taskToJson(Task task) {
        return String.format(
            "{\"id\":%d,\"title\":\"%s\",\"priority\":\"%s\",\"dueDate\":\"%s\"}",
            task.getId(), escape(task.getTitle()), escape(task.getPriority()), escape(task.getDueDate())
        );
    }
}
