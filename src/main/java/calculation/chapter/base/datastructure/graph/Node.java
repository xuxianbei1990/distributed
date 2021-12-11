package calculation.chapter.base.datastructure.graph;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author: xuxianbei
 * Date: 2021/12/8
 * Time: 16:36
 * Version:V1.0
 */
@Getter
@Setter
public class Node {
    private Integer value;
    private Integer in;
    private Integer out;
    private List<Node> nexts;
    private List<Edge> edges;

    public Node(Integer value) {
        this.value = value;
        this.in = 0;
        this.out = 0;
        this.nexts = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
