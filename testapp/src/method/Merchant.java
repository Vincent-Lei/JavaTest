package method;

import java.util.ConcurrentModificationException;

public class Merchant implements Customer {
    private String name = "name-1";

    public Number getPrice(double price, Customer customer) {
        String name2 = "name-1";
        if (customer.isVIP())
            return price * 0.5f;
        return price;
    }

    @Override
    public boolean isVIP() {
        return false;
    }

    public void loop(int[] array) {
        if (array == null)
            return;
        int sum = 0;
        int length = array.length;
        for (int i = 0; i < length; i++) {
            sum += array[i];
        }
        System.out.println(sum);
    }

}
