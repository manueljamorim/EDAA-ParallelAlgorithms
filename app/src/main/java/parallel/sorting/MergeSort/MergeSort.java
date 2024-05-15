package parallel.sorting.MergeSort;


import sorting.IntSorter;

public class MergeSort implements IntSorter {


     public void sort(int[] array) {
        int N = array.length;
        if (N <= 1)
            return; // the array is already sorted in this case
        int[] a = new int[N / 2]; // the first half of the array
        int[] b = new int[N - N / 2]; // the second half of the array
        for (int i = 0; i < a.length; i++)
            a[i] = array[i]; // copy the first half of the input array
        for (int i = 0; i < b.length; i++)
            b[i] = array[i + N / 2]; // copy the second half of the input array

         sort(a);
         sort(b);
         merge(a, b,array); // recursively sort both halves and merge them together
    }

    private void merge(int[] a, int[] b,int[] destiny) {
        int i = 0, j = 0; // the current indices in the two arrays
        for (int k = 0; k < destiny.length; k++) { // for each element of the result array
            if (i >= a.length)
                destiny[k] = b[j++]; // if all elements of the first array have been used, use the next element of
                               // the second array
            else if (j >= b.length)
                destiny[k] = a[i++]; // if all elements of the second array have been used, use the next element of
                               // the first array
            else if (a[i] <= b[j])
                destiny[k] = a[i++]; // if the element of the first array is less than the element of the second
                               // array, use the next element of the first array
            else
                destiny[k] = b[j++]; // otherwise, use the next element of the second array
        }
    }
}
