package command;

import io.Storage;
import io.Ui;
import task.TaskList;

/**
 * General Command class. To be inherited for each command.
 */
public abstract class Command {

    /** Command format for the user's input. */
    public static final String COMMAND_HELP = "help";
    public static final String COMMAND_LIST = "list";
    public static final String COMMAND_BYE = "bye";
    public static final String COMMAND_MARK = "mark";
    public static final String COMMAND_UNMARK = "unmark";
    public static final String COMMAND_DELETE = "delete";
    /** Add task commands. Sample usage as below. not delimited by spaces.
     *  todo: "todo task_name"
     *  deadline: "deadline task_name /by due_date"
     *  event: "event task_name /from start_time /to end_time"
     */
    public static final String COMMAND_TASK_TODO = "todo";
    public static final String COMMAND_TASK_DEADLINE = "deadline";
    public static final String COMMAND_TASK_DEADLINE_DELIMITER = "/by";
    public static final String COMMAND_TASK_DEADLINE_DELIMITER_REGEX =
            "\\s+\\" + COMMAND_TASK_DEADLINE_DELIMITER + "\\s+";
    public static final String COMMAND_TASK_EVENT = "event";
    public static final String COMMAND_TASK_EVENT_DELIMITER1 = "/from";
    public static final String COMMAND_TASK_EVENT_DELIMITER2 = "/to";
    public static final String COMMAND_TASK_EVENT_DELIMITER_REGEX =
            "\\s+\\" + COMMAND_TASK_EVENT_DELIMITER1 +
                    "\\s+|\\s+\\" + COMMAND_TASK_EVENT_DELIMITER2 + "\\s+";

    public abstract String execute(TaskList tasks, Ui ui, Storage storage);

    /** Only to be Overridden by Bye command */
    public boolean isExit() {
        return false;
    }
}
