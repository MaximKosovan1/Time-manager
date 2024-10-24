package view;

import model.Task;
import model.LinkedTask;
import model.User;

import java.util.List;

public class Dashboard {

    public void OutputCustomMessage(String msg) {
        System.out.println(msg);
    }

    public void OutputInitialMenu() {
        System.out.println("==== Вхід в акаунт ====");
        System.out.println("1. Зареєструвати акаунт");
        System.out.println("2. Увійти в акаунт");
    }

    public void OutputRegisterMenu() {
        System.out.println("==== Реєстрація акаунта ====");
        System.out.println("Напишіть ім'я та пароль акаунта");
    }

    public void OutputLoginMenu() {
        System.out.println("==== Вхід в існуючий акаунт ====");
        System.out.println("Напишіть ім'я та пароль акаунта");
    }

    public void OutputMainMenu() {
        System.out.println("==== Головне меню ====");
        System.out.println("1. Меню завдань");
        System.out.println("2. Вийти з акаунта");
    }

    public void OutputUserTaskMenu(List<Task> tasks, List<LinkedTask> linkedTasks) {
        System.out.println("==== Меню завдань ====");
        System.out.println("* Початок списку активних завдань *");
        OutputTaskList(tasks);
        OutputLinkedTaskList(linkedTasks);
        System.out.println("* Кінець списку активних завдань *");
        System.out.println("1. Створити нове завдання");
        System.out.println("2. Створити зв'язане завдання");
        System.out.println("3. Змінити статус завдання");
        System.out.println("4. Видалити завдання");
        System.out.println("5. Повернутися до головного меню");
    }

    public void OutputTaskList(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            System.out.println("Немає доступних завдань.");
            return;
        }
        for (Task task : tasks) {
            OutputTask(task);
        }
    }

    public void OutputLinkedTaskList(List<LinkedTask> linkedTasks) {
        if (linkedTasks == null || linkedTasks.isEmpty()) {
            System.out.println("Немає зв'язаних завдань.");
            return;
        }
        System.out.println("==== Список зв'язаних завдань ====");
        for (LinkedTask linkedTask : linkedTasks) {
            OutputLinkedTask(linkedTask);
        }
    }

    public void OutputLinkedTask(LinkedTask linkedTask) {
        System.out.println();
        System.out.println("| " + linkedTask.name + " (зв'язане з: " + linkedTask.previousTask.name + ") |");
        System.out.println(linkedTask.StartDay + " - " + linkedTask.EndDay);
        System.out.println("Опис - " + linkedTask.description);
        System.out.println("Статус: " + linkedTask.state);
        System.out.println();
    }

    public void OutputTask(Task task) {
        System.out.println();
        System.out.println("| " + task.name + " |");
        System.out.println(task.StartDay + " - " + task.EndDay);
        System.out.println("Опис - " + task.description);
        System.out.println("Статус: " + task.state);
        System.out.println();
    }

    public void OutputError() {
        System.out.println("==== Помилка, спробуй ще раз! ====");
        System.out.println("1. Повернутись до меню реєстарції та входу в акаунт");
    }
}
