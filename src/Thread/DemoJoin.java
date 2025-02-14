package Thread;

public class DemoJoin {
    public static class MyThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
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
        catch (IllegalThreadStateException e){
            System.out.println("IllegalThreadStateException");
            e.printStackTrace();
        }
        System.out.println("MyThread");
    }

    public static void main(String[] args) {

        Thread myThread = new Demo.MyThread();
        //尝试捕获抛出的异常
        try {
            myThread.start();
            throw new Exception();
            //System.out.println("access");
        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
        }

        System.out.println("wait finish");

    }
}
