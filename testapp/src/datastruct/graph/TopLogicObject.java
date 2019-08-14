package datastruct.graph;

import java.util.Stack;

public class TopLogicObject {

    //边表顶点
    static class EdgeNode {
        private int adjVert;
        private EdgeNode next;
        private int weight;

        public EdgeNode(int adjVert) {
            this.adjVert = adjVert;
        }

        public int getAdjVert() {
            return adjVert;
        }

        public void setAdjVert(int adjVert) {
            this.adjVert = adjVert;
        }

        public EdgeNode getNext() {
            return next;
        }

        public void setNext(EdgeNode next) {
            this.next = next;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

    }

    //邻接顶点
    static class VertexNode {
        private int in;//入度
        private String data;
        private EdgeNode firstEdge;

        public VertexNode(int in, String data) {
            this.in = in;
            this.data = data;
        }

        public int getIn() {
            return in;
        }

        public void setIn(int in) {
            this.in = in;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public EdgeNode getFirstEdge() {
            return firstEdge;
        }

        public void setFirstEdge(EdgeNode firstEdge) {
            this.firstEdge = firstEdge;
        }

    }

    private int numVertexes;
    private VertexNode[] adjList;//邻接顶点的一维数组

    public int getNumVertexes() {
        return numVertexes;
    }

    public void setNumVertexes(int numVertexes) {
        this.numVertexes = numVertexes;
    }

    public VertexNode[] getAdjList() {
        return adjList;
    }

    public void setAdjList(VertexNode[] adjList) {
        this.adjList = adjList;
    }

    public static TopLogicObject createTestTopLogicSortObj() {
        TopLogicObject topLogicSort = new TopLogicObject();
        topLogicSort.numVertexes = 14;
        VertexNode node0 = new VertexNode(0, "v0");
        VertexNode node1 = new VertexNode(0, "v1");
        VertexNode node2 = new VertexNode(2, "v2");
        VertexNode node3 = new VertexNode(0, "v3");
        VertexNode node4 = new VertexNode(2, "v4");
        VertexNode node5 = new VertexNode(3, "v5");
        VertexNode node6 = new VertexNode(1, "v6");
        VertexNode node7 = new VertexNode(2, "v7");
        VertexNode node8 = new VertexNode(2, "v8");
        VertexNode node9 = new VertexNode(1, "v9");
        VertexNode node10 = new VertexNode(1, "v10");
        VertexNode node11 = new VertexNode(2, "v11");
        VertexNode node12 = new VertexNode(1, "v12");
        VertexNode node13 = new VertexNode(2, "v13");
        topLogicSort.adjList = new VertexNode[topLogicSort.numVertexes];
        topLogicSort.adjList[0] = node0;
        topLogicSort.adjList[1] = node1;
        topLogicSort.adjList[2] = node2;
        topLogicSort.adjList[3] = node3;
        topLogicSort.adjList[4] = node4;
        topLogicSort.adjList[5] = node5;
        topLogicSort.adjList[6] = node6;
        topLogicSort.adjList[7] = node7;
        topLogicSort.adjList[8] = node8;
        topLogicSort.adjList[9] = node9;
        topLogicSort.adjList[10] = node10;
        topLogicSort.adjList[11] = node11;
        topLogicSort.adjList[12] = node12;
        topLogicSort.adjList[13] = node13;
        node0.firstEdge = new EdgeNode(11);
        node0.firstEdge.next = new EdgeNode(5);
        node0.firstEdge.next.next = new EdgeNode(4);
        node1.firstEdge = new EdgeNode(8);
        node1.firstEdge.next = new EdgeNode(4);
        node1.firstEdge.next.next = new EdgeNode(2);
        node2.firstEdge = new EdgeNode(9);
        node2.firstEdge.next = new EdgeNode(6);
        node2.firstEdge.next.next = new EdgeNode(5);
        node3.firstEdge = new EdgeNode(13);
        node3.firstEdge.next = new EdgeNode(2);
        node4.firstEdge = new EdgeNode(7);
        node5.firstEdge = new EdgeNode(12);
        node5.firstEdge.next = new EdgeNode(8);
        node6.firstEdge = new EdgeNode(5);
        node8.firstEdge = new EdgeNode(7);
        node9.firstEdge = new EdgeNode(11);
        node9.firstEdge.next = new EdgeNode(10);
        node10.firstEdge = new EdgeNode(13);
        node12.firstEdge = new EdgeNode(9);
        return topLogicSort;
    }

    public void topLogicSort() {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < numVertexes; i++) {
            if (adjList[i].in == 0)
                stack.push(i);
        }
        int count = 0;
        int pop;
        while (!stack.isEmpty()) {
            pop = stack.pop();
            count++;
            System.out.println("顶点：" + pop);
            for (EdgeNode node = adjList[pop].firstEdge; node != null; node = node.next) {
                adjList[node.adjVert].in--;
                if (adjList[node.adjVert].in == 0)
                    stack.push(node.adjVert);
            }
        }
        if (count != numVertexes)
            System.out.println("topLogicSort failed");
    }
}
