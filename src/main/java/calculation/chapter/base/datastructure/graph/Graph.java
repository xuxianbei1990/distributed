package calculation.chapter.base.datastructure.graph;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: xuxianbei
 * Date: 2021/12/8
 * Time: 16:41
 * Version:V1.0
 */
@Data
public class Graph {
    private List<Node> nodes;
    private List<Edge> edges;

    public Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
