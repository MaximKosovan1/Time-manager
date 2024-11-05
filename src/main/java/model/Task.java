package model;

import controller.SessionController;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "description")
    public String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    public TaskState state;

    @Column(name = "start_day")
    @Temporal(TemporalType.DATE)
    public Date startDay;

    @Column(name = "end_day")
    @Temporal(TemporalType.DATE)
    public Date endDay;
    @Column(name = "user_id")
    public int userId;

    public Task() {
    }

    public Task(int id, String name, String description, TaskState state, Date startDay, Date endDay, int userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
        this.startDay = startDay;
        this.endDay = endDay;
        this.userId = userId; // Initialize userId
    }

    public int getId() {
        return id;
    }
  /*  public Task getTaskById(int taskId) {
        // Implement logic to fetch the task from the database
        // This could be using JPA, Hibernate, or JDBC, depending on your setup
        return SessionController.GetUser().find(Task.class, taskId);
*/
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() { // Getter for userId
        return userId;
    }

    public void setUserId(int userId) { // Setter for userId (optional)
        this.userId = userId;
    }
}
