package Semap;

import java.util.concurrent.Semaphore;

class OrderedTask {
    private final Semaphore semaphore1 = new Semaphore(1); // 第一个任务直接执行
    private final Semaphore semaphore2 = new Semaphore(0); // 第二个任务等待
    private final Semaphore semaphore3 = new Semaphore(0); // 第三个任务等待

    public void task1() {
        try {
            semaphore1.acquire();
            System.out.println("执行任务 1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore2.release(); // 释放任务 2
        }
    }

    public void task2() {
        try {
            semaphore2.acquire();
            System.out.println("执行任务 2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore3.release(); // 释放任务 3
        }
    }

    public void task3() {
        try {
            semaphore3.acquire();
            System.out.println("执行任务 3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class OrderedExecution {
    public static void main(String[] args) {
        OrderedTask task = new OrderedTask();

        new Thread(task::task3).start();
        new Thread(task::task2).start();
        new Thread(task::task1).start();
    }
}
