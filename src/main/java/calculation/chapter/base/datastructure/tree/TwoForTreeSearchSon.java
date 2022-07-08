package calculation.chapter.base.datastructure.tree;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

/**
 * 题9
 * 给定一棵二叉树的头节点head，已知其中所有节点的值都不一样
 * 返回这颗二叉树中最大搜索的二叉搜索子树的头结点
 *
 * X：左一定是搜索树，右树一定是搜索树
 * 左树的最大节点，右树最小的节点
 * 且x> 左， X< 右，就成立
 * 返回总的节点数
 * 非X：
 *
 * @author: xuxianbei
 * Date: 2021/11/26
 * Time: 15:56
 * Version:V1.0
 */
public class TwoForTreeSearchSon {


    @Data
    static class Info {
        private Boolean searchIs;
        private Integer min;
        private Integer max;
        private Integer maxSubs;

        public Info(boolean isSearch, Integer min, Integer max, Integer maxSubs) {
            this.searchIs = isSearch;
            this.min = min;
            this.max = max;
            this.maxSubs = maxSubs;
        }

    }

    public Info execute(TreeItem<Integer> header) {
        if (header == null) {
            return new Info(true, null, null, 0);
        }

        Info lInfo = execute(header.getLeft());
        Info rInfo = execute(header.getRight());

        Integer min = header.getData();
        Integer max = header.getData();

        min = rInfo.min != null ? Math.min(rInfo.min, min) : min;
        max = lInfo.max != null ? Math.max(lInfo.max, max) : max;

        boolean isSearch = false;
        if (lInfo.getSearchIs() && rInfo.getSearchIs()) {
            if ((lInfo.max != null && header.getData() >= lInfo.max) && (rInfo.min != null && header.getData() <= rInfo.min)) {
                isSearch = true;
            }
            if (lInfo.max == null && rInfo.min == null) {
                isSearch = true;
            }

        }
        Integer maxSubs = 0;
        if (isSearch) {
            maxSubs = lInfo.getMaxSubs() + rInfo.getMaxSubs() + 1;
        } else {
            maxSubs = Math.max(lInfo.getMaxSubs(), rInfo.getMaxSubs());
        }

        return new Info(isSearch, min, max, maxSubs);
    }

    public static void main(String[] args) {
        TreeItem<Integer> treeItem1 = new TreeItem<>(1);
        TreeItem<Integer> treeItem2 = new TreeItem<>(2);
        TreeItem<Integer> treeItem3 = new TreeItem<>(3);
        TreeItem<Integer> treeItem4 = new TreeItem<>(4);
        TreeItem<Integer> treeItem5 = new TreeItem<>(5);
        TreeItem<Integer> treeItem6 = new TreeItem<>(6);
        TreeItem<Integer> treeItem7 = new TreeItem<>(7);
        treeItem4.setLeft(treeItem2);
        treeItem4.setRight(treeItem6);
        treeItem2.setLeft(treeItem1);
        treeItem2.setRight(treeItem3);
        treeItem6.setLeft(treeItem5);
        treeItem6.setRight(treeItem7);
        TwoForTreeSearchSon twoForTreeSearchSon = new TwoForTreeSearchSon();
        System.out.println(twoForTreeSearchSon.execute(treeItem4).getMaxSubs());
    }
}
