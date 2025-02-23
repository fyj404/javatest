package LockDemo.ReadWrite;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class HighConcurrencyRWLockExample {
    private static final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static final Lock readLock = rwLock.readLock();
    private static final Lock writeLock = rwLock.writeLock();
    private static final ConcurrentHashMap<String, String> database = new ConcurrentHashMap<>();

    // 模拟读取操作
    public static void readData(String key) {
        readLock.lock();
        try {
            String value = database.getOrDefault(key, "空");
            System.out.println(Thread.currentThread().getName() + " 读取: " + key + " = " + value);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            readLock.unlock();
        }
    }

    // 模拟写入操作
    public static void writeData(String key, String value) {
        writeLock.lock();
        try {
            database.put(key, value);
            System.out.println(Thread.currentThread().getName() + " 写入: " + key + " = " + value);
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // 提交多个读写任务模拟高并发
        for (int i = 0; i < 5; i++) {
            final int index = i;
            executor.submit(() -> writeData("key" + index, "value" + index));
        }

        for (int i = 0; i < 10; i++) {
            final int index = i % 5;
            executor.submit(() -> readData("key" + index));
        }

        executor.shutdown();
    }
}
