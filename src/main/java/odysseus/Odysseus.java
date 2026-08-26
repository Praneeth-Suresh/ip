package odysseus;

import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Scanner;

/** A console personal assistant that records a traveler's tasks. */
public class Odysseus {
    private static final String DIVIDER = "____________________________________________________________";
    private static final Path DEFAULT_STORAGE_PATH = Path.of("data", "odysseus.txt");

    /** Starts Odysseus and processes commands until the traveler says goodbye. */
    public static void main(String[] args) {
        run(new Scanner(System.in), System.out, DEFAULT_STORAGE_PATH);
    }

    /** Runs an Odysseus conversation using the supplied input, output, and storage path. */
    static void run(Scanner scanner, PrintStream output, Path storagePath) {
        output.println("Ahoy, traveler! I am Odysseus, long tested by sea and fate.");
        output.println("What course shall we chart together?");
        output.println(DIVIDER);

        Ui ui = new Ui(output);
        Storage storage = new Storage(storagePath);
        Parser parser = new Parser();
        TaskList tasks = loadTasks(storage, ui);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            output.println(DIVIDER);
            if (command.equals("bye")) {
                break;
            }
            try {
                if (command.equals("list")) {
                    ui.showTaskList(tasks);
                } else if (command.equals("find") || command.startsWith("find ")) {
                    String keyword = parser.parseFindKeyword(command);
                    ui.showMatchingTasks(tasks, tasks.findTaskNumbers(keyword));
                } else if (TaskAction.MARK.matches(command)) {
                    Task task = tasks.getTask(parser.parseTaskNumber(command, TaskAction.MARK));
                    task.markAsDone();
                    saveTasks(tasks, storage, ui);
                    ui.showTask("Well sailed! I've marked this task as done:", task);
                } else if (TaskAction.UNMARK.matches(command)) {
                    Task task = tasks.getTask(parser.parseTaskNumber(command, TaskAction.UNMARK));
                    task.markAsNotDone();
                    saveTasks(tasks, storage, ui);
                    ui.showTask("This task awaits its hour again:", task);
                } else if (TaskAction.DELETE.matches(command)) {
                    Task task = tasks.deleteTask(parser.parseTaskNumber(command, TaskAction.DELETE));
                    saveTasks(tasks, storage, ui);
                    ui.showTask("The waves have carried this task from our log:", task);
                    printTaskCount(tasks, output);
                } else {
                    Task task = parser.parseTask(command);
                    tasks.addTask(task);
                    saveTasks(tasks, storage, ui);
                    ui.showTask("Well charted. I've added this task:", task);
                    printTaskCount(tasks, output);
                }
            } catch (OdysseusException exception) {
                ui.show(exception.getMessage());
            }
            output.println(DIVIDER);
        }

        output.println("Farewell, traveler. May Athena guide your voyage until we meet again.");
        output.println(DIVIDER);
    }

    /** Loads tasks through storage and falls back to an empty list after a loading error. */
    private static TaskList loadTasks(Storage storage, Ui ui) {
        try {
            return storage.load();
        } catch (OdysseusException exception) {
            ui.show("I could not load the ship's log. Starting with an empty log.");
            return new TaskList();
        }
    }

    /** Saves tasks through storage and reports a recoverable saving error. */
    private static void saveTasks(TaskList tasks, Storage storage, Ui ui) {
        try {
            storage.save(tasks);
        } catch (OdysseusException exception) {
            ui.show("I could not save the ship's log.");
        }
    }

    /** Prints the current number of tasks in the voyage log. */
    private static void printTaskCount(TaskList tasks, PrintStream output) {
        output.println("Our voyage now holds " + tasks.getTaskCount() + " task"
                + (tasks.getTaskCount() == 1 ? "." : "s."));
    }
}
