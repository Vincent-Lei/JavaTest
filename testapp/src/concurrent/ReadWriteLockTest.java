package concurrent;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
    public static void main(String[] args) {
        new ReadWriteLockTest().test();
    }

    private static HashMap<String, Object> cachedMap = new HashMap<>();
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock lockRead = readWriteLock.readLock();
    private static Lock lockWrite = readWriteLock.writeLock();

    private void test() {
        int count = 2000;
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < count; i++) {
            String key = "KEY-" + i % 5;
            executorService.submit(() -> {
                Object o = get(key);
                System.out.println(Thread.currentThread().getName() + " o：" + o.toString());
            });
        }

    }

    private Object get(String key) {
        Object o;
        try {
            lockRead.lock();
            o = cachedMap.get(key);
        } finally {
            lockRead.unlock();
        }

        if (o == null) {
            try {
                lockWrite.lock();
                if (!cachedMap.containsKey(key)) {
                    o = new Object();
                    cachedMap.put(key, o);
                    System.out.println("create cache for key：" + key);
                }
            } finally {
                lockWrite.unlock();
            }
        }


        return o;
    }

}
