package datastruct.graph;

public class Prim {
    /**
     * MST（Minimum Spanning Tree，最小生成树）问题有两种通用的解法，Prim算法就是其中之一，
     * 它是从点的方面考虑构建一颗MST，大致思想是：设图G顶点集合为U，首先任意选择图G中的一点作为起始点a，
     * 将该点加入集合V，再从集合U-V中找到另一点b使得点b到V中任意一点的权值最小，此时将b点也加入集合V；
     * 以此类推，现在的集合V={a，b}，再从集合U-V中找到另一点c使得点c到V中任意一点的权值最小，此时将c点加入集合V，
     * 直至所有顶点全部被加入V，此时就构建出了一颗MST。因为有N个顶点，所 以该MST就有N-1条边，每一次向集合V中加入一个点，
     * 就意味着找到一条MST的边。
     * ---------------------
     * 原文：https://blog.csdn.net/yeruby/article/details/38615045
     */
    public void miniSpanTreePrim(Graph graph) {
        int vertexCount = graph.getVertexCount();
        int[] lowCost = new int[vertexCount];
        int[] adjvex = new int[vertexCount];
        int[][] matrix = graph.getVertexMatrix();

        int min, minIndex = 0, sum = 0;

        for (int i = 0; i < vertexCount; i++) {
            lowCost[i] = matrix[0][i];
        }
        for (int i = 1; i < vertexCount; i++) {
            min = Graph.MAX;
            for (int j = 1; j < vertexCount; j++) {
                if (lowCost[j] > 0 && lowCost[j] < min) {
                    min = lowCost[j];
                    minIndex = j;
                }
            }
            if (min == Graph.MAX)
                continue;
            System.out.println("顶点" + adjvex[minIndex] + "权值：" + min);
            sum += lowCost[minIndex];
            lowCost[minIndex] = 0;
            for (int j = 1; j < vertexCount; j++) {
                if (lowCost[j] != 0 && lowCost[j] > matrix[minIndex][j]) {
                    lowCost[j] = matrix[minIndex][j];
                    adjvex[minIndex] = j;
                }
            }

        }
        System.out.println("最小生成树权值 = " + sum);
    }
}
