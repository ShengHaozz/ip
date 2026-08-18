import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

import tasks.*;
import commands.*;
import exceptions.*;

public class Bob {
    private static ArrayList<Task> list = new ArrayList<>();
    private static String horiLines = "_".repeat(30);
    private static Command[] commands = {
        new MarkCommand(list),
        new ExitCommand(list),
        new ListCommand(list),
        new AddCommand(list)
    };
    public static void main(String[] args) {
        System.out.println(horiLines);
        System.out.println("Hello! I'm Bob.");
        System.out.println("What can I do for you?");
        System.out.println(horiLines);

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            System.out.println(horiLines);
            boolean processed;
            try 
            {
                processed = Arrays
                    .<Command>stream(commands)
                    .map(
                        c -> c.processInput(nextLine)
                    )
                    .reduce(false, (a, b) -> a || b);
            } catch (ExitException e) {
                System.out.println(e.getMessage());
                break;
            } catch (BobException e) {
                System.out.println(e.getMessage());
            }
            
            if (!processed) {
                System.out.println("What's that?");
            }
            
            System.out.println(horiLines);
        }
    }
}

