package controller.menuStates;

import controller.DashboardController;
import controller.UserController;
import view.Dashboard;

public class RegistrationMenu implements MenuState{
    private Dashboard _dashboard;
    private UserController _userController;

    public RegistrationMenu(){
        _dashboard = new Dashboard();
        _userController = new UserController();
    }

    @Override
    public void Interaction() {
        if(_userController.Register()){
            DashboardController.Instance.SetState(new MainMenu());
        }
        else {
            DashboardController.Instance.SetState(new ErrorMenu());
        }
    }

    @Override
    public void Display() {
        _dashboard.OutputRegisterMenu();
    }

    @Override
    public void SelectMenu(int option) {
    }
}
