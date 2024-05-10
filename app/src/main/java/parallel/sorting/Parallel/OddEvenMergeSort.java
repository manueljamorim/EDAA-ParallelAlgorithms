package parallel.sorting.Parallel;

import java.util.Arrays;
import java.util.concurrent.*;

public class OddEvenMergeSort {
    private final ExecutorService executor;
    private final int availableProcessors;

    public OddEvenMergeSort() {
        this.availableProcessors = Runtime.getRuntime().availableProcessors();
        this.executor = Executors.newFixedThreadPool(availableProcessors);
    }

    public int[] sort(int[] array) throws InterruptedException, ExecutionException {
        int optimalSize = Math.max(array.length / availableProcessors, 1); // Ensure non-zero size
        Future<?> sortFuture = oddEvenMergeSort(array, 0, array.length, optimalSize);
        sortFuture.get(); // Ensure sorting completes before proceeding.
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
        return array;
    }

    private CompletableFuture<Void> oddEvenMergeSort(int[] array, int low, int high, int optimalSize) {
        if (high - low <= optimalSize) {
            // Use sequential sort when the task size is less than or equal to the optimal size
            return CompletableFuture.runAsync(() -> Arrays.sort(array, low, high), executor);
        }

        int mid = low + (high - low) / 2;

        CompletableFuture<Void> left = oddEvenMergeSort(array, low, mid, optimalSize);
        CompletableFuture<Void> right = oddEvenMergeSort(array, mid, high, optimalSize);

        return CompletableFuture.allOf(left, right)
                .thenRun(() -> merge(array, low, mid, high));
    }

    private void merge(int[] array, int low, int mid, int high) {
        int[] temp = new int[high - low];
        int i = low, j = mid, k = 0;
        while (i < mid && j < high) {
            temp[k++] = array[i] <= array[j] ? array[i++] : array[j++];
        }
        while (i < mid) {
            temp[k++] = array[i++];
        }
        while (j < high) {
            temp[k++] = array[j++];
        }
        System.arraycopy(temp, 0, array, low, temp.length);
    }

    public static void main(String[] args) {
        int[] array = {5, 3, 2, 8, 6, 1, 4, 7};
        OddEvenMergeSort sorter = new OddEvenMergeSort();
        try {
            sorter.sort(array);
            System.out.println(Arrays.toString(array));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
