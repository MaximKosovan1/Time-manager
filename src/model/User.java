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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public User() {
        tasks = new ArrayList<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) throws IOException {
        this.tasks.add(task);
        saveUsersToFile();
    }

    public void removeTask(Task task) throws IOException {
        this.tasks.remove(task);
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
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> users = objectMapper.readValue(new File("users.json"), new TypeReference<List<User>>() {});

        for (User user : users) {
            if (user.username.equals(this.username)) {
                user.tasks = this.tasks;
                break;
            }
        }

        objectMapper.writeValue(new File("users.json"), users);
    }

    private void saveUsersToFile() throws IOException {
        List<User> users = loadUsersFromFile();

        for (User user : users) {
            if (user.username.equals(this.username)) {
                user.tasks = this.tasks;
            }
        }
        objectMapper.writeValue(new File("users.json"), users);
    }

    public Task findTaskByName(String taskName) {
        for (Task task : tasks) {
            if (task.name.equals(taskName)) {
                return task;
            }
        }
        return null;
    }

    private List<User> loadUsersFromFile() {
        try {
            return objectMapper.readValue(new File("users.json"), new TypeReference<List<User>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
