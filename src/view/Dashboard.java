package view;

import model.Task;
import model.User;

import java.util.List;

public class Dashboard {
    public void OutputCustomMessage(String msg)
    {
        System.out.println(msg);
    }
    public void OutputInitialMenu() {
        System.out.println("==== Вхід в акаунт ====");
        System.out.println("1. Зареєструвати акаунт");
        System.out.println("2. Увійти в акаунт");
    }
    public void OutputRegisterMenu(){
        System.out.println("==== Реєстрація акаунта ====");
        System.out.println("Напишіть ім'я та пароль акаунта");
    }
    public void OutputLoginMenu(){
        System.out.println("==== Вхід в існуючий акаунт ====");
        System.out.println("Напишіть ім'я та пароль акаунта");
    }
    public void OutputMainMenu() {
        System.out.println("==== Головне меню ====");
        System.out.println("1. Меню завдань");
        System.out.println("2. Меню завдань в групі");
        System.out.println("3. Приєднатись до групи");
        System.out.println("4. Створити групу");
        System.out.println("5. Вийти з акаунта");
    }
    public void OutputUserTaskMenu(List<Task> tasks) {
        System.out.println("==== Меню завдань ====");
        System.out.println("* Початок списку активних завдань *");
        OutputTaskList(tasks);
        System.out.println("* Кінець списку активних завдань *");
        System.out.println("1. Створити нове завдання");
        System.out.println("2. Змінити статус завдання");
        System.out.println("3. Видалити завдання групу");
        System.out.println("4. Пошук інших завдань");
        System.out.println("5. Повернутися до головного меню");
    }
    public void OutputTaskList(List<Task> tasks) {
        for (Task task : tasks) {
            OutputTask(task);
        }
    }
    public void OutputTask(Task task) {
        System.out.println();
        System.out.println(task.StartDay + " - " + task.EndDay);
        System.out.println("| " + task.name + " |");
        System.out.println("Опис ->");
        System.out.println(task.description);
        System.out.println("<-");
        System.out.println("Статус" + task.state);
        System.out.println();
    }
    public void OutputError() {
        System.out.println("==== Помилка, спробуй ще раз! ====");
        System.out.println("1. Повернутись до меню реєстарції та входу в акаунт");
    }
}
