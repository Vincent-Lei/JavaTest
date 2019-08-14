package aop;

import java.lang.reflect.Proxy;

public class Main {
    public Main() {
        System.out.println("--main--");
    }

    public static void main(String[] args) {
        IProxyInterface proxyInterface = (IProxyInterface) Proxy.newProxyInstance(Main.class.getClassLoader(), new Class[]{IProxyInterface.class}, new ProxyInvocationHandler(new ProxyImpl()));
        proxyInterface.getName();
        proxyInterface.getId();
        proxyInterface.doTestPrint("this is main test");
    }
}
