package javaImpl;

/**
 * Represents the result of processing a Task.
 * Contains the original task ID and processed output.
 */
public class Result {
    private final int taskId; // Reference to the original task
    private final String output; // Processed result data

    public Result(int taskId, String output) {
        this.taskId = taskId;
        this.output = output;
    }

    // Getters for result properties
    public int getTaskId() {
        return taskId;
    }

    public String getOutput() {
        return output;
    }
}