package commands;
import java.util.ArrayList;

import tasks.*;

public abstract class Command {
    private ArrayList<Task> taskList;

    public Command(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public abstract void processInput(String input);
}
