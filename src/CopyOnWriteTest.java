import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class CopyOnWriteTest {
    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        // 读线程1（读取旧数据）
        new Thread(() -> {
            System.out.println("读线程1 开始");
            for (Integer num : list) {
                System.out.println("读线程1 读到: " + num);
                try {
                    Thread.sleep(100);  // 模拟延迟
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(50); // 确保读线程1已经开始
        int threadCount = 1;
        CountDownLatch latch = new CountDownLatch(threadCount);
        // 写线程（添加新数据）
        new Thread(() -> {
            System.out.println("写线程 添加 4");
            list.add(4);
            System.out.println("写线程 完成");
            latch.countDown();
        }).start();

        //Thread.sleep(200); // 等待写入完成
        latch.await();
        // 读线程2（读取最新数据）
        new Thread(() -> {
            System.out.println("读线程2 开始");
            for (Integer num : list) {
                System.out.println("读线程2 读到: " + num);
            }
        }).start();
    }
}
