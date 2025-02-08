import java.util.concurrent.CountDownLatch;

public class ConcurrentTest {
    public static void main(String[] args) throws InterruptedException {
        int threadCount = 5;
        CountDownLatch startSignal = new CountDownLatch(1);  // 控制所有线程同时开始
        CountDownLatch doneSignal = new CountDownLatch(threadCount);  // 等待所有线程完成

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    startSignal.await();  // 等待开始信号
                    System.out.println(Thread.currentThread().getName() + " 开始执行");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    doneSignal.countDown();  // 任务完成，计数 -1
                }
            }).start();
        }

        Thread.sleep(1000);  // 模拟主线程准备时间
        System.out.println("所有线程准备完毕，开始执行任务");
        startSignal.countDown();  // 所有线程同时开始

        doneSignal.await();  // 等待所有线程执行完毕
        System.out.println("所有线程执行完毕");
    }
}
