package LockDemo;

public class InstanceLockExample {
    // 同步实例方法
    public synchronized void synchronizedMethod() {
        System.out.println(Thread.currentThread().getName() + " 获取到锁");
        try {
            Thread.sleep(1000); // 模拟任务处理
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 执行完毕");
    }

    public static void main(String[] args) throws InterruptedException {
        InstanceLockExample example = new InstanceLockExample();
        //LockDemo.InstanceLockExample example2 = new LockDemo.InstanceLockExample();
        // 创建两个线程，调用同一个同步实例方法
        Thread thread1 = new Thread(example::synchronizedMethod, "线程1");
        Thread thread2 = new Thread(example::synchronizedMethod, "线程2");

        // 启动线程
        thread1.start();
        thread2.start();

        // 等待线程完成
        thread1.join();
        thread2.join();
    }
}