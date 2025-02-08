import java.util.concurrent.Exchanger;

public class ExchangerExample {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            try {
                String dataA = "线程A的数据";
                System.out.println("线程A: 发送数据 -> " + dataA);
                String receivedData = exchanger.exchange(dataA);
                System.out.println("线程A: 接收到数据 -> " + receivedData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                String dataB = "线程B的数据";
                System.out.println("线程B: 发送数据 -> " + dataB);
                String receivedData = exchanger.exchange(dataB);
                System.out.println("线程B: 接收到数据 -> " + receivedData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
