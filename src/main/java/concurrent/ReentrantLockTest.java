package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    public static void main(String[] args) {
        new ReentrantLockTest().test();
    }

    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();
    private int count = 0;
    private static final int MAX = 100;

    private void add() throws InterruptedException {
        lock.lock();
        try {
            while (count == MAX) {
                notFull.await();
            }
            count++;
            System.out.println("add one count  =" + count);
            Thread.sleep(1000);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }


    private void minus() throws InterruptedException {
        lock.lock();
        try {
            while (count <= 0) {
                notEmpty.await();
            }
            count--;
            System.out.println("minus one count  =" + count);
            Thread.sleep(1000);
            notFull.signal();
        } finally {
            lock.unlock();
        }
    }

    public void test() {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    add();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                try {
                    minus();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t3 = new Thread(() -> {
            boolean getLock = false;
            try {
                getLock = lock.tryLock();
                if (getLock) {
                    System.out.println("tryLock success");
                } else {
                    System.out.println("tryLock failed");
                    getLock = lock.tryLock(10, TimeUnit.SECONDS);
                    if (getLock) {
                        System.out.println("tryLock with time success");
                    } else {
                        System.out.println("tryLock with time failed");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("tryLock with time InterruptedException");
            } finally {
                if (getLock)
                    lock.unlock();
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
