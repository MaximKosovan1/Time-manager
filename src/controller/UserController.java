package controller;

import model.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class UserController {
    public boolean Register(String username, String password) {
        User newUser = new User(username, password);
        String filePath = "users.txt";
        String content = newUser.username + " " + newUser.password;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    writer.write(content);
                    return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean Login(String username, String password)
    {
        return false;
    }
}

