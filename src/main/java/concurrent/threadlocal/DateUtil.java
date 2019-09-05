package concurrent.threadlocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class DateUtil {
    public static final ThreadLocal<SimpleDateFormat> DEFAULT_TL_YMD_HM_SS = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
    public static final ThreadLocal<SimpleDateFormat> DEFAULT_TL_YMD_HM = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA));
    public static final ThreadLocal<SimpleDateFormat> DEFAULT_TL_YMD = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
    public static final ThreadLocal<SimpleDateFormat> DEFAULT_TL_HM = ThreadLocal.withInitial(() -> new SimpleDateFormat("HH:mm", Locale.CHINA));

    private static final HashMap<String, ThreadLocal<SimpleDateFormat>> patternMap = new HashMap<>();

    private static ThreadLocal<SimpleDateFormat> getTlByPattern(String pattern) {
        if (pattern == null || pattern.trim().length() == 0)
            throw new IllegalArgumentException("pattern is null");
        ThreadLocal<SimpleDateFormat> tl;
        synchronized (patternMap) {
            tl = patternMap.get(pattern);
            if (tl == null) {
                tl = new ThreadLocal<>();
                patternMap.put(pattern, tl);
            }
        }
        if (tl.get() == null)
            tl.set(new SimpleDateFormat(pattern, Locale.CHINA));
        return tl;
    }

    public static String timeStamp2PatternStr(long timeStamp, String pattern) {
        return timeStamp2PatternStr(timeStamp, getTlByPattern(pattern));
    }

    public static String timeStamp2PatternStr(long timeStamp, ThreadLocal<SimpleDateFormat> tlPatten) {
        return tlPatten.get().format(new Date(timeStamp));
    }

    public static String timeStamp2PatternStr(Date date, String pattern) {
        return timeStamp2PatternStr(date, getTlByPattern(pattern));
    }

    public static String timeStamp2PatternStr(Date date, ThreadLocal<SimpleDateFormat> tlPatten) {
        return tlPatten.get().format(date);
    }

    public static long patternStr2timeStamp(String str, String patten) {
        return patternStr2timeStamp(str, getTlByPattern(patten));
    }

    public static long patternStr2timeStamp(String str, ThreadLocal<SimpleDateFormat> tlPatten) {
        if (str == null)
            return 0;
        try {
            return tlPatten.get().parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Date patternStr2Date(String str, String patten) {
        return patternStr2Date(str, getTlByPattern(patten));
    }

    public static Date patternStr2Date(String str, ThreadLocal<SimpleDateFormat> tlPatten) {
        if (str == null)
            return null;
        try {
            return tlPatten.get().parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isSameDay(Date d1, Date d2) {
        return DEFAULT_TL_YMD.get().format(d1.getTime()).equals(DEFAULT_TL_YMD.get().format(d2.getTime()));
    }

    public static boolean isSameDay(long d1, long d2) {
        return DEFAULT_TL_YMD.get().format(new Date(d1)).equals(DEFAULT_TL_YMD.get().format(new Date(d2)));
    }

    public static boolean isSameDay(String d1, String d2) {
        if (d1 == null || d2 == null || d1.length() < 10 || d2.length() < 10)
            return false;
        return d1.substring(0, 10).equals(d2.substring(0, 10));
    }
}
