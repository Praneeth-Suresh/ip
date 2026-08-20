import java.io.PrintStream;
import java.util.Scanner;

/**
 * A console personal assistant that records a traveler's tasks for the current session.
 */
public class Odysseus {
    private static final String DIVIDER = "____________________________________________________________";

    /**
     * Starts Odysseus and processes task commands until the traveler says goodbye.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        run(new Scanner(System.in), System.out);
    }

    /**
     * Runs an Odysseus conversation with the supplied input and output streams.
     *
     * @param scanner source of user commands
     * @param output destination for chatbot responses
     */
    static void run(Scanner scanner, PrintStream output) {
        String banner = "  ___    ____  __   __  ____   ____  _____  _   _  ____\n"
                + " / _ \\  |  _ \\ \\ \\ / / / ___| / ___|| ____|| | | |/ ___|\n"
                + "| | | | | | | | \\ V /  \\___ \\ \\___ \\|  _|  | | | |\\___ \\\n"
                + "| |_| | | |_| |  | |    ___) | ___) | |___ | |_| | ___) |\n"
                + " \\___/  |____/   |_|   |____/ |____/|_____| \\___/ |____/\n";
        output.println(banner);
        output.println("Ahoy, traveler! I am Odysseus, long tested by sea and fate.");
        output.println("What course shall we chart together?");
        output.println(DIVIDER);

        TaskList tasks = new TaskList();
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            output.println(DIVIDER);
            if (command.equals("bye")) {
                break;
            }
            try {
                if (command.equals("list")) {
                    printTaskList(tasks, output);
                } else if (TaskAction.MARK.matches(command)) {
                    Task task = tasks.getTask(parseTaskNumber(command, TaskAction.MARK));
                    task.markAsDone();
                    output.println("Well sailed! I've marked this task as done:");
                    output.println("  " + task);
                } else if (TaskAction.UNMARK.matches(command)) {
                    Task task = tasks.getTask(parseTaskNumber(command, TaskAction.UNMARK));
                    task.markAsNotDone();
                    output.println("This task awaits its hour again:");
                    output.println("  " + task);
                } else if (TaskAction.DELETE.matches(command)) {
                    Task task = tasks.deleteTask(parseTaskNumber(command, TaskAction.DELETE));
                    output.println("The waves have carried this task from our log:");
                    output.println("  " + task);
                    printTaskCount(tasks, output);
                } else {
                    Task task = createTask(command);
                    tasks.addTask(task);
                    output.println("Well charted. I've added this task:");
                    output.println("  " + task);
                    printTaskCount(tasks, output);
                }
            } catch (OdysseusException exception) {
                output.println(exception.getMessage());
            }
            output.println(DIVIDER);
        }

        output.println("Farewell, traveler. May Athena guide your voyage until we meet again.");
        output.println(DIVIDER);
    }

    /** Prints the current task list in insertion order. */
    private static void printTaskList(TaskList tasks, PrintStream output) throws OdysseusException {
        if (tasks.getTaskCount() == 0) {
            output.println("My ship's log is clear, traveler.");
            return;
        }
        output.println("Here are the tasks on our voyage, traveler:");
        for (int taskNumber = 1; taskNumber <= tasks.getTaskCount(); taskNumber++) {
            output.println(taskNumber + ". " + tasks.getTask(taskNumber));
        }
    }

    /** Prints the current number of tasks in the voyage log. */
    private static void printTaskCount(TaskList tasks, PrintStream output) {
        output.println("Our voyage now holds " + tasks.getTaskCount() + " task"
                + (tasks.getTaskCount() == 1 ? "." : "s."));
    }

    /** Parses a one-based task number from a mark or unmark command. */
    private static int parseTaskNumber(String command, TaskAction action) throws OdysseusException {
        String actionWord = action.getCommandWord();
        String numberText = command.substring(actionWord.length()).trim();
        if (numberText.isEmpty()) {
            throw new OdysseusException("Name the task to " + actionWord + ", for example: " + actionWord + " 2.");
        }
        try {
            return Integer.parseInt(numberText);
        } catch (NumberFormatException exception) {
            throw new OdysseusException("Use a task number after " + actionWord + ", for example: " + actionWord + " 2.");
        }
    }

    /**
     * Creates a typed task from a supported task command.
     *
     * @param command the user's command
     * @return the created task
     * @throws OdysseusException when the command lacks a required part or is unknown
     */
    private static Task createTask(String command) throws OdysseusException {
        if (command.equals("todo") || command.startsWith("todo ")) {
            String description = command.length() == 4 ? "" : command.substring(5);
            if (description.isBlank()) {
                throw new OdysseusException("A to-do needs a task to steer by. Try: todo borrow book.");
            }
            return new Todo(description);
        }
        if (command.equals("deadline") || command.startsWith("deadline ")) {
            int byIndex = command.indexOf(" /by ");
            if (byIndex < 0) {
                throw new OdysseusException("A deadline needs /by <date or time>. Try: deadline return book /by Sunday.");
            }
            String description = command.substring(9, byIndex);
            String by = command.substring(byIndex + 5);
            if (description.isBlank()) {
                throw new OdysseusException("Name the task before /by, then give its deadline.");
            }
            if (by.isBlank()) {
                throw new OdysseusException("Name when the task is due after /by.");
            }
            return new Deadline(description, by);
        }
        if (command.equals("event") || command.startsWith("event ")) {
            int fromIndex = command.indexOf(" /from ");
            int toIndex = command.indexOf(" /to ");
            if (fromIndex < 0 || toIndex < 0 || toIndex < fromIndex) {
                throw new OdysseusException("An event needs /from <start> /to <end>. Try: event meeting /from 2pm /to 4pm.");
            }
            if (toIndex <= fromIndex + 7) {
                throw new OdysseusException("Give both an event start after /from and an end after /to.");
            }
            String description = command.substring(6, fromIndex);
            String from = command.substring(fromIndex + 7, toIndex);
            String to = command.substring(toIndex + 5);
            if (description.isBlank()) {
                throw new OdysseusException("Name the event before its voyage times.");
            }
            if (from.isBlank() || to.isBlank()) {
                throw new OdysseusException("Give both an event start after /from and an end after /to.");
            }
            return new Event(description, from, to);
        }
        throw new OdysseusException("I cannot chart a course from that command. Try todo, deadline, event, list, mark, unmark, or bye.");
    }
}
