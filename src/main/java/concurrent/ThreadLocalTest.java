package concurrent;

import concurrent.threadlocal.DateUtil;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalTest {
    public static void main(String[] args) {
        testSafetyDateFormat();
    }

    private static void testSafetyDateFormat() {
        long monthSecMis = 30 * 24 * 60 * 60 * 1000L;
        Date date = new Date();
        int taskCount = 100;
        final long[] timeStamps = new long[taskCount];
        for (int i = 0; i < taskCount; i++) {
            timeStamps[i] = date.getTime() + monthSecMis * i;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < taskCount; i++) {
            final long ts = timeStamps[i];
            executorService.execute(() -> {
                System.out.println(DateUtil.timeStamp2PatternStr(ts, DateUtil.DEFAULT_TL_YMD_HM_SS));
            });
        }
    }
}
