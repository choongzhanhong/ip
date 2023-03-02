package task;

import java.util.ArrayList;
import io.Storage;

import io.Ui;

public class TaskList {
    private static final ArrayList<Task> tasks = new ArrayList<Task>();
    private static int numberOfTasks = 0;

    /**
     * Print the contents of Task List
     */
    public static String getTaskListString() {
        String output = "Your Tasks: \n";
        for (Task task : tasks) {
            output += task.getTaskNumber() + task.toString() + '\n';
        }
        return output;
    }

    /**
     * Adds a Task to the list of Tasks.
     * @param task {@link Task} object.
     */
    public static void addTask(Task task) {
        tasks.add(task);
        numberOfTasks++;
    }

    public static void addTaskFromFile(String[] input) {
        switch(input[0]) {
        case "T":
            Todo newTodo = new Todo(input[2], getNextTaskNumber());
            addTask(newTodo); //problem cos tasks is not instantiated/static.
            break;
        case "D":
            Deadline newDeadline = new Deadline(input[2], getNextTaskNumber(), input[3]);
            addTask(newDeadline);
            break;
        case "E":
            Event newEvent = new Event(input[2], getNextTaskNumber(), input[3], input[4]);
            addTask(newEvent);
            break;
        }

        // mark as done
        if (input[1].equals("1")) {
            tasks.get(getNumberOfTasks() - 1).markAsDone();
        }
    }

    /**
     * Deletes a task from the task list and decrement number of tasks.
     * @param taskNumber 1-indexed task index.
     */
    public static Task deleteTask(int taskNumber) {
        Task deletedTask = tasks.get(taskNumber - 1);
        tasks.remove(taskNumber - 1);
        numberOfTasks--;
        return deletedTask;
    }

    /**
     * Two-in-one method to mark or unmark task since it is so similar
     * @param command Either "mark" or "unmark"
     * @param commandArgs Should be an int corresponding to the task number (1-index)
     * @return Feedback string: "Task marked: ____"
     */
    public static String executeMarkUnmark(String command, String commandArgs) {
        int taskNumber;
        // Check is Integer
        try {
            taskNumber = Integer.parseInt(commandArgs);
        } catch (NumberFormatException e) {
            // Not an int. Send back an error message.
            return Ui.ERROR_MESSAGE_TASK_INDEX;
        }
        // Check Integer is in range
        try {
            if (command.equals("mark")) {
                return tasks.get(taskNumber - 1).markAsDone();
            } else if (command.equals("unmark")) {
                return tasks.get(taskNumber - 1).markAsUndone();
            }
        } catch (IndexOutOfBoundsException e) {
            return Ui.ERROR_MESSAGE_TASK_INDEX;
        }
        // If nothing else works...
        return "Huh?";
    }

    // Getter for number of task
    public static int getNumberOfTasks() {
        return numberOfTasks;
    }

    // A bit unoptimised, but this is to get the next number for numbering purposes.
    public static int getNextTaskNumber() {
        return numberOfTasks + 1;
    }

    public static void writeAllToFile(Storage storage) {
        String output = "";
        for (Task task : tasks) {
            if (task != null) {
                output += task.getFileWriteFormat() + '\n';
            }
        }
        storage.writeToFile(output);
    }
}
