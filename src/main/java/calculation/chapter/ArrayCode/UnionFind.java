package calculation.chapter.ArrayCode;

import java.util.*;

public class UnionFind {

    static class Node {
        int value;
        int size;
        Node parent;

        public Node(int value) {
            this.value = value;
            parent = this;
            size = 1;
        }
    }

    public UnionFind (int size){
        for (int i = 1; i <= size; i++) {
            help.put(i, new Node(i));
        }
    }

    Map<Integer, Node> help = new HashMap<>();

    public boolean isSameSet(int a, int b) {
        Node node1 = help.get(a);
        Node node2 = help.get(b);
        if (node1 != null && node2 != null) {
            node1 = findParent(node1);
            node2 = findParent(node2);
            return node1 == node2;
        }
        return false;
    }

    private Node findParent(Node node) {
        Node resutl = node;
        List<Node> list = new ArrayList();
        while (resutl != resutl.parent) {
            resutl = resutl.parent;
            list.add(resutl);
        }
        for (Node node1 : list) {
            node1.parent = resutl;
        }
        return resutl;
    }

    public void union(int a, int b) {
        Node node1 = help.get(a);
        Node node2 = help.get(b);
        if (node1 != null && node2 != null) {
            node1 = findParent(node1);
            node2 = findParent(node2);
            Node max = node1.size >= node2.size ? node1 : node2;
            Node min = max == node1 ? node2 : node1;
            min.parent = max;
        }
    }

    public static void main(String[] args) {
        UnionFind unionFind = new UnionFind(4);
        unionFind.isSameSet(1, 2);
    }





}
