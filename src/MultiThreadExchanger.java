import java.util.concurrent.Exchanger;

public class MultiThreadExchanger {
    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<>();

        // 线程A1和A2交换数据
        new Thread(() -> {
            try {
                int data = 100;
                System.out.println("线程A1: 发送 -> " + data);
                int received = exchanger.exchange(data);
                System.out.println("线程A1: 接收到 -> " + received);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                int data = 200;
                System.out.println("线程A2: 发送 -> " + data);
                int received = exchanger.exchange(data);
                System.out.println("线程A2: 接收到 -> " + received);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
