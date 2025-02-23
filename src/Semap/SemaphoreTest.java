package Semap;

import java.util.concurrent.Semaphore;

class Task implements Runnable {
    private Semaphore semaphore;

    public Task(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire(); // 获取信号量
            System.out.println(Thread.currentThread().getName() + " 获取到资源，开始执行任务...");
            Thread.sleep(2000); // 模拟任务执行
            System.out.println(Thread.currentThread().getName() + " 释放资源");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(); // 释放信号量
        }
    }
}

public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2); // 最多允许 2 个线程同时执行

        for (int i = 1; i <= 5; i++) {
            new Thread(new Task(semaphore), "线程" + i).start();
        }
    }
}

