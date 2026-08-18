package commands;
import tasks.*;

public abstract class Command {
    private Task[] taskArray;
    private int taskLen;

    public Command(Task[] taskArray, int taskLen) {
        this.taskArray = taskArray;
        this.taskLen = taskLen;
    }

    public abstract void processInput(String input);
}
