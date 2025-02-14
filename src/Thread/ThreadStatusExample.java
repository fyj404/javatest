package Thread;

public class ThreadStatusExample {
    //
    public static void main(String[] args) {
        // 创建一个线程
        Thread thread = new Thread(() -> {
            try {
                for(int j=0; j<100; j++){
                    Thread.sleep(10); // 模拟一些工作
                    for(int i=1;i<=10000000;i++){
                        float x=i*i;
                        x=x*x;

                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 获取线程状态，初始状态是 NEW
        System.out.println("线程初始状态: " + thread.getState());

        // 启动线程
        thread.start();

        // 在执行过程中获取线程状态
        while (thread.isAlive()) {
            System.out.println("线程当前状态: " + thread.getState());
            try {
                Thread.sleep(10); // 每100毫秒检查一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 线程终止后的状态
        System.out.println("线程结束后状态: " + thread.getState());
    }
}
