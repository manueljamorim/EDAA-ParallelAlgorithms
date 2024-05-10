package parallel.sorting.Parallel;

import java.util.Arrays;
import java.util.concurrent.*;

public class OddEvenMergeSort {
    private final ExecutorService executor;

    public OddEvenMergeSort() {
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public int[] sort(int[] array) throws InterruptedException, ExecutionException {
        Future<?> sortFuture = oddEvenMergeSort(array, 0, array.length);
        sortFuture.get(); // Ensure sorting completes before proceeding.
        executor.shutdown();
        //executor.awaitTermination(1, TimeUnit.HOURS);
        return array;
    }

    public CompletableFuture<Void> oddEvenMergeSort(int[] array, int low, int high) {
        if (high - low <= 1) {
            return CompletableFuture.completedFuture(null);
        }

        int mid = low + (high - low) / 2;

        CompletableFuture<Void> left = oddEvenMergeSort(array, low, mid);
        CompletableFuture<Void> right = oddEvenMergeSort(array, mid, high);

        return CompletableFuture.allOf(left, right)
                .thenRun(() -> oddEvenMerge(array, low, mid, high));
    }

    private void oddEvenMerge(int[] array, int low, int mid, int high) {
        if (high - low == 1) return;
        int n = high - low;
        int m = n / 2;

        if (n % 2 == 1) m++;
        int[] temp = new int[n];
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
