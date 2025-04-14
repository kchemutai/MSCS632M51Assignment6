package javaImpl;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-safe queue for distributing tasks to workers.
 * Uses ReentrantLock and Condition for synchronization.
 */
public class SharedTaskQueue {
    private final Queue<Task> taskQueue = new LinkedList<>();
    private final ReentrantLock queueLock = new ReentrantLock();
    private final Condition notEmpty = queueLock.newCondition();
    private boolean isStopped = false;

    /**
     * Adds a task to the queue and signals waiting threads.
     * 
     * @param task The task to be added
     * @throws IllegalStateException if queue is stopped
     */
    public void addTask(Task task) {
        queueLock.lock();
        try {
            if (isStopped)
                throw new IllegalStateException("Queue has been stopped");
            taskQueue.add(task);
            notEmpty.signal(); // Wake up one waiting thread
        } finally {
            queueLock.unlock();
        }
    }

    /**
     * Retrieves a task from the queue, blocking if empty.
     * 
     * @return Next task or null if queue is stopped
     * @throws InterruptedException if thread is interrupted while waiting
     */
    public Task getTask() throws InterruptedException {
        queueLock.lock();
        try {
            // Wait while queue is empty and not stopped
            while (taskQueue.isEmpty() && !isStopped) {
                notEmpty.await();
            }
            return isStopped ? null : taskQueue.poll();
        } finally {
            queueLock.unlock();
        }
    }

    /**
     * Stops the queue and wakes all waiting threads.
     */
    public void stop() {
        queueLock.lock();
        try {
            isStopped = true;
            notEmpty.signalAll(); // Wake all waiting threads
        } finally {
            queueLock.unlock();
        }
    }
}