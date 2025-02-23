package LockDemo.Reentrant;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MultiConditionExample {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition conditionA = lock.newCondition();
    private static final Condition conditionB = lock.newCondition();
    private static boolean isATurn = true; // 用于标识当前是否轮到 A 线程

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> printA(5));
        Thread threadB = new Thread(() -> printB(5));

        threadA.start();
        threadB.start();
    }

    public static void printA(int times) {
        lock.lock();
        try {
            for (int i = 0; i < times; i++) {
                while (!isATurn) {
                    conditionA.await(); // 等待 B 线程完成
                }
                System.out.println("A");
                isATurn = false;
                conditionB.signal(); // 通知 B 线程
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public static void printB(int times) {
        lock.lock();
        try {
            for (int i = 0; i < times; i++) {
                while (isATurn) {
                    conditionB.await(); // 等待 A 线程完成
                }
                System.out.println("B");
                isATurn = true;
                conditionA.signal(); // 通知 A 线程
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}

