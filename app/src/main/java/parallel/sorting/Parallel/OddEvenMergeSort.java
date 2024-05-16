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
        int[] temp = new int[high - low];  // Temporary array to store merged results

        // 1. Interleaving: Split into odd and even subsequences
        int oddIndex = 0, evenIndex = 0;
        for (int i = low; i < high; i++) {
            if ((i - low) % 2 == 0) {
                temp[evenIndex++] = array[i]; // Even index
            } else {
                temp[oddIndex++] = array[i]; // Odd index
            }
        }

        // 2. Local Sorting (in parallel for efficiency)
        int finalOddIndex = oddIndex;
        CompletableFuture<Void> oddSort = CompletableFuture.runAsync(
                () -> Arrays.sort(temp, 0, finalOddIndex), executor
        );
        int finalOddIndex1 = oddIndex;
        CompletableFuture<Void> evenSort = CompletableFuture.runAsync(
                () -> Arrays.sort(temp, finalOddIndex1, high - low), executor
        );
        CompletableFuture.allOf(oddSort, evenSort).join();

        // 3. Final Merge (odd-even merge)
        oddIndex = 0;
        evenIndex = (high - low + 1) / 2;
        for (int i = low; i < high; i++) {
            if (oddIndex < (high - low + 1) / 2 &&
                    (evenIndex >= high - low || temp[oddIndex] <= temp[evenIndex])) {
                array[i] = temp[oddIndex++];
            } else {
                array[i] = temp[evenIndex++];
            }
        }
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
