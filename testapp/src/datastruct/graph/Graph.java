package datastruct.graph;

import java.util.LinkedList;

public class Graph {
    public static final int MAX = 1234567;

    private int vertexCount;
    private int[] vertex;
    private int[][] vertexMatrix;
    private boolean[] isVisited;

    public Graph(int vertexCount) {
        this.vertexCount = vertexCount;
        this.vertex = new int[vertexCount];
        this.isVisited = new boolean[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            vertex[i] = i;
        }
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int[] getVertex() {
        return vertex;
    }

    public int[][] getVertexMatrix() {
        return vertexMatrix;
    }

    public void setVertexMatrix(int[][] vertexMatrix) {
        this.vertexMatrix = vertexMatrix;
    }

    public int getVertexOutDegree(int vertexIndex) {
        if (vertexIndex < 0 || vertexIndex >= vertexCount) {
            return -1;
        }
        int degree = 0;
        int[] array = vertexMatrix[vertexIndex];
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0 && array[i] != MAX)
                degree++;
        }
        return (degree == 0 ? -1 : degree);
    }

    public int getVertexInDegree(int vertexIndex) {
        if (vertexIndex < 0 || vertexIndex >= vertexCount) {
            return -1;
        }
        int degree = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (vertexMatrix[i][vertexIndex] != 0 && vertexMatrix[i][vertexIndex] != MAX)
                degree++;
        }
        return (degree == 0 ? -1 : degree);
    }

    public int getVertexOutWeight(int vertexIndex) {
        if (vertexIndex < 0 || vertexIndex >= vertexCount) {
            return -1;
        }
        int weight = 0;
        int[] array = vertexMatrix[vertexIndex];
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0 && array[i] != MAX)
                weight += array[i];
        }
        return (weight == 0 ? -1 : weight);
    }

    public int getVertexInWeight(int vertexIndex) {
        if (vertexIndex < 0 || vertexIndex >= vertexCount) {
            return -1;
        }
        int weight = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (vertexMatrix[i][vertexIndex] != 0 && vertexMatrix[i][vertexIndex] != MAX)
                weight += vertexMatrix[i][vertexIndex];
        }
        return (weight == 0 ? -1 : weight);
    }

    public int getFirstNeighbor(int vertexIndex) {
        if (vertexIndex < 0 || vertexIndex >= vertexCount) {
            return -1;
        }
        for (int i = 0; i < vertexCount; i++) {
            if (vertexMatrix[vertexIndex][i] > 0 && vertexMatrix[vertexIndex][i] < MAX)
                return i;
        }
        return -1;
    }

    public int getNextNeighbor(int vertexIndex, int previousNeighbor) {
        if (vertexIndex < 0 || vertexIndex >= vertexCount) {
            return -1;
        }
        for (int i = previousNeighbor + 1; i < vertexCount; i++) {
            if (vertexMatrix[vertexIndex][i] > 0 && vertexMatrix[vertexIndex][i] < MAX)
                return i;
        }
        return -1;
    }

    private void firstDepthSearch(int vertexIndex) {
        assert (vertexIndex < 0 || vertexIndex >= vertexCount);
        isVisited[vertexIndex] = true;
        System.out.println("访问节点：" + vertexIndex);
        int w = getFirstNeighbor(vertexIndex);
        while (w != -1) {
            if (!isVisited[w]) {
                firstDepthSearch(w);
            }
            w = getNextNeighbor(vertexIndex, w);
        }
    }

    /**
     * FDS深度优先遍历
     */
    public void firstDepthSearch() {
        for (int i = 0; i < isVisited.length; i++) {
            isVisited[i] = false;
        }
        for (int i = 0; i < vertexCount; i++) {
            if (!isVisited[i])
                firstDepthSearch(i);
        }

    }

    private void broadFirstSearch(int vertexIndex) {
        assert (vertexIndex < 0 || vertexIndex >= vertexCount);
        int u, w;
        LinkedList<Integer> linkedList = new LinkedList<>();
        isVisited[vertexIndex] = true;
        System.out.println("访问节点：" + vertexIndex);
        linkedList.add(vertexIndex);
        while (!linkedList.isEmpty()) {
            u = linkedList.removeFirst();
            w = getFirstNeighbor(u);
            while (w != -1) {
                if (!isVisited[w]) {
                    isVisited[w] = true;
                    System.out.println("访问节点：" + w);
                    linkedList.add(w);
                }
                w = getNextNeighbor(u, w);
            }
        }

    }

    /**
     * 广度优先遍历
     */
    public void broadFirstSearch() {
        for (int i = 0; i < isVisited.length; i++) {
            isVisited[i] = false;
        }
        for (int i = 0; i < vertexCount; i++) {
            if (!isVisited[i])
                broadFirstSearch(i);
        }
    }

    public static Graph createTestGraph() {
        int vertexCount = 9;
        int[][] vertexMatrix = new int[vertexCount][vertexCount];
        vertexMatrix[0] = new int[]{0, 10, Graph.MAX, Graph.MAX, Graph.MAX, 11, Graph.MAX, Graph.MAX, Graph.MAX};
        vertexMatrix[1] = new int[]{10, 0, 18, Graph.MAX, Graph.MAX, Graph.MAX, 16, Graph.MAX, 12};
        vertexMatrix[2] = new int[]{Graph.MAX, Graph.MAX, 0, 22, Graph.MAX, Graph.MAX, Graph.MAX, Graph.MAX, 8};
        vertexMatrix[3] = new int[]{Graph.MAX, Graph.MAX, 22, 0, 20, Graph.MAX, Graph.MAX, 16, 21};
        vertexMatrix[4] = new int[]{Graph.MAX, Graph.MAX, Graph.MAX, 20, 0, 26, Graph.MAX, 7, Graph.MAX};
        vertexMatrix[5] = new int[]{11, Graph.MAX, Graph.MAX, Graph.MAX, 26, 0, 17, Graph.MAX, Graph.MAX};
        vertexMatrix[6] = new int[]{Graph.MAX, 16, Graph.MAX, Graph.MAX, Graph.MAX, 17, 0, 19, Graph.MAX};
        vertexMatrix[7] = new int[]{Graph.MAX, Graph.MAX, Graph.MAX, 16, 7, Graph.MAX, 19, 0, Graph.MAX};
        vertexMatrix[8] = new int[]{Graph.MAX, 12, 8, 21, Graph.MAX, Graph.MAX, Graph.MAX, Graph.MAX, 0};

        Graph graph = new Graph(vertexCount);
        graph.setVertexMatrix(vertexMatrix);
        return graph;
    }
}
