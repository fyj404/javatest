package Thread;

public class UserThreadExample {
    public static void main(String[] args) {
        Thread userThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("非守护线程执行: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("非守护线程结束");
        });

        userThread.start();

        System.out.println("主线程结束，但 JVM 仍在运行，直到非守护线程完成");
    }
}
