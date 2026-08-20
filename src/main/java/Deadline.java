/** A task that must be completed by a given date or time string. */
public class Deadline extends Task {
    private final String by;

    /**
     * Creates an incomplete deadline task.
     *
     * @param description text describing the task
     * @param by deadline text, retained without date parsing
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    protected String getTypeMarker() {
        return "D";
    }

    @Override
    protected String getDetails() {
        return " (by: " + by + ")";
    }
}
