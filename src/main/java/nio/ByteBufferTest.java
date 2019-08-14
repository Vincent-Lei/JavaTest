package nio;


import java.nio.ByteBuffer;

public class ByteBufferTest {
    private static final int SIZE = 1024;
    private static byte[] dataStr1;
    private static byte[] dataStr2;

    static {
        try {
            dataStr1 = "this is test ByteBuffer".getBytes("utf-8");
            dataStr2 = "---java.nio.ByteBuffer---".getBytes("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(SIZE);
        //输出ByteBuffer capacity、limit、position信息
        printByteBuffInfo(byteBuffer);
        //write
        byteBuffer.put(dataStr1);
        //read
        //切换到读取模式
        byteBuffer.flip();
        //输出ByteBuffer capacity、limit、position信息
        printByteBuffInfo(byteBuffer);
        byte[] getBytes = new byte[byteBuffer.limit()];
        byteBuffer.get(getBytes);
        System.out.println(new String(getBytes));
        //write
        int position = byteBuffer.position();
        //继续写之前要清空
        byteBuffer.clear();
        //clear并没有内存擦除，只是修改position、limit，如果想继续讲原来的数据作为有效数据，
        // 可以修改position
        byteBuffer.position(position);
        byteBuffer.put(dataStr2);
        printByteBuffInfo(byteBuffer);
        //read
        byteBuffer.flip();
        printByteBuffInfo(byteBuffer);
        getBytes = new byte[byteBuffer.limit()];
        byteBuffer.get(getBytes);
        System.out.println(new String(getBytes));
        //重复读取
        System.out.println("--------------rewind-----------------");
        byteBuffer.rewind();
        byte[] buff = new byte[10];
        byteBuffer.get(buff);
        printByteBuffInfo(byteBuffer);
        //标记读取到的位置
        byteBuffer.mark();
        byteBuffer.get(buff);
        printByteBuffInfo(byteBuffer);
        //position回复到标记的位置
        byteBuffer.reset();
        printByteBuffInfo(byteBuffer);
    }

    private static void printByteBuffInfo(ByteBuffer byteBuffer) {
        System.out.println("-----byteBuffer info-----");
        System.out.println("capacity = " + byteBuffer.capacity());
        System.out.println("limit = " + byteBuffer.limit());
        System.out.println("position = " + byteBuffer.position());
    }
}
