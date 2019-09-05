package nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;

public class NIOServer {

    public static void main(String[] args) {
        try {
            new NIOServer().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<SocketChannel, SocketHolder> map = new HashMap<>();

    public void start() throws IOException {
        System.out.println("NIOServer start");
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(9999));
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            if (selector.select() <= 0)
                continue;
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    iterator.remove();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    SocketHolder socketHolder = new SocketHolder(socketChannel,true);
                    map.put(socketChannel, socketHolder);
                } else if (selectionKey.isReadable()) {
                    iterator.remove();
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    SocketHolder socketHolder = map.get(socketChannel);
                    socketHolder.read();
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
            }
        }
    }


}
