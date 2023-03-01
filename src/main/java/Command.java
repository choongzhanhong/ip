/**
 * General Command class. To be inherited for each command.
 */
public abstract class Command {

    public abstract String execute(TaskList tasks, Ui ui, Storage storage);

    /** Only to be Overriden by Bye command */
    public boolean isExit() {
        return false;
    }
}
