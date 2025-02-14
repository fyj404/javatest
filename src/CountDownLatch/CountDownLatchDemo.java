package CountDownLatch;

import java.util.concurrent.CountDownLatch;

class Worker implements Runnable {
    private final CountDownLatch startLatch;
    private final CountDownLatch doneLatch;
    private final String name;
    public Worker(String name,CountDownLatch startLatch, CountDownLatch doneLatch) {
        this.startLatch = startLatch;
        this.doneLatch = doneLatch;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            startLatch.await();  // 等待主线程的启动信号
            System.out.println(this.name+" | "+System.nanoTime() + " | " + Thread.currentThread().getName() + " 开始执行任务");
            Thread.sleep((long) (Math.random() * 1000));  // 模拟任务执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            doneLatch.countDown();  // 任务完成，计数 -1
        }
    }
}

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        int threadCount = 5;
        CountDownLatch startLatch = new CountDownLatch(1);  // 控制所有线程同时开始
        CountDownLatch doneLatch = new CountDownLatch(threadCount);  // 计数所有线程完成

        for (int i = 0; i < threadCount; i++) {
            new Thread(new Worker(String.valueOf(i),startLatch, doneLatch)).start();
        }

        Thread.sleep(1000);  // 让所有线程都准备好
        System.out.println("所有线程准备完毕，开始执行任务");
        startLatch.countDown();  // 让所有线程同时开始

        doneLatch.await();  // 等待所有线程执行完毕
        System.out.println("所有线程执行完毕");
    }
}
