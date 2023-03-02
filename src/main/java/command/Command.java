package command;

import io.Storage;
import io.Ui;
import task.TaskList;

/**
 * General Command class. To be inherited for each command.
 */
public abstract class Command {

    public abstract String execute(TaskList tasks, Ui ui, Storage storage);

    /** Only to be Overridden by Bye command */
    public boolean isExit() {
        return false;
    }
}
