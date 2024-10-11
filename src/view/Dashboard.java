package view;

public class Dashboard {
    public void OutputEnterAccount() {
        System.out.println("---Вхід в акаунт---");
        System.out.println("1. Зареєструвати акаунт");
        System.out.println("2. Увійти в акаунт");
    }
    public void OutputRegisterMenu(){
        System.out.println("---Реєстрація акаунта---");
        System.out.println("Напишіть ім'я та пароль акаунта");
    }
    public void OutputLoginMenu(){
        System.out.println("---Вхід в існуючий акаунт---");
        System.out.println("Напишіть ім'я та пароль акаунта");
    }
    public void OutputMainMenu() {
        System.out.println("---Головне меню---");
        System.out.println("1. Список завдань");
        System.out.println("2. Список завдань в групі");
        System.out.println("3. Приєднатись до групи");
        System.out.println("4. Створити групу");
        System.out.println("5. Вийти з акаунта");
    }
    public void OutputError()
    {
        System.out.println("Помилка, спробуй ще раз!");
    }
}
