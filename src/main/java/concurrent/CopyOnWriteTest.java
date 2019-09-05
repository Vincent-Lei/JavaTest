package concurrent;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;

public class CopyOnWriteTest {
    public static void main(String[] args) {
        new CopyOnWriteTest().testSafe();
    }

    private CopyOnWriteArrayList<Integer> cowwal = new CopyOnWriteArrayList<>();
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(2, null);
    private final int addCount = 1000;

    private void testSafe() {
        for (int i = 0; i < addCount; i++) {
            cowwal.add(i);
        }

        new Thread(() -> {
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("start remove");
            for (int i = 0; i < addCount; i++) {
                cowwal.remove(i);
            }
        }).start();

        new Thread(() -> {
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("start iterator");
            for (int i = 0, size = cowwal.size(); i < size; i++) {
                cowwal.get(i);
            }
        }).start();
    }
}
