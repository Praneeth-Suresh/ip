/**
 * A task recorded in Odysseus's voyage log.
 *
 * <p>A task owns its description and completion state so callers need not manage
 * the two pieces of information separately.</p>
 */
public abstract class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates an incomplete task with the given description.
     *
     * @param description text describing the task
     */
    protected Task(String description) {
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

    /** Returns this task's description. */
    protected String getDescription() {
        return description;
    }

    /** Returns this task's one-letter type marker. */
    protected abstract String getTypeMarker();

    /** Returns type-specific details for display. */
    protected String getDetails() {
        return "";
    }

    /** Returns this task's type, status, description, and details for display. */
    @Override
    public final String toString() {
        String status = isDone ? "[X]" : "[ ]";
        return "[" + getTypeMarker() + "]" + status + " " + description + getDetails();
    }
}
