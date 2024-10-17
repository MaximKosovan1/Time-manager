package controller.menuStates;

import controller.DashboardController;
import controller.SessionController;
import controller.TaskController;
import view.Dashboard;

import java.util.Scanner;

public class UserTaskMenu implements MenuState {
    private Dashboard _dashboard;
    private TaskController _taskController;

    public UserTaskMenu() {
        _dashboard = new Dashboard();
        _taskController = new TaskController();
    }

    @Override
    public void Interaction() {
        var scanner = new Scanner(System.in);
        SelectMenu(scanner.nextInt());
    }

    @Override
    public void Display() {
        _dashboard.OutputUserTaskMenu(SessionController.GetUser().tasks);
    }

    @Override
    public void SelectMenu(int option) {
        switch(option) {
            case 1:
                _taskController.CreateNewTask();
                DashboardController.Instance.SetState(this);
                break;
            case 2:
                _taskController.ChangeTaskStatusByName();
                DashboardController.Instance.SetState(this);
                break;
            case 3:
                _taskController.DeleteTaskByName();
                DashboardController.Instance.SetState(this);
                break;
            case 4:
                DashboardController.Instance.SetState(new MainMenu());
                break;
            default:
                DashboardController.Instance.SetState(this);
        }
    }
}
