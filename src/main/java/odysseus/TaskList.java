package odysseus;

import java.util.ArrayList;
import java.util.List;

/** An in-memory collection of tasks for the current voyage. */
public class TaskList {
    private static final int FIRST_TASK_NUMBER = 1;
    private final List<Task> tasks = new ArrayList<>();

    /** Adds a task to this list. */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /** Adds multiple tasks to this list using a variable number of arguments. */
    public void addTasks(Task... tasksToAdd) {
        for (Task task : tasksToAdd) {
            addTask(task);
        }
    }

    /** Returns the requested one-based task number. */
    public Task getTask(int taskNumber) throws OdysseusException {
        if (!isValidTaskNumber(taskNumber)) {
            throw new OdysseusException("There is no task " + taskNumber
                    + ". Use list to see the tasks on our voyage.");
        }
        return tasks.get(toTaskIndex(taskNumber));
    }

    /** Removes and returns the requested one-based task number. */
    public Task deleteTask(int taskNumber) throws OdysseusException {
        Task task = getTask(taskNumber);
        int taskIndex = toTaskIndex(taskNumber);
        assert task == tasks.get(taskIndex) : "A validated task number identifies the task to remove";
        return tasks.remove(taskIndex);
    }

    /** Returns the number of tasks in this list. */
    public int getTaskCount() {
        return tasks.size();
    }

    /** Returns the one-based numbers of tasks whose descriptions contain the keyword. */
    public List<Integer> findTaskNumbers(String keyword) {
        List<Integer> matchingNumbers = new ArrayList<>();
        for (int taskNumber = FIRST_TASK_NUMBER; taskNumber <= tasks.size(); taskNumber++) {
            if (tasks.get(toTaskIndex(taskNumber)).hasDescriptionContaining(keyword)) {
                matchingNumbers.add(taskNumber);
            }
        }
        return matchingNumbers;
    }

    /** Returns whether a number identifies a task currently in this list. */
    private boolean isValidTaskNumber(int taskNumber) {
        return taskNumber >= FIRST_TASK_NUMBER && taskNumber <= tasks.size();
    }

    /** Converts a validated one-based task number to its zero-based list index. */
    private int toTaskIndex(int taskNumber) {
        return taskNumber - FIRST_TASK_NUMBER;
    }
}
