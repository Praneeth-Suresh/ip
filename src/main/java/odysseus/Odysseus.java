package odysseus;

import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Scanner;

/** A console personal assistant that records a traveler's tasks. */
public class Odysseus {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String INTRO_ART = String.join(System.lineSeparator(),
            " ___    ____  __   __  ____   ____  _____  _   _  ____",
            " / _ \\  |  _ \\ \\ \\ / / / ___| / ___|| ____|| | | |/ ___|",
            "| | | | | | | | \\ V /  \\___ \\ \\___ \\|  _|  | | | |\\___ \\",
            "| |_| | | |_| |  | |    ___) | ___) | |___ | |_| | ___) |",
            " \\___/  |____/   |_|   |____/ |____/|_____| \\___/ |____/");
    private static final Path DEFAULT_STORAGE_PATH = Path.of("data", "odysseus.txt");
    private final Storage storage;
    private final Parser parser;
    private final TaskList tasks;
    private final String loadingMessage;

    /** Creates Odysseus using the standard voyage-log location. */
    public Odysseus() {
        this(DEFAULT_STORAGE_PATH);
    }

    /**
     * Creates Odysseus with the supplied voyage-log location.
     *
     * @param storagePath location of the persistent voyage log
     */
    public Odysseus(Path storagePath) {
        storage = new Storage(storagePath);
        parser = new Parser();
        TaskList loadedTasks;
        String recoveredLoadingMessage = "";
        try {
            loadedTasks = storage.load();
        } catch (OdysseusException exception) {
            loadedTasks = new TaskList();
            recoveredLoadingMessage = "I could not load the ship's log. Starting with an empty log.";
        }
        tasks = loadedTasks;
        loadingMessage = recoveredLoadingMessage;
    }

    /** Starts Odysseus and processes commands until the traveler says goodbye. */
    public static void main(String[] args) {
        run(new Scanner(System.in), System.out, DEFAULT_STORAGE_PATH);
    }

    /** Runs an Odysseus conversation using the supplied input, output, and storage path. */
    static void run(Scanner scanner, PrintStream output, Path storagePath) {
        output.println(INTRO_ART);
        output.println("Ahoy, traveler! I am Odysseus, long tested by sea and fate.");
        output.println("What course shall we chart together?");
        output.println(DIVIDER);

        Odysseus odysseus = new Odysseus(storagePath);
        if (!odysseus.loadingMessage.isEmpty()) {
            output.println(odysseus.loadingMessage);
        }
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            output.println(DIVIDER);
            if (command.equals("bye")) {
                break;
            }
            output.println(odysseus.getResponse(command));
            output.println(DIVIDER);
        }

        output.println("Farewell, traveler. May Athena guide your voyage until we meet again.");
        output.println(DIVIDER);
    }

    /**
     * Processes one traveler command and returns Odysseus's response.
     *
     * @param command command to process
     * @return a user-facing response in Odysseus's voice
     */
    public String getResponse(String command) {
        try {
            if (command.equals("bye")) {
                return "Farewell, traveler. May Athena guide your voyage until we meet again.";
            }
            if (command.equals("list")) {
                return taskListResponse();
            }
            if (command.equals("find") || command.startsWith("find ")) {
                return matchingTasksResponse(parser.parseFindKeyword(command));
            }
            if (TaskAction.MARK.matches(command)) {
                Task task = tasks.getTask(parser.parseTaskNumber(command, TaskAction.MARK));
                task.markAsDone();
                return saveWarning() + "Well sailed! I've marked this task as done:"
                        + System.lineSeparator() + "  " + task;
            }
            if (TaskAction.UNMARK.matches(command)) {
                Task task = tasks.getTask(parser.parseTaskNumber(command, TaskAction.UNMARK));
                task.markAsNotDone();
                return saveWarning() + "This task awaits its hour again:"
                        + System.lineSeparator() + "  " + task;
            }
            if (TaskAction.DELETE.matches(command)) {
                Task task = tasks.deleteTask(parser.parseTaskNumber(command, TaskAction.DELETE));
                return saveWarning() + "The waves have carried this task from our log:"
                        + System.lineSeparator() + "  " + task
                        + System.lineSeparator() + taskCountResponse();
            }
            Task task = parser.parseTask(command);
            tasks.addTask(task);
            return saveWarning() + "Well charted. I've added this task:"
                    + System.lineSeparator() + "  " + task
                    + System.lineSeparator() + taskCountResponse();
        } catch (OdysseusException exception) {
            return exception.getMessage();
        }
    }

    /** Saves tasks through storage and reports a recoverable saving error. */
    private String saveWarning() {
        try {
            storage.save(tasks);
            return "";
        } catch (OdysseusException exception) {
            return "I could not save the ship's log." + System.lineSeparator();
        }
    }

    /** Returns the task-count sentence for the current voyage log. */
    private String taskCountResponse() {
        return "Our voyage now holds " + tasks.getTaskCount() + " task"
                + (tasks.getTaskCount() == 1 ? "." : "s.");
    }

    /** Returns a formatted rendering of every task in the voyage log. */
    private String taskListResponse() throws OdysseusException {
        if (tasks.getTaskCount() == 0) {
            return "My ship's log is clear, traveler.";
        }
        StringBuilder response = new StringBuilder("Here are the tasks on our voyage, traveler:");
        for (int number = 1; number <= tasks.getTaskCount(); number++) {
            response.append(System.lineSeparator()).append(number).append(". ").append(tasks.getTask(number));
        }
        return response.toString();
    }

    /** Returns a formatted rendering of every task matching a keyword. */
    private String matchingTasksResponse(String keyword) throws OdysseusException {
        StringBuilder response = new StringBuilder("Here are the matching tasks in your list:");
        for (int taskNumber : tasks.findTaskNumbers(keyword)) {
            response.append(System.lineSeparator()).append(taskNumber).append(". ").append(tasks.getTask(taskNumber));
        }
        return response.toString();
    }
}
