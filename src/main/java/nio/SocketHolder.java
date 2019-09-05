package nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketHolder {
    private static final int BUFFER_LENGTH = 1024;
    private ByteBuffer buffer = ByteBuffer.allocate(BUFFER_LENGTH);
    private SocketChannel socketChannel;
    private ByteArrayOutputStream bos = new ByteArrayOutputStream();
    private boolean isServeHolder;

    public SocketHolder(SocketChannel socketChannel, boolean isServeHolder) {
        this.socketChannel = socketChannel;
        this.isServeHolder = isServeHolder;
    }

    public void read() throws IOException {
        buffer.clear();
        bos.reset();

        while (socketChannel.read(buffer) > 0) {
            buffer.flip();
            byte[] tempReadBuff = new byte[buffer.limit()];
            buffer.get(tempReadBuff);
            bos.write(tempReadBuff, 0, tempReadBuff.length);
            buffer.clear();
        }
        byte[] info = bos.toByteArray();
        if (info != null && info.length > 0) {
            String s = new String(info);
            System.out.println(isServeHolder ? "Sever receive：" + s : "Client receive：" + s);
            if (isServeHolder)
                write("sever copy{ " + s + " }");
        }
    }

    public void write(String msg) throws IOException {
        buffer.clear();
        buffer.put(msg.getBytes());
        buffer.flip();
        socketChannel.write(buffer);
    }
}