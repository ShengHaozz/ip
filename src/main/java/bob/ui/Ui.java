package bob.ui;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import bob.task.Task;
import bob.task.TaskList;

/**
 * Handles interactions with the user, such as reading input and formatting
 * responses.
 */
public class Ui {
    private static final String HORIZONTAL_LINE = "_".repeat(30);
    private final Scanner scanner;
    private String lastResponse = "";

    /**
     * Constructs a new Ui instance with standard input scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Sets the welcome greeting response.
     */
    public void setWelcome() {
        this.lastResponse = "Hello! I'm Bob.\nWhat can I do for you?";
    }

    /**
     * Sets the goodbye response message.
     */
    public void setGoodbye() {
        this.lastResponse = "Goodbye.";
    }

    /**
     * Prints a horizontal divider line.
     */
    public void showDividerLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Reads a line of user input.
     *
     * @return the command string entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Checks if there is another line of input available.
     *
     * @return true if there is input available, false otherwise
     */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Sets the response to display the full list of tasks.
     *
     * @param tasks the task list to format
     */
    public void setTaskList(TaskList tasks) {
        assert tasks != null : "TaskList cannot be null when displaying tasks";
        String formattedTasks = IntStream.range(0, tasks.size())
                .<String>mapToObj(i -> String.format("%d: %s", i + 1, tasks.get(i).toString()))
                .collect(Collectors.joining("\n"));

        this.lastResponse = formattedTasks.isEmpty() ? "No Tasks" : "Tasks:\n" + formattedTasks;
    }

    /**
     * Sets the response to display the tasks that match a search keyword with their
     * original list
     * indices.
     *
     * @param matchingEntries the list of entries containing 1-based indices and
     *                        matching tasks
     */
    public void setMatchingTasks(List<Map.Entry<Integer, Task>> matchingEntries) {
        assert matchingEntries != null : "Matching entries list cannot be null";
        String formattedTasks = matchingEntries.stream()
                .<String>map(entry -> String.format("%d.%s", entry.getKey(), entry.getValue().toString()))
                .collect(Collectors.joining("\n"));

        this.lastResponse = formattedTasks.isEmpty() ? "No matching tasks"
                : "Here are the matching tasks in your list:\n" + formattedTasks;
    }

    /**
     * Sets the response message after adding a task.
     *
     * @param task       the added task
     * @param totalCount the total number of tasks after addition
     */
    public void setTaskAdded(Task task, int totalCount) {
        assert task != null : "Added task cannot be null";
        assert totalCount >= 0 : "Total task count cannot be negative";
        this.lastResponse = String.format("Task added:\n%s\n%d %s in list",
                task.toString(), totalCount, totalCount < 2 ? "item" : "items");
    }

    /**
     * Sets the response message after deleting a task.
     *
     * @param task       the deleted task
     * @param totalCount the total number of tasks after deletion
     */
    public void setTaskDeleted(Task task, int totalCount) {
        assert task != null : "Deleted task cannot be null";
        assert totalCount >= 0 : "Total task count cannot be negative";
        this.lastResponse = String.format("Removed: \n%s\n%d %s in list",
                task.toString(), totalCount, totalCount < 2 ? "item" : "items");
    }

    /**
     * Sets the response message after marking or unmarking a task.
     *
     * @param task   the task that was marked or unmarked
     * @param isDone true if marked as done, false if marked as not done
     */
    public void setTaskMarked(Task task, boolean isDone) {
        assert task != null : "Task to mark/unmark cannot be null";
        String status = isDone ? "Marked as done:" : "Marked as not done:";
        this.lastResponse = status + "\n " + task.toString();
    }

    /**
     * Sets the response message after updating a task, showing before and after details.
     *
     * @param before the task state before update
     * @param after  the task state after update
     */
    public void setTaskUpdated(Task before, Task after) {
        assert before != null : "Before task cannot be null";
        assert after != null : "After task cannot be null";
        this.lastResponse = String.format("Task updated from:\n  %s\nto:\n  %s",
                before.toString(), after.toString());
    }

    /**
     * Sets a generic response message.
     *
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.lastResponse = message;
    }

    /**
     * Sets an error response message.
     *
     * @param message the error message to set
     */
    public void setError(String message) {
        this.lastResponse = message;
    }

    /**
     * Returns the most recent response message generated by the UI.
     *
     * @return the last generated response string
     */
    public String getLastResponse() {
        return lastResponse;
    }

    /**
     * Closes the underlying scanner resource.
     */
    public void close() {
        scanner.close();
    }
}
