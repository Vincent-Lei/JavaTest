package concurrent;

import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {
    public static void main(String[] args) {
        new CyclicBarrierTest().test();
    }

    private static ExecutorService executor = Executors.newFixedThreadPool(1);
    private static Vector<String> v1 = new Vector<>();
    private static Vector<String> v2 = new Vector<>();
    private static final String[] FIRST_NAME = {"A", "B", "C", "D", "E"};
    private static final String[] SECOND_NAME = {"a", "b", "c", "d", "e"};
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
        executor.execute(() -> {
            String first = v1.remove(0);
            String second = v2.remove(0);
            if (!first.toLowerCase().equals(second.toLowerCase())) {
                System.err.println("first != second");
            } else
                System.out.println(first + second);
        });
    });

    private void test() {
        new Thread(() -> {
            int index = 0;
            while (true) {
                if (index == FIRST_NAME.length)
                    index = 0;
                v1.add(FIRST_NAME[index]);
                index++;
                try {
                    cyclicBarrier.await();
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }

        }).start();
        new Thread(() -> {
            int index = 0;
            while (true) {
                if (index == SECOND_NAME.length)
                    index = 0;
                v2.add(SECOND_NAME[index]);
                index++;
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }
}
