import java.util.concurrent.*;

public class CyclicBarrierExample {
    public static void main(String[] args) {
        int threadCount = 3;
        CyclicBarrier barrier = new CyclicBarrier(threadCount, () -> {
            System.out.println("所有线程到达屏障，开始执行！");
        });

        for (int i = 0; i < threadCount; i++) {
            new Thread(new Worker(barrier), "线程-" + (i + 1)).start();
        }
    }

    static class Worker implements Runnable {
        private final CyclicBarrier barrier;

        public Worker(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " 正在准备...");
                Thread.sleep((long) (Math.random() * 3000)); // 模拟不同线程的准备时间
                System.out.println(Thread.currentThread().getName() + " 已准备好，等待其他线程...");

                barrier.await(); // 线程在这里等待所有线程到达屏障

                System.out.println(Thread.currentThread().getName() + " 开始执行任务！");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
