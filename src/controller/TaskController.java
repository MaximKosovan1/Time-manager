package controller;

import com.fasterxml.jackson.databind.util.Converter;
import model.Task;
import model.TaskState;
import view.Dashboard;

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
        Date startDay = null;
        Date endDay = null;
        TaskState state = null;
        boolean isConverted = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        var scanner = new Scanner(System.in);

        _dashboard.OutputCustomMessage("Введіть назву завдання");
        name = scanner.nextLine();
        _dashboard.OutputCustomMessage("Введіть опис завдання");
        description = scanner.nextLine();
        while(!isConverted) {
            _dashboard.OutputCustomMessage("Введіть початкову дату завдання (yyyy-MM-dd)");
            try {
                isConverted = true;
                startDay = dateFormat.parse(scanner.nextLine());
            } catch (ParseException e) {
                isConverted = false;
                _dashboard.OutputCustomMessage("Неправильний формат дати, використовуйте yyyy-MM-dd");
            }
        }
        isConverted = false;
        while(!isConverted) {
            _dashboard.OutputCustomMessage("Введіть кінцеву дату завдання (yyyy-MM-dd)");
            try {
                isConverted = true;
                endDay = dateFormat.parse(scanner.nextLine());
            } catch (ParseException e) {
                isConverted = false;
                _dashboard.OutputCustomMessage("Неправильний формат дати, використовуйте yyyy-MM-dd");
            }
        }
        isConverted = false;
        while(!isConverted) {
            _dashboard.OutputCustomMessage("Введіть статус завдання");
            _dashboard.OutputCustomMessage("1 - " + TaskState.ToDo);
            _dashboard.OutputCustomMessage("2 - " + TaskState.InProgress);
            _dashboard.OutputCustomMessage("3 - " + TaskState.Done);
            state = TaskState.FromValue(scanner.nextInt());
            isConverted = true;
            if (state == null) {
                isConverted = false;
                _dashboard.OutputCustomMessage("Неправильний статус, завдання не створено");
            }
        }
        CreateNewTask(name, description, state, endDay, startDay);
    }
    public void CreateNewTask(String name, String description, TaskState state, Date endDay, Date startDay)
    {
        SessionController.GetUser().tasks.add(new Task(name, description,
                state, endDay, startDay));
    }
}
