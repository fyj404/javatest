package Atomic;

import java.util.concurrent.atomic.AtomicReference;

class SpinLock {
    private final AtomicReference<Thread> owner = new AtomicReference<>();

    public void lock() {
        Thread currentThread = Thread.currentThread();
        // 如果锁已被占用，则不断尝试获取
        while (!owner.compareAndSet(null, currentThread)) {
            // 自旋等待
        }
        /*
        Thread currentThread = Thread.currentThread();
        int spinCount = 0;
        while (!owner.compareAndSet(null, currentThread)) {
            spinCount++;
            if (spinCount > 1000) { // 自旋 1000 次后放弃
                try {
                    Thread.sleep(10); // 让出 CPU
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                spinCount = 0; // 重新开始计数
            }
        }*/
    }

    public void unlock() {
        Thread currentThread = Thread.currentThread();
        owner.compareAndSet(currentThread, null);
    }
}

public class SpinLockTest {
    private static final SpinLock spinLock = new SpinLock();

    public static void main(String[] args) {
        Runnable task = () -> {
            spinLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 获取到锁");
                Thread.sleep(1000); // 模拟业务操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                spinLock.unlock();
                System.out.println(Thread.currentThread().getName() + " 释放了锁");
            }
        };

        Thread t1 = new Thread(task, "线程1");
        Thread t2 = new Thread(task, "线程2");

        t1.start();
        t2.start();
    }
}
