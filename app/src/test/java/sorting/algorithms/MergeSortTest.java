package sorting.algorithms;

import sorting.IntSorter;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest extends IntSorterTest{

    @Override
    protected IntSorter createSorter() {
        return new MergeSort();
    }
}