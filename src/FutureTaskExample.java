import java.util.concurrent.*;

public class FutureTaskExample {
    public static void main(String[] args) throws Exception {
        // 创建一个 Callable 任务
        Callable<Integer> task = () -> {
            Thread.sleep(2000); // 模拟耗时计算
            return 42;
        };

        // 使用 FutureTask 来包装 Callable
        FutureTask<Integer> futureTask = new FutureTask<>(task);

        // 使用线程池执行 FutureTask
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(futureTask);

        // 主线程可以做其他事情
        System.out.println("主线程继续执行...");

        // 获取任务结果（阻塞等待）
        Integer result = futureTask.get();
        System.out.println("任务结果: " + result);

        // 关闭线程池
        executor.shutdown();
    }
}