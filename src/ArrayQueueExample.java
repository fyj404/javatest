package com.example.ArrayQueueExample;
import java.util.concurrent.*;

class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                queue.put(i);  // 插入元素，如果队列满，线程阻塞

                boolean success = queue.offer(i);  // 尝试向队列插入元素 如果队列已满，不会阻塞，而是返回 false。 如果队列已满，返回 false

                int remainingCapacity = queue.remainingCapacity();  // 查看队列剩余容量
                System.out.println("生产者生产: " + i+" 剩余空间: "+remainingCapacity);

                Thread.sleep(500);  // 模拟生产时间
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                //Integer item = queue.take();  // 获取元素，如果队列空，线程阻塞

                Integer item = queue.poll();  // 如果队列为空，返回 null

                if(item != null) {
                    System.out.println("消费者消费: " + item);
                    Thread.sleep(1000);  // 模拟消费时间
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class ArrayQueueExample {
    public static void main(String[] args) {
        //BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);  // 队列容量为5
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);
        Thread producerThread = new Thread(new Producer(queue));
        Thread consumerThread = new Thread(new Consumer(queue));

        producerThread.start();
        consumerThread.start();
    }
}
