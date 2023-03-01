public class CommandHelp extends Command {

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return ui.MESSAGE_HELP;
    }
}
