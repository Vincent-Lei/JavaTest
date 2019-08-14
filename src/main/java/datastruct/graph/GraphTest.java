package datastruct.graph;

public class GraphTest {
    public static void main(String[] args) {

        Graph graph = Graph.createTestGraph();
//        for (int i = 0; i < vertexCount; i++) {
//            System.out.print("vertex：" + i + " OutDegree：" + graph.getVertexOutDegree(i));
//            System.out.println(" OutWeight：" + graph.getVertexOutWeight(i));
//        }
//        System.out.println("---------------------------------------");
//        for (int i = 0; i < vertexCount; i++) {
//            System.out.print("vertex：" + i + " InDegree：" + graph.getVertexInDegree(i));
//            System.out.println(" InWeight：" + graph.getVertexInWeight(i));
//        }
//        System.out.println("---------------------------------------");
//        graph.firstDepthSearch();
//        System.out.println("---------------------------------------");
//        graph.broadFirstSearch();

        Prim prim = new Prim();
        prim.miniSpanTreePrim(graph);

        System.out.println("---------------------------------------");

        KruskalGraph kruskalGraph = new KruskalGraph();
        kruskalGraph.miniSpanTreeKruskal();

        System.out.println("---------------------------------------");

        Dijstra dijstra = new Dijstra();
        dijstra.shortestPathDijstra(graph);

        System.out.println("---------------------------------------");

        TopLogicObject topLogicObject = TopLogicObject.createTestTopLogicSortObj();
        topLogicObject.topLogicSort();

    }
}
