package controller;

import view.Dashboard;

import java.util.Scanner;

public class DashboardController {
    private Dashboard _dashboard;
    private UserController _userController;
    public DashboardController(){
        _dashboard = new Dashboard();
        _userController = new UserController();
        Start();
    }
    public void Start() {
        _dashboard.OutputEnterAccount();
        var scanner = new Scanner(System.in);
        boolean isEntered = false;
        while(isEntered != true) {
            switch (scanner.nextLine()) {
                case "1":
                    _dashboard.OutputRegisterMenu();
                    isEntered = _userController.Register(scanner.nextLine(), scanner.nextLine());
                    break;
                case "2":
                    _dashboard.OutputLoginMenu();
                    isEntered = _userController.Login(scanner.nextLine(), scanner.nextLine());
                    break;
                default:
                    _dashboard.OutputError();
                    isEntered = false;
            }
        }
    }
}
