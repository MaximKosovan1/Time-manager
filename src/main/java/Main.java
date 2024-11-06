import controller.AppConfig;
import controller.BeanManager;
import controller.DashboardController;
import controller.TaskController;
import controller.menuStates.InitialMenu;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        BeanManager beanManager= new BeanManager();
        BeanManager.setContext(context);
        TaskController taskController = context.getBean(TaskController.class);
        DashboardController dashboard = context.getBean(DashboardController.class);

        dashboard.SetState(new InitialMenu());

        context.close();
    }
}
