package controller.menuStates;

import controller.DashboardController;
import view.Dashboard;

import java.util.Scanner;

public class InitialMenu implements MenuState {
    private Dashboard _dashboard;

    public InitialMenu(){
        _dashboard = new Dashboard();
    }
    @Override
    public void Interaction() {
        Scanner scanner = new Scanner(System.in);
        SelectMenu(scanner.nextInt());
    }

    @Override
    public void Display() {
        _dashboard.OutputInitialMenu();
    }

    @Override
    public void SelectMenu(int option) {
        switch(option)
        {
            case 1:
                DashboardController.Instance.SetState(new RegistrationMenu());
                break;
            case 2:
                DashboardController.Instance.SetState(new LoginMenu());
                break;
            default:
                DashboardController.Instance.SetState(new ErrorMenu());
        }
    }
}
