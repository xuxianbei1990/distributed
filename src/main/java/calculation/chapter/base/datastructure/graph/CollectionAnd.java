package calculation.chapter.base.datastructure.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 题1：
 * 1）有若干的样本a,b,c,d...类型假设是V
 * 2）在并查集中一开始认为每个样本都在单独的集合里
 * 3）用户可以在任何时候调用如下两个方法：
 * boolean isSameSet(Vx, Vy);查询样本x和样本y是否属于一个集合
 * void union(Vx, Vy);把x和y各自所在集合的所有样本合并成一个集合
 * 4）isSameSet和union方法的代价越低越好
 *
 * @author: xuxianbei
 * Date: 2021/12/7
 * Time: 16:05
 * Version:V1.0
 */
public class CollectionAnd<T> {

    static class Node<T> {
        public T value;

        public Node(T t) {
            this.value = t;
        }
    }

    private Map<T, Node> nodeMap = new HashMap<>();
    private Map<Node, Node> parentMap = new HashMap<>();
    private Map<Node, Integer> nodeSize = new HashMap<>();

    public int size() {
        return nodeSize.size();
    }


    public void add(Collection<T> collection) {
        collection.forEach(t -> add(t));
    }

    public void add(T t) {
        Node<T> node = new Node(t);
        nodeMap.put(t, node);
        parentMap.put(node, node);
        nodeSize.put(node, 1);
    }


    public boolean isSameSet(T x, T y) {
        if (x == null || y == null) {
            return false;
        }
        Node nodex = nodeMap.get(x);
        Node nodey = nodeMap.get(y);
        if (nodex == null || nodey == null) {
            return false;
        }
        Node parentx = getParentNode(nodex);
        Node parenty = getParentNode(nodey);
        return parentx.equals(parenty);
    }

    private Node getParentNode(Node nodex) {
        if (nodex == null) {
            return null;
        }
        Node parent = null;
        Node index = parentMap.get(nodex);
        while (index != parent) {
            parent = index;
            index = parentMap.get(index);
        }
        return parent;
    }

    public boolean union(T x, T y) {
        if (x == null || y == null) {
            return false;
        }
        Node nodex = nodeMap.get(x);
        Node nodey = nodeMap.get(y);
        if (nodex == null || nodey == null) {
            return false;
        }
        Integer sizex = nodeSize.get(nodex);
        Integer sizey = nodeSize.get(nodey);
        Node maxNode = sizex > sizey ? nodex : nodey;
        Node minNode = sizex <= sizey ? nodex : nodey;
        parentMap.put(minNode, maxNode);
        return true;
    }

}
