package controller.menuStates;

import controller.DashboardController;
import controller.UserController;
import view.Dashboard;

public class LoginMenu implements MenuState{
    private Dashboard _dashboard;
    private UserController _userController;

    public LoginMenu(){
        _dashboard = new Dashboard();
        _userController = new UserController();
    }
    @Override
    public void Interaction() {
        if(_userController.Login()){
            DashboardController.Instance.SetState(new MainMenu());
        }
        else {
            DashboardController.Instance.SetState(new ErrorMenu());
        }
    }

    @Override
    public void Display() {
        _dashboard.OutputLoginMenu();
    }

    @Override
    public void SelectMenu(int option) {
    }
}
