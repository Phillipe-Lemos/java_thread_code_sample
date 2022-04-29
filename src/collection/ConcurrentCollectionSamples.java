package collection;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConcurrentCollectionSamples {

    /**
     * Generate a Stream of strings using the Stream.of method and returns a
     * parallel version of it
     * 
     * @return Parallel stream of strings.
     */
    private static Stream<String> generateParallelStream() {
        return Stream.of("lion", "tiger", "bears", "dolphins", "tamandua").parallel();
    }

    /**
     * Using the parallel stream of strings, create a ConcurrentMap using
     * Collectors.groupingByConcurrent.
     */
    private static void mutableReductionWithConcurrentMapAndGrouping() {
        final ConcurrentMap<Integer, List<String>> map = generateParallelStream()
                .collect(Collectors.groupingByConcurrent(String::length));
        System.out.println("Concurrent map with string concatenation : " + map);
    }

    /**
     * Using the parallel stream of strings, create a ConcurrentMap using
     * Collectors.toConcurrentMap.
     */
    private static void mutableReductionWithConcurrentMap() {
        final ConcurrentMap<Integer, String> map = generateParallelStream()
                .collect(Collectors.toConcurrentMap(String::length, k -> k, (s1, s2) -> s1 + "," + s2));
        System.out.println("Concurrent map with List of string as value in a map : " + map);
    }

    public static void main(String... args) {
        mutableReductionWithConcurrentMap();
        mutableReductionWithConcurrentMapAndGrouping();
    }
}
