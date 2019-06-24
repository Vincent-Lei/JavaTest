package datastruct.tree;

public class BSTree {
    private class Node {
        public int key;
        public String value;
        public Node left;
        public Node right;

        public Node(int key) {
            this.key = key;
            this.value = String.valueOf(key);
        }
    }

    private Node rootNode;

    public boolean insert(int key) {
        if (rootNode == null) {
            rootNode = new Node(key);
            return true;
        }
        if (rootNode.key == key) {
            rootNode = null;
            return true;
        }
        Node node = rootNode;
        Node parent = null;
        while (node != null) {
            parent = node;
            if (node.key > key)
                node = node.left;
            else if (node.key < key)
                node = node.right;
            else {
                return false;
            }
        }
        node = new Node(key);
        if (parent.key > key)
            parent.left = node;
        else
            parent.right = node;
        return true;
    }

    public boolean delete(int key) {
        if (rootNode == null)
            return false;
        Node node = rootNode;
        Node parent = null;
        while (node != null) {
            if (node.key == key) {
                realDelete(parent, node);
                return true;
            }
            parent = node;
            if (node.key > key) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return false;
    }

    private void realDelete(Node parent, Node node) {
        if (node.left == null) {
            if (parent.left == node)
                parent.left = node.right;
            else
                parent.right = node.right;
        } else if (node.right == null) {
            if (parent.left == node)
                parent.left = node.left;
            else
                parent.right = node.left;
        } else {
            Node p2 = null;
            Node cur = null;
            Node node2 = node.right;
            while (node2 != null) {
                cur = node2;
                if (cur.left != null)
                    p2 = cur;
                node2 = node2.left;
            }
            if (p2 != null)
                p2.left = cur.right;

            cur.left = node.left;
            if (cur != node.right)
                cur.right = node.right;

            if (parent.key > node.key) {
                parent.left = cur;
            } else
                parent.right = cur;
        }
    }

    public String getValue(int key) {
        if (rootNode == null)
            return null;
        Node node = rootNode;
        while (node != null) {
            if (node.key == key)
                return node.value;
            else if (node.key > key)
                node = node.left;
            else
                node = node.right;
        }
        return null;
    }

    private void realPrint(Node node) {
        if (node != null) {
            System.out.println(node.key);
            realPrint(node.left);
            realPrint(node.right);
        }
    }

    public void print() {
        if (rootNode == null) {
            System.err.println("RootNode is  null");
            return;
        }
        realPrint(rootNode);
    }
}
