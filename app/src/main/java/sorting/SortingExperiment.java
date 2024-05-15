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
            startTime = System.currentTimeMillis();
        }
        public void stop(){
            endTime = System.currentTimeMillis();
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

    public void run(){

        IntSorter s = this.sorter.algorithm.get();
        int[] a = this.array.get();
        this.n = a.length;

        StopWatch clock = new StopWatch();
        clock.start();
        s.sort(a);
        clock.stop();
        this.time = clock.get();

    }

}
