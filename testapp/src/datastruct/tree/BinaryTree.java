package datastruct.tree;

public class BinaryTree {
    public static class Node {
        private int id;
        public String name;
        public Node left;
        public Node right;

        public Node(int id) {
            this.id = id;
            this.name = "节点" + id;
        }
    }

    public Node rootNode;

    private BinaryTree() {
    }

    public static BinaryTree createTree(int level) {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.rootNode = new Node(0);
        level--;
        appendNode(level, binaryTree.rootNode);
        return binaryTree;
    }


    private static void appendNode(int nl, Node node) {
        if (nl < 0)
            return;
        node.left = new Node(2 * node.id + 1);
        node.right = new Node(2 * node.id + 2);
        nl--;
        appendNode(nl, node.left);
        appendNode(nl, node.right);
    }
}
