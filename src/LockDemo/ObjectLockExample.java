package LockDemo;

public class ObjectLockExample {
    private final Object lock = new Object();
    private int count = 0;
    public void synchronizedBlockMethod() {
        // 使用 synchronized 锁定代码块，锁定对象 lock
        //代码块锁
        synchronized (lock) {
            count++;
            System.out.println(Thread.currentThread().getName() + " 获取到锁");
            try {
                Thread.sleep(1000); // 模拟一些工作
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 执行完毕");
        }

        count+=5;
    }

    public static void main(String[] args) throws InterruptedException {
        ObjectLockExample example = new ObjectLockExample();

        // 创建两个线程同时调用 synchronizedBlockMethod
        Thread thread1 = new Thread(example::synchronizedBlockMethod, "线程1");
        Thread thread2 = new Thread(example::synchronizedBlockMethod, "线程2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
