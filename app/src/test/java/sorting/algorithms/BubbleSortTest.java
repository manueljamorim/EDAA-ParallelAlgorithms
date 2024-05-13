package sorting.algorithms;

import sorting.IntSorter;

import java.util.Optional;


public class BubbleSortTest extends IntSorterTest{
    @Override
    protected IntSorter createSorter() {
        return new QuickSort(Optional.empty());
    }
}

