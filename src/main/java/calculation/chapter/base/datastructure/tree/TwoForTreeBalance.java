package calculation.chapter.base.datastructure.tree;

import lombok.Data;
import org.apache.commons.math3.geometry.enclosing.EnclosingBall;

/**
 * 题7：
 * 给定一棵二叉树的头节点head。返回这颗二叉树是不是平衡二叉树
 *
 * @author: xuxianbei
 * Date: 2021/11/17
 * Time: 13:30
 * Version:V1.0
 */
public class TwoForTreeBalance {

    @Data
    static class Info {
        private Integer height;
        private boolean balance;

        public Info(Integer height, boolean balance) {
            this.height = height;
            this.balance = balance;
        }
    }

    public Info execute(TreeItem<Integer> treeItem) {
        if (treeItem == null) {
            return new Info(0, true);
        }
        Info lInfo = execute(treeItem.getLeft());
        Info rInfo = execute(treeItem.getRight());

        Integer height = Math.max(lInfo.getHeight(), rInfo.getHeight()) + 1;

        boolean balance = false;
        if (lInfo.balance && rInfo.balance && Math.abs(lInfo.getHeight() - rInfo.getHeight()) < 2) {
            balance = true;
        }
        return new Info(height, balance);
    }

    public static void main(String[] args) {
        TreeItem<Integer> treeItem1 = new TreeItem<>(1);
        TreeItem<Integer> treeItem2 = new TreeItem<>(2);
        TreeItem<Integer> treeItem3 = new TreeItem<>(3);
        TreeItem<Integer> treeItem5 = new TreeItem<>(5);
        TreeItem<Integer> treeItem6 = new TreeItem<>(6);
        TreeItem<Integer> treeItem7 = new TreeItem<>(7);
        TreeItem<Integer> treeItem8 = new TreeItem<>(8);
        treeItem1.setLeft(treeItem2);
        treeItem1.setRight(treeItem3);
        treeItem2.setLeft(treeItem5);
        treeItem2.setRight(treeItem6);
        treeItem3.setLeft(treeItem7);
        treeItem3.setRight(treeItem8);
        TwoForTreeBalance twoForTreeBalance = new TwoForTreeBalance();
        System.out.println(twoForTreeBalance.execute(treeItem1));
    }
}
