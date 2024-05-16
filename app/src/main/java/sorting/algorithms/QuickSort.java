package sorting.algorithms;

import sorting.IntSorter;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class QuickSort implements IntSorter {
    public interface PivotChooser {
        int choose(int begin, int end, int[] array);

    }

    PivotChooser pivot;

    public QuickSort(Optional<PivotChooser> choosePivot) {
        this.pivot = choosePivot.orElseGet(() -> (int begin, int end, int[] array) -> (begin + end) / 2);
    }

    @Override
    public void sort(int[] array) {
        sort(0, array.length, array);
    }

    private String print_array(int start, int end, int[] a) {
        StringBuilder s = new StringBuilder("[");
        int i;
        for (i = start; i < end - 1; i++) {
            s.append(String.format("%d", a[i])).append(",");
        }
        if (i < end) {
            s.append(String.format("%d", a[i]));
        }

        return s.append("]").toString();
    }

    private String print_quicksort(int begin, int end, int pivot_index, int[] array) {
        return String.format("%s%d%s",
                print_array(begin, pivot_index, array),
                array[pivot_index],
                print_array(pivot_index + 1, end, array)
        );

    }

    private void sort(int begin, int end, int[] array) {
        if ((begin+1) >= end) {
            return;
        }
        int pivot_index = this.pivot.choose(begin, end, array);
        int pivot = array[pivot_index];
        for (int i = begin; i < end; i++) {
            if (array[i] > pivot && i < pivot_index) {
                array[pivot_index] = array[i];
                array[i] = array[pivot_index - 1];
                pivot_index = pivot_index - 1;
                array[pivot_index] = pivot;
                i--;
            } else if (array[i] < pivot && i > pivot_index) {
                array[pivot_index] = array[i];
                array[i] = array[pivot_index + 1];
                pivot_index = pivot_index + 1;
                array[pivot_index] = pivot;
            }
        }
        sort(begin, pivot_index - 1, array);
        sort(pivot_index + 1, end, array);


    }
}

