

class ClassLockExample {
    //静态方法指的是一个类的所有对象共同一个
    //静态变量也是所有对象公用
    //非静态的不共用
    public static synchronized void staticMethod1() {
        System.out.println(Thread.currentThread().getName() + " is executing staticMethod1");
        try { Thread.sleep(3000); } catch (InterruptedException e) {}
    }

    public static void staticMethod2() {
        synchronized (ClassLockExample.class) { // 类锁
            System.out.println(Thread.currentThread().getName() + " is executing staticMethod2");
            try { Thread.sleep(3000); } catch (InterruptedException e) {}
        }
    }

    public static void main(String[] args) {
        new Thread(() -> ClassLockExample.staticMethod2(), "Thread-1").start();
        new Thread(() -> ClassLockExample.staticMethod2(), "Thread-1").start();
        //new Thread(() -> ClassLockExample.staticMethod2(), "Thread-2").start();
    }
}