package datastruct.graph;

public class Dijstra {

    public void shortestPathDijstra(Graph graph) {
        int vertexCount = graph.getVertexCount();
        int[] shortestPaths = new int[vertexCount];
        boolean[] isGetOk = new boolean[vertexCount];
        int[][] matrix = graph.getVertexMatrix();

        int min;
        int index = 0;
        for (int i = 0; i < vertexCount; i++) {
            shortestPaths[i] = matrix[0][i];
        }
        shortestPaths[0] = 0;
        isGetOk[0] = true;

        for (int i = 1; i < vertexCount; i++) {
            min = Graph.MAX;
            for (int j = 0; j < vertexCount; j++) {
                if (!isGetOk[j] && shortestPaths[j] < min) {
                    index = j;
                    min = shortestPaths[j];
                }
            }
            isGetOk[index] = true;
            for (int j = 0; j < vertexCount; j++) {
                if (!isGetOk[j] && min + matrix[index][j] < shortestPaths[j]) {
                    shortestPaths[j] = matrix[index][j];
                }
            }
        }

        for (int i = 0; i < vertexCount; i++) {
            System.out.println("v0到v" + i + "最小路径为：" + shortestPaths[i]);
        }
    }
}
