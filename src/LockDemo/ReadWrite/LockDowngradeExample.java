package LockDemo.ReadWrite;

import java.util.concurrent.locks.*;

public class LockDowngradeExample {
    private static final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static final Lock readLock = rwLock.readLock();
    private static final Lock writeLock = rwLock.writeLock();
    private static int value = 0;

    public static void lockDowngrade() {
        writeLock.lock();
        try {
            value = 100;
            System.out.println("写操作完成，值设置为：" + value);

            // 锁降级：在持有写锁时获取读锁
            readLock.lock();
            System.out.println("读取刚写入的值：" + value);
        } finally {
            readLock.unlock();
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(LockDowngradeExample::lockDowngrade).start();
    }
}
