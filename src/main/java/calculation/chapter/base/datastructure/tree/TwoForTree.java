package calculation.chapter.base.datastructure.tree;

import calculation.chapter.base.link.Node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

/**
 * 题1：二叉树的先序，中序、后序遍历
 * 先序：任何子树的处理顺序都是，先头节点、再左子树、然后右子树
 * 中序：任何子树的处理顺序都是，先左子树、再头节点、然后右子树
 * 后序：任何子树的处理顺序都是，先左子树、再右子树、然后头节点
 * 递归和非递归实现
 *
 * @author: xuxianbei
 * Date: 2021/11/9
 * Time: 13:33
 * Version:V1.0
 */
public class TwoForTree {

    public void preFor(TreeItem<Integer> header) {
        if (header == null) {
            return;
        }
        System.out.print(header.getData());
        preFor(header.getLeft());
        preFor(header.getRight());
    }

    public void preForWhile(TreeItem<Integer> header) {
        Stack<TreeItem<Integer>> stack = new Stack<>();
        stack.add(header);
        while (!stack.isEmpty()) {
            TreeItem<Integer> index = stack.pop();
            System.out.print(index.getData());
            if (index.getRight() != null) {
                stack.add(index.getRight());
            }
            if (index.getLeft() != null) {
                stack.add(index.getLeft());
            }
        }
        System.out.println();
    }

//    public void preForwhile(TreeItem<Integer> header) {
//        TreeItem<Integer> index = header;
//        LinkedList<TreeItem<Integer>> linkedList = new LinkedList();
//        linkedList.add(index);
//        while (linkedList.size() > 0) {
//            TreeItem<Integer> treeItem = linkedList.pop();
//            System.out.print(treeItem.getData());
//            if (treeItem.getLeft() != null) {
//                linkedList.add(treeItem.getLeft());
//            }
//            if (treeItem.getRight() != null) {
//                linkedList.add(treeItem.getRight());
//            }
//        }
//    }


    public void midFor(TreeItem<Integer> header) {
        if (header == null) {
            return;
        }
        midFor(header.getLeft());
        System.out.print(header.getData());
        midFor(header.getRight());
    }

    public void midForWhile(TreeItem<Integer> header) {
        Stack<TreeItem> stack = new Stack<TreeItem>();
        if (header != null) {
            while (header != null || !stack.empty()) {
                if (header != null) {
                    stack.push(header);
                    header = header.getLeft();
                } else {
                    header = stack.pop();
                    System.out.print(header.getData());
                    header = header.getRight();
                }
            }
        }
        System.out.println();
    }

    public void sufForWhile(TreeItem<Integer> header) {
        Stack<TreeItem<Integer>> stack = new Stack<>();
        Stack<TreeItem<Integer>> stack1 = new Stack<>();
        stack.add(header);
        while (!stack.isEmpty()) {
            TreeItem<Integer> index = stack.pop();
            if (index.getLeft() != null) {
                stack.add(index.getLeft());
            }
            if (index.getRight() != null) {
                stack.add(index.getRight());

            }
            stack1.add(index);
        }
        while (!stack1.isEmpty()) {
            System.out.print(stack1.pop().getData());
        }
        System.out.println();
    }

    public void sufFor(TreeItem<Integer> header) {
        if (header == null) {
            return;
        }

        sufFor(header.getLeft());
        sufFor(header.getRight());
        System.out.print(header.getData());
    }


    public static void main(String[] args) {
        TwoForTree twoForTree = new TwoForTree();
        TreeItem<Integer> treeItem1 = new TreeItem<>(1);
        TreeItem<Integer> treeItem2 = new TreeItem<>(2);
        TreeItem<Integer> treeItem3 = new TreeItem<>(3);
        TreeItem<Integer> treeItem4 = new TreeItem<>(4);
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
        twoForTree.preFor(treeItem1);
        System.out.println();
        twoForTree.preForWhile(treeItem1);
        twoForTree.midFor(treeItem1);
        System.out.println();
        twoForTree.midForWhile(treeItem1);
        twoForTree.sufFor(treeItem1);
        System.out.println();
        twoForTree.sufForWhile(treeItem1);

    }
}
