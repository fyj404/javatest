import java.time.DateTimeException;
import java.util.LinkedList;

public class WaitNotifyExample {
    private static final int MAX_CAPACITY = 5;  // 缓冲区最大容量
    private final LinkedList<Integer> buffer = new LinkedList<>();
    private final Object lock = new Object();

    // 生产者
    class Producer implements Runnable {
        @Override
        public void run() {
            int value = 0;
            while (true) {
                synchronized (lock) {
                    while (buffer.size() == MAX_CAPACITY) {  // 缓冲区满，等待消费者消费
                        try {
                            System.out.println("缓冲区已满，生产者等待...");
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    buffer.add(value);
                    System.out.println("生产者生产: " + value);
                    value++;
                    lock.notify();  // 唤醒消费者
                }
                try {
                    Thread.sleep(2000);  // 模拟生产耗时
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 消费者
    class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (buffer.isEmpty()) {  // 缓冲区为空，等待生产者生产
                        try { //如果括号内抛出异常，则直接跳到catch部分处理
                            System.out.println("缓冲区为空，消费者等待...");
                            lock.wait();
                        }catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    int value = buffer.poll();
                    System.out.println("消费者消费: " + value);
                    lock.notify();  // 唤醒生产者
                }
                try {
                    Thread.sleep(500);  // 模拟消费耗时
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        WaitNotifyExample pc = new WaitNotifyExample();
        new Thread(pc.new Producer()).start();
        new Thread(pc.new Consumer()).start();
    }
}
