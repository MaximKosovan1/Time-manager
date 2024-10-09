package controller;

import model.User;

import java.util.Scanner;

public class UserController {
    public void Register() {
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        String password = scanner.nextLine();

        User newUser = new User(username, password);
    }
    public boolean Login()
    {
        return false;
    }
}

