package managing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierSample {

    private CyclicBarrier cyclicBarrier;
    
    private List<List<Integer>> finalResults = Collections.synchronizedList(new ArrayList<>());
    
    private Random random = new Random();
    
    private int numPartialResults;
    
    private int numOfWorkers;

    public static void main(String[] args) {
        CyclicBarrierSample demo = new CyclicBarrierSample();
        demo.runSimulation(5, 3);
    }


    public void runSimulation(int numWorkers, int numberOfPartialResults) {
        numPartialResults = numberOfPartialResults;
        numOfWorkers = numWorkers;

        cyclicBarrier = new CyclicBarrier(numOfWorkers, new AggregatorThread());

        System.out.println("Spawning " + numOfWorkers + " worker threads to compute " + numPartialResults + " partial results each");
 
        for (int i = 0; i < numOfWorkers; i++) {
            Thread worker = new Thread(new NumberCruncherThread());
            worker.setName("Thread " + i);
            worker.start();
        }
    }

    class NumberCruncherThread implements Runnable {

        @Override
        public void run() {
            String thisThreadName = Thread.currentThread().getName();
            List<Integer> partialResult = new ArrayList<>();

            // Crunch some numbers and store the partial result
            for (int i = 0; i < numPartialResults; i++) {    
                Integer num = random.nextInt(10);
                System.out.println(thisThreadName + ": Crunching some numbers! Final result - " + num);
                partialResult.add(num);
            }

            finalResults.add(partialResult);
            try {
                System.out.println(thisThreadName  + " waiting for others to reach barrier.");
                cyclicBarrier.await(); // waits until all threads, parties, have invoked on this barrier
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    class AggregatorThread implements Runnable {

        @Override
        public void run() {

            String thisThreadName = Thread.currentThread().getName();

            System.out.println(thisThreadName + ": Computing sum of " + numOfWorkers  + " workers, having " + numPartialResults + " results each.");
            int sum = 0;

            for (List<Integer> threadResult : finalResults) {
                System.out.print(thisThreadName + " - adding ");
                for (Integer partialResult : threadResult) {
                    System.out.print(partialResult+" ");
                    sum += partialResult;
                }
                System.out.println();
            }
            System.out.println(thisThreadName + ": Final result = " + sum);
        }
    }

}