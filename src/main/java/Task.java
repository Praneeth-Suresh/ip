/**
 * A task recorded in Odysseus's voyage log.
 *
 * <p>A task owns its description and completion state so callers need not manage
 * the two pieces of information separately.</p>
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates an incomplete task with the given description.
     *
     * @param description text describing the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks this task as done. */
    public void markAsDone() {
        isDone = true;
    }

    /** Marks this task as not done. */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns this task's status and description for display.
     *
     * @return the task in the form {@code [ ] description} or {@code [X] description}
     */
    @Override
    public String toString() {
        String status = isDone ? "[X]" : "[ ]";
        return status + " " + description;
    }
}
