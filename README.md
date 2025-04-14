# MSCS632Assignment6

1 Overview

You will design and implement a Data Processing System that simulates multiple worker threads processing data in parallel. The system will consist of multiple worker threads that retrieve data from a shared queue, process it, and save results to a shared resource. You will implement this system in Java and Go, each with specific concurrency models and error-handling mechanisms to avoid deadlocks and manage shared resources efficiently.

2 Application Requirements

The Ride Sharing System must include the following components at a minimum. You should add additional functionality and feel free to be creative.

    1. Shared Resource Queue:
    Implement a shared queue of tasks that each worker thread will retrieve and process.
    Use appropriate synchronization techniques in both languages to avoid race conditions when accessing the shared queue.
    2. Worker Threads:
    Each worker thread should retrieve tasks from the shared queue, process them, and write results to a shared results list or output file.
    Implement a task processing function that simulates a delay to represent computational work.
    3. Concurrency Management:
    Use synchronization techniques to manage access to the shared queue and output resource, ensuring that:
    Threads do not deadlock while waiting for access.
    All threads properly complete their tasks without redundant or missed data entries.
    Ensure safe termination of all threads after task completion.
    4. Exception Handling:
    Handle potential exceptions that may arise during processing, such as empty queue access or file I/O errors.
    Implement specific error-handling strategies:
    Java: Use try-catch blocks to handle exceptions for operations like accessing the queue or reading/writing files.
    Go: Check for errors returned from functions and manage graceful exit with defer statements where necessary.
    5. Logging:
    Log any exceptions or errors that occur during execution, and include messages indicating when a thread starts, completes, or encounters an error.

3 Implementation Details

    1. Queue Structure:
    Implement a shared queue structure that supports addTask() and getTask() methods with appropriate synchronization.
    Ensure each worker thread can safely access this queue without causing data inconsistency.
    2. Concurrency Techniques:
    Java: Use synchronized blocks or ReentrantLocks for queue access. Consider using Executors for thread management to handle worker threads efficiently.
    Go: Use goroutines and channels for managing worker pro- cesses and data flow. Channels can serve as a concurrency- safe queue to handle task assignments.
    3. Error Handling:
    Java: Implement try-catch blocks to handle specific exceptions like InterruptedException for thread interruptions and IOException for file operations.
    Check for errors after operations that can fail (e.g., file handling) and handle these gracefully, ensuring resources are released properly.

4 Deliverable

    1. Code Implementation:
    Implement the multi-threaded Data Processing System in both Java and Go, ensuring it meets all specified requirements. Your code should be stored in a GitHub repo for this assignment. Add the link to the repo in your report.
    Ensure correct application of concurrency techniques and proper error handling in both implementations.
    2. Documentation:
    A brief report, 1-2 pages, explaining the concurrency and exception handling techniques used in each language.
    Discuss the differences in handling concurrency between Java and Go, focusing on the different concurrency models.
    Report should follow APA 7 guidelines.
    3. Sample Output
    Provide screenshots of your code and sample output showing system usage in both languages.
    The screenshots should be appended to your report.

Submit one document (.docx or pdf) that includes your report, GitHub link, and your screenshots.
