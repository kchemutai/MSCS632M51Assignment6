package javaImpl;

/**
 * Represents a task to be processed by worker threads.
 * Contains an identifier and data to be processed.
 */
public class Task {
    private final int id; // Unique identifier for the task
    private final String data; // Data payload to be processed

    public Task(int id, String data) {
        this.id = id;
        this.data = data;
    }

    // Getters for task properties
    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }
}