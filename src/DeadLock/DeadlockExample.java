package DeadLock;

class DeadlockExample {
    private static final Object resource1 = new Object();
    private static final Object resource2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (resource1) { // 持有 resource1
                System.out.println("Thread 1 locked resource 1");
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                synchronized (resource2) { // 尝试获取 resource2
                    System.out.println("Thread 1 locked resource 2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resource2) { // 持有 resource2
                System.out.println("Thread 2 locked resource 2");
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                synchronized (resource1) { // 尝试获取 resource1
                    System.out.println("Thread 2 locked resource 1");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
