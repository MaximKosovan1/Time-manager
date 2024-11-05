package model;

import model.Task;
import model.TaskState;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "linked_tasks")
public class LinkedTask extends Task {

    @ManyToOne
    @JoinColumn(name = "previous_task_id")
    public Task previousTask;

    public LinkedTask() {}

    public LinkedTask(int id, String name, String description, TaskState state, Date startDay, Date endDay, Task previousTask, int userId) {
        super(id, name, description, state, startDay, endDay, userId);
        this.previousTask = previousTask; // Store the reference to the previous task
    }

}
