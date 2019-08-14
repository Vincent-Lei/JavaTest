package datastruct.tree.huffman;

public class ZYTest {
    private static final String SOURCE_PATH = "C:\\Users\\Android\\Desktop\\test\\111.txt";
    private static final String YS_PATH = "C:\\Users\\Android\\Desktop\\test\\111-压缩.zy";

    public static void main(String[] args) {
        FileEntity fileEntity = new FileEntity(SOURCE_PATH);
        fileEntity.file2HfmCode();

        fileEntity = new FileEntity(YS_PATH);
        fileEntity.hfmCode2File(".txt");
    }
}
