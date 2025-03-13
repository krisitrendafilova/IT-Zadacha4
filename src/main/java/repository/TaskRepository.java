package repository;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private static TaskRepository instance;
    private static List<Task> taskList;
    private static int id = 1;

    private TaskRepository() {
        taskList = new ArrayList<>();
    }


//    public static void setTaskList(List<Task> taskList) {
//        TaskRepository.taskList = taskList;
//    }

    public static TaskRepository getInstance() {
        if (instance == null) {
            instance = new TaskRepository();
        }
        return instance;
    }

    public static void addTask(Task task) {
        task.setId(id++);
        taskList.add(task);
    }

    public Task getTaskById(int id) {
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public List<Task> getTaskList() {
        return new ArrayList<>(taskList);
    }
}
