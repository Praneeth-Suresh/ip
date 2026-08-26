package odysseus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/** Tests task-list indexing and deletion behavior. */
class TaskListTest {
    @Test
    void deleteTask_removesRequestedTaskAndRenumbersList() throws OdysseusException {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("first"));
        tasks.addTask(new Todo("second"));

        Task deleted = tasks.deleteTask(1);

        assertEquals("[T][ ] first", deleted.toString());
        assertEquals(1, tasks.getTaskCount());
        assertEquals("[T][ ] second", tasks.getTask(1).toString());
    }

    @Test
    void getTask_rejectsZeroAndPastEnd() {
        TaskList tasks = new TaskList();

        assertThrows(OdysseusException.class, () -> tasks.getTask(0));
        assertThrows(OdysseusException.class, () -> tasks.getTask(1));
    }
}
