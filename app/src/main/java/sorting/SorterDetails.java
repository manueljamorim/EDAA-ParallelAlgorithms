package sorting;

import java.util.Map;
import java.util.function.Supplier;

public class SorterDetails {
    public Supplier<IntSorter> algorithm;
    public String name;
    public boolean isParallel;
    public Map<String,String> info;

    SorterDetails(Supplier<IntSorter> algorithm, String name, boolean isParallel, Map<String,String> info){
        this.algorithm = algorithm;
        this.name = name;
        this.isParallel = isParallel;
        this.info = info;
    }
}
