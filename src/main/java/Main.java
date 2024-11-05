import controller.DashboardController;
import controller.DatabaseUtil;
import controller.SessionController;
import controller.menuStates.InitialMenu;

public class Main {
    public static void main(String[] args) {
        SessionController session = new SessionController();
        DashboardController dashboard = new DashboardController();
        dashboard.SetState(new InitialMenu());
    }
}