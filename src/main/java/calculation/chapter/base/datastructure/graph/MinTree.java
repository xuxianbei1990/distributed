package calculation.chapter.base.datastructure.graph;

import calculation.chapter.three.ChooseSort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 题6
 * 最小生成树算法之Kruskal
 * 1）总是从权值最小的边开始考虑，依次考察权值依次变大的边
 * 2）当前的边要么进入最小生成树的集合，要么丢弃
 * 3）如果当前的边进入最小生成树的集合中不会形成环，就要当前边
 * 4）如果当前的边进入最小生成树的集合中会形成环，就不要当前边
 * 5）考察完所有边之后，最小生成树的集合也得到了
 *
 * @author: xuxianbei
 * Date: 2021/12/11
 * Time: 16:13
 * Version:V1.0
 */
public class MinTree {

    public void executeK(List<Edge> edges) {
        //排序从小到大排序
        ChooseSort.practice(edges, (a, b) -> a.getWeight() > b.getWeight() ? 0 : 1);
        Set<Node> nodeSet = new HashSet();
        for (Edge edge : edges) {
            Node nodef = edge.getFrom();
            Node nodet = edge.getTo();
            if (nodeSet.contains(nodef) && nodeSet.contains(nodet)) {
                continue;
            } else {
                nodeSet.add(nodef);
                nodeSet.add(nodet);
                System.out.println(edge);
            }
        }
    }

    /**
     * P算法
     * 1）从指定点出发，
     * 2）启动相邻的边，
     * 3）从启动的边中找到最小的边
     * 4）确定边的点是否在集合中，不在放入
     * 5）周而复始
     * 边状态：启动，使用，放弃，未察觉
     */
    public void executeP(Node node) {
        Set<Node> nodeSet = new HashSet<>();
        nodeSet.add(node);
        Node index = node;
        while (index != null) {
            Edge edge = getMin(index.getEdges());
            if (nodeSet.add(edge.getTo())) {
                System.out.println(edge);
                index = edge.getTo();
            } else {
                index = null;
            }
        }

    }

    private Edge getMin(List<Edge> edges) {
        Edge minEdge = edges.get(0);
        for (Edge edge : edges) {
            minEdge = edge.getWeight() > minEdge.getWeight() ? minEdge : edge;
        }
        return minEdge;
    }
}
