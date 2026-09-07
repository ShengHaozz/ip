package bob.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.AbstractMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bob.task.Task;
import bob.task.TaskList;
import bob.task.ToDo;

/**
 * Unit tests for {@link Ui}.
 */
public class UiTest {
    private Ui ui;

    @BeforeEach
    public void setUp() {
        ui = new Ui();
    }

    @Test
    public void setWelcome_updatesLastResponse() {
        ui.setWelcome();
        assertEquals("Hello! I'm Bob.\nWhat can I do for you?", ui.getLastResponse());
    }

    @Test
    public void setGoodbye_updatesLastResponse() {
        ui.setGoodbye();
        assertEquals("Goodbye.", ui.getLastResponse());
    }

    @Test
    public void setTaskAdded_updatesLastResponse() {
        Task task = new ToDo("read book");
        ui.setTaskAdded(task, 1);
        assertEquals("Task added:\n[T][ ] read book\n1 item in list", ui.getLastResponse());

        ui.setTaskAdded(task, 2);
        assertEquals("Task added:\n[T][ ] read book\n2 items in list", ui.getLastResponse());
    }

    @Test
    public void setTaskDeleted_updatesLastResponse() {
        Task task = new ToDo("read book");
        ui.setTaskDeleted(task, 0);
        assertEquals("Removed: \n[T][ ] read book\n0 item in list", ui.getLastResponse());

        ui.setTaskDeleted(task, 3);
        assertEquals("Removed: \n[T][ ] read book\n3 items in list", ui.getLastResponse());
    }

    @Test
    public void setTaskMarked_updatesLastResponse() {
        Task task = new ToDo("read book");
        ui.setTaskMarked(task, true);
        assertEquals("Marked as done:\n [T][ ] read book", ui.getLastResponse());

        ui.setTaskMarked(task, false);
        assertEquals("Marked as not done:\n [T][ ] read book", ui.getLastResponse());
    }

    @Test
    public void setTaskList_updatesLastResponse() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("task 1"));
        tasks.add(new ToDo("task 2"));
        ui.setTaskList(tasks);
        assertEquals("Tasks:\n1: [T][ ] task 1\n2: [T][ ] task 2", ui.getLastResponse());
    }

    @Test
    public void setMatchingTasks_updatesLastResponse() {
        Task task = new ToDo("task 1");
        ui.setMatchingTasks(List.of(new AbstractMap.SimpleEntry<>(1, task)));
        assertEquals("Here are the matching tasks in your list:\n1.[T][ ] task 1", ui.getLastResponse());
    }

    @Test
    public void setErrorAndMessage_updatesLastResponse() {
        ui.setError("Error occurred");
        assertEquals("Error occurred", ui.getLastResponse());

        ui.setMessage("Simple message");
        assertEquals("Simple message", ui.getLastResponse());
    }
}
