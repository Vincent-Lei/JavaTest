package datastruct.graph;

/**
 * http://data.biancheng.net/view/41.html
 * 克鲁斯卡尔算法(Kruskal算法)求最小生成树
 * <p>
 * 对于任意一个连通网的最小生成树来说，在要求总的权值最小的情况下，最直接的想法就是将连通网中的所有边按照权值大小进行升序排序，从小到大依次选择。
 * <p>
 * 由于最小生成树本身是一棵生成树，所以需要时刻满足以下两点：
 * 生成树中任意顶点之间有且仅有一条通路，也就是说，生成树中不能存在回路；
 * 对于具有 n 个顶点的连通网，其生成树中只能有 n-1 条边，这 n-1 条边连通着 n 个顶点。
 * 连接 n 个顶点在不产生回路的情况下，只需要 n-1 条边。
 * 所以克鲁斯卡尔算法的具体思路是：将所有边按照权值的大小进行升序排序，然后从小到大一一判断，
 * 条件为：如果这个边不会与之前选择的所有边组成回路，就可以作为最小生成树的一部分；反之，舍去。
 * 直到具有 n 个顶点的连通网筛选出来 n-1 条边为止。筛选出来的边和所有的顶点构成此连通网的最小生成树。
 * 判断是否会产生回路的方法为：在初始状态下给每个顶点赋予不同的标记，对于遍历过程的每条边，其都有两个顶点，
 * 判断这两个顶点的标记是否一致，如果一致，说明它们本身就处在一棵树中，如果继续连接就会产生回路；如果不一致，
 * 说明它们之间还没有任何关系，可以连接。
 */
public class KruskalGraph {
    public static final int MAX = Integer.MAX_VALUE;

    private static class Edge {
        private int begin;
        private int end;
        private int weight;

        public Edge(int begin, int end, int weight) {
            this.begin = begin;
            this.end = end;
            this.weight = weight;
        }
    }

    private int edgeSize;
    private Edge[] edges;

    public KruskalGraph() {
        this.edgeSize = 15;
        this.edges = new Edge[edgeSize];

        Edge edge0 = new Edge(4, 7, 7);
        Edge edge1 = new Edge(2, 8, 8);
        Edge edge2 = new Edge(0, 1, 10);
        Edge edge3 = new Edge(0, 5, 11);
        Edge edge4 = new Edge(1, 8, 12);
        Edge edge5 = new Edge(3, 7, 16);
        Edge edge6 = new Edge(1, 6, 16);
        Edge edge7 = new Edge(5, 6, 17);
        Edge edge8 = new Edge(1, 2, 18);
        Edge edge9 = new Edge(6, 7, 19);
        Edge edge10 = new Edge(3, 4, 20);
        Edge edge11 = new Edge(3, 8, 21);
        Edge edge12 = new Edge(2, 3, 22);
        Edge edge13 = new Edge(3, 6, 24);
        Edge edge14 = new Edge(4, 5, 26);
        edges[0] = edge0;
        edges[1] = edge1;
        edges[2] = edge2;
        edges[3] = edge3;
        edges[4] = edge4;
        edges[5] = edge5;
        edges[6] = edge6;
        edges[7] = edge7;
        edges[8] = edge8;
        edges[9] = edge9;
        edges[10] = edge10;
        edges[11] = edge11;
        edges[12] = edge12;
        edges[13] = edge13;
        edges[14] = edge14;

    }

    public void miniSpanTreeKruskal() {
        int m, n, sum = 0;
        int[] signArray = new int[edgeSize];
        for (int i = 0; i < edgeSize; i++) {
            m = find(signArray, edges[i].begin);
            n = find(signArray, edges[i].end);
            //找到的两个未标记点一样 表示形成回路  舍弃
            if (m != n) {
                //将七点标记为终点index
                signArray[m] = n;
                sum += edges[i].weight;
                System.out.println("选取边" + edges[i].begin + "---" + edges[i].end + "权重为：" + edges[i].weight);
            } else {
                System.out.println("边" + edges[i].begin + "---" + edges[i].end + "回环");
            }
        }

        System.out.println("sum = " + sum);
    }

    /**
     * 找到已连接的边中未被标记的点
     */
    private int find(int[] signArray, int f) {
        while (signArray[f] > 0) {
            f = signArray[f];
        }
        return f;
    }
}
