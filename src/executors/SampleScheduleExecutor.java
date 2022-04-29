package executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SampleScheduleExecutor {

    public static void main(String... args) throws Exception {
        ScheduledExecutorService scheduleExecutor = Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> result = scheduleExecutor.schedule(() -> {
            System.out.println("I was called");
        }, 5, TimeUnit.SECONDS);
        System.out.println( "The remains time in microsecods for the task runs : " + result.getDelay(TimeUnit.MICROSECONDS));
        scheduleExecutor.shutdown();
    }

}
