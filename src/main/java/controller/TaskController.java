package controller;

import model.LinkedTask;
import model.Task;
import model.TaskState;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import view.Dashboard;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TaskController {
    private Dashboard _dashboard;

    public TaskController() {
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

        createNewTask(name, description, state, startDay, endDay);
    }

    public void CreateNewLinkedTask() {
        Scanner scanner = new Scanner(System.in);
        _dashboard.OutputCustomMessage("Введіть назву попереднього завдання, з яким хочете зв'язати нове");
        String previousTaskName = scanner.nextLine();

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

            LinkedTask linkedTask = new LinkedTask(
                    0,
                    name,
                    description,
                    state,
                    startDay,
                    endDay,
                    previousTask,
                    SessionController.GetUser().id
            );

            EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.persist(linkedTask);
                transaction.commit();

                _dashboard.OutputCustomMessage("Зв'язане завдання \"" + name + "\" успішно створено і прив'язано до \"" + previousTaskName + "\".");
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                _dashboard.OutputCustomMessage("Виникла помилка під час створення зв'язаного завдання: " + e.getMessage());
                e.printStackTrace();
            } finally {
                entityManager.close();
            }
        }
}
    private void createNewTask(String name, String description, TaskState state, Date startDay, Date endDay) {
        Task newTask = new Task(0, name, description, state, startDay, endDay, SessionController.GetUser().getUserId());

        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(newTask);
            transaction.commit();

            SessionController.GetUser().tasks.add(newTask);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            _dashboard.OutputCustomMessage("Database error while adding task: " + e.getMessage());
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    private Date ReadDate() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            _dashboard.OutputCustomMessage("Введіть дату");
            String input = scanner.nextLine();
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(input);
            } catch (ParseException e) {
                _dashboard.OutputCustomMessage("Неправильний формат, спробуйте ще раз!");
            }
        }
    }

    private TaskState ReadTaskState() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int input = scanner.nextInt();
            TaskState state = TaskState.FromValue(input);
            if (state != null) {
                return state;
            } else {
                _dashboard.OutputCustomMessage("Неправильний формат, спробуйте ще раз!");
            }
        }
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

        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            String hql = "UPDATE Task SET state = :state WHERE name = :name AND userId = :userId";
            int rowsUpdated = entityManager.createQuery(hql)
                    .setParameter("state", newState)
                    .setParameter("name", taskName)
                    .setParameter("userId", SessionController.GetUser().getUserId())
                    .executeUpdate();

            if (rowsUpdated > 0) {
                _dashboard.OutputCustomMessage("Task status updated successfully.");
            } else {
                updateLinkedTaskStatus(entityManager, taskName, newState);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error updating task status: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    private void updateLinkedTaskStatus(EntityManager entityManager, String taskName, TaskState newState) {
        String hql = "UPDATE LinkedTask SET state = :state WHERE name = :name AND userId = :userId";
        int rowsUpdated = entityManager.createQuery(hql)
                .setParameter("state", newState)
                .setParameter("name", taskName)
                .setParameter("userId", SessionController.GetUser().getUserId())
                .executeUpdate();

        if (rowsUpdated > 0) {
            _dashboard.OutputCustomMessage("Task status updated successfully.");
        } else {
            _dashboard.OutputCustomMessage("Task not found.");
        }
    }

    public void DeleteTaskByName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the task or linked task you want to delete: ");
        String taskName = scanner.nextLine();

        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            String hql = "DELETE FROM Task WHERE name = :name AND userId = :userId";
            int rowsDeleted = entityManager.createQuery(hql)
                    .setParameter("name", taskName)
                    .setParameter("userId", SessionController.GetUser().getUserId())
                    .executeUpdate();

            if (rowsDeleted > 0) {
                _dashboard.OutputCustomMessage("Task deleted successfully.");
            } else {
                deleteLinkedTask(entityManager, taskName);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error deleting task: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    private void deleteLinkedTask(EntityManager entityManager, String taskName) {
        String hql = "DELETE FROM LinkedTask WHERE name = :name AND userId = :userId";
        int rowsDeleted = entityManager.createQuery(hql)
                .setParameter("name", taskName)
                .setParameter("userId", SessionController.GetUser().getUserId())
                .executeUpdate();

        if (rowsDeleted > 0) {
            _dashboard.OutputCustomMessage("Linked task deleted successfully.");
        } else {
            _dashboard.OutputCustomMessage("Task not found.");
        }
    }
}
