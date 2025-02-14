package CountDownLatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        int threadCount = 3;
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 执行任务");
                    Thread.sleep(1000);  // 模拟任务执行时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();  // 任务完成后计数 -1
                }
            }).start();
        }

        latch.await();  // 等待所有任务完成
        System.out.println("所有任务执行完毕，主线程继续");
    }
}
