package com.example.Exchanger;
import java.util.concurrent.Exchanger;

public class ProducerConsumerExample {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(new Producer(exchanger)).start();
        new Thread(new Consumer(exchanger)).start();
    }
}

class Producer implements Runnable {
    private Exchanger<String> exchanger;

    public Producer(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            String data = "生产的数据";
            System.out.println("生产者: 生成数据 -> " + data);
            String response = exchanger.exchange(data);
            System.out.println("生产者: 接收到消费者反馈 -> " + response);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    private Exchanger<String> exchanger;

    public Consumer(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000); // 模拟消费者准备时间
            String receivedData = exchanger.exchange("消费者的确认信息");
            System.out.println("消费者: 接收到生产者数据 -> " + receivedData);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
