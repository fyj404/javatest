import java.util.concurrent.*;

public class CyclicBarrierWithTask {
    public static void main(String[] args) {
        int threadCount = 4;
        CyclicBarrier barrier = new CyclicBarrier(threadCount, () -> {
            System.out.println("=== 阶段任务执行（如日志记录、数据汇总等） ===");
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
                System.out.println(Thread.currentThread().getName() + " 执行任务 A");
                Thread.sleep((long) (Math.random() * 2000));
                System.out.println(Thread.currentThread().getName() + " 到达屏障点 1");

                barrier.await(); // 第一阶段屏障点

                System.out.println(Thread.currentThread().getName() + " 执行任务 B");
                Thread.sleep((long) (Math.random() * 2000));
                System.out.println(Thread.currentThread().getName() + " 到达屏障点 2");

                barrier.await(); // 第二阶段屏障点

                System.out.println(Thread.currentThread().getName() + " 执行任务 C");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
