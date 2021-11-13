package calculation.chapter.base.datastructure.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 题3：二叉树的序列化和反序列化
 * 1）可以用先序、中序、后序、按层遍历、实现二叉树的序列化
 * 2）         用什么方式序列化，就用什么方式反序列化
 *
 * @author: xuxianbei
 * Date: 2021/11/11
 * Time: 11:15
 * Version:V1.0
 */
public class TwoForTreeSerializable {

    public void recursionTree(TreeItem<Integer> treeItem, Queue<Integer> list) {
        if (treeItem == null) {
            list.add(null);
        } else {
            list.add(treeItem.getData());
            recursionTree(treeItem.getLeft(), list);
            recursionTree(treeItem.getRight(), list);
        }
    }

    public Queue<Integer> serializable(TreeItem<Integer> treeItem) {
        Queue<Integer> Queue = new LinkedList<>();
        recursionTree(treeItem, Queue);
        return Queue;
    }

    public TreeItem<Integer> unSerializable(Queue<Integer> queue) {
        Integer value = queue.poll();
        if (value == null) {
            return null;
        }
        TreeItem<Integer> header = new TreeItem<>(value);
        header.setLeft(unSerializable(queue));
        header.setRight(unSerializable(queue));
        return header;
    }

    public Queue<Integer> serializableStorey(TreeItem<Integer> header) {
        if (header == null) {
            return null;
        }
        TreeItem<Integer> index = header;
        Queue<Integer> result = new LinkedList<>();
        Queue<TreeItem<Integer>> help = new LinkedList<>();
        help.add(index);
        result.add(index.getData());
        while (!help.isEmpty()) {
            index = help.poll();
            if (index.getLeft() != null) {
                help.add(index.getLeft());
                result.add(index.getLeft().getData());
            } else {
                result.add(null);
            }
            if (index.getRight() != null) {
                help.add(index.getRight());
                result.add(index.getRight().getData());
            } else {
                result.add(null);
            }
        }
        return result;
    }


    public TreeItem<Integer> unSerializableStorey(Queue<Integer> queue) {
        if (queue == null) {
            return null;
        }
        Queue<TreeItem<Integer>> help = new LinkedList<>();
        Integer value = queue.poll();
        TreeItem<Integer> header = new TreeItem<>(value);
        help.add(header);
        while (!help.isEmpty()) {
            TreeItem<Integer> index = help.poll();
            index.setLeft(generateTreeItem(queue.poll()));
            index.setRight(generateTreeItem(queue.poll()));
            if (index.getLeft() != null) {
                help.add(index.getLeft());
            }
            if (index.getRight() != null) {
                help.add(index.getRight());
            }
        }
        return header;
    }

    public TreeItem<Integer> generateTreeItem(Integer value) {
        if (value == null) {
            return null;
        }
        return new TreeItem<>(value);
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
        TwoForTreeSerializable twoForTreeSerializable = new TwoForTreeSerializable();
        Queue<Integer> queue = twoForTreeSerializable.serializable(treeItem1);
        System.out.println(queue);
        System.out.println(twoForTreeSerializable.unSerializable(queue));
        queue = twoForTreeSerializable.serializableStorey(treeItem1);
        System.out.println(queue);
        System.out.println(twoForTreeSerializable.unSerializableStorey(queue));
    }

}

