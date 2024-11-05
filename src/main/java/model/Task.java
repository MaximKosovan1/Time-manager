package model;

import java.util.Date;

public class Task {
    public int id;
    public String name;
    public String description;
    public TaskState state;
    public Date StartDay;
    public Date EndDay;
    public int userId; // Add userId field

    public Task() {
    }

    public Task(int id, String name, String description, TaskState state, Date startDay, Date endDay, int userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
        StartDay = startDay;
        EndDay = endDay;
        this.userId = userId; // Initialize userId
    }

    public int getId() {
        return id;
    }

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
