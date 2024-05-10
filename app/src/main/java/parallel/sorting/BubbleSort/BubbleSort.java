package parallel.sorting.BubbleSort;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class BubbleSort {
    private long elapsedTime = 0;
    private boolean logs = false; // enables logging

    public void sort(int[] array, boolean isParallel) {
        if (isParallel) {
            parallelOddEvenSort(array);
        } else {
            sequentialBubbleSort(array);
        }
    }

    private void parallelOddEvenSort(int[] array) {
        final int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CyclicBarrier barrier = new CyclicBarrier(threads, () -> {
            if (logs) System.out.println("Phase completed, all threads synchronized.");
        });

        long startTime = System.currentTimeMillis();
        int n = array.length;

        for (int phase = 0; phase < n; phase++) {
            AtomicBoolean swapped = new AtomicBoolean(false);
            boolean oddPhase = (phase % 2 != 0);
            int startPhase = oddPhase ? 1 : 0;

            for (int t = 0; t < threads; t++) {
                int chunkSize = (n / threads) + (t < threads - 1 ? 1 : 0); // Allow overlap except for the last thread
                int start = t * (n / threads) + startPhase;
                int end; // Ensures we do not go out of bounds

                if (t == threads - 1) {
                    end = n - 1; // Ensure the last thread covers all remaining elements
                } else {
                    end = Math.min(start + chunkSize - 1, n - 1);
                }

                executor.execute(() -> {
                    for (int i = start; i < end; i += 2) {
                        if (array[i] > array[i + 1]) {
                            int temp = array[i];
                            array[i] = array[i + 1];
                            array[i + 1] = temp;
                        }
                    }
                    try {
                        barrier.await();
                        if (logs) System.out.println("Thread passed barrier.");
                    } catch (Exception e) {
                        System.out.println("Barrier exception: " + e.getMessage());
                        Thread.currentThread().interrupt();
                    }
                });

            }
            if (logs) System.out.println("Phase " + phase + " completed.");
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        elapsedTime = System.currentTimeMillis() - startTime;
    }

    private void sequentialBubbleSort(int[] array) {
        long startTime = System.currentTimeMillis();
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
