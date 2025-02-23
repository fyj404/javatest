package LockDemo;

public class SynchronizedInterruptExample {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        // 线程1持有锁5秒
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("线程-1 获取锁，执行中...");
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    System.out.println("线程-1 被中断！");
                }
            }
        });
        t1.start();

        // 线程2尝试获取锁，并在2秒后中断
        Thread t2 = new Thread(() -> {
            System.out.println("线程-2 尝试获取锁...");
            synchronized (lock) {
                System.out.println("线程-2 获取锁成功！");
            }
        });
        t2.start();

        // 在2秒后中断线程2
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("主线程：中断 线程-1");
                t1.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}