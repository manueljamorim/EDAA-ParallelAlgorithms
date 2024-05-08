package parallel.sorting;

import parallel.sorting.MergeSort.MergeSort;
import parallel.sorting.QuickSort.QuickSort;

public class App {

    public static final int MAX_NUMBER = 40000;

    public int[] getRandomArray(int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = (int) (Math.random() * 40000);
        }
        return array;
    }

    public static void main(String[] args) {
        String[] algorithms = { "Merge Sort", "Quick Sort" };

        int algorithm = 0;
        System.out.println("Choose the sorting algorithm: ");
        for (int i = 0; i < algorithms.length; i++) {
            System.out.println((i + 1) + ". " + algorithms[i]);
        }
        algorithm = Integer.parseInt(System.console().readLine());

        boolean is_parallel = false;
        System.out.println("Parallell? y/n");
        is_parallel = System.console().readLine().equals("y");

        int length = 0;
        System.out.println("Enter the length of the array: ");
        length = Integer.parseInt(System.console().readLine());

        int[] unsortedArray = new App().getRandomArray(length);

        System.out.println("Sorting -----------------");
        System.out.println("Algorithm: " + algorithms[algorithm - 1]);
        System.out.println("Length: " + length);
        System.out.println("Parallel: " + is_parallel);

        if (algorithm == 1) {
            // Merge Sort
            int[] sortedArray = unsortedArray;
            MergeSort mergeSort = new MergeSort();
            sortedArray = mergeSort.sort(sortedArray, is_parallel);
            long elapsedTime1 = mergeSort.getElapsedTime();
            System.out.println("Time: " + elapsedTime1 + " ms");

            /*
             * for (int i = 0; i < sortedArray.length; i++) {
             * System.out.println(sortedArray[i]);
             * }
             */

            boolean isSorted = new App().checkSorted(sortedArray);
            System.out.println("isSorted: " + isSorted);

        } else if (algorithm == 2) {
            // Quick Sort
            int[] sortedArray = unsortedArray;
            QuickSort quickSort = new QuickSort();
            quickSort.sort(sortedArray, is_parallel);
            long elapsedTime1 = quickSort.getElapsedTime();
            System.out.println("Time: " + elapsedTime1 + " ms");

            boolean isSorted = new App().checkSorted(sortedArray);

            /*
             * for (int i = 0; i < sortedArray.length; i++) {
             * System.out.println(sortedArray[i]);
             * }
             */

            System.out.println("isSorted: " + isSorted);
        }

    }

    public boolean checkSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

}
