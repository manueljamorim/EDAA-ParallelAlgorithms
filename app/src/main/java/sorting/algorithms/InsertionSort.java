package sorting.algorithms;

import sorting.IntSorter;

public class InsertionSort implements IntSorter {
    @Override
    public void sort(int[] array) {

        for(int i=1;i < array.length;i++){
            int temp = array[i];
            int j = i;
            while(j > 0 &&  temp < array[j-1]){
                array[j] = array[j-1];
                j--;
            }
            array[j] = temp;
        }
    }
}

