package datastruct.tree.huffman;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
    private static PriorityQueue<Node> map2Queue(HashMap<Byte, Integer> map) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        if (map != null) {
            Node node;
            Map.Entry<Byte, Integer> entry;
            Iterator<Map.Entry<Byte, Integer>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                entry = iterator.next();
                node = new Node(entry.getValue(), entry.getKey());
                priorityQueue.add(node);
            }
        }
        return priorityQueue;
    }

    private static Node queue2Tree(PriorityQueue<Node> priorityQueue) {
        if (priorityQueue == null)
            return null;
        Node n1, n2;
        Node nr;
        while (priorityQueue.size() > 1) {
            n1 = priorityQueue.poll();
            n2 = priorityQueue.poll();
            n1.code = Node.CODE_LEFT;
            n2.code = Node.CODE_RIGHT;

            nr = new Node(n1.num + n2.num);
            nr.leftNode = n1;
            nr.rightNode = n2;

            priorityQueue.add(nr);
        }
        return priorityQueue.poll();
    }

    public static HashMap<Byte, String> treeToHuffmanCode(HashMap<Byte, Integer> map) {
        Node rootNode = queue2Tree(map2Queue(map));
        if (rootNode != null) {
            HashMap<Byte, String> codeMap = new HashMap<>();
            getHuffmanCode(rootNode, "", codeMap);
            return codeMap;
        }
        return null;
    }

    private static void getHuffmanCode(Node node, String code, HashMap<Byte, String> codeMap) {
        if (node != null) {
            code = code + (node.code == null ? "" : node.code);
            if (node.leftNode == null && node.rightNode == null) {
                codeMap.put(node.by, code);
            }
            getHuffmanCode(node.leftNode, code, codeMap);
            getHuffmanCode(node.rightNode, code, codeMap);
        }
    }
}
