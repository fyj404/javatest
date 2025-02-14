package ThreadPool;

import java.util.concurrent.*;

public class FixedThreadPoolWithQueue {
    public static void main(String[] args) {
        // 使用自定义线程池，限制队列最多存 5 个任务
        ExecutorService executor = new ThreadPoolExecutor(
                3, 3, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5), // 任务队列最多存 5 个任务
                new ThreadPoolExecutor.AbortPolicy() // 队列满时拒绝新任务
        );

        for (int i = 1; i <= 20; i++) {
            final int taskId = i;
            try {
                executor.submit(() -> {
                    System.out.println("Task " + taskId + " executed by " + Thread.currentThread().getName());
                    try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
                });
            } catch (RejectedExecutionException e) {
                System.out.println("Task " + taskId + " 被拒绝！");
            }
        }

        executor.shutdown();
    }
}
