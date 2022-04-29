package streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class StreamExamples{

    private static void streamFromIterateMethod(){
        IntStream.iterate(0, x -> x + 1 ).limit(10).map(x -> x * 2).forEach( x -> System.out.print(x + " "));
        System.out.println();
        IntStream.iterate(0, x -> x + 1 ).limit(10).parallel().map(x -> x * 2).forEach( x -> System.out.print(x + " "));
        System.out.println();
    }

    /**
     * Here we have a side effect inside the lambda expression
     */
    private static void streamFromCollectionWithSiteEffects(){
        Arrays.asList("Car", "Truck", "Jipe").parallelStream()
                .map(s -> { System.out.println(s);  return s.toUpperCase();}).forEach(System.out::println); 
    }

    /**
     * Here we have a stream that has a statefull lambda expression. Where a external variable is change inside the 
     * lambda.
     */
    private static void streamFromCollectionStatefulLambda(){
        List<Integer> data = Collections.synchronizedList(new ArrayList<>());
        Arrays.asList(1,2,3,4,5)
           .parallelStream().map(i -> {data.add(i); return i;}) 
           .forEach(i -> System.out.print(i + " ")); 
 
    }

    public static void main(String...args){
        streamFromIterateMethod();
        System.out.println("==================================");
        streamFromCollectionWithSiteEffects();
        System.out.println("==================================");
        streamFromCollectionStatefulLambda();
    }
}