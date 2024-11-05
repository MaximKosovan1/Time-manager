package model;

public enum TaskState {
    ToDo(1),
    InProgress(2),
    Done(3);

    private final int value;

    TaskState(int value) {
        this.value = value;
    }

    public static TaskState FromValue(int value) {
        for (TaskState state : TaskState.values()) {
            if (state.value == value) {
                return state;
            }
        }
        return null; // or throw an exception
    }
}
