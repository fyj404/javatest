package LockDemo.ReadWrite;

import java.util.concurrent.locks.*;

public class ReadWriteLockExample {
    private static final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static final Lock readLock = rwLock.readLock();
    private static final Lock writeLock = rwLock.writeLock();
    private static int value = 0;

    // 读取方法
    public static void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取 value: " + value);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
    }

    // 写入方法
    public static void write(int newValue) {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在写入 value: " + newValue);
            value = newValue;
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        // 多个读取线程
        for (int i = 0; i < 3; i++) {
            new Thread(ReadWriteLockExample::read, "Reader-" + i).start();
        }

        // 一个写入线程
        new Thread(() -> write(42), "Writer").start();
    }
}
