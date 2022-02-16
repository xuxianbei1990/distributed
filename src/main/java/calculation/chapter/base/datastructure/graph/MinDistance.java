package calculation.chapter.base.datastructure.graph;

import java.util.*;

/**
 * 题7：
 * 选中一个点出发，到任意点最短距离
 *
 * @author: xuxianbei
 * Date: 2021/12/11
 * Time: 17:03
 * Version:V1.0
 */
public class MinDistance {

    static class NodeMinDistance {
        Node node;
        Integer minDistance;

        public NodeMinDistance(Node node, Integer minDistance) {
            this.node = node;
            this.minDistance = minDistance;
        }
    }

    public void execute(List<Node> nodeList, Node nodeA, Node nodeB) {
        List<NodeMinDistance> nodeMinDistances = new ArrayList<>();
        nodeMinDistances.add(new NodeMinDistance(nodeA, 0));

        Node index = nodeA;
        for (Node node : nodeList) {
            if (!node.equals(index)) {
                Integer minDistance = getWeight(index.getEdges(), node);
                nodeMinDistances.add(new NodeMinDistance(node, minDistance));
            }
        }
        for (int i = 1; i < nodeMinDistances.size(); i++) {
            NodeMinDistance nodeMinDistanceB = nodeMinDistances.get(i);
            for (int j = i; j < nodeMinDistances.size(); j++) {
                NodeMinDistance nodeMinDistanceC = nodeMinDistances.get(j);
                if (nodeMinDistanceB.node.equals(nodeMinDistanceC.node)) {
                    continue;
                }
                Integer distance = getWeight(nodeMinDistanceB.node.getEdges(), nodeMinDistanceC.node);
                if (distance > -1) {
                    if (nodeMinDistanceC.minDistance > -1) {
                        nodeMinDistanceC.minDistance = Math.min(nodeMinDistanceC.minDistance, nodeMinDistanceB.minDistance + distance);
                    } else {
                        nodeMinDistanceC.minDistance = nodeMinDistanceB.minDistance + distance;
                    }
                }
            }
        }
        Optional<NodeMinDistance> nodeMinDistanceOptional = nodeMinDistances.stream().filter(t -> t.node.equals(nodeB)).findFirst();
        if (nodeMinDistanceOptional.isPresent()) {
            System.out.println(nodeMinDistanceOptional.get().minDistance);
        }
    }

    private Integer getWeight(List<Edge> edges, Node node) {
        for (Edge edge : edges) {
            if (edge.getTo().equals(node)) {
                return edge.getWeight();
            }
        }
        return -1;
    }

}
