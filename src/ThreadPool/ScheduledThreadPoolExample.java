package ThreadPool;

import java.util.concurrent.*;

public class ScheduledThreadPoolExample {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

        // 延迟 3 秒后执行的任务
        scheduler.schedule(() -> {
            System.out.println("Task executed after 3 seconds!");
        }, 3, TimeUnit.SECONDS);

        // 每隔 2 秒执行一次的任务
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Task executed every 2 seconds!");
        }, 5, 2, TimeUnit.SECONDS);
    }
}
