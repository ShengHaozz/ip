package commands;

import java.util.ArrayList;

import tasks.Task;
import exceptions.*;

public class MarkCommand extends Command {
    public MarkCommand(ArrayList<Task> list) {
        super(list);
    }

    public boolean processInput(String input) throws BobException {
        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String arg = parts[1];
        int taskId;
        switch (command) {
            case "mark":
                try {
                    taskId = Integer.parseInt(arg);
                    this.taskList.get(taskId - 1).mark();
                } catch (NumberFormatException e) {
                    throw new BobException("Error: Argument must be an integer");
                } catch (IndexOutOfBoundsException e) {
                    throw new BobException("Error: taskId out of bounds");
                }
                System.out.println("Marked as done:");
                System.out.println(" " + this.taskList.get(taskId - 1));
                return true;
            
            case "unmark":
                try {
                    taskId = Integer.parseInt(arg);
                    this.taskList.get(taskId - 1).unmark();
                } catch (NumberFormatException e) {
                    throw new BobException("Error: Argument must be an integer");
                } catch (IndexOutOfBoundsException e) {
                    throw new BobException("Error: taskId out of bounds");
                }
                System.out.println("Marked as not done:");
                System.out.println(" " + this.taskList.get(taskId - 1));
                return true;
        }
        return false;
    }
}
