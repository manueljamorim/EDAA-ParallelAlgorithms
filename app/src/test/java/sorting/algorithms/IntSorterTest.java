package sorting.algorithms;

import org.junit.jupiter.api.Test;
import sorting.IntSorter;

public abstract class IntSorterTest {

    abstract protected IntSorter createSorter();

    public boolean isSorted(int[]array){
        for (int i = 0; i < array.length-1; i++){
            if (array[i] > array[i+1]){
                return false;
            }
        }
        return true;
    }

    @Test
    public void sort_already_sorted(){
        var sorter = createSorter();
        int[] a = new int[3];
        a[0] = 1;
        a[1] = 2;
        a[2] = 3;
        sorter.sort(a);
        assert isSorted(a);

    }
    @Test
    public void sort_empty(){
        var sorter = createSorter();
        int[] a = new int[0];
        sorter.sort(a);
        assert isSorted(a);
    }

    @Test
    public void sort_1(){
        var sorter = createSorter();
        int[] a = new int[1];
        a[0] = 1;
        sorter.sort(a);
        assert isSorted(a);
    }

    @Test
    public void sort_2(){
        var sorter = createSorter();
        int[] a = new int[2];
        a[0] = 1;
        a[1] = 0;
        sorter.sort(a);
        assert isSorted(a);
    }

    @Test
    public void sort_3(){
        var sorter = createSorter();
        int[] a = new int[3];
        a[0] = 1;
        a[1] = -1;
        a[2] = 0;
        sorter.sort(a);
        assert isSorted(a);
    }

}
