package calculation.chapter.base.datastructure.tree;

/**
 * 题4：如何设计一个打印整棵树的打印函数
 *
 * @author: xuxianbei
 * Date: 2021/11/13
 * Time: 11:27
 * Version:V1.0
 */
public class TwoForTreePrint {

    public void execute(TreeItem<Integer> header, Integer level, String direct) {
        if (header == null) {
            return;
        }
        execute(header.getRight(), ++level, "v");
        String value = print(header.getData(), level, direct);
        System.out.println(value);
        execute(header.getLeft(), level, "^");
    }

    private String print(Integer data, Integer level, String direct) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < level; i++) {
            stringBuilder.append("  ");
        }
        return stringBuilder.toString() + direct + data + direct;
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
        TwoForTreePrint twoForTreePrint = new TwoForTreePrint();
        twoForTreePrint.execute(treeItem1, 0, "H");
    }
}
