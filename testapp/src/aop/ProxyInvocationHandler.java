package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyInvocationHandler implements InvocationHandler {
    private ProxyImpl proxy;

    public ProxyInvocationHandler(ProxyImpl proxy) {
        this.proxy = proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        if ("getName".equals(name))
            System.out.println("aop getName");
        if ("getId".equals(name))
            System.out.println("aop getId");
        if ("doTestPrint".equals(name) && args.length > 0) {
            String msg = (String) args[0];
            if (msg != null && msg.length() > 0) {
                char[] cs = msg.toCharArray();
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = cs.length - 1; i >= 0; i--) {
                    stringBuilder.append(cs[i]);
                }
                args[0] = stringBuilder.toString();
            }

        }
        return method.invoke(this.proxy, args);
    }
}
