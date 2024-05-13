package sorting.algorithms;

import sorting.IntSorter;

public class MergeSort implements IntSorter {

    private void merge(int[] left,int[] right, int[] array){
        int left_index = 0 ,right_index = 0 ,index  = 0;
        while (index < array.length){
            if(left_index < left.length && right_index < right.length) {
                int l_value = left[left_index];
                int r_value = right[right_index];
                if (l_value < r_value) {
                    array[index] = l_value;
                    left_index++;
                } else {
                    array[index] = r_value;
                    right_index++;
                }
            }else if(left_index < left.length){
                array[index] = left[left_index];
                left_index++;

            }else{
                array[index] = right[right_index];
                right_index++;

            }
            index++;
        }
    }

    @Override
    public void sort(int[] array) {
        if (array.length < 2){
            return;
        }
        int[] left = new int[array.length/2];
        int[] right = new int[array.length - array.length/2];

        int index = 0;
        for(int i =0; i < array.length/2; i++){
            left[index] = array[i];
            index++;
        }
        index = 0;
        for(int i = (array.length/2); i < array.length; i++){
            right[index] = array[i];
            index++;
        }
        sort(left);
        sort(right);
        merge(left,right,array);
    }
}
