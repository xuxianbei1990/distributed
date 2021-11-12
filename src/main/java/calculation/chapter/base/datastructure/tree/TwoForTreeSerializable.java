package calculation.chapter.base.datastructure.tree;

import java.util.ArrayList;
import java.util.List;

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

    public void recursionTree(TreeItem<Integer> treeItem, List<Integer> list) {
        if (treeItem == null) {
            return;
        }
        list.add(treeItem.getData());
        recursionTree(treeItem.getLeft(), list);
        recursionTree(treeItem.getRight(), list);
    }

    public List<Integer> serializable(TreeItem<Integer> treeItem) {
        List<Integer> list = new ArrayList<>();
        recursionTree(treeItem, list);
        return list;
    }

    int index = 0;

    public TreeItem<Integer> unSerializable(List<Integer> list) {
        if (index >= list.size()) {
            return null;
        }
        TreeItem<Integer> header = new TreeItem<>(list.get(index));
        index++;
        if (header == null) {
            return header;
        }
        header.setLeft(unSerializable(list));
        header.setRight(unSerializable(list));
        return header;
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
        List<Integer> list = twoForTreeSerializable.serializable(treeItem1);
        System.out.println(list);
        System.out.println(twoForTreeSerializable.unSerializable(list));
    }

}

