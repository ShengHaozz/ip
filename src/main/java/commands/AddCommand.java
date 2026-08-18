package commands;

import java.util.ArrayList;

import exceptions.BobException;
import tasks.*;

public class AddCommand extends Command {
    public AddCommand(ArrayList<Task> taskList) {
        super(taskList);
    }

    public void processInput(String input) throws BobException {
        String[] parts = input.split(" ", 2);
        String command = parts[0];
        if (command.equals("todo") || command.equals("deadline") || command.equals("event")) {
            String arg;
            try {
                arg = parts[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new BobException(command + " needs a description");
            }

            switch (command) {
                case "todo":
                    ToDo todo = new ToDo(arg);
                    this.taskList.add(todo); 
                    this.printAddition(todo);
                    break;
                
                case "deadline":
                    String[] deadlineParts = arg.split(" /by ");
                    Deadline deadline = new Deadline(deadlineParts[0], deadlineParts[1]);
                    this.taskList.add(deadline); 
                    this.printAddition(deadline);
                    break;
                
                case "event":
                    String[] eventParts = arg.split(" /from | /to ");
                    Event event = new Event(eventParts[0], eventParts[1], eventParts[2]);
                    this.taskList.add(event); 
                    this.printAddition(event);
                    break;
            }


        }
    }

    private void printAddition(Task t) {
        System.out.println("Task added:");
        System.out.println(t.getEntryString());
        System.out.println(String.format(
            "%d %s in list",
            this.taskList.size(),
            this.taskList.size() < 2 ? "item" : "items"
        ));
    }
}
