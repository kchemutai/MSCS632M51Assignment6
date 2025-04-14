package javaImpl;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Worker thread that processes tasks from the shared queue.
 * Each worker runs in its own thread and processes tasks until queue is
 * stopped.
 */
public class Worker implements Runnable {
    private static final Logger logger = Logger.getLogger(Worker.class.getName());
    private final int id; // Worker identifier
    private final SharedTaskQueue taskQueue; // Shared task source
    private final List<Result> results; // Shared results collection
    private final ReentrantLock resultsLock; // Lock for results synchronization

    public Worker(int id, SharedTaskQueue taskQueue, List<Result> results, ReentrantLock resultsLock) {
        this.id = id;
        this.taskQueue = taskQueue;
        this.results = results;
        this.resultsLock = resultsLock;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Task task = taskQueue.getTask();
                if (task == null)
                    break; // Exit when queue is stopped

                logger.log(Level.INFO, "Worker {0} started processing task {1}",
                        new Object[] { id, task.getId() });

                // Simulate processing time
                Thread.sleep((long) (Math.random() * 500));

                // Process task (convert to uppercase)
                String processedData = task.getData().toUpperCase();
                Result result = new Result(task.getId(), processedData);

                // Safely add result to shared collection
                resultsLock.lock();
                try {
                    results.add(result);
                } finally {
                    resultsLock.unlock();
                }

                logger.log(Level.INFO, "Worker {0} completed task {1} with result: {2}",
                        new Object[] { id, task.getId(), processedData });
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Worker " + id + " was interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
}