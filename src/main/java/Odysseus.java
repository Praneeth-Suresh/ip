import java.util.Scanner;

/**
 * A console personal assistant that records a traveler's voyage log for the current session.
 */
public class Odysseus {
    private static final int MAX_LOG_ENTRIES = 100;
    private static final String DIVIDER = "____________________________________________________________";

    /**
     * Starts Odysseus and processes voyage-log commands until the traveler says goodbye.
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
        String[] voyageLog = new String[MAX_LOG_ENTRIES];
        int logEntryCount = 0;
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(DIVIDER);
            if (command.equals("bye")) {
                break;
            }
            if (command.equals("list")) {
                if (logEntryCount == 0) {
                    System.out.println("My ship's log is clear, traveler.");
                } else {
                    for (int i = 0; i < logEntryCount; i++) {
                        System.out.println((i + 1) + ". " + voyageLog[i]);
                    }
                }
            } else {
                voyageLog[logEntryCount] = command;
                logEntryCount++;
                System.out.println("Added to my ship's log: " + command);
            }
            System.out.println(DIVIDER);
        }

        System.out.println("Farewell, traveler. May Athena guide your voyage until we meet again.");
        System.out.println(DIVIDER);
    }
}
