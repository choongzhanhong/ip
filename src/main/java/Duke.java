import command.Command;
import io.*;
import task.Deadline;
import task.Event;
import task.TaskList;
import task.Todo;
import task.Task;

import java.util.Scanner;

public class Duke {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    // TODO: Find a way to combine this and the default constructor.
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(); //storage.load());
        } catch (DukeException e) {
            System.out.println("Error");
            tasks = new TaskList();
        }
    }

    public Duke() {
        ui = new Ui();
        storage = new Storage();
        try {
            tasks = new TaskList(); //storage.load());
        } catch (DukeException e) {
            System.out.println("Error");
            tasks = new TaskList();
        }
    }

    /**
     * Runs duke. Takes in a command-line argument on the directory (if any).
     *
     * @param args First arg should be directory.
     */
    public static void main(String[] args) {
        if (Parser.isValidPath(args[0])) {
            new Duke(args[0]).run();
        } else {
            new Duke().run();
        }
    }

    private void run() {
        Ui.printGreeting();



        boolean isExit = false;

        while (!isExit) {
            System.out.print("> ");
            String fullCommand = ui.readCommand();
            String feedback = executeCommand(fullCommand);
            System.out.println(feedback);

            Ui.printHLine();
        }

    }

    /**
     * Performs each input's methods as long as it exists, if not return error
     * @param inputLine Input directly from command-line
     * @return Feedback string or error string
     */
    public static String executeCommand(String inputLine) {
        final String[] commandTypeAndArgs = Parser.splitCommandAndArgs(inputLine);
        final String command = commandTypeAndArgs[0];
        final String commandArgs = commandTypeAndArgs[1];

        // Check command against the set list of commands.
        // If it doesn't exist, default is invalid
        switch(command) {
        case Command.COMMAND_HELP:
            return Ui.MESSAGE_HELP;
        case Command.COMMAND_LIST:
            if (tasks.getNumberOfTasks() < 1) {
                return Ui.ERROR_TASKS_EMPTY;
            }
            return TaskList.getTaskListString();
        case Command.COMMAND_MARK: // Fallthrough
        case Command.COMMAND_UNMARK:
            return TaskList.executeMarkUnmark(command, commandArgs);
        case Command.COMMAND_TASK_TODO:
            return handleAddTaskTodo(commandArgs);
        case Command.COMMAND_TASK_DEADLINE:
            return handleAddTaskDeadline(commandArgs);
        case Command.COMMAND_TASK_EVENT:
            return handleAddTaskEvent(commandArgs);
        case Command.COMMAND_DELETE:
            return handleDelete(commandArgs);
        case Command.COMMAND_BYE:
            Ui.printExitMessage();
            TaskList.writeAllToFile();
            System.exit(0);
            // Fallthrough (If somehow cannot exit? LOL)
        default:
            return Ui.ERROR_MESSAGE_INVALID_COMMAND;
        }
    }

    /**
     * ==============================================================
     * Below handling Tasks (Validation and addTask)
     * ==============================================================
     */
    private static String handleAddTaskTodo(String commandArgs) {
        try {
            Todo newTask = new Todo(Parser.processTaskTodo(commandArgs), TaskList.getNextTaskNumber());
            tasks.addTask(newTask);
            return Ui.feedbackTaskAdded(newTask);
        } catch (DukeException e) {
            return Ui.ERROR_MESSAGE_ARGUMENT_MISSING;
        }
    }

    private static String handleAddTaskDeadline(String commandArgs) {
        try {
            String[] deadlineArgs = Parser.processTaskDeadline(commandArgs);
            Deadline newTask =
                    new Deadline(deadlineArgs[0], TaskList.getNextTaskNumber(), deadlineArgs[1]);
            tasks.addTask(newTask);
            return Ui.feedbackTaskAdded(newTask);
        } catch (DukeException e) {
            return Ui.ERROR_MESSAGE_ARGUMENT_NUMBER;
        }
    }

    private static String handleAddTaskEvent(String commandArgs) {
        try {
            String[] eventArgs = Parser.processTaskEvent(commandArgs);
            Event newTask =
                    new Event(eventArgs[0], TaskList.getNextTaskNumber(),
                            eventArgs[1], eventArgs[2]);
            tasks.addTask(newTask);
            return Ui.feedbackTaskAdded(newTask);
        } catch (DukeException e) {
            return Ui.ERROR_MESSAGE_ARGUMENT_NUMBER;
        }
    }

    /**
     * Validates input for delete command, and then deletes the task.
     * @param commandArgs 1-indexed number to be parsed as integer.
     * @return Feedback string, either successful delete or throw number exception.
     */
    private static String handleDelete(String commandArgs) {
        int taskNumber;

        // Parse Int first
        try {
            taskNumber = Integer.parseInt(commandArgs);
        } catch (NumberFormatException e) {
            return Ui.ERROR_MESSAGE_TASK_INDEX;
        }

        // Index out of bounds
        if (taskNumber > TaskList.getNumberOfTasks()) {
            return Ui.ERROR_MESSAGE_TASK_INDEX;
        }

        Task deletedTask = TaskList.deleteTask(taskNumber);
        String output = "Noted. I've removed this task:\n"
                + deletedTask.toString() + '\n'
                + "Now you have " + TaskList.getNumberOfTasks() + " task(s) in the list.";
        return output;
    }
}
