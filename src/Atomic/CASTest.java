package Atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class CASTest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        // 使用 CAS 进行更新
        boolean success = atomicInteger.compareAndSet(0, 10); // 期望值 0，更新为 10
        System.out.println("CAS 结果：" + success + "，当前值：" + atomicInteger.get());

        success = atomicInteger.compareAndSet(0, 20); // 期望值是 0，但当前值是 10，更新失败
        System.out.println("CAS 结果：" + success + "，当前值：" + atomicInteger.get());
    }
}
