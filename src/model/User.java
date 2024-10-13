package model;

import java.util.List;

public class User {
    public String username;
    public String password;
    public List<Group> groups;
    public List<Task> tasks;

    // порожній конструктор без параметрів потрібен для Jackson
    public User() {
    }

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}
