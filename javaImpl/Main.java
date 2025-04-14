package javaImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main application class that coordinates workers and tasks.
 * Creates thread pool, initializes shared resources, and manages workflow.
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final int NUM_WORKERS = 4; // Number of worker threads
    private static final int NUM_TASKS = 10; // Number of tasks to process

    public static void main(String[] args) {
        // Initialize shared resources
        SharedTaskQueue taskQueue = new SharedTaskQueue();
        List<Result> results = new ArrayList<>();
        ReentrantLock resultsLock = new ReentrantLock();

        // Create thread pool with fixed number of workers
        ExecutorService executor = Executors.newFixedThreadPool(NUM_WORKERS);

        // Start worker threads
        for (int i = 0; i < NUM_WORKERS; i++) {
            executor.execute(new Worker(i + 1, taskQueue, results, resultsLock));
        }

        // Add tasks to the queue
        for (int i = 0; i < NUM_TASKS; i++) {
            taskQueue.addTask(new Task(i + 1, "task-" + (i + 1)));
        }

        // Signal queue to stop and shutdown executor
        taskQueue.stop();
        executor.shutdown();

        // Wait for all tasks to complete
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Main thread interrupted", e);
                Thread.currentThread().interrupt();
            }
        }

        // Display results
        logger.log(Level.INFO, "All tasks completed. Results:");
        results.forEach(result -> logger.log(Level.INFO, "Task {0}: {1}",
                new Object[] { result.getTaskId(), result.getOutput() }));
    }
}