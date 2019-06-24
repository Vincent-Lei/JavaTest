import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encryption {
    private static final int MODE_ENCODE = 1;
    private static final int MODE_DECODE = 2;

    public static void encode(String fileSourcePath, String fileOuterPath, String password) {
        work(fileSourcePath, fileOuterPath, password, MODE_ENCODE);
    }

    public static void decode(String fileSourcePath, String fileOuterPath, String password) {
        work(fileSourcePath, fileOuterPath, password, MODE_DECODE);
    }


    private static void work(String fileSourcePath, String fileOuterPath, String password, int mode) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        byte[] inputBuffer = new byte[1024];
        byte[] outBuffer = new byte[1024];
        byte[] passwordByte = password.getBytes();
        int len;
        try {
            fileInputStream = new FileInputStream(fileSourcePath);
            fileOutputStream = new FileOutputStream(fileOuterPath);
            while ((len = fileInputStream.read(inputBuffer)) > 0) {
                realEncodeOrDecode(inputBuffer, outBuffer, passwordByte, len, mode);
                fileOutputStream.write(outBuffer, 0, len);
            }
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private static void realEncodeOrDecode(byte[] inputBuffer, byte[] outBuffer, byte[] passwordByte, int len, int mode) {
        byte input;
        for (int i = 0; i < len; i++) {
            input = inputBuffer[i];
            if (mode == MODE_ENCODE) {
                for (int j = 0; j < passwordByte.length; j++) {
                    input = (byte) (input ^ passwordByte[j]);
                }
            } else {
                for (int j = passwordByte.length - 1; j >= 0; j--) {
                    input = (byte) (input ^ passwordByte[j]);
                }
            }
            outBuffer[i] = input;
        }
    }

}
