package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOClient {

    public static void main(String[] args) {
        try {
            new NIOClient().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    SocketHolder socketHolder;
    int msgIndex;

    public void start() throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 9999));
        socketChannel.configureBlocking(false);
        socketHolder = new SocketHolder(socketChannel, false);
        socketHolder.write("你好你好 Hello");
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);

        new Thread(new Runnable() {
            @Override
            public void run() {
               while (true){
                   try {
                       Thread.sleep(1000);
                       msgIndex++;
                       socketHolder.write("this is test msg：" + msgIndex);
                   }catch (Exception e){
                       e.printStackTrace();
                   }
               }
            }
        }).start();
        while (true) {
            if (selector.select() <= 0) {
                continue;
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isReadable()) {
                    socketHolder.read();
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
            }
        }
    }
}
