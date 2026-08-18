import java.util.Scanner;
import java.lang.NumberFormatException;

import tasks.*;

public class Bob {
    private static Task[] list = new Task[100];
    private static int listPtr = 0;
    private static String horiLines = "_".repeat(30);
    public static void main(String[] args) {
        System.out.println(horiLines);
        System.out.println("Hello! I'm Bob.");
        System.out.println("What can I do for you?");
        System.out.println(horiLines);

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            System.out.println(horiLines);
            if (nextLine.equals("bye")) {
                // if input is "bye"
                System.out.println("Goodbye.");
                System.out.println(horiLines);
                break;
            } else if (nextLine.equals("list")) {
                // if input is "list"
                for (int i = 0; i < listPtr; i++) {
                    System.out.println((i + 1) + ": " + list[i].getEntryString());
                }
                System.out.println(horiLines);
                continue;
            }  
            try {
                checkCommand(nextLine);
            } catch (BobException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(horiLines);
        }
    }

    private static void checkCommand(String input) throws BobException{
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            throw new BobException("Error: argument needed for " + parts[0]);
        }
        String command = parts[0];
        String arg = parts[1];
        switch (command) {
            case "mark":
            case "unmark":
                int taskId;
                try {
                    taskId = Integer.parseInt(arg);
                } catch (NumberFormatException e) {
                    throw new BobException("Error: argument is not a task id");
                }
                
                if (taskId > listPtr) {
                    throw new BobException("Error: Task not found");
                }
                Task task = list[taskId - 1];
                
                if (command.equals("mark")) {
                    task.mark();
                    System.out.println("Marked as done:");
                } else {
                    task.unmark();
                    System.out.println("Marked as not done:");
                }
                
                System.out.println(task.getEntryString());
                break;
            
            case "todo":
                ToDo todo = new ToDo(arg);
                list[listPtr++] = todo;    
                printAddition(todo, listPtr);
                break;
            
            case "deadline":
                String[] deadlineParts = arg.split(" /by ");
                Deadline deadline = new Deadline(deadlineParts[0], deadlineParts[1]);
                list[listPtr++] = deadline;
                printAddition(deadline, listPtr);
                break;
            
            case "event":
                String[] eventParts = arg.split(" /from | /to ");
                Event event = new Event(eventParts[0], eventParts[1], eventParts[2]);
                list[listPtr++] = event;
                printAddition(event, listPtr);
                break;

            default:
                System.out.println("What's that??");
        }
    }

    private static void printAddition(Task task, int listPtr) {
        System.out.println("Added: ");
        System.out.println("  " + task.getEntryString());
        System.out.println(String.format(
            "%d %s in list",
            listPtr,
            listPtr < 2 ? "item" : "items"
        ));
    }
}

