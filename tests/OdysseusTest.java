import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/** Deterministic behavior tests for valid and invalid Odysseus commands. */
public class OdysseusTest {
    public static void main(String[] args) throws Exception {
        rejectsInvalidCommandsWithoutChangingTasks();
        rejectsTheOneHundredAndFirstTask();
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

    private static void rejectsTheOneHundredAndFirstTask() throws Exception {
        TaskList tasks = new TaskList();
        for (int i = 0; i < 100; i++) {
            tasks.addTask(new Todo("task " + i));
        }

        try {
            tasks.addTask(new Todo("one too many"));
            throw new AssertionError("Expected the 101st task to be rejected");
        } catch (OdysseusException exception) {
            assertContains(exception.getMessage(), "already holds 100 tasks");
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
