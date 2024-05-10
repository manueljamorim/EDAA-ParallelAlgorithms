package parallel.sorting.Parallel;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

public class BitonicMergeSort {
    private static final ForkJoinPool pool = new ForkJoinPool();

    public static void sort(int[] originalArray) throws InterruptedException, ExecutionException {
        int n = originalArray.length;
        int newSize = nextPowerOfTwo(n);
        int[] array = Arrays.copyOf(originalArray, newSize);
        Arrays.fill(array, n, newSize, Integer.MAX_VALUE);

        ForkJoinTask<?> task = pool.submit(() -> bitonicSort(array, 0, newSize, 1));
        task.get(); // Wait for the task to complete

        System.arraycopy(array, 0, originalArray, 0, n); // Copy back the sorted original length

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.HOURS);
    }

    private static int nextPowerOfTwo(int number) {
        int result = 1;
        while (result < number) result <<= 1;
        return result;
    }

    private static void bitonicSort(int[] array, int low, int count, int dir) {
        if (count > 1) {
            int k = count / 2;
            ForkJoinTask<?> left = ForkJoinTask.adapt(() -> bitonicSort(array, low, k, 1)).fork();
            ForkJoinTask<?> right = ForkJoinTask.adapt(() -> bitonicSort(array, low + k, k, 0)).fork();
            right.join(); // Ensuring right task completes
            left.join(); // Ensuring left task completes
            bitonicMerge(array, low, count, dir);
        }
    }

    private static void bitonicMerge(int[] array, int low, int count, int dir) {
        if (count > 1) {
            int k = count / 2;
            for (int i = low; i < low + k; i++) {
                compareAndSwap(array, i, i + k, dir);
            }
            bitonicMerge(array, low, k, dir);
            bitonicMerge(array, low + k, k, dir);
        }
    }

    private static void compareAndSwap(int[] array, int i, int j, int dir) {
        if ((dir == 1 && array[i] > array[j]) || (dir == 0 && array[i] < array[j])) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
    
}
