package model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User {
    public String username;
    public String password;
    public List<Task> tasks;
    public List<LinkedTask> linkedTasks;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public User() {
        tasks = new ArrayList<>();
        linkedTasks = new ArrayList<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        tasks = new ArrayList<>();
        linkedTasks = new ArrayList<>();
    }

    public void addTask(Task task) throws IOException {
        this.tasks.add(task);
        saveUsersToFile();
    }

    public void addLinkedTask(LinkedTask linkedTask) throws IOException {
        this.linkedTasks.add(linkedTask);
        saveUsersToFile();
    }

    public void removeTask(Task task) throws IOException {
        this.tasks.remove(task);
        saveUsersToFile();
    }

    public void removeLinkedTask(LinkedTask linkedTask) throws IOException {
        this.linkedTasks.remove(linkedTask);
        saveUsersToFile();
    }

    public boolean removeTaskByName(String taskName) throws IOException {
        for (Task task : tasks) {
            if (task.name.equals(taskName)) {
                tasks.remove(task);
                UpdateUserData();
                return true;
            }
        }
        return false;
    }

    public void UpdateUserData() throws IOException {
        List<User> users = loadUsersFromFile();

        for (User user : users) {
            if (user.username.equals(this.username)) {
                user.tasks = this.tasks;
                user.linkedTasks = this.linkedTasks;
                break;
            }
        }

        saveUsersToFile(users);
    }

    private void saveUsersToFile() throws IOException {
        List<User> users = loadUsersFromFile();

        for (User user : users) {
            if (user.username.equals(this.username)) {
                user.tasks = this.tasks;
                user.linkedTasks = this.linkedTasks;
            }
        }

        saveUsersToFile(users);
    }

    private List<User> loadUsersFromFile() {
        try {
            return objectMapper.readValue(new File("users.json"), new TypeReference<List<User>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Task findTaskByName(String taskName) {
        for (Task task : tasks) {
            if (task.name.equals(taskName)) {
                return task;
            }
        }
        return null;
    }

    public LinkedTask findLinkedTaskByName(String taskName) {
        for (LinkedTask linkedTask : linkedTasks) {
            if (linkedTask.name.equals(taskName)) {
                return linkedTask;
            }
        }
        return null;
    }

    private void saveUsersToFile(List<User> users) throws IOException {
        objectMapper.writeValue(new File("users.json"), users);
    }
}
