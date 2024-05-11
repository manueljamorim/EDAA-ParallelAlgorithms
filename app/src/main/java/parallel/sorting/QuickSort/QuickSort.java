package parallel.sorting.QuickSort;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import parallel.sorting.MergeSort.ParallelMergeSort;

public class QuickSort {
    private long startTime;
    private long endTime;

    public long getElapsedTime() {
        return endTime - startTime;
    }

    public void sort(int[] input, boolean is_parallel) {
        if (is_parallel) {
            final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() - 1);
            //System.out.println("Number of processors: " + Runtime.getRuntime().availableProcessors());
            startTime = System.currentTimeMillis();
            forkJoinPool.invoke(new ParallelQuickSort(input, 0, input.length - 1));
            endTime = System.currentTimeMillis();
            return; // not implemented
        } else {
            startTime = System.currentTimeMillis();
            qsort(input, 0, input.length - 1);
            endTime = System.currentTimeMillis();
        }
    }

    private static void qsort(int[] array, int low, int high) {
        if (low >= high) {
            return; // already sorted
        }

        int pivot = partition(array, low, high);

        qsort(array, low, pivot - 1);
        qsort(array, pivot + 1, high);
    }

    private static int partition(int[] array, int left, int right) {

        int low = left;
        int high = right;
        int pivot = array[left]; // Choose the leftmost element as the pivot

        while (low < high) {

            while (array[high] > pivot)
                --high;
            while (array[low] <= pivot && low < high)
                ++low;

            swap(array, low, high);
        }

        swap(array, left, low);

        return low;
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static final class ParallelQuickSort extends RecursiveTask<Void> {
        private static final int MAX = 8192;
        private final int[] array;
        private final int low;
        private final int high;

        public ParallelQuickSort(final int[] array, final int low, final int high) {
            this.array = array;
            this.low = low;
            this.high = high;
        }

        @Override
        protected Void compute() {
            if (low < high) {
                int pivot = partition(array, low, high);

                if (high - low <= MAX) { // Sequential implementation
                    qsort(array, low, pivot - 1);
                    qsort(array, pivot + 1, high);
                } else { // Parallel implementation
                    ParallelQuickSort left = new ParallelQuickSort(array, low, pivot - 1);
                    ParallelQuickSort right = new ParallelQuickSort(array, pivot + 1, high);
                    invokeAll(left, right);
                }

            }
            return null;
        }

    }
}
