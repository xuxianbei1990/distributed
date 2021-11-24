package calculation.chapter.base.datastructure.tree;

import lombok.Data;

/**
 * 返回两个点最远的距离
 * 分为两种情况：第一种过X点，第二种不过X点
 * 第一种：过X点，那么就是左右距离X点最远点，既高度最高的
 * 第二种：不过X点，那么就是具体X点最远点，既高度
 *
 * @author: xuxianbei
 * Date: 2021/11/19
 * Time: 10:57
 * Version:V1.0
 */
public class TwoForTreeDistance {

    @Data
    static class Info {
        private Integer storey;
        private TreeItem<Integer> node1;

        Info(Integer storey, TreeItem<Integer> node1) {
            this.storey = storey;
            this.node1 = node1;
        }
    }


    public Info execute(TreeItem<Integer> header, int storey) {
        if (header == null) {
            new Info(0, null);
        }
        Info lInfo = execute(header.getLeft(), ++storey);
        Info rInfo = execute(header.getRight(), storey);
        Integer storey1 = Math.max(lInfo.storey, rInfo.storey);
        TreeItem<Integer> node1;
        if (storey1 == lInfo.storey) {
            node1 = lInfo.node1;
        } else {
            node1 = rInfo.node1;
        }
        return new Info(storey, node1);
    }
}
