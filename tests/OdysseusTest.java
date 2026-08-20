import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/** Deterministic behavior tests for valid and invalid Odysseus commands. */
public class OdysseusTest {
    public static void main(String[] args) throws Exception {
        rejectsInvalidCommandsWithoutChangingTasks();
        deletesTasksAndRenumbersTheList();
        growsBeyondTheOriginalArrayLimit();
    }

    private static void rejectsInvalidCommandsWithoutChangingTasks() {
        String output = run("""
                todo
                todo read book
                mark two
                mark 2
                deadline return book /by
                deadline return book /by Sunday
                event project meeting /from /to 4pm
                event project meeting /from Mon 2pm /to 4pm
                unmark 4
                mark 1
                list
                bye
                """);

        assertContains(output, "A to-do needs a task to steer by.");
        assertContains(output, "Use a task number after mark");
        assertContains(output, "There is no task 2.");
        assertContains(output, "A deadline needs /by <date or time>.");
        assertContains(output, "Give both an event start after /from and an end after /to.");
        assertContains(output, "There is no task 4.");
        assertContains(output, "1. [T][X] read book");
        assertContains(output, "2. [D][ ] return book (by: Sunday)");
        assertContains(output, "3. [E][ ] project meeting (from: Mon 2pm to: 4pm)");
        assertNotContains(output, "4. [");
    }

    private static void deletesTasksAndRenumbersTheList() {
        String output = run("""
                todo read book
                deadline return book /by Sunday
                event project meeting /from Mon 2pm /to 4pm
                mark 3
                delete 2
                delete 9
                list
                bye
                """);

        assertContains(output, "The waves have carried this task from our log:");
        assertContains(output, "[D][ ] return book (by: Sunday)");
        assertContains(output, "There is no task 9.");
        assertContains(output, "1. [T][ ] read book");
        assertContains(output, "2. [E][X] project meeting (from: Mon 2pm to: 4pm)");
        assertNotContains(output, "3. [");
    }

    private static void growsBeyondTheOriginalArrayLimit() throws Exception {
        TaskList tasks = new TaskList();
        for (int i = 0; i < 101; i++) {
            tasks.addTask(new Todo("task " + i));
        }
        if (tasks.getTaskCount() != 101) {
            throw new AssertionError("Expected the task list to grow beyond 100 tasks");
        }
    }

    private static String run(String commands) {
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
        try (PrintStream output = new PrintStream(capturedOutput, true, StandardCharsets.UTF_8);
                Scanner scanner = new Scanner(commands)) {
            Odysseus.run(scanner, output);
        }
        return capturedOutput.toString(StandardCharsets.UTF_8);
    }

    private static void assertContains(String text, String expected) {
        if (!text.contains(expected)) {
            throw new AssertionError("Expected output to contain: " + expected + "\nActual output:\n" + text);
        }
    }

    private static void assertNotContains(String text, String unexpected) {
        if (text.contains(unexpected)) {
            throw new AssertionError("Expected output not to contain: " + unexpected + "\nActual output:\n" + text);
        }
    }
}
