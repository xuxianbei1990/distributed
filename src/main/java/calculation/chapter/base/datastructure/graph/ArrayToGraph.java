package calculation.chapter.base.datastructure.graph;

import java.util.List;
import java.util.Optional;

/**
 * 题2  每个数组的第一项表示权重：第二项表示from，第三项表示to
 * {[3,2,4],[1,4,5],[2,2,8]}
 *
 * @author: xuxianbei
 * Date: 2021/12/8
 * Time: 17:57
 * Version:V1.0
 */
public class ArrayToGraph {

    public Node getNode(List<Node> nodeList, Integer value) {
        Optional<Node> optional = nodeList.stream().filter(t -> t.getValue().equals(value)).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        } else {
            Node node1 = new Node(value);
            nodeList.add(node1);
            return node1;
        }
    }

    public Graph execute(int[][] arrays) {
        Graph graph = new Graph();
        for (int i = 0; i < arrays.length; i++) {
            int weight = arrays[i][0];
            int nodef = arrays[i][1];
            int nodet = arrays[i][2];
            Node node1 = getNode(graph.getNodes(), nodef);
            Node node2 = getNode(graph.getNodes(), nodet);
            Edge edge = getEdge(graph.getEdges(), weight, node1, node2);
            node1.getEdges().add(edge);
            node1.getNexts().add(node2);
            node1.setOut(node1.getOut() + 1);
            node2.setIn(node2.getIn() + 1);
        }
        return graph;
    }

    public static void main(String[] args) {
        int[][] graphArray = new int[][]{{2, 2, 4}, {1, 2, 5}, {5, 4, 5}, {7, 5, 8}};
        ArrayToGraph arrayToGraph = new ArrayToGraph();
        System.out.println(arrayToGraph.execute(graphArray));
    }

    private Edge getEdge(List<Edge> edges, int weight, Node node1, Node node2) {
        Optional<Edge> edgeOptional = edges.stream().filter(t -> t.getWeight() == weight && t.getFrom().equals(node1) && t.getTo().equals(node2)).findFirst();
        if (edgeOptional.isPresent()) {
            return edgeOptional.get();
        } else {
            Edge edge = new Edge(weight, node1, node2);
            edges.add(edge);
            return edge;
        }
    }
}
