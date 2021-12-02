package calculation.chapter.base.datastructure.tree;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 题12
 * 给定一颗二叉树的头结点head和另外两个节点a和b；返回a和b的最低公共祖先
 *
 * @author: xuxianbei
 * Date: 2021/11/30
 * Time: 11:28
 * Version:V1.0
 */
public class MinCommonAncestors {


    public TreeItem<Integer> executeMap(TreeItem<Integer> header, TreeItem<Integer> a, TreeItem<Integer> b) {
        if (header == null || a == null || b == null) {
            return header;
        }

        Map<TreeItem<Integer>, TreeItem<Integer>> map = new HashMap();
        map.put(header, header);
        process(header, map);
        Set<TreeItem<Integer>> set = new HashSet();
        putSet(map, set, a);
        return putSet(map, set, b);
    }

    private TreeItem<Integer> putSet(Map<TreeItem<Integer>, TreeItem<Integer>> map, Set<TreeItem<Integer>> set, TreeItem<Integer> a) {
        TreeItem<Integer> treeItem = map.get(a);
        if (treeItem != null) {
            if (set.add(treeItem)) {
                putSet(map, set, treeItem);
            } else {
                return treeItem;
            }
        }
        return null;
    }

    private void process(TreeItem<Integer> header, Map<TreeItem<Integer>, TreeItem<Integer>> map) {
        if (header == null) {
            return;
        }
        process(header.getLeft(), map);
        if (header.getLeft() != null) {
            map.put(header.getLeft(), header);
        }
        process(header.getRight(), map);
        if (header.getRight() != null) {
            map.put(header.getRight(), header);
        }
    }

    public static void main(String[] args) {
        TreeItem<Integer> header = new TreeItem(1);
        TreeItem<Integer> header2 = new TreeItem(2);
        TreeItem<Integer> header3 = new TreeItem(3);
        TreeItem<Integer> header4 = new TreeItem(4);
        TreeItem<Integer> header5 = new TreeItem(5);
        header.setLeft(header2);
        header.setRight(header3);
        header3.setLeft(header4);
        header3.setRight(header5);
        MinCommonAncestors minCommonAncestors = new MinCommonAncestors();
        System.out.println(minCommonAncestors.executeMap(header, header4, header5));
        System.out.println(minCommonAncestors.progress(header, header4, header5));
    }


    /**
     * O1，O2不在X上
     * 1.在左树上发现节点O1 或 在右树上发现节点O1
     * 2.在右树上发现节点O2 或 在右树上发现节点O2
     * O1，O2 在X上
     * 1.在左树发现O1或O2
     * 2.在右树发现O1或O2
     */
    @Data
    static class Info {
        private TreeItem<Integer> node;
        private boolean findO1;
        private boolean findO2;

        public Info(TreeItem<Integer> node, boolean findO1, boolean findO2) {
            this.node = node;
            this.findO1 = findO1;
            this.findO2 = findO2;
        }
    }

    /**
     * @param header
     * @param a
     * @param b
     * @return
     */
    public Info progress(TreeItem<Integer> header, TreeItem<Integer> a, TreeItem<Integer> b) {
        if (header == null) {
            return new Info(null, false, false);
        }
        Info lInfo = progress(header.getLeft(), a, b);
        Info rInfo = progress(header.getRight(), a, b);

        boolean findO1 = header == a || lInfo.findO1 || rInfo.findO1;
        boolean findO2 = header == b || lInfo.findO2 || rInfo.findO2;
        TreeItem<Integer> node = null;

        if (lInfo.findO1 || lInfo.findO2) {
            node = lInfo.node;
        }
        if (rInfo.findO1 || rInfo.findO2) {
            node = rInfo.node;
        }
        if (node == null) {
            if (findO1 && findO2) {
                node = header;
            }
        }
        return new Info(node, findO1, findO2);
    }
}
