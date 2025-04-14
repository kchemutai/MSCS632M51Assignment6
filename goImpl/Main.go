package main

import (
	"fmt"
	"log"
	"math/rand"
	"sync"
	"time"
)

// Task represents a unit of work with an ID and data payload
type Task struct {
	ID   int    // Unique task identifier
	Data string // Data to be processed
}

// Result represents the output of processing a Task
type Result struct {
	TaskID int    // Reference to original task
	Output string // Processed output data
}

// worker function processes tasks from a channel and sends results to another channel
// id: Worker identifier
// taskChan: Read-only channel for receiving tasks
// resultsChan: Write-only channel for sending results
// wg: WaitGroup for coordinating completion
func worker(id int, taskChan <-chan Task, resultsChan chan<- Result, wg *sync.WaitGroup) {
	defer wg.Done() // Signal completion when worker exits
	
	// Process tasks from channel until it's closed
	for task := range taskChan {
		log.Printf("Worker %d started processing task %d", id, task.ID)
		
		// Simulate variable processing time
		time.Sleep(time.Duration(rand.Intn(500)) * time.Millisecond)
		
		// Process task data (convert to uppercase with prefix)
		processedData := fmt.Sprintf("PROCESSED_%s", task.Data)
		result := Result{TaskID: task.ID, Output: processedData}
		
		// Send result to output channel
		resultsChan <- result
		log.Printf("Worker %d completed task %d with result: %s", id, task.ID, processedData)
	}
}

func main() {
	const numWorkers = 4  // Number of concurrent workers
	const numTasks = 10   // Number of tasks to process

	// Create buffered channels for tasks and results
	taskChan := make(chan Task, numTasks)
	resultsChan := make(chan Result, numTasks)
	var wg sync.WaitGroup // For tracking worker completion

	// Launch worker goroutines
	for i := 1; i <= numWorkers; i++ {
		wg.Add(1) // Increment WaitGroup counter
		go worker(i, taskChan, resultsChan, &wg)
	}

	// Producer goroutine: feeds tasks to the channel
	go func() {
		for i := 1; i <= numTasks; i++ {
			task := Task{
				ID:   i,
				Data: fmt.Sprintf("task-%d", i),
			}
			taskChan <- task
		}
		close(taskChan) // Signal workers to exit when done
	}()

	// Goroutine to close results channel when all workers finish
	go func() {
		wg.Wait()       // Wait for all workers to complete
		close(resultsChan) // Close results channel
	}()

	// Collect and display results
	var results []Result
	for result := range resultsChan {
		results = append(results, result)
	}

	log.Println("All workers finished. Results:")
	for _, result := range results {
		log.Printf("Task %d: %s", result.TaskID, result.Output)
	}
}