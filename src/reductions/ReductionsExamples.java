package reductions;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.IntStream;

public class ReductionsExamples {

    
    /**
     * Here the reduction uses a function that is not associative, then 
     * the result of parallel and sequencial is not the same.
     */
    private static void reductionWithNonAssociativeFunction(){
        System.out.println("Reduction With Non-Associative Function");
        List<Integer> list = List.of(1,2,3,4,5,6);
        Integer parallelResult = list.parallelStream().reduce(0, (a,b) -> a-b, (c,d) -> c-d);
        Integer sequencialResult = list.stream().reduce(0, (a,b) -> a-b, (c,d) -> c-d);
        System.out.println("Parallel stream result : " +  parallelResult);
        System.out.println("Sequencial stream result : " + sequencialResult);
    }


    /**
     * Here the reduction uses a associative function
     */
    private static void reductionWithAssociativeFunction(){
        System.out.println("Reduction With Associative Function");
        List<Integer> list = List.of(1,2,3,4,5,6);
        Integer parallelResult = list.parallelStream().reduce(0, (a,b) -> a+b, (c,d) -> c+d);
        Integer sequencialResult = list.stream().reduce(0, (a,b) -> a+b, (c,d) -> c+d);
        System.out.println("Parallel stream result : " +  parallelResult);
        System.out.println("Sequencial stream result : " + sequencialResult);
    }

    /**
     *  Reduction with statistics using DoubleSummaryStatistics class
     */   
    private static void generateStatics() {
        System.out.println("Generate statics");
        DoubleSummaryStatistics result = IntStream.rangeClosed(1, 100).parallel().asDoubleStream()
                .collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept,
                DoubleSummaryStatistics::combine);
        System.out.println("Statics : " + result);
    }

    /**
     * Common reduction functions : sum, average and count
     */
    private static void commonReducers(){
        System.out.println("Common statics functions");
       List<Integer> list = List.of(1,2,3,4,5, 6, 7, 8,9,10);
       System.out.println("Sum : " + list.parallelStream().mapToInt(x -> x).sum());
       System.out.println("Avg : " + list.parallelStream().mapToInt(x -> x).average());
       System.out.println("count : " + list.parallelStream().mapToInt(x -> x).count());
    }

    public static void main(String... args) {
        reductionWithNonAssociativeFunction();
        System.out.println("===================================");
        reductionWithAssociativeFunction();
        System.out.println("===================================");
        generateStatics();
        System.out.println("===================================");
        commonReducers();
        System.out.println("===================================");
    }

}