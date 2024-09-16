package model;
import java.util.Date;

public class Task {
    public String name;
    public String description;
    public TaskState state;
    public Date StartDay;
    public Date EndDay;
}

enum TaskState {
    ToDo,
    InProgress,
    Done
}