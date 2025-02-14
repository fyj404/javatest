package Thread;

public class ThreadBlockExample {
    private static final Object lock=new Object();
    public static void main(String[] args) {
        // 创建第一个线程，它尝试获取锁
        Thread thread1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("线程1：获取锁并开始工作");
                    Thread.sleep(20000); // 模拟任务
                    System.out.println("线程1：工作完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 创建第二个线程，它会在第一个线程持有锁时等待
        Thread thread2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("线程2：获取锁并开始工作");
                    Thread.sleep(2000); // 模拟任务
                    System.out.println("线程2：工作完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 启动第一个线程
        thread1.start();

        // 给第一个线程一些时间去获取锁
        try {
            Thread.sleep(500); // 让线程1先获取锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 获取线程状态（注意此时线程2还没开始执行）
        System.out.println("线程2状态 (应该是 BLOCKED): " + thread2.getState());

        // 启动第二个线程
        thread2.start();
        try {
            Thread.sleep(500); // 让线程2尝试去获取锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程2状态 (应该是 BLOCKED): " + thread2.getState());
        // 等待线程1和线程2执行完毕
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 打印线程2执行完毕后的状态
        System.out.println("线程2状态 (应该是 TERMINATED): " + thread2.getState());
    }
}
