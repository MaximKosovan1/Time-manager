package controller;

import controller.menuStates.MenuState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardController {
    static public DashboardController Instance;

    public MenuState CurrentMenuState;
    @Autowired
    public DashboardController(){
        if(Instance == null) Instance = this;
    }

    public void SetState(MenuState state){
        CurrentMenuState = state;
        Display();
        Interaction();
    }

    public void Interaction() {
        CurrentMenuState.Interaction();
    }

    public void Display() {
        CurrentMenuState.Display();
    }
    public void SelectMenu (int option) {
        CurrentMenuState.SelectMenu(option);
    }
}
