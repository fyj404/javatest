# 死锁

## 1. 什么是死锁？
死锁是指两个或多个线程相互等待对方释放资源，导致它们都无法继续执行，形成永久阻塞的状态。

举个例子：

线程 A 持有资源 R1，想要获取资源 R2。
线程 B 持有资源 R2，想要获取资源 R1。
A 等 B 释放，B 也等 A 释放，结果相互等待，永远不会结束。

## 代码示例
```java
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

```

## 死锁的四个必要条件（不可同时满足才能避免死锁）
根据 “Coffman 死锁条件”，死锁发生需要满足以下四个条件：
互斥（Mutual Exclusion）	资源一次只能被一个线程使用
占有并等待（Hold and Wait）	线程占有一个资源的同时，等待另一个资源
不可抢占（No Preemption）	资源不能被强制回收，只能由持有线程释放
循环等待（Circular Wait）	存在 A → B → C → A 的等待链

## 如何检测 Java 死锁
使用 ThreadMXBean 进行检测
```java
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class DeadlockDetector {
    public static void main(String[] args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        new Thread(() -> {
            while (true) {
                long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
                if (deadlockedThreads != null && deadlockedThreads.length > 0) {
                    System.out.println("Deadlock detected!");
                    for (long id : deadlockedThreads) {
                        ThreadInfo threadInfo = threadMXBean.getThreadInfo(id);
                        System.out.println("Thread: " + threadInfo.getThreadName() + " is deadlocked.");
                    }
                    break;
                }
                try { Thread.sleep(5000); } catch (InterruptedException ignored) {}
            }
        }).start();
    }
}

```
## 避免死锁
避免嵌套锁
尽量减少锁的数量，避免多线程同时持有多个锁。


锁定顺序一致
确保所有线程获取锁的顺序一致，避免循环等待

使用 tryLock() 代替 synchronized

使用 Lock + Condition 代替 synchronized

# CAS与原子操作

CAS的全称是：比较并交换（Compare And Swap）。在CAS中，有这样三个值：

V：要更新的变量(var)

E：预期值(expected)

N：新值(new)

CAS 存在问题：
ABA 问题（可用 AtomicStampedReference 解决）

高并发下自旋消耗 CPU（可用指数退避优化）

不能保证多个变量的原子性（可用 synchronized 保护临界区）

# 自旋锁（SpinLock）详解
自旋锁（SpinLock） 是一种轻量级锁，当线程尝试获取锁但锁已被占用时，它不会进入 阻塞状态，而是不断循环检查（自旋）锁是否可用。

特点：

线程不会阻塞，而是持续尝试获取锁。
适用于锁的持有时间短的场景，可以避免线程切换的开销。
适用于多核 CPU，可以减少上下文切换开销。