import java.util.concurrent.*;

class DelayedTask implements Delayed {
    private final String name;
    private final long startTime;

    public DelayedTask(String name, long delayTime) {
        this.name = name;
        this.startTime = System.currentTimeMillis() + delayTime;
    }

    // 返回任务剩余的延迟时间
    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    // 比较延迟时间，用于在队列中排序
    @Override
    public int compareTo(Delayed o) {
        if (this.startTime < ((DelayedTask) o).startTime) {
            return -1;
        } else if (this.startTime > ((DelayedTask) o).startTime) {
            return 1;
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "DelayedTask{name='" + name + "', startTime=" + startTime + '}';
    }
}

class TaskProducer implements Runnable {
    private final DelayQueue<DelayedTask> queue;

    public TaskProducer(DelayQueue<DelayedTask> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            // 添加延迟任务到队列中
            queue.put(new DelayedTask("Task-1", 3000)); // 延迟 3 秒
            System.out.println("Added Task-1 with 3 seconds delay.");
            Thread.sleep(1000);

            queue.put(new DelayedTask("Task-2", 1000)); // 延迟 1 秒
            System.out.println("Added Task-2 with 1 second delay.");
            Thread.sleep(1000);

            queue.put(new DelayedTask("Task-3", 5000)); // 延迟 5 秒
            System.out.println("Added Task-3 with 5 seconds delay.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class TaskConsumer implements Runnable {
    private final DelayQueue<DelayedTask> queue;

    public TaskConsumer(DelayQueue<DelayedTask> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 获取延迟队列中的任务，直到延迟时间到
                DelayedTask task = queue.take(); // take 会阻塞直到队列中任务的延迟时间到
                System.out.println("Consumed: " + task.getName() + " at " + System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class DelayQueueExample {
    public static void main(String[] args) {
        DelayQueue<DelayedTask> queue = new DelayQueue<>();

        // 创建生产者和消费者线程
        Thread producerThread = new Thread(new TaskProducer(queue));
        Thread consumerThread = new Thread(new TaskConsumer(queue));

        // 启动生产者和消费者线程
        producerThread.start();
        consumerThread.start();
    }
}
