import java.util.Scanner;

public class Bob {
    public static void main(String[] args) {
        String horiLines = "_".repeat(30);
        System.out.println(horiLines);
        System.out.println("Hello! I'm Bob.");
        System.out.println("What can I do for you?");
        System.out.println(horiLines);

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            System.out.println(horiLines);
            if (nextLine == "bye") {
                System.out.println("Goodbye.");
                break;
            }

            System.out.println(nextLine);
            System.out.println(horiLines);
        }
    }
}
