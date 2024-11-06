package controller.menuStates;

import controller.BeanManager;
import controller.DashboardController;
import controller.UserController;
import view.Dashboard;

public class LoginMenu implements MenuState{
    private Dashboard _dashboard;
    private UserController _userController;

    public LoginMenu(){
        _dashboard = BeanManager.getContext().getBean(Dashboard.class);
        _userController = BeanManager.getContext().getBean(UserController.class);
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
