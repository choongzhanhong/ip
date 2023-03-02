package command;

import io.Storage;
import io.Ui;
import task.TaskList;

/**
 * This Command executes a safe exit of Duke.
 */
public class CommandExit extends Command {

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.writeAllToFile(storage);
        return ui.MESSAGE_OUTRO;
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
