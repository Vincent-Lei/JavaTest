package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static void main(String[] args) {
        limitThreadStream();
    }


//    private void semaphore() {
//        final Semaphore semaphore = new Semaphore(3);
//        ExecutorService executors = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < 10; i++) {
//            executors.submit(() -> {
//                try {
//                    semaphore.acquire();
//                    System.out.println("--semaphore--" + Thread.currentThread().getId());
//                    Thread.sleep(1000);
//                } catch (Exception e) {
//                } finally {
//                    semaphore.release();
//                }
//            });
//        }
//
//    }

    private static void limitThreadStream() {
        ExecutorService executors = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            executors.submit(() -> {
                runRunnable(() -> {
                    System.out.println(Thread.currentThread().getName() + " run event");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            });
        }
    }

    private static final int MAX_RUNNER_COUNT = 5;
    private static final Semaphore semaphoreRunner = new Semaphore(MAX_RUNNER_COUNT);

    private static void runRunnable(Runnable runnable) {
        try {
            semaphoreRunner.acquire();
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            semaphoreRunner.release();
        }
    }


}
