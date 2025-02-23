package LockDemo.ReadWrite;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class RWLockReadWriteTest {
    private static final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static final Lock readLock = rwLock.readLock();
    private static final Lock writeLock = rwLock.writeLock();
    private static String sharedData = "初始数据";

    public static void readData() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 开始读取...");
            Thread.sleep(3500); // 模拟读取时间
            System.out.println(Thread.currentThread().getName() + " 读取到: " + sharedData);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName() + " 读取结束");
        }
    }

    public static void writeData(String newData) {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 开始写入...");
            Thread.sleep(300); // 模拟写入时间
            sharedData = newData;
            System.out.println(Thread.currentThread().getName() + " 写入完成: " + sharedData);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            writeLock.unlock();
            System.out.println(Thread.currentThread().getName() + " 写入结束");
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // 先启动读线程
        executor.submit(() -> readData());

        // 再启动写线程，看看是否可以在读期间写入
        executor.submit(() -> writeData("新数据"));

        // 再启动另一个读线程，验证是否能读取到最新数据
        executor.submit(() -> readData());

        executor.shutdown();
    }
}

