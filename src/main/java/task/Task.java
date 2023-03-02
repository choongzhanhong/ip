package task;

import io.Storage;

/**
 * Abstract class to cover different task types.
 * Individual tasks. Has to be inherited.
 */

public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected int taskNumber;

    // Constructor
    public Task(String description, int taskNumber) {
        this.description = description;
        this.isDone = false;
        this.taskNumber = taskNumber;
    }

    // To be used to list out the task.
    @Override
    public String toString() {
        return (this.getDoneIcon() + " " + this.description);
    }

    // return a string to indicate done or not done.
    private String getDoneIcon() {
        return (this.isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    protected String getTaskNumber() {
        return (this.taskNumber + ".");
    }

    // use "mark" to mark done
    public String markAsDone() {
        this.isDone = true;
        return "Marked as done: " + this;
    }
    // use "unmark" to mark a task not done
    public String markAsUndone() {
        this.isDone = false;
        return "Marked as undone: " + this;
    }

    /**
     * To be used by child classes to append their own file write.
     * @return String in the format " 1/0 | description"
     */
    public String getFileWriteFormat() {
        String done = isDone ? "1" : "0";
        return String.format("%s %s %s %s", Storage.FILE_DELIMITER,
                done, Storage.FILE_DELIMITER, description);
    };
}
