package commands;

import java.util.ArrayList;

import tasks.Task;

public class ListCommand extends Command {
    public ListCommand(ArrayList<Task> taskList) {
        super(taskList);
    }

    public boolean processInput(String input) {
        if (input.equals("list")) {
            System.out.println("Tasks:");
            for (int i = 0; i < this.taskList.size(); i++) {
                System.out.println(String.format(
                    "%d: %s",
                    i + 1,
                    this.taskList.get(i)    
                ));
            }
            return true;
        }
        return false;
    }
}
