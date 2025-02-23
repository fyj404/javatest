package LockDemo;

public class SynchronizedTryExample {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " 获得锁，执行中...");
                try {
                    Thread.sleep(5000); // 占用锁5秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "线程-1");

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 尝试获取锁...");
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " 获取锁成功！");
            }
        }, "线程-2");

        t1.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        t2.start();
    }
}

