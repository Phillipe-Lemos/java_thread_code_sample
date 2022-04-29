package issues;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RaceConditionExample {

    private static AtomicInteger unSyncValue;

    private static AtomicInteger syncVaue;

    //Although AtomicInteger is a synchronized class, the plus operation is not, and the 
    //resolution of value.get() + 10 and value.get() - 3 is not thread-safe.
    //it should be used the getAndAdd(), getAndDecreament().....
    private static void triggerRaceCondition() throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i = 0; i < 10; i++){
            service.submit( () -> { 
                unSyncValue.getAndSet( unSyncValue.get() + 10 );
                syncVaue.addAndGet(10);
            });
        }
        
        for(int i = 0; i < 10; i++){
            service.submit( () -> {
                unSyncValue.getAndSet( unSyncValue.get() - 3);
                syncVaue.getAndAdd(-3); 
            });
        } 
        service.shutdown();
        service.awaitTermination(20, TimeUnit.SECONDS);
        System.out.println(String.format("Unsynch value : %d and synch value %d", unSyncValue.get(), syncVaue.get()));
    }


    public static void main(String...args) throws Exception{
        for(int i = 0; i < 20; i++){
            unSyncValue = new AtomicInteger(0);
            syncVaue  = new AtomicInteger(0);
             triggerRaceCondition();

        }
    }
}
