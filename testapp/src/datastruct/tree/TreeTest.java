package datastruct.tree;

public class TreeTest {
    private BinaryTree binaryTree;

    public TreeTest(int n) {
        binaryTree = BinaryTree.createTree(n);
    }

    public void preOrderTraverse() {
        realPreOrderTraverse(binaryTree.rootNode);
    }

    public void postOrderTraverse() {
        realPostOrderTraverse(binaryTree.rootNode);
    }

    public void middleOrderTraverse() {
        realMiddleOrderTraverse(binaryTree.rootNode);
    }


    private void realPreOrderTraverse(BinaryTree.Node node) {
        if (node == null)
            return;
        System.out.println(node.name);
        realPreOrderTraverse(node.left);
        realPreOrderTraverse(node.right);
    }

    private void realPostOrderTraverse(BinaryTree.Node node) {
        if (node == null)
            return;
        realPostOrderTraverse(node.left);
        realPostOrderTraverse(node.right);
        System.out.println(node.name);
    }

    private void realMiddleOrderTraverse(BinaryTree.Node node) {
        if (node == null)
            return;
        realMiddleOrderTraverse(node.left);
        System.out.println(node.name);
        realMiddleOrderTraverse(node.right);
    }
}
