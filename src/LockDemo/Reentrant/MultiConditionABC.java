package LockDemo.Reentrant;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MultiConditionABC {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition conditionA = lock.newCondition();
    private static final Condition conditionB = lock.newCondition();
    private static final Condition conditionC = lock.newCondition();
    private static int state = 0; // 0:A, 1:B, 2:C

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> print("A", 0, conditionA, conditionB));
        Thread threadB = new Thread(() -> print("B", 1, conditionB, conditionC));
        Thread threadC = new Thread(() -> print("C", 2, conditionC, conditionA));

        threadA.start();
        threadB.start();
        threadC.start();
    }

    public static void print(String name, int targetState, Condition currentCondition, Condition nextCondition) {
        for (int i = 0; i < 5; i++) {
            lock.lock();
            try {
                while (state != targetState) {
                    currentCondition.await();
                }
                System.out.println(name);
                state = (state + 1) % 3;
                nextCondition.signal();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }
}

