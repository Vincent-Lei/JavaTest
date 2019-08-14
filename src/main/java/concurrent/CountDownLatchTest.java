package concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {
    public static void main(String[] args) {
        new CountDownLatchTest().test();
    }

    private static final int COUNT = 10;
    private static CountDownLatch countDownLatch = new CountDownLatch(COUNT);

    private void test() {
        ExecutorService executorService = Executors.newFixedThreadPool(COUNT);
        for (int i = 0; i < COUNT; i++) {
            final int index = i;
            executorService.execute(() -> {
                try {
                    Thread.sleep(index * 500);
                    System.out.println(Thread.currentThread().getName() + " finish working");
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("work down!!!!");
    }
}
