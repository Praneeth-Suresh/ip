package odysseus;

import java.util.ArrayList;
import java.util.List;

/** An in-memory collection of tasks for the current voyage. */
public class TaskList {
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
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new OdysseusException("There is no task " + taskNumber
                    + ". Use list to see the tasks on our voyage.");
        }
        return tasks.get(taskNumber - 1);
    }

    /** Removes and returns the requested one-based task number. */
    public Task deleteTask(int taskNumber) throws OdysseusException {
        getTask(taskNumber);
        return tasks.remove(taskNumber - 1);
    }

    /** Returns the number of tasks in this list. */
    public int getTaskCount() {
        return tasks.size();
    }

    /** Returns the one-based numbers of tasks whose descriptions contain the keyword. */
    public List<Integer> findTaskNumbers(String keyword) {
        List<Integer> matchingNumbers = new ArrayList<>();
        for (int taskNumber = 1; taskNumber <= tasks.size(); taskNumber++) {
            if (tasks.get(taskNumber - 1).hasDescriptionContaining(keyword)) {
                matchingNumbers.add(taskNumber);
            }
        }
        return matchingNumbers;
    }
}
