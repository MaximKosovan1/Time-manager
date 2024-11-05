package model;

import java.util.Date;

public class LinkedTask extends Task {
    public Task previousTask;
    public Integer previousTaskId;

    public LinkedTask()
    {

    }

    public LinkedTask(int id, String name, String description, TaskState state, Date startDay, Date endDay, int userId) {
        super(id, name, description, state, startDay, endDay, userId); // Call parent constructor
    }
}
