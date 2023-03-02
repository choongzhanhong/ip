package task;

import io.Storage;

public class Deadline extends Task {
    protected String by;

    /**
     * A task with a deadline.
     * @param description Name of task
     * @param taskNumber the 1-indexed number of task
     * @param by Deadline
     */
    public Deadline(String description, int taskNumber, String by) {
        super(description, taskNumber);
        this.by = by;
    }

    @Override
    public String toString() {
        String byWhen = " (by: " + this.by + ")";
        return "[D]" + super.toString() + byWhen;
    }

    @Override
    public String getFileWriteFormat() {
        String output = "D " + super.getFileWriteFormat()
                + String.format(" %s %s", Storage.FILE_DELIMITER, by);
        return output;
    }
}
