package datastruct.tree.huffman;

public class Node implements Comparable<Node> {
    public static final String CODE_LEFT = "0";
    public static final String CODE_RIGHT = "1";

    public int num;
    public byte by;
    public String code;
    public Node leftNode;
    public Node rightNode;

    public Node(int num) {
        this.num = num;
    }

    public Node(int num, byte by) {
        this.num = num;
        this.by = by;
    }

    @Override
    public int compareTo(Node o) {
        return this.num - o.num;
    }
}
