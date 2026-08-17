package tasks;

public class Deadline extends Task {
    private String deadline;

    public Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    public String getEntryString() {
        return String.format(
            "[D]%s (by: %s)",
            super.getEntryString(),
            this.deadline
        );
    }
}
