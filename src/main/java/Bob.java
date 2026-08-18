import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

import tasks.*;
import commands.*;

public class Bob {
    private static ArrayList<Task> list = new ArrayList<>();
    private static String horiLines = "_".repeat(30);
    private static Command[] commands = {
        new MarkCommand(list),
        new ExitCommand(list),
        new ListCommand(list),
        new AddCommand(list)
    }
    public static void main(String[] args) {
        System.out.println(horiLines);
        System.out.println("Hello! I'm Bob.");
        System.out.println("What can I do for you?");
        System.out.println(horiLines);

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            System.out.println(horiLines);
            Arrays
                .<Command>stream(commands)
                .forEach(
                    c -> c.processInput(nextLine);
                )
            System.out.println(horiLines);
        }
    }
}

