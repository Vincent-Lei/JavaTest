package method;

public class NativeMerchant extends Merchant {
    @Override
    public Double getPrice(double price, Customer customer) {
        if (customer.isVIP())
            return price + price;
        return 0d;
    }

    public Double getPrice2(double price, Customer2 customer) {
        if (customer.isVIP2())
            return price + price;
        return 0d;
    }

    public int add(int a, int b, int c, int d, int f) {
        return a + f;
    }

    @Override
    public boolean isVIP() {
        return true;
    }
}
