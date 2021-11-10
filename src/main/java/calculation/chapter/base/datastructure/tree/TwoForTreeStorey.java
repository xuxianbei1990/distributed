package calculation.chapter.base.datastructure.tree;

import org.apache.poi.ss.formula.functions.T;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 题2：实现二叉树的按层遍历
 * 1）其实就是宽度优先遍历，用队列
 * 2）可以通过设置flag变量的方式，来发现某一层的结束（看题目）
 * 两种实现方式：使用hashmap，不使用hashmap
 *
 * @author: xuxianbei
 * Date: 2021/11/10
 * Time: 15:54
 * Version:V1.0
 */
public class TwoForTreeStorey {

    public void storey(TreeItem<Integer> treeItem) {
        LinkedList<TreeItem<Integer>> linkedList = new LinkedList();
        TreeItem<Integer> index = treeItem;
        linkedList.add(index);
        while (!linkedList.isEmpty()) {
            TreeItem<Integer> treeItem1 = linkedList.pop();
            System.out.print(treeItem1.getData());
            if (treeItem1.getLeft() != null) {
                linkedList.add(treeItem1.getLeft());
            }
            if (treeItem1.getRight() != null) {
                linkedList.add(treeItem1.getRight());
            }
        }
        System.out.println();
    }

    public int maxStoreyNum(TreeItem<Integer> treeItem) {
        LinkedList<TreeItem<Integer>> linkedList = new LinkedList();
        TreeItem<Integer> index = treeItem;
        Map<TreeItem<Integer>, Integer> hashMap = new HashMap();
        linkedList.add(index);
        //树最宽
        int max = 0;
        int sum = 0;
        int cur = 1;
        //树最宽的层数
        int maxStorey = 1;
        hashMap.put(index, 1);
        while (!linkedList.isEmpty()) {
            index = linkedList.pop();
            int temp = hashMap.get(index);
            if (temp == cur) {
                sum++;
            } else {
                max = Math.max(sum, max);
                if (max >= sum) {
                    maxStorey = cur;
                }
                cur++;
                sum = 1;
            }
            temp++;
            if (index.getLeft() != null) {
                hashMap.put(index.getLeft(), temp);
                linkedList.add(index.getLeft());
            }
            if (index.getRight() != null) {
                hashMap.put(index.getRight(), temp);
                linkedList.add(index.getRight());
            }
        }
        max = Math.max(sum, max);
        if (max >= sum) {
            maxStorey = cur;
        }
        return sum;

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
        TwoForTreeStorey twoForTreeStorey = new TwoForTreeStorey();
        twoForTreeStorey.storey(treeItem1);
        System.out.println(twoForTreeStorey.maxStoreyNum(treeItem1));
    }
}
