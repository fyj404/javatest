package LockDemo;

public class BlockExample {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void method1() {
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + " is executing method1");
            try { Thread.sleep(3000); } catch (InterruptedException e) {}
        }
    }

    public void method2() {
        synchronized (lock2) {
            System.out.println(Thread.currentThread().getName() + " is executing method2");
            try { Thread.sleep(3000); } catch (InterruptedException e) {}
        }
    }

    public static void main(String[] args) {
        BlockExample obj = new BlockExample();

        new Thread(() -> obj.method1(), "Thread-1").start();
        new Thread(() -> obj.method2(), "Thread-2").start();
    }
}
