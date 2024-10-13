package controller.menuStates;

import controller.SessionController;
import view.Dashboard;

public class UserTaskMenu implements MenuState {
    private Dashboard _dashboard;

    public UserTaskMenu() {
        _dashboard = new Dashboard();
    }

    @Override
    public void Interaction() {
    }

    @Override
    public void Display() {
        _dashboard.OutputUserTaskMenu(SessionController.GetUser().tasks);
    }

    @Override
    public void SelectMenu(int option) {
    }
}
