package method;

public class SynchronizedClass {
    private static final Object object = new Object();

    public void test() {
        synchronized (object){
            System.out.println("synchronized");
        }
    }
}
