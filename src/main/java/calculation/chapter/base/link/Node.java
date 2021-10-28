package calculation.chapter.base.link;

import lombok.Data;

import java.util.Objects;

/**
 * @author: xuxianbei
 * Date: 2021/6/24
 * Time: 13:56
 * Version:V1.0
 */
@Data
public class Node<T> {
    // 保存节点的数据
    private T data;
    // 指向下个节点的引用
    private Node<T> next;

    public Node() {
    }

    public Node(T data) {
        this.data = data;
    }

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return data.equals(node.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
