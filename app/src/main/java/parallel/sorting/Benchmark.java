package parallel.sorting;

import parallel.sorting.MergeSort.MergeSort;
import parallel.sorting.Parallel.BitonicMergeSort;
import parallel.sorting.Parallel.OddEvenMergeSort;
import parallel.sorting.QuickSort.QuickSort;
import parallel.sorting.BubbleSort.BubbleSort;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class Benchmark {

    //public static final int MAX_NUMBER = 40000;
    //private static final int[] SIZES = {10000, 100000, 1000000, 10000000};

    //public static void main(String[] args) throws ExecutionException, InterruptedException {
    //    for (int size : SIZES) {
    //        System.out.println("Benchmarking array size: " + size);
    //        int[] originalArray = getRandomArray(size);

    //        // Benchmark each sorting algorithm
    //        Thread.sleep(500);
    //        if (size < 1000000) { // Avoid Merge Sort on size 1,000,000
    //            benchmarkSorting("Merge Sort", new MergeSort(), Arrays.copyOf(originalArray, originalArray.length), true);
    //            Thread.sleep(500);
    //            benchmarkSorting("Merge Sort", new MergeSort(), Arrays.copyOf(originalArray, originalArray.length), false);
    //            Thread.sleep(500);
    //        }
    //        benchmarkSorting("Quick Sort", new QuickSort(), Arrays.copyOf(originalArray, originalArray.length), true);
    //        Thread.sleep(500);
    //        benchmarkSorting("Quick Sort", new QuickSort(), Arrays.copyOf(originalArray, originalArray.length), false);
    //        Thread.sleep(500);
    //        if (size < 100000) { // Avoid Bubble Sort on size 100,000
    //            benchmarkSorting("Bubble Sort", new BubbleSort(), Arrays.copyOf(originalArray, originalArray.length), true);
    //            Thread.sleep(500);
    //            benchmarkSorting("Bubble Sort", new BubbleSort(), Arrays.copyOf(originalArray, originalArray.length), false);
    //            Thread.sleep(500);
    //        }
    //        benchmarkSorting("Odd-Even Merge Sort", new OddEvenMergeSort(), Arrays.copyOf(originalArray, originalArray.length), true); // Always parallel
    //        Thread.sleep(500);
    //        benchmarkSorting("Bitonic Merge Sort", BitonicMergeSort.class, Arrays.copyOf(originalArray, originalArray.length), true); // Always parallellel
    //    }
    //}

    //private static void benchmarkSorting(String algorithmName, Object sorter, int[] array, boolean isParallel) throws ExecutionException, InterruptedException {
    //    try {
    //        long startTime = System.currentTimeMillis();
    //        if (sorter instanceof MergeSort) {
    //            ((MergeSort) sorter).sort(array, isParallel);
    //        } else if (sorter instanceof QuickSort) {
    //            ((QuickSort) sorter).sort(array, isParallel);
    //        } else if (sorter instanceof BubbleSort) {
    //            ((BubbleSort) sorter).sort(array, isParallel);
    //        } else if (sorter instanceof OddEvenMergeSort) {
    //            ((OddEvenMergeSort) sorter).sort(array);
    //        } else if (sorter == BitonicMergeSort.class) {
    //            BitonicMergeSort.sort(array);
    //        }
    //        long endTime = System.currentTimeMillis();
    //        System.out.println(algorithmName + " (" + (isParallel ? "Parallel" : "Non-parallel") + "): " + (endTime - startTime) + " ms. Sorted: " + checkSorted(array));
    //    } catch (Exception e) {
    //        System.out.println(algorithmName + " (" + (isParallel ? "Parallel" : "Non-parallel") + "): Failed to sort the array.");
    //    }
    //}


    //public static boolean checkSorted(int[] array) {
    //    for (int i = 0; i < array.length - 1; i++) {
    //        if (array[i] > array[i + 1]) {
    //            return false;
    //        }
    //    }
    //    return true;
    //}
}
