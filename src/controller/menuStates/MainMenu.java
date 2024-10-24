package controller.menuStates;

import controller.DashboardController;
import controller.SessionController;
import view.Dashboard;

import java.util.Scanner;

public class MainMenu implements MenuState{
    private Dashboard _dashboard;

    public MainMenu(){
        _dashboard = new Dashboard();
    }

    @Override
    public void Display() {
        _dashboard.OutputMainMenu();
    }

    @Override
    public void Interaction() {
        var scanner = new Scanner(System.in);
        SelectMenu(scanner.nextInt());
    }

    @Override
    public void SelectMenu(int option) {
        switch(option)
        {
            case 1:
                DashboardController.Instance.SetState(new UserTaskMenu());
                break;
            case 2:
                SessionController.SetUser(null);
                DashboardController.Instance.SetState(new InitialMenu());
                break;
            default:
                DashboardController.Instance.SetState(new MainMenu());
        }
    }
}
