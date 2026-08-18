package commands;
import java.util.ArrayList;

import tasks.*;
import exceptions.*;

public abstract class Command {
    protected ArrayList<Task> taskList;

    public Command(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public abstract void processInput(String input) throws BobException;
}
