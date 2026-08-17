import java.util.Scanner;

import tasks.Task;

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
            } else if (
                nextLine.split(" ")[0].equals("mark") || nextLine.split(" ")[0].equals("unmark")
            ) {
                String[] parts = nextLine.split(" ");
                int taskId = Integer.parseInt(parts[1]);
                if (taskId > listPtr) {
                    System.out.println("No such task");
                    System.out.println(horiLines);
                    continue;
                }
                Task task = list[taskId - 1];
                
                if (parts[0].equals("mark")) {
                    task.mark();
                    System.out.println("Marked as done:");
                } else {
                    task.unmark();
                    System.out.println("Marked as not done:");
                }
                
                System.out.println(task.getEntryString());
            } else {
                // if input is anything else
                System.out.println("added: " + nextLine);
                list[listPtr++] = new Task(nextLine);
            }

            System.out.println(horiLines);
        }
    }
}
