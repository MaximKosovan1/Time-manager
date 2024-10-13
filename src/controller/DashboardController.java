package controller;

import controller.menuStates.InitialMenu;
import controller.menuStates.MenuState;
import view.Dashboard;

import java.util.Scanner;

public class DashboardController {
    static public DashboardController Instance;

    public MenuState CurrentMenuState;

    public DashboardController(){
        if(Instance == null) Instance = this;

        SetState(new InitialMenu());
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
