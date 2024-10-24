import controller.DashboardController;
import controller.SessionController;
import controller.menuStates.InitialMenu;

public class Main {
    public static void main(String[] args) {
        var session = new SessionController();
        var dashboard = new DashboardController();
        dashboard.SetState(new InitialMenu());
    }
}