package calculation.chapter.ArrayCode;

import java.util.*;

public class UnionFind2<V> {

    static class Node<V> {
        V value;
        Node<V> prev;
        int size;

        public Node(V value) {
            this.value = value;
            this.prev = this;
            this.size = 1;
        }
    }

    Map<V, Node<V>> help1 = new HashMap<>();

    public UnionFind2() {

    }
    public UnionFind2(List<V> values) {

        for (int i = 0; i < values.size(); i++) {
            help1.put(values.get(i), new Node<>(values.get(i)));
        }
    }

    boolean isSameSet(V x, V y) {
        Node<V> x1 = help1.get(x);
        Node<V> y1 = help1.get(y);
        return find(x1).equals(find(y1));
    }

    private Node<V> find(Node<V> x1) {
        Node<V> loop = x1;
        List<Node<V>> list = new ArrayList<>();
        while (loop.prev != loop) {
            list.add(loop);
            loop = loop.prev;
        }
        if (!list.isEmpty()) {
            for (Node<V> node : list) {
                node.prev = loop;
            }
        }
        return loop;
    }

    void union(V x, V y) {
        Node<V> x1 = find(help1.get(x));
        Node<V> y1 = find(help1.get(y));
        if (x1.size < y1.size) {
            x1.prev = y1;
            y1.size = y1.size + x1.size;
        } else {
            y1.prev = x1;
            x1.size = x1.size + y1.size;
        }
    }

    String value;
    public boolean wordBreak(String s, List<String> wordDict) {
        char[] str;
        //思路：取wordDict第一个单词去消除 s，如果能，继续用第一个单词去消除s，不能，就换下一个去消除，如果s长度为0 则返回true
        value = s;
        process(wordDict, 0);
        return value.length() == 0;
    }

    void process(List<String> wordDict, int index) {
        if (index == wordDict.size()) {
            return;
        }
        if (value.length() == 0) {
            return;
        }
        String item = wordDict.get(index);
        if (clean(item)) {
            process(wordDict, 0);
        } else {
            process(wordDict, index + 1);
        }
    }

    boolean clean(String item) {

        String temp = value.substring(0, item.length());
        if (temp.equals(item)) {
            value = value.substring(item.length());
            return true;
        } else {
            return false;
        }
    }


    public static void main(String[] args) {
        UnionFind2 find2 = new UnionFind2();
        find2.wordBreak("catsandog", new ArrayList<String>(Arrays.asList("cats","dog","sand","and","cat")));
    }

    public int numIslands(char[][] grid) {

        UnionFind unionFind = new UnionFind(grid);
        for (int j = 1; j< grid[0].length; j++) {
            if (grid[0][j] == '1' && grid[0][j-1] == '1') {
                unionFind.union(0,j, 0, j-1);
            }
        }

        for (int i = 1; i < grid.length; i++) {
            if (grid[i][0] == '1' && grid[i-1][0] == '1') {
                unionFind.union(i, 0, i-1, 0);
            }
        }

        for (int i = 1; i< grid.length; i++) {
            for (int j= 1; j < grid[0].length; j++) {
                if (grid[i][j] == '1' && grid[i-1][j] == '1') {
                    unionFind.union(i, j, i - 1, j);
                }
                if (grid[i][j] == '1' && grid[i][j - 1] == '1') {
                    unionFind.union(i, j, i, j -1);
                }
            }
        }

        return unionFind.set;
    }

    class UnionFind {
        int[] parent;
        int[] size;
        int set;
        int col;
        int row;

        public UnionFind(char[][] grid) {
            row = grid.length;
            col = grid[0].length;
            parent = new int[row * col];
            size = new int[parent.length];
            set = 0;
            for (int i = 0; i< grid.length; i++) {
                for (int j=0; j< grid[0].length; j++) {
                    if (grid[i][j] == '1') {
                        int index = getIndex(i, j);
                        parent[index] = index;
                        size[index] = 1;
                        set++;
                    }
                }
            }
        }

        public void union(int i1, int i2, int j1, int j2) {
            int i = getIndex(i1, i2);
            int j = getIndex(j1, j2);
            int parenti = findParent(i);
            int parentj = findParent(j);
            if (parenti != parentj) {
                if (size[parenti] > size[parentj]) {
                    parent[parentj] = parenti;
                    size[parenti] += size[parentj];
                } else {
                    parent[parenti] = parentj;
                    size[parentj] += size[parenti];
                }
                set--;
            }
        }

        public int findParent(int i) {
            int loop = i;
            while (parent[loop] != loop) {
                loop = parent[loop];
            }
            return loop;
        }

        public int getIndex(int i, int j) {
            return i * row + j;
        }
    }

}
