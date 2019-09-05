package concurrent;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        consume(30);
//        future();
//        completableFuture();
        completionService();
    }

    private static final AtomicInteger atomicInteger = new AtomicInteger(1);
    private static final AtomicInteger rejectInteger = new AtomicInteger(1);

    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 5, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(20), ((Runnable r) -> {
        Thread thread = new Thread(r);
        thread.setName("thread-" + atomicInteger.getAndIncrement());
        return thread;

    }), (r, executor) -> System.out.println("reject runnable count：" + rejectInteger.getAndIncrement()));


    private static void consume(int taskCount) {
        for (int i = 0; i < taskCount; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " consume task");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void future() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "this is result";
            }
        });

        try {
            String str = future.get();
            System.out.println(str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    private static void completableFuture() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "result1";
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "result2";
        });
        CompletableFuture<String> f3 = f1.thenCombine(f2, (s, s2) -> {
            System.out.println("Combine  result");
            return s + "-" + s2;
        });

        try {
            System.out.println(f3.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void completionService() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);
        int taskCount = 3;
        for (int i = 0; i < taskCount; i++) {
            final int index = i;
            completionService.submit(() -> {
                System.out.println("task " + index);
                Thread.sleep(1000);
                return index + 1;
            });
        }

        for (int i = 0; i < taskCount; i++) {
            Integer integer = completionService.take().get();
            System.out.println("integer： " + integer);
        }
    }
}
