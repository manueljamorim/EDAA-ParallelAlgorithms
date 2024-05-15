package sorting;
import sorting.algorithms.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
public class Benchmark{
    private static int DEFAULT_MAX = 40000;
    private static int[] randomArray(int length,int max_number) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = (int) (Math.random() * max_number);
        }
        return array;
    }
    static List<SorterDetails> sequential_nSquare = Arrays.asList(
            new SorterDetails(SelectionSort::new,"mergesort",false,null),
            new SorterDetails(InsertionSort::new,"insertionsort",false,null),
            new SorterDetails(BubbleSort::new,"bubblesort",false,null)
    );


    static List<SorterDetails> sequential_nlogn= Arrays.asList(
            new SorterDetails (MergeSort::new,"mergesort",false,null),
            new SorterDetails(() -> new QuickSort(Optional.empty()),"quicksort",false,null)
    );

    public static List<SortingExperiment> sequentialExperiments(){

        List<Supplier<int[]>> arrays= new LinkedList<>();
        int n = 10;
        int EXPONENT = 8;
        for(int i = 0; i < EXPONENT; i++) {
            final int n1 = n;
            arrays.add(() -> randomArray(n1, DEFAULT_MAX));
            n = n * 10;
        }

        List<SortingExperiment> experiments = new LinkedList<>();
        for (Supplier<int[]>array : arrays){
            for(SorterDetails sorter: sequential_nlogn){
                experiments.add(new SortingExperiment(sorter,array));
            }
            for(SorterDetails sorter: sequential_nSquare){
                experiments.add(new SortingExperiment(sorter,array));
            }
        }
        return experiments;
    }

    public static void runBenchmarks() throws IOException{
        FileWriter fileWriter = new FileWriter("append.csv");
        PrintWriter pr= new PrintWriter(fileWriter);

        var experiments = sequentialExperiments();
        for(var experiment : experiments){
            experiment.run(true);
            String result = experiment.results_csv();
            System.out.println(result);
            pr.print(result);
            pr.flush();
        }
        pr.close();
    }

}
