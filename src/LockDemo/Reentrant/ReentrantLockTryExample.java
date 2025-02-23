package LockDemo.Reentrant;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class ReentrantLockTryExample {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        // 1. 正常锁定示例
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("线程-1 获取锁，执行中...");
                Thread.sleep(5000); // 占用锁5秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        // 2. tryLock() 示例
        new Thread(() -> {
            try {
                System.out.println("线程-2 尝试使用 tryLock() 获取锁...");
                if (lock.tryLock()) {
                    try {
                        System.out.println("线程-2 获取锁成功！");
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("线程-2 获取锁失败！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // 3. tryLock() + 超时机制示例
        new Thread(() -> {
            try {
                System.out.println("线程-3 尝试使用 tryLock(2秒)...");
                if (lock.tryLock(2, TimeUnit.SECONDS)) {
                    try {
                        System.out.println("线程-3 超时前获取锁成功！");
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("线程-3 在2秒内未获取到锁，放弃！");
                }
            } catch (InterruptedException e) {
                System.out.println("线程-3 被中断！");
            }
        }).start();

        // 4. lockInterruptibly() 示例
        Thread t4 = new Thread(() -> {
            try {
                System.out.println("线程-4 使用 lockInterruptibly() 尝试获取锁...");
                lock.lockInterruptibly();
                try {
                    System.out.println("线程-4 获取锁成功！");
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                System.out.println("线程-4 在等待时被中断！");
            }
        });
        t4.start();

        // 在1秒后中断线程4
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("主线程：中断 线程-4");
                t4.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
