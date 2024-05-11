package parallel.sorting.MergeSort;

import java.util.concurrent.ForkJoinPool;
import parallel.sorting.MergeSort.ParallelMergeSort;

public class MergeSort {
    private long startTime;
    private long endTime;

    public long getElapsedTime() {
        return endTime - startTime;
    }

    public int[] sort(int[] input, boolean is_parallel) {
        if (is_parallel) {
            final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() - 1);
            //System.out.println("Number of processors: " + Runtime.getRuntime().availableProcessors());
            startTime = System.currentTimeMillis();
            forkJoinPool.invoke(new ParallelMergeSort(input, 0, input.length - 1));
            endTime = System.currentTimeMillis();
            return input;
        } else {
            startTime = System.currentTimeMillis();
            int[] sortedArray = sort(input);
            endTime = System.currentTimeMillis();
            return sortedArray;
        }
    }

    public int[] sort(int[] input) {
        int N = input.length;
        if (N <= 1)
            return input; // the array is already sorted in this case
        int[] a = new int[N / 2]; // the first half of the array
        int[] b = new int[N - N / 2]; // the second half of the array
        for (int i = 0; i < a.length; i++)
            a[i] = input[i]; // copy the first half of the input array
        for (int i = 0; i < b.length; i++)
            b[i] = input[i + N / 2]; // copy the second half of the input array
        return merge(sort(a), sort(b)); // recursively sort both halves and merge them together
    }

    private int[] merge(int[] a, int[] b) {
        int[] c = new int[a.length + b.length]; // the array to hold the merged result
        int i = 0, j = 0; // the current indices in the two arrays
        for (int k = 0; k < c.length; k++) { // for each element of the result array
            if (i >= a.length)
                c[k] = b[j++]; // if all elements of the first array have been used, use the next element of
                               // the second array
            else if (j >= b.length)
                c[k] = a[i++]; // if all elements of the second array have been used, use the next element of
                               // the first array
            else if (a[i] <= b[j])
                c[k] = a[i++]; // if the element of the first array is less than the element of the second
                               // array, use the next element of the first array
            else
                c[k] = b[j++]; // otherwise, use the next element of the second array
        }
        return c;
    }

}
