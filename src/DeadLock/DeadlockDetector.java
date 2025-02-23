package DeadLock;

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
