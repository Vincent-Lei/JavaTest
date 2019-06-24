import datastruct.tree.TreeTest;


public class Main {


    public static void main(String[] args) {
        System.out.println("this is java application test");
        TreeTest treeTest = new TreeTest(4);
        treeTest.preOrderTraverse();
//        treeTest.postOrderTraverse();
//        treeTest.middleOrderTraverse();
        test();
    }

    private static final String FILE_PATH_SOURCE = "C:\\Users\\Android\\Desktop\\test\\1.png";
    private static final String FILE_PATH_ENCODE = "C:\\Users\\Android\\Desktop\\test\\encode.en";
    private static final String FILE_PATH_DECODE = "C:\\Users\\Android\\Desktop\\test\\decode.png";
    private static final String PASSWORD = "123424ADDFFF";

    private static final String INPUT_ENCODE = FILE_PATH_SOURCE;
    private static final String OUT_ENCODE = FILE_PATH_ENCODE;

    private static final String INPUT_DECODE = FILE_PATH_ENCODE;
    private static final String OUT_DECODE = FILE_PATH_DECODE;


    private static void test() {
//        Encryption.encode(INPUT_ENCODE, OUT_ENCODE, PASSWORD);
        Encryption.decode(INPUT_DECODE, OUT_DECODE, PASSWORD);
    }


}
