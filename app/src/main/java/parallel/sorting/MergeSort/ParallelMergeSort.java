package parallel.sorting.MergeSort;

import java.util.concurrent.RecursiveTask;

public class ParallelMergeSort extends RecursiveTask {

    private static final int MAX = 8192;
    private final int[] array;
    private final int[] helper;
    private final int low;
    private final int high;

    public ParallelMergeSort(final int[] array, final int low, final int high) {
        this.array = array;
        helper = new int[array.length];
        this.low = low;
        this.high = high;
    }

    @Override
    protected Void compute() {
        if (low < high) {
            if (high - low <= MAX) { // Sequential implementation
                mergesort(array, helper, low, high);
            } else { // Parallel implementation
                final int middle = (low + high) / 2;
                final ParallelMergeSort left = new ParallelMergeSort(array, low, middle);
                final ParallelMergeSort right = new ParallelMergeSort(array, middle + 1, high);
                invokeAll(left, right);
                merge(array, helper, low, middle, high);
            }
        }
        return null;
    }

    private void merge(int[] array, int[] helper, int low, int middle, int high) {
        // Copy both parts into the helper array
        for (int i = low; i <= high; i++) {
            helper[i] = array[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;

        // Copy the smallest values from either the left or the right side back to the
        // original array
        while (i <= middle && j <= high) {
            if (helper[i] <= helper[j]) {
                array[k] = helper[i];
                i++;
            } else {
                array[k] = helper[j];
                j++;
            }
            k++;
        }

        // Copy the rest of the left side of the array into the target array
        while (i <= middle) {
            array[k] = helper[i];
            k++;
            i++;
        }
    }

    private void mergesort(int[] array, int[] helper, int low, int high) {
        if (low >= high)
            return;
        final int middle = (low + high) / 2;

        mergesort(array, helper, low, middle);
        mergesort(array, helper, middle + 1, high);
        merge(array, helper, low, middle, high);
    }

}