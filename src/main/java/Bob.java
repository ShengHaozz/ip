import java.util.Scanner;

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
                break;
            } else if (nextLine.equals("list")) {
                // if input is "list"
                for (int i = 0; i < listPtr; i++) {
                    System.out.println((i + 1) + ": " + list[i].getEntryString());
                }
            } else {
                // if input is anything else
                System.out.println("added: " + nextLine);
                list[listPtr++] = new Task(nextLine);
            }

            System.out.println(horiLines);
        }
    }
}
