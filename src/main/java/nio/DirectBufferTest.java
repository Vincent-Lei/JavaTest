package nio;

import runtime.RunTimeTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DirectBufferTest {
    public static void main(String[] args) throws IOException {
        directCopyFile();
//        copyFile2();
    }

    private static final String IN_PATH = "C:\\Users\\Android\\Desktop\\PDF\\数据结构与算法分析C++描述_Mark.Allen.Weiss.pdf";
    private static final String OUT_PATH = "C:\\Users\\Android\\Desktop\\PDF\\test\\数据结构与算法分析C++描述_Mark.Allen.Weiss.pdf";

    private static void directCopyFile() throws IOException {
        long start = System.currentTimeMillis();
        FileChannel inChannel = FileChannel.open(Paths.get(IN_PATH), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get(OUT_PATH), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        MappedByteBuffer inMappedByteBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedByteBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
        RunTimeTest.print();
        int buffLength = 1024 * 4;
        buffLength = buffLength > inChannel.size() ? (int) inChannel.size() : buffLength;
        byte[] bytes = new byte[buffLength];
        while (true) {
            if (buffLength <= inMappedByteBuffer.remaining()) {
                inMappedByteBuffer.get(bytes);
                outMappedByteBuffer.put(bytes);
            } else {
                buffLength = inMappedByteBuffer.remaining();
                bytes = new byte[buffLength];
                inMappedByteBuffer.get(bytes);
                outMappedByteBuffer.put(bytes);
                break;
            }
        }
        RunTimeTest.print();
        inChannel.close();
        outChannel.close();
        long end = System.currentTimeMillis();
        System.out.println("cost time  =" + (end - start) / 1000.0f + "s");
    }


    private static void copyFile() throws IOException {
        long start = System.currentTimeMillis();
        FileChannel inChannel = FileChannel.open(Paths.get(IN_PATH), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get(OUT_PATH), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        RunTimeTest.print();
        while (inChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            outChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        RunTimeTest.print();
        inChannel.close();
        outChannel.close();
        long end = System.currentTimeMillis();
        System.out.println("cost time  =" + (end - start) / 1000.0f + "s");
    }

    private static void copyFile2() throws IOException {
        long start = System.currentTimeMillis();
        FileInputStream fis = new FileInputStream(IN_PATH);
        FileOutputStream fos = new FileOutputStream(OUT_PATH);
        byte[] buff = new byte[4096];
        int len;
        RunTimeTest.print();
        while ((len = fis.read(buff)) > 0) {
            fos.write(buff, 0, len);
        }
        RunTimeTest.print();
        fos.flush();
        fis.close();
        fos.close();

        long end = System.currentTimeMillis();
        System.out.println("cost time  =" + (end - start) / 1000.0f + "s");
    }
}
