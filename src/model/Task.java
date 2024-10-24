package model;
import java.util.Date;

public class Task {
    public String name;
    public String description;
    public TaskState state;
    public Date StartDay;
    public Date EndDay;

    public Task()
    {
    }

    public Task(String name, String description, TaskState state, Date startDay, Date endDay) {
        this.name = name;
        this.description = description;
        this.state = state;
        StartDay = startDay;
        EndDay = endDay;
    }
}

