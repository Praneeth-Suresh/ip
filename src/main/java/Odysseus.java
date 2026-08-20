import java.util.Scanner;

/**
 * A console personal assistant that records a traveler's tasks for the current session.
 */
public class Odysseus {
    private static final int MAX_TASKS = 100;
    private static final String DIVIDER = "____________________________________________________________";

    /**
     * Starts Odysseus and processes task commands until the traveler says goodbye.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        String banner = "  ___    ____  __   __  ____   ____  _____  _   _  ____\n"
                + " / _ \\  |  _ \\ \\ \\ / / / ___| / ___|| ____|| | | |/ ___|\n"
                + "| | | | | | | | \\ V /  \\___ \\ \\___ \\|  _|  | | | |\\___ \\\n"
                + "| |_| | | |_| |  | |    ___) | ___) | |___ | |_| | ___) |\n"
                + " \\___/  |____/   |_|   |____/ |____/|_____| \\___/ |____/\n";
        System.out.println(banner);
        System.out.println("Ahoy, traveler! I am Odysseus, long tested by sea and fate.");
        System.out.println("What course shall we chart together?");
        System.out.println(DIVIDER);

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(DIVIDER);
            if (command.equals("bye")) {
                break;
            }
            if (command.equals("list")) {
                if (taskCount == 0) {
                    System.out.println("My ship's log is clear, traveler.");
                } else {
                    System.out.println("Here are the tasks on our voyage, traveler:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + ". " + tasks[i]);
                    }
                }
            } else if (command.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(command.substring(5));
                Task task = tasks[taskNumber - 1];
                task.markAsDone();
                System.out.println("Well sailed! I've marked this task as done:");
                System.out.println("  " + task);
            } else if (command.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(command.substring(7));
                Task task = tasks[taskNumber - 1];
                task.markAsNotDone();
                System.out.println("This task awaits its hour again:");
                System.out.println("  " + task);
            } else {
                tasks[taskCount] = new Task(command);
                taskCount++;
                System.out.println("Added to my ship's log: " + command);
            }
            System.out.println(DIVIDER);
        }

        System.out.println("Farewell, traveler. May Athena guide your voyage until we meet again.");
        System.out.println(DIVIDER);
    }
}
