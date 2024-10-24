package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserController {

    public boolean Register() {
        var scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        String password = scanner.nextLine();
        User newUser = new User(username, password);

        ObjectMapper objectMapper = new ObjectMapper();
        List<User> users = new ArrayList<>();
        File file = new File("users.json");

        try {
            if (file.exists()) {
                users = objectMapper.readValue(file, new TypeReference<List<User>>() {});
            }

            users.add(newUser);

            objectMapper.writeValue(file, users);

            SessionController.SetUser(newUser);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean Login() {
        var scanner = new Scanner(System.in);
        String typedUsername = scanner.nextLine();
        String typedPassword = scanner.nextLine();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<User> users = objectMapper.readValue(new File("users.json"), new TypeReference<List<User>>() {});

            for (User user : users) {
                if (user.username.equals(typedUsername) && user.password.equals(typedPassword)) {
                    SessionController.SetUser(user);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

