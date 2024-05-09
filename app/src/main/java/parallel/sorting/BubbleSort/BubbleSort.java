package parallel.sorting.BubbleSort;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class BubbleSort {
    private long startTime;
    private long endTime;

    public long getElapsedTime() {
        return endTime - startTime;
    }

    public void sort(int[] input, boolean is_parallel) {
        if (is_parallel) {
            final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() - 1);
            System.out.println("Number of processors: " + Runtime.getRuntime().availableProcessors());
            startTime = System.currentTimeMillis();
            forkJoinPool.invoke(new ParallelBubbleSort(input));
            endTime = System.currentTimeMillis();
        } else {
            startTime = System.currentTimeMillis();
            bubbleSort(input);
            endTime = System.currentTimeMillis();
        }
    }

    private void bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // swap temp and arr[i]
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    private static final class ParallelBubbleSort extends RecursiveAction {
        private static final int THRESHOLD = 8192;
        private final int[] array;

        public ParallelBubbleSort(final int[] array) {
            this.array = array;
        }

        @Override
        protected void compute() {
            if (array.length <= THRESHOLD) {
                new BubbleSort().bubbleSort(array);
            } else {
                int midpoint = array.length / 2;
                int[] left = new int[midpoint];
                int[] right = new int[array.length - midpoint];
                System.arraycopy(array, 0, left, 0, midpoint);
                System.arraycopy(array, midpoint, right, 0, array.length - midpoint);

                ParallelBubbleSort leftTask = new ParallelBubbleSort(left);
                ParallelBubbleSort rightTask = new ParallelBubbleSort(right);

                invokeAll(leftTask, rightTask);

                merge(left, right, array);
            }
        }

        private void merge(int[] left, int[] right, int[] array) {
            int i = 0, j = 0, k = 0;
            while (i < left.length && j < right.length) {
                if (left[i] <= right[j]) {
                    array[k++] = left[i++];
                } else {
                    array[k++] = right[j++];
                }
            }

            while (i < left.length) {
                array[k++] = left[i++];
            }

            while (j < right.length) {
                array[k++] = right[j++];
            }
        }
    }
}
