package commands;

import java.util.ArrayList;

import exceptions.*;
import tasks.Task;

public class ExitCommand extends Command {
    public ExitCommand(ArrayList<Task> taskList) {
        super(taskList);
    }

    public void processInput(String input) throws ExitException {
        if (input.equals("bye")) {
            throw new ExitException("Goodbye.");
        }
    }
}
