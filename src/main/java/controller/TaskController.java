package controller;

import model.LinkedTask;
import model.Task;
import model.TaskState;
import view.Dashboard;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        Scanner scanner = new Scanner(System.in);

        _dashboard.OutputCustomMessage("Введіть назву завдання");
        String name = scanner.nextLine();
        _dashboard.OutputCustomMessage("Введіть опис завдання");
        String description = scanner.nextLine();

        _dashboard.OutputCustomMessage("Введіть початкову дату завдання (yyyy-MM-dd)");
        Date startDay = ReadDate();
        _dashboard.OutputCustomMessage("Введіть кінцеву дату завдання (yyyy-MM-dd)");
        Date endDay = ReadDate();

        _dashboard.OutputCustomMessage("Введіть статус завдання");
        _dashboard.OutputCustomMessage("1 - " + TaskState.ToDo);
        _dashboard.OutputCustomMessage("2 - " + TaskState.InProgress);
        _dashboard.OutputCustomMessage("3 - " + TaskState.Done);
        TaskState state = ReadTaskState();

        CreateNewTask(name, description, state, endDay, startDay);
    }

    public void CreateNewLinkedTask() {
        Scanner scanner = new Scanner(System.in);
        _dashboard.OutputCustomMessage("Введіть назву попереднього завдання, з яким хочете зв'язати нове");
        String previousTaskName = scanner.nextLine();

        try {
            Task previousTask = SessionController.GetUser().findTaskByName(previousTaskName);

            if (previousTask != null) {
                _dashboard.OutputCustomMessage("Введіть назву нового завдання");
                String name = scanner.nextLine();
                _dashboard.OutputCustomMessage("Введіть опис нового завдання");
                String description = scanner.nextLine();

                _dashboard.OutputCustomMessage("Введіть початкову дату завдання (yyyy-MM-dd)");
                Date startDay = ReadDate();
                _dashboard.OutputCustomMessage("Введіть кінцеву дату завдання (yyyy-MM-dd)");
                Date endDay = ReadDate();

                _dashboard.OutputCustomMessage("Введіть статус завдання");
                _dashboard.OutputCustomMessage("1 - " + TaskState.ToDo);
                _dashboard.OutputCustomMessage("2 - " + TaskState.InProgress);
                _dashboard.OutputCustomMessage("3 - " + TaskState.Done);
                TaskState state = ReadTaskState();

                // Create and save the new linked task
                LinkedTask linkedTask = new LinkedTask();
                linkedTask.name = name;
                linkedTask.description = description;
                linkedTask.state = state;
                linkedTask.StartDay = startDay;
                linkedTask.EndDay = endDay;
                linkedTask.previousTaskId = previousTask.getId(); // Ensure this ID exists in tasks table
                linkedTask.userId = SessionController.GetUser().getUserId();

                // Add the linked task to the database
                addLinkedTaskToDatabase(linkedTask); // Implement this method to insert into linked_tasks table

                _dashboard.OutputCustomMessage("Зв'язане завдання \"" + name + "\" успішно створено і прив'язано до \"" + previousTaskName + "\".");
            } else {
                _dashboard.OutputCustomMessage("Завдання з такою назвою не знайдено.");
            }
        } catch (SQLException e) {
            _dashboard.OutputCustomMessage("Виникла помилка під час створення зв'язаного завдання: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void addLinkedTaskToDatabase(LinkedTask linkedTask) throws SQLException {
        String insertSQL = "INSERT INTO linked_tasks (name, description, state, start_day, end_day, previous_task_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

            stmt.setString(1, linkedTask.name);
            stmt.setString(2, linkedTask.description);
            stmt.setString(3, linkedTask.state.toString());
            stmt.setDate(4, new java.sql.Date(linkedTask.StartDay.getTime()));
            stmt.setDate(5, new java.sql.Date(linkedTask.EndDay.getTime()));
            stmt.setInt(6, linkedTask.previousTaskId); // Ensure this ID is valid
            stmt.setInt(7, linkedTask.userId);

            stmt.executeUpdate();
        }
    }

    private void CreateNewTask(String name, String description, TaskState state, Date startDay, Date endDay) {
        String insertSQL = "INSERT INTO tasks (name, description, state, start_day, end_day, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, state.toString());
            stmt.setDate(4, new java.sql.Date(startDay.getTime()));
            stmt.setDate(5, new java.sql.Date(endDay.getTime()));
            stmt.setInt(6, SessionController.GetUser().getUserId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating task failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int newTaskId = generatedKeys.getInt(1);
                    Task newTask = new Task(newTaskId, name, description, state, startDay, endDay, SessionController.GetUser().getUserId());
                    SessionController.GetUser().tasks.add(newTask); // Directly add the task to the user's task list
                } else {
                    throw new SQLException("Creating task failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            _dashboard.OutputCustomMessage("Database error while adding task: " + e.getMessage());
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
    public void ChangeTaskStatusByName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the task or linked task you want to update: ");
        String taskName = scanner.nextLine();

        System.out.println("Enter new status:");
        System.out.println("1 - ToDo");
        System.out.println("2 - InProgress");
        System.out.println("3 - Done");

        int statusChoice = scanner.nextInt();
        TaskState newState = TaskState.FromValue(statusChoice);

        if (newState == null) {
            System.out.println("Invalid status selected.");
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "UPDATE tasks SET state = ? WHERE name = ? AND user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newState.toString());
                stmt.setString(2, taskName);
                stmt.setInt(3, SessionController.GetUser().getUserId());
                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    _dashboard.OutputCustomMessage("Task status updated successfully.");
                } else {
                    updateLinkedTaskStatus(conn, taskName, newState);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error updating task status: " + e.getMessage());
        }
    }

    private void updateLinkedTaskStatus(Connection conn, String taskName, TaskState newState) throws SQLException {
        String sql = "UPDATE linked_tasks SET state = ? WHERE name = ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newState.toString());
            stmt.setString(2, taskName);
            stmt.setInt(3, SessionController.GetUser().getUserId());
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                _dashboard.OutputCustomMessage("Task status updated successfully.");
            } else {
                _dashboard.OutputCustomMessage("Task not found.");
            }
        }
    }

    public void DeleteTaskByName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the task or linked task you want to delete: ");
        String taskName = scanner.nextLine();

        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "DELETE FROM tasks WHERE name = ? AND user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, taskName);
                stmt.setInt(2, SessionController.GetUser().getUserId());
                int rowsDeleted = stmt.executeUpdate();

                if (rowsDeleted > 0) {
                    _dashboard.OutputCustomMessage("Task deleted successfully.");
                } else {
                    deleteLinkedTask(conn, taskName);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error deleting task: " + e.getMessage());
        }
    }

    private void deleteLinkedTask(Connection conn, String taskName) throws SQLException {
        String sql = "DELETE FROM linked_tasks WHERE name = ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, taskName);
            stmt.setInt(2, SessionController.GetUser().getUserId());
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                _dashboard.OutputCustomMessage("Task deleted successfully.");
            } else {
                _dashboard.OutputCustomMessage("Task not found.");
            }
        }
    }
}
