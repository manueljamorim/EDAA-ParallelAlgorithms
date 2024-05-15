package parallel.sorting;

import parallel.sorting.MergeSort.MergeSort;
import parallel.sorting.Parallel.BitonicMergeSort;
import parallel.sorting.Parallel.OddEvenMergeSort;
import parallel.sorting.QuickSort.QuickSort;
import parallel.sorting.BubbleSort.BubbleSort;
import sorting.Benchmark;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class App {

    public static final int MAX_NUMBER = 40000;

    public int[] getRandomArray(int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = (int) (Math.random() * MAX_NUMBER);
        }
        return array;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        if(true){
                Runnable runBenchmarks = () -> {
                    try {
                        Benchmark.runBenchmarks();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };
                runBenchmarks.run();
        }else {


            Scanner scanner = new Scanner(System.in);
            String[] algorithms = {"Merge Sort", "Quick Sort", "Bubble Sort", "Odd-Even Merge Sort (only parallel)", "Bitonic Merge Sort (only parallel)"};

            System.out.println("Choose the sorting algorithm: ");
            for (int i = 0; i < algorithms.length; i++) {
                System.out.println((i + 1) + ". " + algorithms[i]);
            }
            int algorithm = Integer.parseInt(scanner.nextLine()); // Read algorithm choice

            boolean is_parallel = false;
            if (algorithm >= 1 && algorithm <= 3) { // Assuming only the first three are parallelizable
                System.out.println("Parallel? y/n");
                is_parallel = scanner.nextLine().equalsIgnoreCase("y"); // Read parallel option
            }

            System.out.println("Enter the length of the array: ");
            int length = Integer.parseInt(scanner.nextLine()); // Read array length

            int[] unsortedArray = new App().getRandomArray(length);

            System.out.println("Sorting -----------------");
            System.out.println("Algorithm: " + algorithms[algorithm - 1]);
            System.out.println("Length: " + length);
            System.out.println("Parallel: " + is_parallel);

            long startTime, endTime;
            boolean isSorted;

            switch (algorithm) {
                case 1: // Merge Sort
                    MergeSort mergeSort = new MergeSort();
                    startTime = System.currentTimeMillis();
                    mergeSort.sort(unsortedArray);
                    endTime = System.currentTimeMillis();
                    System.out.println("Time: " + (endTime - startTime) + " ms");
                    isSorted = checkSorted(unsortedArray);
                    System.out.println("isSorted: " + isSorted);
                    break;

                case 2: // Quick Sort
                    QuickSort quickSort = new QuickSort();
                    startTime = System.currentTimeMillis();
                    quickSort.sort(unsortedArray, is_parallel);
                    endTime = System.currentTimeMillis();
                    System.out.println("Time: " + (endTime - startTime) + " ms");
                    isSorted = checkSorted(unsortedArray);
                    System.out.println("isSorted: " + isSorted);
                    break;

                case 3: // Bubble Sort
                    BubbleSort bubbleSort = new BubbleSort();
                    startTime = System.currentTimeMillis();
                    bubbleSort.sort(unsortedArray, is_parallel);
                    endTime = System.currentTimeMillis();
                    System.out.println("Time: " + (bubbleSort.getElapsedTime()) + " ms");
                    isSorted = checkSorted(unsortedArray);
                    System.out.println("isSorted: " + isSorted);
                    break;

                case 4: // Odd-Even Merge Sort
                    OddEvenMergeSort oddEvenMergeSort = new OddEvenMergeSort();
                    startTime = System.currentTimeMillis();
                    int[] sortedArrayOddEven = oddEvenMergeSort.sort(unsortedArray);
                    endTime = System.currentTimeMillis();
                    System.out.println("Time: " + (endTime - startTime) + " ms");
                    isSorted = checkSorted(sortedArrayOddEven);
                    System.out.println("isSorted: " + isSorted);
                /*for (int j : sortedArrayOddEven ) {
                    System.out.print(j + " ");
                }*/
                    break;
                case 5: // Bitonic Merge Sort
                    startTime = System.currentTimeMillis();
                    BitonicMergeSort.sort(unsortedArray); // Assuming static sort method
                    endTime = System.currentTimeMillis();
                    System.out.println("Time: " + (endTime - startTime) + " ms");
                    isSorted = checkSorted(unsortedArray);
                    System.out.println("isSorted: " + isSorted);
                /*for (int j : unsortedArray ) {
                    System.out.print(j + " ");
                }*/
                    break;

                default:
                    System.out.println("Invalid algorithm choice.");
            }
            scanner.close();
        }
    }

    public static boolean checkSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
