package Semap;

import java.util.concurrent.Semaphore;

class APIRequestHandler {
    private final Semaphore semaphore = new Semaphore(3); // 允许 3 个请求并发

    public void handleRequest(String request) {
        try {
            semaphore.acquire();
            System.out.println("处理请求：" + request);
            Thread.sleep(1000); // 模拟接口处理时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}

public class RateLimiter {
    public static void main(String[] args) {
        APIRequestHandler apiHandler = new APIRequestHandler();

        for (int i = 1; i <= 10; i++) {
            int requestID = i;
            new Thread(() -> apiHandler.handleRequest("Request-" + requestID)).start();
        }
    }
}
