package datastruct.tree;

public class BSTTest {
    public static void main(String[] args) {
        int[] array = {100, 98, 5, 4, 67, 89, 34, 6, 7, 456, 88, 9, 679, 44, 90};
        BSTree bsTree = new BSTree();
        for (int i = 0; i < array.length; i++) {
            if (!bsTree.insert(array[i]))
                System.err.println("BSTree can not insert ：" + array[i]);
        }
        bsTree.print();
        System.out.println("-------------------------");
        bsTree.delete(5);
        bsTree.print();
//        System.out.println(bsTree.getValue(456));
//        System.out.println(bsTree.getValue(9));
//        System.out.println(bsTree.getValue(311));
    }
}
