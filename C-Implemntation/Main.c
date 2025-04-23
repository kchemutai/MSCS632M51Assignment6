#include <stdio.h>

// Quick Sort Partition Function
int partition(int arr[], int low, int high) {
    int pivot = arr[high];  // Pivot element
    int i = low - 1;        // Index of smaller element

    for (int j = low; j < high; j++) {
        if (arr[j] <= pivot) {
            i++;
            // Swap arr[i] and arr[j]
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    // Swap arr[i + 1] and arr[high] (or pivot)
    int temp = arr[i + 1];
    arr[i + 1] = arr[high];
    arr[high] = temp;

    return i + 1;
}

// Quick Sort Recursive Function
void quick_sort(int arr[], int low, int high) {
    if (low < high) {
        // Partition index
        int pi = partition(arr, low, high);

        // Recursively sort elements before and after partition
        quick_sort(arr, low, pi - 1);
        quick_sort(arr, pi + 1, high);
    }
}

// Function to calculate mean
float calculate_mean(int arr[], int size) {
    int sum = 0;
    for (int i = 0; i < size; i++)
        sum += arr[i];
    return (float)sum / size;
}

// Function to calculate median
float calculate_median(int arr[], int size) {
    // Make a copy to avoid modifying the original array
    int sorted[size];
    for (int i = 0; i < size; i++)
        sorted[i] = arr[i];

    quick_sort(sorted, 0, size - 1);

    if (size % 2 == 0)
        return (sorted[size / 2 - 1] + sorted[size / 2]) / 2.0;
    else
        return sorted[size / 2];
}

// Function to calculate and print mode(s)
void calculate_mode(int arr[], int size) {
    int max_count = 0;
    int freq[size];

    for (int i = 0; i < size; i++) {
        freq[i] = 0;
        for (int j = 0; j < size; j++) {
            if (arr[i] == arr[j]) freq[i]++;
        }
        if (freq[i] > max_count) max_count = freq[i];
    }

    printf("Mode(s): ");
    for (int i = 0; i < size; i++) {
        if (freq[i] == max_count) {
            int is_unique = 1;
            for (int j = 0; j < i; j++) {
                if (arr[i] == arr[j]) is_unique = 0;
            }
            if (is_unique) printf("%d ", arr[i]);
        }
    }
    printf("\n");
}

int main() {
    int numbers[] = {1, 3, 2, 2, 4, 5, 3};
    int size = sizeof(numbers) / sizeof(numbers[0]);

    printf("Mean: %.2f\n", calculate_mean(numbers, size));
    printf("Median: %.2f\n", calculate_median(numbers, size));
    calculate_mode(numbers, size);

    return 0;
}
