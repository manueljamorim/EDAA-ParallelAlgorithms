package sorting;

import java.util.function.Supplier;

public class SortingExperiment {
    private  class StopWatch{
        private long startTime;
        private long endTime;

        public long get() {
            return endTime - startTime;
        }
        public void start(){
            startTime = System.nanoTime() / 1000;
        }
        public void stop(){
            endTime = System.nanoTime() / 1000;
        }
    }
    public SorterDetails sorter;
    public int n;
    public long time;
    private Supplier<int[]> array;

    public SortingExperiment(SorterDetails sorter, Supplier<int[]>array){
        this.sorter =sorter;
        this.array = array;

    }

    public String results_csv(){
        StringBuilder r = new StringBuilder();
        r.append(sorter.name).append(",");
        r.append(sorter.isParallel).append(",");
        r.append(n).append(",");
        r.append(time).append("\n");
        return r.toString();
    }


    public void run(boolean log){

        IntSorter s = this.sorter.algorithm.get();
        int[] a = this.array.get();
        this.n = a.length;
        if (log){
            System.out.printf("[Experiment] %s %s on %d elements\n",sorter.name,sorter.isParallel?"parallel": "sequential",n);
        }
        StopWatch clock = new StopWatch();
        clock.start();
        s.sort(a);
        clock.stop();
        this.time = clock.get();
        if(log){
            System.out.printf("   -> Took %d ms \n",time/1000);
        }

    }

}
