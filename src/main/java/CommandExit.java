/**
 * This Command executes a safe exit of Duke.
 */
public class CommandExit extends Command {

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printExitMessage();
        tasks.writeAllToFile(storage);
        System.exit(0);
        return "Unable to exit LOL";
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
