package sorting.algorithms;

import sorting.IntSorter;

public class BubbleSortTest extends IntSorterTest{
    @Override
    protected IntSorter createSorter() {
        return new BubbleSort();
    }
}

