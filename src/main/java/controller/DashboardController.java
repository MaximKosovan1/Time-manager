package controller;

import controller.menuStates.MenuState;

public class DashboardController {
    static public DashboardController Instance;

    public MenuState CurrentMenuState;

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
