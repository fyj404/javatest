package LockDemo.Reentrant;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantExample {
    private final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantExample example = new ReentrantExample();
        System.out.println("测试 synchronized 可重入性");
        example.synchronizedMethod1();

        System.out.println("\n测试 ReentrantLock 可重入性");
        example.reentrantLockMethod1();
    }

    // synchronized 可重入示例
    public synchronized void synchronizedMethod1() {
        System.out.println("进入 synchronizedMethod1");
        synchronizedMethod2();
    }

    public synchronized void synchronizedMethod2() {
        System.out.println("进入 synchronizedMethod2 (可重入成功)");
    }

    // ReentrantLock 可重入示例
    public void reentrantLockMethod1() {
        lock.lock();
        try {
            System.out.println("进入 reentrantLockMethod1");
            reentrantLockMethod2();
        } finally {
            lock.unlock();
        }
    }

    public void reentrantLockMethod2() {
        lock.lock();
        try {
            System.out.println("进入 reentrantLockMethod2 (可重入成功)");
        } finally {
            lock.unlock();
        }
    }
}

