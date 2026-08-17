package tasks;

public class Event extends Task {
    String from;
    String to;

    public Event(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    public String getEntryString() {
        return String.format(
            "[D]%s (from: %s to: %s)",
            super.getEntryString(),
            this.from, 
            this.to
        );
    }
}
