package bob.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import bob.exception.BobException;
import bob.task.Deadline;
import bob.task.Event;
import bob.task.Task;
import bob.task.TaskList;
import bob.task.ToDo;
import bob.util.DatetimeHelper;

/**
 * Provides persistent storage for a list of {@link Task} objects.
 * <p>
 * Tasks are stored in a text file using a type-specific format:
 * </p>
 *
 * <pre>
 * {@link ToDo}:      T | status | name
 * {@link Deadline}:  D | status | name | deadline
 * {@link Event}:     E | status | name | from | to
 * </pre>
 * <p>
 * The storage file is located at {@code ./data/tasks.txt}.
 * If the file does not exist when loading, an empty task list is returned.
 * </p>
 */
public class TaskStorage implements Storage<TaskList> {

    private static final Path FILE_PATH = Paths.get("data", "tasks.txt");
    private static final String STORAGE_DELIMITER_REGEX = " \\| ";
    private static final String TYPE_TODO = "T";
    private static final String TYPE_DEADLINE = "D";
    private static final String TYPE_EVENT = "E";
    private static final String STATUS_DONE = "1";

    private final Path path;

    /**
     * Constructs a TaskStorage instance using the default storage path
     * ({@code data/tasks.txt}).
     */
    public TaskStorage() {
        this(FILE_PATH);
    }

    /**
     * Constructs a TaskStorage instance with a custom file path.
     *
     * @param path the path to the storage file
     */
    public TaskStorage(Path path) {
        this.path = path;
    }

    /**
     * Loads the tasks from the persistent storage file.
     *
     * @return a {@link TaskList} containing all parsed tasks, or an empty list if
     *         file doesn't exist
     * @throws BobException if an I/O error occurs or the file content is malformed
     */
    @Override
    public TaskList load() throws BobException {
        List<Task> tasks = new ArrayList<>();

        // No file on first startup -> return empty task list.
        if (!Files.exists(path)) {
            return new TaskList(tasks);
        }

        try {
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }

                tasks.add(parseTask(line));
            }

        } catch (IOException e) {
            throw new BobException("I/O Error: Unable to load tasks from storage");
        }

        return new TaskList(tasks);
    }

    /**
     * Saves the given task list to the storage file.
     *
     * @param tasks the list of tasks to save
     * @throws BobException if an I/O error occurs during saving
     */
    @Override
    public void save(TaskList tasks) throws BobException {
        try {
            // Create ./data/ if it does not exist.
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            List<String> lines = new ArrayList<>();

            for (Task task : tasks) {
                lines.add(task.export());
            }

            Files.write(path, lines);

        } catch (IOException e) {
            throw new BobException("I/O Error: Unable to save tasks");
        }
    }

    /**
     * Parses a single line from the storage file into a {@link Task} object.
     *
     * @param line the formatted string representation of a task
     * @return the parsed {@link Task}
     * @throws BobException if the line format is invalid or contains unparseable
     *                      dates
     */
    private Task parseTask(String line) throws BobException {
        String[] parts = line.split(STORAGE_DELIMITER_REGEX);

        if (parts.length < 3) {
            throw new BobException("Error: Invalid task export format: " + line);
        }

        String type = parts[0];
        boolean isDone = parts[1].equals(STATUS_DONE);

        Task task;
        switch (type) {
            case TYPE_TODO:
                task = parseTodoFromStorage(parts);
                break;

            case TYPE_DEADLINE:
                task = parseDeadlineFromStorage(parts, line);
                break;

            case TYPE_EVENT:
                task = parseEventFromStorage(parts, line);
                break;

            default:
                throw new BobException("Error: Unknown task type of " + type);
        }

        if (isDone) {
            task.mark();
        }

        return task;
    }

    /**
     * Parses a {@link ToDo} task from storage line tokens.
     *
     * @param parts the tokens extracted from the storage line
     * @return the parsed {@link ToDo} task
     */
    private Task parseTodoFromStorage(String[] parts) {
        return new ToDo(parts[2]);
    }

    /**
     * Parses a {@link Deadline} task from storage line tokens.
     *
     * @param parts the tokens extracted from the storage line
     * @param line  the raw storage line for error reporting
     * @return the parsed {@link Deadline} task
     * @throws BobException if the token count is invalid or date format cannot be
     *                      parsed
     */
    private Task parseDeadlineFromStorage(String[] parts, String line) throws BobException {
        if (parts.length != 4) {
            throw new BobException("Error: Corrupted deadline format: " + line);
        }

        try {
            LocalDateTime deadline = LocalDateTime.parse(parts[3], DatetimeHelper.ISO_FORMATTER);
            return new Deadline(parts[2], deadline);
        } catch (DateTimeParseException e) {
            throw new BobException("Error: Corrupted date-time format: " + line);
        }
    }

    /**
     * Parses an {@link Event} task from storage line tokens.
     *
     * @param parts the tokens extracted from the storage line
     * @param line  the raw storage line for error reporting
     * @return the parsed {@link Event} task
     * @throws BobException if the token count is invalid or date format cannot be
     *                      parsed
     */
    private Task parseEventFromStorage(String[] parts, String line) throws BobException {
        if (parts.length != 5) {
            throw new BobException("Error: Corrupted event format: " + line);
        }

        try {
            LocalDateTime from = LocalDateTime.parse(parts[3], DatetimeHelper.ISO_FORMATTER);
            LocalDateTime to = LocalDateTime.parse(parts[4], DatetimeHelper.ISO_FORMATTER);
            return new Event(parts[2], from, to);
        } catch (DateTimeParseException e) {
            throw new BobException("Error: Corrupted date-time format: " + line);
        }
    }
}
