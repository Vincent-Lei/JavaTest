package concurrent;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {
    public static void main(String[] args) {
        new StampedLockTest().correctUseInterrupt();
    }

    private int state;

    private static final StampedLock stampeLock = new StampedLock();

    private void test() {
        new Thread(() -> {
            while (true) {
                long stamp = 0;
                try {
                    stamp = stampeLock.writeLock();
                    state++;
                    System.out.println("writeLock---------");
                } finally {
                    stampeLock.unlockWrite(stamp);
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                long stamp = stampeLock.tryOptimisticRead();
                int temp = state;
                if (!stampeLock.validate(stamp)) {
                    try {
                        stamp = stampeLock.readLock();
                        temp = state;
                        System.out.println("readLock temp：" + temp);
                    } finally {
                        stampeLock.unlockRead(stamp);
                    }
                } else {
                    System.out.println("tryOptimisticRead temp：" + temp);
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private void errorUseInterrupt() {
        new Thread(() -> {
            stampeLock.writeLock();
            System.out.println("永远阻塞在此处，不释放写锁");
            LockSupport.park();
        }).start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(() -> {
            long stamp = 0;
            try {
                stamp = stampeLock.readLock();
                System.out.println("t2 readLock");
            } finally {
                stampeLock.unlockRead(stamp);
            }
        });
        t2.start();
        //不正确使用StampedLock  interrupt 导致CPU占用高
        t2.interrupt();
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void correctUseInterrupt() {
        new Thread(() -> {
            stampeLock.writeLock();
            System.out.println("永远阻塞在此处，不释放写锁");
            LockSupport.park();
        }).start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(() -> {
            long stamp = 0;
            try {
                stamp = stampeLock.readLockInterruptibly();
                System.out.println("t2 readLock");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                stampeLock.unlockRead(stamp);
            }
        });
        t2.start();
        t2.interrupt();
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
