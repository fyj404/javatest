import java.util.concurrent.*;
class Task implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        // 模拟计算需要一秒
        Thread.sleep(1000);
        return 2;
    }

    public static void main(String[] args) {
        // 创建线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        Task task = new Task();
        Future<Integer> result = executor.submit(task);

        try {
            // 推荐使用带超时参数的 get 方法
            System.out.println("计算结果: " + result.get(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            System.err.println("当前线程被中断");
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            System.err.println("计算任务执行异常: " + e.getCause());
        } catch (TimeoutException e) {
            System.err.println("获取结果超时");
        } finally {
            // 关闭线程池
            executor.shutdown();
        }
    }
}