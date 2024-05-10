package parallel.sorting.BubbleSort;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BubbleSort {
    private long elapsedTime = 0;
    private boolean logs = true; // enables logging

    public void sort(int[] array, boolean isParallel) {
        if (isParallel) {
            parallelDivideAndConquerSort(array);
        } else {
            sequentialBubbleSort(array);
        }
    }

    private void sequentialBubbleSort(int[] array) {
        long startTime = System.currentTimeMillis();
        bubbleSort(array, 0, array.length - 1);
        elapsedTime = System.currentTimeMillis() - startTime;
    }

    private void parallelDivideAndConquerSort(int[] array) {
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        long startTime = System.currentTimeMillis();

        // Divide array and sort segments in parallel
        int chunkSize = (int) Math.ceil(array.length / (double) threads);
        for (int i = 0; i < threads; i++) {
            int start = i * chunkSize;
            int end = Math.min((i + 1) * chunkSize - 1, array.length - 1);
            executor.execute(() -> bubbleSort(array, start, end));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Merge sort needed if segments were independently sorted
        // This is a simplified merge step that re-sorts the entire array
        bubbleSort(array, 0, array.length - 1);

        elapsedTime = System.currentTimeMillis() - startTime;
    }

    private void bubbleSort(int[] array, int start, int end) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = start; i < end; i++) {
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    swapped = true;
                }
            }
        } while (swapped);
    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}
