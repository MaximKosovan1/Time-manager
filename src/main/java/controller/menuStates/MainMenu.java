package controller.menuStates;

import controller.BeanManager;
import controller.DashboardController;
import controller.SessionController;
import view.Dashboard;

import java.util.Scanner;

public class MainMenu implements MenuState{
    private Dashboard _dashboard;

    public MainMenu(){
        _dashboard = BeanManager.getContext().getBean(Dashboard.class);
    }

    @Override
    public void Display() {
        _dashboard.OutputMainMenu();
    }

    @Override
    public void Interaction() {
        Scanner scanner = new Scanner(System.in);
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
