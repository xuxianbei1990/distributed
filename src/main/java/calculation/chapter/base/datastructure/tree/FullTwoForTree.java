package calculation.chapter.base.datastructure.tree;

import lombok.Data;
import lombok.Getter;

/**
 * 2^h - 1 = sum(N)
 *
 * @author: xuxianbei
 * Date: 2021/11/29
 * Time: 18:27
 * Version:V1.0
 */
public class FullTwoForTree {

    static class Info {
        boolean is;

        public Info(boolean is) {
            this.is = is;
        }
    }


    public Info execute(TreeItem<Integer> header) {
        if (header == null) {
            return new Info(true);
        }

        Info isR = execute(header.getRight());
        Info isL = execute(header.getLeft());
        if (isL.is && isR.is) {
            if (header.getRight() != null && header.getLeft() != null) {
                return new Info(true);
            } else if (header.getRight() == null && header.getLeft() == null) {
                return new Info(true);
            } else {
                return new Info(false);
            }
        } else {
            return new Info(false);
        }
    }

    @Data
    static class Info2 {
        private Integer height;
        private Integer nodeCount;

        public Info2(Integer height, Integer nodeCount) {
            this.height = height;
            this.nodeCount = nodeCount;
        }
    }

    public boolean execute2(TreeItem<Integer> header) {
        Info2 info2 = progress(header);
        return Math.pow(2, info2.getHeight()) - 1 == info2.nodeCount;
    }

    private Info2 progress(TreeItem<Integer> header) {
        if (header == null) {
            return new Info2(0, 0);
        }
        Info2 lInfo2 = progress(header.getLeft());
        Info2 rInfo2 = progress(header.getRight());
        Integer height = 1;
        Integer nodeCount = 1;
        height = Math.max(lInfo2.getHeight(), rInfo2.getHeight()) + height;
        nodeCount = lInfo2.getNodeCount() + rInfo2.getNodeCount() + nodeCount;
        return new Info2(height, nodeCount);
    }

    public static void main(String[] args) {
        FullTwoForTree fullTwoForTree = new FullTwoForTree();
        TreeItem<Integer> header = new TreeItem(1);
        TreeItem<Integer> header2 = new TreeItem(2);
        TreeItem<Integer> header3 = new TreeItem(3);
        TreeItem<Integer> header4 = new TreeItem(4);
        TreeItem<Integer> header5 = new TreeItem(4);
        header.setLeft(header2);
        header.setRight(header3);
        header3.setLeft(header4);
        header3.setRight(header5);
        System.out.println(fullTwoForTree.execute(header).is);
        System.out.println(fullTwoForTree.execute2(header));
    }
}
