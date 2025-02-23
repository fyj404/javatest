package LockDemo.Reentrant;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionLockExample {
    //ReentrantLock：可重入锁，支持同一线程多次获取同一把锁。
    private static final ReentrantLock lock = new ReentrantLock(true); // 可重入锁
    //Condition：与锁配合实现等待/通知机制。
    private static final Condition condition = lock.newCondition();
    private static boolean ready = false;

    public static void main(String[] args) throws InterruptedException {
        Thread worker1 = new Thread(() -> worker(1));
        Thread worker2 = new Thread(() -> worker(2));
        Thread notifier = new Thread(ConditionLockExample::notifier);

        worker1.start();
        worker2.start();
        Thread.sleep(1000); // 等待1秒确保两个线程进入等待状态
        notifier.start();

        worker1.join();
        worker2.join();
        notifier.join();
    }

    private static void worker(int id) {
        lock.lock(); // 获取锁
        try {
            System.out.println("Worker " + id + " is waiting...");
            while (!ready) {
                condition.await(); // 等待通知 等待信号并释放锁，直到被唤醒
            }
            System.out.println("Worker " + id + " is proceeding...");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock(); // 释放锁
        }
    }

    private static void notifier() {
        lock.lock(); // 获取锁
        try {
            System.out.println("Notifier is working...");
            ready = true;
            condition.signalAll(); // 通知所有等待的线程
            System.out.println("Notifier has sent the signal.");
        } finally {
            lock.unlock(); // 释放锁
        }
    }
}