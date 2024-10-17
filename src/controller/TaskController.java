package controller;

import com.fasterxml.jackson.databind.util.Converter;
import model.Task;
import model.TaskState;
import view.Dashboard;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TaskController {
    private Dashboard _dashboard;

    public TaskController(){
        _dashboard = new Dashboard();
    }

    public void CreateNewTask() {
        String name;
        String description;
        Date startDay;
        Date endDay;
        TaskState state;

        var scanner = new Scanner(System.in);

        _dashboard.OutputCustomMessage("Введіть назву завдання");
        name = scanner.nextLine();
        _dashboard.OutputCustomMessage("Введіть опис завдання");
        description = scanner.nextLine();

        _dashboard.OutputCustomMessage("Введіть початкову дату завдання (yyyy-MM-dd)");
        startDay = ReadDate();
        _dashboard.OutputCustomMessage("Введіть кінцеву дату завдання (yyyy-MM-dd)");
        endDay = ReadDate();

        _dashboard.OutputCustomMessage("Введіть статус завдання");
        _dashboard.OutputCustomMessage("1 - " + TaskState.ToDo);
        _dashboard.OutputCustomMessage("2 - " + TaskState.InProgress);
        _dashboard.OutputCustomMessage("3 - " + TaskState.Done);
        state = ReadTaskState();

        CreateNewTask(name, description, state, endDay, startDay);
    }
    public void ChangeTaskStatusByName() {
        var scanner = new Scanner(System.in);

        _dashboard.OutputCustomMessage("Введіть назву завдання, для якого хочете змінити статус");
        String taskName = scanner.nextLine();

        try {
            Task task = SessionController.GetUser().findTaskByName(taskName);

            if (task != null) {
                _dashboard.OutputCustomMessage("Введіть новий статус для завдання \"" + taskName + "\":");
                _dashboard.OutputCustomMessage("1 - " + TaskState.ToDo);
                _dashboard.OutputCustomMessage("2 - " + TaskState.InProgress);
                _dashboard.OutputCustomMessage("3 - " + TaskState.Done);

                TaskState newState = ReadTaskState();
                task.state = newState;  // Оновлюємо статус завдання

                SessionController.GetUser().UpdateUserData();  // Оновлюємо дані користувача
                _dashboard.OutputCustomMessage("Статус завдання \"" + taskName + "\" змінено на " + newState);
            } else {
                _dashboard.OutputCustomMessage("Завдання з такою назвою не знайдено.");
            }
        } catch (IOException e) {
            _dashboard.OutputCustomMessage("Виникла помилка під час зміни статусу завдання: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void DeleteTaskByName() {
        var scanner = new Scanner(System.in);

        _dashboard.OutputCustomMessage("Введіть назву завдання, яке хочете видалити");
        String taskName = scanner.nextLine();

        try {
            boolean isDeleted = SessionController.GetUser().removeTaskByName(taskName);

            if (isDeleted) {
                _dashboard.OutputCustomMessage("Завдання \"" + taskName + "\" успішно видалено.");
            } else {
                _dashboard.OutputCustomMessage("Завдання з такою назвою не знайдено.");
            }
        } catch (IOException e) {
            _dashboard.OutputCustomMessage("Виникла помилка під час видалення завдання: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void CreateNewTask(String name, String description, TaskState state, Date endDay, Date startDay) {
        try {
            SessionController.GetUser().addTask(new Task(name, description, state, endDay, startDay));
        } catch (IOException e) {
            _dashboard.OutputCustomMessage("Виникла помилка під час додавання завдання: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private Date ReadDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        boolean isValid = false;

        while (!isValid) {
            _dashboard.OutputCustomMessage("Введіть дату");
            try {
                date = dateFormat.parse(new Scanner(System.in).nextLine());
                isValid = true;
            } catch (ParseException e) {
                _dashboard.OutputCustomMessage("Неправильний формат, спробуйте ще раз!");
            }
        }
        return date;
    }

    private TaskState ReadTaskState() {
        TaskState state = null;
        boolean isValid = false;

        while (!isValid) {
            int input = new Scanner(System.in).nextInt();
            state = TaskState.FromValue(input);

            if (state != null) {
                isValid = true;
            } else {
                _dashboard.OutputCustomMessage("Неправильний формат, спробуйте ще раз!");
            }
        }
        return state;
    }
}
