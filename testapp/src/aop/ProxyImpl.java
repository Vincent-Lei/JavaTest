package aop;

public class ProxyImpl implements IProxyInterface {
    private Main main = new Main();
    {
        System.out.println("--ProxyImpl---1");
    }
    @Override
    public String getName() {
        return "ProxyImpl";
    }

    @Override
    public int getId() {
        return 19089;
    }

    @Override
    public void doTestPrint(String msg) {
        System.out.println("msg=>>" + msg);
    }
}
