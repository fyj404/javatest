package LockDemo.Reentrant;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class InterruptibleConditionExample {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Thread waitThread = new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + ": 正在等待信号...");
                condition.await(); // 等待信号，可被中断
                System.out.println(Thread.currentThread().getName() + ": 收到信号，继续执行");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + ": 等待被中断，线程退出");
            } finally {
                lock.unlock();
            }
        }, "WaitThread");

        Thread interruptThread = new Thread(() -> {
            try {
                Thread.sleep(2000); // 等待2秒再发出中断
                System.out.println(Thread.currentThread().getName() + ": 发出中断信号");
                waitThread.interrupt(); // 中断等待线程
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "InterruptThread");

        waitThread.start();
        interruptThread.start();

        waitThread.join();
        interruptThread.join();
        System.out.println("程序结束");
    }
}
