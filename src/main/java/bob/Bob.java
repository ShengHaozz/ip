package bob;

import bob.command.Command;
import bob.exception.BobException;
import bob.parser.Parser;
import bob.storage.Storage;
import bob.storage.TaskStorage;
import bob.task.TaskList;
import bob.ui.Ui;

/**
 * Serves as the main entry point for the Bob task management application.
 */
public class Bob {
    private final Storage<TaskList> storage;
    private TaskList tasks;
    private final Ui ui;

    private boolean isExit = false;
    private boolean isLastResponseError = false;

    /**
     * Constructs a new Bob application instance.
     */
    public Bob() {
        this.ui = new Ui();
        this.storage = new TaskStorage();
        try {
            this.tasks = this.storage.load();
        } catch (BobException e) {
            this.ui.setError(e.getMessage());
            this.tasks = new TaskList();
        }
        assert this.ui != null : "Ui should be initialized";
        assert this.storage != null : "TaskStorage should be initialized";
        assert this.tasks != null : "TaskList should be initialized";
    }

    /**
     * Runs the main command loop of the application in CLI mode.
     */
    public void run() {
        getGreeting();

        while (ui.hasNextCommand()) {
            String fullCommand = ui.readCommand();
            getResponse(fullCommand);
            if (this.isExit) {
                break;
            }
        }
    }

    /**
     * Generates a response for the user's chat message input in GUI mode and prints
     * to stdout.
     *
     * @param input the raw input command string entered by the user
     * @return the response string generated after command execution or error
     *         handling
     */
    public String getResponse(String input) {
        assert this.tasks != null : "TaskList should not be null when getting response";
        assert this.ui != null : "Ui should not be null when getting response";
        assert this.storage != null : "TaskStorage should not be null when getting response";

        System.out.println(input);

        ui.showDividerLine();
        this.isLastResponseError = false;
        try {
            Command c = Parser.parse(input);
            c.execute(tasks, ui, storage);
            this.isExit = c.isExit();
        } catch (BobException e) {
            this.isLastResponseError = true;
            ui.setError(e.getMessage());
        }
        System.out.println(ui.getLastResponse());
        ui.showDividerLine();
        return ui.getLastResponse();
    }

    /**
     * Checks whether the application has received an exit command.
     *
     * @return true if an exit command was executed, false otherwise
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * Checks whether the most recent response represents an error.
     *
     * @return true if the most recent response is an error, false otherwise
     */
    public boolean isLastResponseError() {
        return isLastResponseError;
    }

    /**
     * Returns the welcome greeting message for the user.
     *
     * @return the initial greeting string
     */
    public String getGreeting() {
        ui.showDividerLine();
        ui.setWelcome();
        System.out.println(ui.getLastResponse());
        ui.showDividerLine();
        return ui.getLastResponse();
    }

    /**
     * Initializes and launches the Bob application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new Bob().run();
    }
}
