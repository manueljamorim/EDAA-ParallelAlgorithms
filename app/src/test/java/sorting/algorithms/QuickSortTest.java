package sorting.algorithms;

import org.junit.jupiter.api.Test;
import sorting.IntSorter;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest extends IntSorterTest{

    @Override
    protected IntSorter createSorter() {
        return new QuickSort(Optional.empty());
    }
}