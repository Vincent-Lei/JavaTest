package method;

public class ExceptionClass {

    public void testException() {
        int num = 0;
        try {
            num = Integer.parseInt("223");
        } catch (Exception e) {
            num = 90;
        } finally {
            System.out.println("num = " + num);
        }
    }
}
