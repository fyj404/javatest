package ForkJoin;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

class PrintTask extends RecursiveAction {
    private int start, end;
    private static final int THRESHOLD = 5;

    public PrintTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            for (int i = start; i <= end; i++) {
                System.out.println(Thread.currentThread().getName() + " prints: " + i);
            }
        } else {
            int mid = (start + end) / 2;
            PrintTask leftTask = new PrintTask(start, mid);
            PrintTask rightTask = new PrintTask(mid + 1, end);
            invokeAll(leftTask, rightTask);
        }
    }
}

public class ForkJoinActionExample {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        PrintTask task = new PrintTask(1, 20);
        pool.invoke(task);
    }
}
