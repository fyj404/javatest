package Thread;

public class DemoInter {

    public static class MyThread implements Runnable {
        @Override
        public void run() {
            System.out.println("MyThread");
        }
    }

    public static void main(String[] args) {

        //接口继承
        new Thread(new MyThread()).start();

        // Java 8 函数式编程，可以省略MyThread类
        //lambda表达式 匿名表达式 匿名函数
        new Thread(() -> {
            System.out.println("Java 8 匿名内部类");
        }).start();
    }
}