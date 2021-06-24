package calculation.chapter.base.link;

import lombok.Data;

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

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

}
