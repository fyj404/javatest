package ForkJoin;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

class SumTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 1000; // 阈值
    private int start, end;

    public SumTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            int sum = 0;
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            int mid = (start + end) / 2;
            SumTask leftTask = new SumTask(start, mid);
            SumTask rightTask = new SumTask(mid + 1, end);

            /*
            leftTask.fork(); // 递归拆分
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();
               */

            leftTask.fork();   // 异步执行左任务
            rightTask.fork();  // 让右任务也异步执行

            int leftResult = leftTask.join();   // 等待左任务完成
            int rightResult = rightTask.join(); // 等待右任务完成



            return leftResult + rightResult;
        }
    }
}

public class ForkJoinExample {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new SumTask(1, 10000);
        int result = pool.invoke(task);
        System.out.println("Sum from 1 to 10000 = " + result);
    }
}

