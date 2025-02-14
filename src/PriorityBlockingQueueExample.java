package com.example.PriorityBlockingQueue;
import java.util.concurrent.*;

class Task implements Comparable<Task> {
    private final int priority;
    private final String name;

    public Task(int priority, String name) {
        this.priority = priority;
        this.name = name;
    }

    // 实现 compareTo 方法，根据优先级排序，优先级较高的任务排前
    @Override
    public int compareTo(Task other) {
        return Integer.compare(other.priority, this.priority);  // 优先级高的排前
    }

    @Override
    public String toString() {
        return "Task{name='" + name + "', priority=" + priority + "}";
    }
}

class Producer implements Runnable {
    private final BlockingQueue<Task> queue;

    public Producer(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                Task task = new Task(i, "Task-" + i);
                queue.put(task);  // 将任务放入队列，若队列满则阻塞
                System.out.println("生产者生产: " + task);
                Thread.sleep(50);  // 模拟生产时间
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private final BlockingQueue<Task> queue;

    public Consumer(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                Task task = queue.take();  // 从队列取出任务，若队列空则阻塞
                System.out.println("消费者消费: " + task);
                Thread.sleep(1000);  // 模拟消费时间
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class PriorityBlockingQueueExample {
    public static void main(String[] args) {
        // 创建一个优先级队列，默认按优先级排序
        BlockingQueue<Task> queue = new PriorityBlockingQueue<>();

        // 创建生产者和消费者线程
        Thread producerThread = new Thread(new Producer(queue));
        Thread consumerThread = new Thread(new Consumer(queue));

        // 启动生产者和消费者线程
        producerThread.start();
        try {
            Thread.sleep(200);  // 模拟消费时间
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        consumerThread.start();
    }
}
