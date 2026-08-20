/** An in-memory collection of tasks for the current voyage. */
public class TaskList {
    private static final int MAX_TASKS = 100;

    private final Task[] tasks = new Task[MAX_TASKS];
    private int taskCount;

    /** Adds a task to this list. */
    public void addTask(Task task) throws OdysseusException {
        if (taskCount == MAX_TASKS) {
            throw new OdysseusException("Our voyage log already holds 100 tasks. Complete one before adding another.");
        }
        tasks[taskCount] = task;
        taskCount++;
    }

    /** Returns the requested one-based task number. */
    public Task getTask(int taskNumber) throws OdysseusException {
        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new OdysseusException("There is no task " + taskNumber
                    + ". Use list to see the tasks on our voyage.");
        }
        return tasks[taskNumber - 1];
    }

    /** Returns the number of tasks in this list. */
    public int getTaskCount() {
        return taskCount;
    }
}
