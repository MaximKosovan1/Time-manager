package model;

public class User {
    public String username;
    public String password;
    public Group[] groups;
    public Task[] tasks;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}
