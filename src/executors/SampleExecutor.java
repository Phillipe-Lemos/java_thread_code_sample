package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class SampleExecutor {
    static AtomicLong atomicLong = new AtomicLong(0);

    /**
     * Here I synchronize the method using the synchronized keyword is needed because the plus(+) is not synchronized
     * To avoid synchronized keyword, we should use the public final long getAndAdd​(long delta) method.
     */
    static synchronized void incrementBy10() {
        atomicLong.getAndSet(atomicLong.get() + 10);
    }

    public static void main(String... args) throws Exception {
        ExecutorService s = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            s.submit(() -> incrementBy10());
        }
        s.shutdown();
        // here blocks untils all taks have finished or timeout occurs.
        s.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println(atomicLong.get());
    }
}
