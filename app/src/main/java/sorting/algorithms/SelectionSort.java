package sorting.algorithms;

import sorting.IntSorter;

public class SelectionSort implements IntSorter {

    private int min(int begin,int end,int[] array){
        if(begin >= end){
            return -1;
        }
        int min_index = begin;
        int min_val = array[min_index];

        for(int i = begin+1; i < end; i++){
            if (array[i] < min_val){
                min_index = i;
                min_val = array[min_index];
            }
        }
        return min_index;
    }
    @Override
    public void sort(int[] array) {
        for(int i=0; i < array.length; i++){
            int next_min = min(i,array.length,array);
            int min_value = array[next_min];
            array[next_min] = array[i];
            array[i] = min_value;
        }
    }
}
