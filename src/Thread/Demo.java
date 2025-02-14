package Thread;

public class Demo {
    public static class MyThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("MyThread");
        }
    }
    public  void test() {
        try {

            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("MyThread");
    }

    public static void main(String[] args) {
        //静态方法不能调用非静态方法
        //static不能调用没有staticde
        //如果想调用需要先创建对象
        Demo d=new Demo();


        Thread myThread = new MyThread();
        System.out.println("wait");
        //将线程交给cpu去处理，从new状态变成runable
        //进入等待cpu资源，如果有资源就会调用它的run函数
        //myThread.start();
        d.test();
        System.out.println("wait finish");

    }
}
