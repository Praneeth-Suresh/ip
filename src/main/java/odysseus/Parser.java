package odysseus;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/** Converts user command text into task operations and task values. */
public class Parser {
    /**
     * Parses the one-based task number that follows an existing-task action.
     *
     * @param command complete user command
     * @param action action that owns the command prefix
     * @return validated one-based task number
     * @throws OdysseusException if no valid task number follows the action
     */
    public int parseTaskNumber(String command, TaskAction action) throws OdysseusException {
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
     * Creates a task from a supported creation command.
     *
     * @param command complete user command
     * @return task described by the command
     * @throws OdysseusException if the command is malformed or unsupported
     */
    public Task parseTask(String command) throws OdysseusException {
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
            if (description.isBlank() || by.isBlank()) {
                throw new OdysseusException("A deadline needs a description and a date.");
            }
            try {
                return new Deadline(description, LocalDate.parse(by));
            } catch (DateTimeParseException exception) {
                throw new OdysseusException("Deadline dates use yyyy-MM-dd, for example: 2019-10-15.");
            }
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
            if (description.isBlank() || from.isBlank() || to.isBlank()) {
                throw new OdysseusException("Give an event description, start, and end.");
            }
            return new Event(description, from, to);
        }
        throw new OdysseusException("I cannot chart a course from that command. Try todo, deadline, event, list, mark, unmark, or bye.");
    }
}
