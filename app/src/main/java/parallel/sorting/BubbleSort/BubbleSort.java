package parallel.sorting.BubbleSort;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.CountDownLatch;

public class BubbleSort {
    private long elapsedTime = 0;
    private boolean logs = false; //enables logging

    public void sort(int[] array, boolean isParallel) {
        if (isParallel) {
            parallelOddEvenSort(array);
        } else {
            sequentialBubbleSort(array);
        }
    }

    private void sequentialBubbleSort(int[] array) {
        long startTime = System.currentTimeMillis();
        boolean sorted = false;
        int n = array.length;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < n - 1; i++) {
                if (array[i] > array[i + 1]) {
                    swap(array, i, i + 1);
                    sorted = false;
                }
            }
            n--;
        }
        long endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
    }

    private void parallelOddEvenSort(int[] array) {
        ExecutorService executor = Executors.newWorkStealingPool();
        long startTime = System.currentTimeMillis();
        boolean sorted = false;

        while (!sorted) {
            AtomicInteger swapCount = new AtomicInteger(0);
            for (int phase = 0; phase < 2; phase++) {
                CountDownLatch latch = new CountDownLatch((array.length - 1) / 2);
                if (logs) {
                    System.out.println("Starting phase " + (phase == 0 ? "Even" : "Odd") + " with " + latch.getCount() + " tasks.");
                }
                for (int i = phase; i < array.length - 1; i += 2) {
                    executor.execute(new SortTask(array, i, swapCount, latch));
                }
                try {
                    latch.await();
                    if (logs) {
                        System.out.println("Phase " + (phase == 0 ? "Even" : "Odd") + " completed with " + swapCount.get() + " swaps.");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            if (swapCount.get() == 0) {
                sorted = true;
                if (logs) {
                    System.out.println("No swaps needed, array is sorted.");
                }
            }
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        long endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
    }

    private void swap(int[] array, int i, int j) {
        synchronized (array) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    class SortTask implements Runnable {
        private int[] array;
        private int index;
        private AtomicInteger swapCount;
        private CountDownLatch latch;

        SortTask(int[] array, int index, AtomicInteger swapCount, CountDownLatch latch) {
            this.array = array;
            this.index = index;
            this.swapCount = swapCount;
            this.latch = latch;
        }

        @Override
        public void run() {
            if (array[index] > array[index + 1]) {
                swap(array, index, index + 1);
                swapCount.incrementAndGet();
            }
            latch.countDown();
        }
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setLogs(boolean logs) {
        this.logs = logs;
    }
}
