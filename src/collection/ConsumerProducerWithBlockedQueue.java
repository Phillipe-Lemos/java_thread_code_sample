package collection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class ConsumerProducerWithBlockedQueue {

    private static int MAX = 20;

    private BlockingQueue<Integer> queue = new LinkedBlockingDeque<>();

    /**
     * A simple producer that add 20 items in a BlockingQueue.
     */
    Callable<Boolean> producer = () -> {
        System.out.println("Starting prodcuer");
        for (int i = 0; i < MAX; i++) {
            System.out.println(
                    String.format("Produced  value : %d - by thread : %s", i, Thread.currentThread().getName()));
            queue.offer(i);
        }
        return true;
    };

    /**
     * A simple consumer that removes itens from a BlockingQueue.
     */
    Callable<Boolean> consumer = () -> {
        System.out.println("Starting consumer");
        do {
            int consumed = queue.take();
            System.out.println(
                    String.format("Consumed  value : %d - by thread : %s", consumed, Thread.currentThread().getName()));
        } while (!queue.isEmpty());
        return true;
    };

    public void manage() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(producer);
        executorService.submit(consumer);
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println(" Size of queue : " + queue.size());
        System.out.println(" Queue : " + queue);
    }

    public static void main(String... args) throws Exception {
        ConsumerProducerWithBlockedQueue cp = new ConsumerProducerWithBlockedQueue();
        cp.manage();
    }
}
