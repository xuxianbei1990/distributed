package calculation.chapter.base.datastructure.graph;

import lombok.Data;

/**
 * @author: xuxianbei
 * Date: 2021/12/8
 * Time: 16:39
 * Version:V1.0
 */
@Data
public class Edge {
    private Integer weight;
    private Node from;
    private Node to;

    public Edge(Integer weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
