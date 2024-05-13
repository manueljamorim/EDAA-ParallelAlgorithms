package sorting.algorithms;

import sorting.IntSorter;

import java.util.Optional;

public class QuickSort implements IntSorter {
    public interface PivotChooser {
        int choose(int begin, int end, int[]array);

    }
    PivotChooser pivot;
    public QuickSort(Optional<PivotChooser> choosePivot){
        this.pivot = choosePivot.orElseGet(
                () -> (int begin, int end, int[] array) -> (begin + end) / 2
        );
    }
    @Override
    public void sort(int[] array){
        sort(0,array.length,array);
    }

    private void sort(int begin,int end,int [] array){
        if(begin >= end){
            return;
        }
        int pivot_index = this.pivot.choose(begin,end,array);
        int pivot = array[pivot_index];
        for(int i = begin; i < end; i++){
            if (array[i] > pivot && i < pivot_index){
                array[pivot_index] = array[i];
                array[i] = array[pivot_index -1];
                pivot_index = pivot_index - 1;
                array[pivot_index] = pivot;
            }
            else if(array[i] < pivot  && i > pivot_index){
                array[pivot_index] = array[i];
                array[i] = array[pivot_index + 1];
                pivot_index = pivot_index + 1;
                array[pivot_index] = pivot;
            }
        }
        sort(begin,pivot_index-1,array);
        sort(pivot_index+1,end,array);
    }
}

