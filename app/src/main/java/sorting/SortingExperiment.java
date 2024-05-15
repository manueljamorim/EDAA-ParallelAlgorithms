package sorting;

import java.util.List;
import java.util.Map;
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
    public String algorithm;
    public boolean isParallel;
    public float memoryUsage;
    public float timeTaken;
    public Map<String,String> info;
    public int n;
    public long time;
    private Supplier<IntSorter> sorter;
    private Supplier<int[]> array;

    public SortingExperiment( Supplier<IntSorter> sorter,Supplier<int[]>array,String algorithm, boolean isParallel,Map<String,String>info){
        this.algorithm = algorithm;
        this.isParallel = isParallel;
        this.sorter =sorter;
        this.array = array;
        this.info = info;
    }

    public void run(){

        IntSorter s = this.sorter.get();
        int[] a = this.array.get();
        this.n = a.length;

        StopWatch clock = new StopWatch();
        clock.start();
        s.sort(a);
        clock.stop();
        this.time = clock.get();

    }

}
