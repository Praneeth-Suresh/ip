import java.util.Scanner;

public class Odysseus {
    public static void main(String[] args) {
        String divider = "____________________________________________________________";
        String banner = "  ___    ____  __   __  ____   ____  _____  _   _  ____\n"
                + " / _ \\  |  _ \\ \\ \\ / / / ___| / ___|| ____|| | | |/ ___|\n"
                + "| | | | | | | | \\ V /  \\___ \\ \\___ \\|  _|  | | | |\\___ \\\n"
                + "| |_| | | |_| |  | |    ___) | ___) | |___ | |_| | ___) |\n"
                + " \\___/  |____/   |_|   |____/ |____/|_____| \\___/ |____/\n";
        System.out.println(banner);
        System.out.println("Ahoy, traveler! I am Odysseus, long tested by sea and fate.");
        System.out.println("What course shall we chart together?");
        System.out.println(divider);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(divider);
            if (command.equals("bye")) {
                break;
            }
            System.out.println(command);
            System.out.println(divider);
        }

        System.out.println("Farewell, traveler. May Athena guide your voyage until we meet again.");
        System.out.println(divider);
    }
}
