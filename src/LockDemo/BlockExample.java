package LockDemo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BlockExample {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private final LinkedList<Integer> buffer = new LinkedList<>();
    public void method1() {
        //map2.put(lock1, map1);
        for(int i=1;i<=10;i++){
            synchronized (lock1) {
                buffer.add(i);
            }
            try { Thread.sleep(30); } catch (InterruptedException e) {}
        }

    }

    public void method2() {
        for(int i=1;i<=10;i++){
            synchronized (lock1) {
                if(buffer.isEmpty()==false){
                    Integer item=buffer.poll();
                }
            }
            try { Thread.sleep(30); } catch (InterruptedException e) {}
        }
    }

    public static void main(String[] args) {
        BlockExample obj = new BlockExample();

        new Thread(() -> obj.method1(), "Thread-1").start();
        new Thread(() -> obj.method2(), "Thread-2").start();
    }
}
