package bob.task;

/**
 * Represents a general task in the task list.
 */
public abstract class Task {
    /** Description of the task. */
    protected String name;

    /** Completion status of the task. */
    protected boolean isDone;

    /**
     * Constructs a Task with the specified description.
     *
     * @param name the description of the task
     */
    public Task(String name) {
        assert name != null && !name.isBlank() : "Task description should not be null or blank";
        this.name = name;
        this.isDone = false;
    }

    /**
     * Returns the string representation of the task showing status icon and
     * description.
     *
     * @return string representation of this task
     */
    @Override
    public String toString() {
        return "[" + (this.isDone ? "X" : " ") + "] " + this.name;
    }

    /**
     * Checks if the task description contains the specified search keyword
     * (case-insensitive).
     *
     * @param keyword the substring to search for
     * @return true if the description contains the keyword, false otherwise
     */
    public boolean containsKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return false;
        }
        return this.name.toLowerCase().contains(keyword.toLowerCase());
    }

    /**
     * Marks the task as completed.
     */
    public void mark() {
        this.isDone = true;
        assert this.isDone : "Task should be marked as done";
    }

    /**
     * Marks the task as not completed.
     */
    public void unmark() {
        this.isDone = false;
        assert !this.isDone : "Task should be marked as not done";
    }

    /**
     * Formats the task as a string suitable for persistent storage export.
     *
     * @return the exported string representation of the task
     */
    public abstract String export();
}
