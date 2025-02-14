package LockDemo;

public class ObjectLock {
    public synchronized void method1() {  // 实例方法加锁
        System.out.println(Thread.currentThread().getName() + " is executing method1");
        try { Thread.sleep(3000); } catch (InterruptedException e) {}
    }

    public void method2() {
        synchronized (this) {  // 代码块加锁
            System.out.println(Thread.currentThread().getName() + " is executing method2");
            try { Thread.sleep(3000); } catch (InterruptedException e) {}
        }
    }

    public static void main(String[] args) {
        ObjectLock obj = new ObjectLock();

        new Thread(() -> obj.method1(), "Thread-1").start();
        new Thread(() -> obj.method2(), "Thread-2").start();
    }
}
