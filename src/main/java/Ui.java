import java.io.PrintStream;

/** Handles messages shown to the traveler. */
public class Ui {
    private final PrintStream output;

    /** Creates a UI that writes to the given output stream. */
    public Ui(PrintStream output) {
        this.output = output;
    }

    /** Shows a task list. */
    public void showTaskList(TaskList tasks) throws OdysseusException {
        if (tasks.getTaskCount() == 0) {
            output.println("My ship's log is clear, traveler.");
            return;
        }
        output.println("Here are the tasks on our voyage, traveler:");
        for (int number = 1; number <= tasks.getTaskCount(); number++) {
            output.println(number + ". " + tasks.getTask(number));
        }
    }

    /** Shows a user-facing message. */
    public void show(String message) {
        output.println(message);
    }

    /** Shows a task with its preceding message. */
    public void showTask(String message, Task task) {
        show(message);
        output.println("  " + task);
    }
}
