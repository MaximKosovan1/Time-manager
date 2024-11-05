package controller.menuStates;

import controller.DashboardController;
import controller.SessionController;
import view.Dashboard;

import java.util.Scanner;

public class ErrorMenu implements MenuState{
    private Dashboard _dashboard;

    public ErrorMenu(){
        _dashboard = new Dashboard();
    }

    @Override
    public void Interaction() {
        SessionController.SetUser(null);
        Scanner scanner = new Scanner(System.in);
        SelectMenu(scanner.nextInt());
    }

    @Override
    public void Display() {
        _dashboard.OutputError();
    }

    @Override
    public void SelectMenu(int option) {
        switch(option)
        {
            case 1:
                DashboardController.Instance.SetState(new InitialMenu());
            default:
                DashboardController.Instance.SetState(this);
        }
    }
}
