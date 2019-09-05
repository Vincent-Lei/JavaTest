package runtime;


public class RunTimeTest {
    public static void main(String[] args) {
        print();
    }

    public static void print() {
        Runtime runtime = Runtime.getRuntime();
        long javaMax = runtime.maxMemory();
        long javaTotal = runtime.totalMemory();
        long javaFree = runtime.freeMemory();
        long javaUsed = javaTotal - javaFree;
        // Java 内存使用超过最大限制的 85%
        float proportion = (float) javaUsed / javaMax;

        System.out.println("javaUsed memory = " + formatMemorySize(javaUsed));
    }

    private static float formatMemorySize(long size) {
        return formatMemorySize(size, false);
    }

    private static float formatMemorySize(long size, boolean kb) {
        if (kb)
            return size * 1.0f / 1024;
        return size * 1.0f / 1024 / 1024;
    }
}
