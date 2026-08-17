import java.util.Scanner;

import tasks.*;

public class Bob {
    public static void main(String[] args) {
        String horiLines = "_".repeat(30);
        System.out.println(horiLines);
        System.out.println("Hello! I'm Bob.");
        System.out.println("What can I do for you?");
        System.out.println(horiLines);

        Scanner sc = new Scanner(System.in);
        Task[] list = new Task[100];
        int listPtr = 0;

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
            String[] parts = nextLine.split(" ", 2);
            String cmd = parts[0];
            String arg = parts[1];
            switch (cmd) {
                case "mark":
                case "unmark":
                    int taskId = Integer.parseInt(arg);
                    if (taskId > listPtr) {
                        System.out.println("No such task");
                        System.out.println(horiLines);
                        continue;
                    }
                    Task task = list[taskId - 1];
                    
                    if (cmd.equals("mark")) {
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
            System.out.println(horiLines);
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

