package calculation.chapter.base.datastructure.tree;

import lombok.Data;

/**
 * 返回两个点最远的距离
 * 分为两种情况：第一种过X点，第二种不过X点
 * 第一种：过X点，那么就是左右距离X点最远点，
 * <p>
 * 第二种：不过X点，那么就是具体X点最远点，
 *
 * @author: xuxianbei
 * Date: 2021/11/19
 * Time: 10:57
 * Version:V1.0
 */
public class TwoForTreeDistance {

    @Data
    static class Info {
        private Integer height;
        private Integer maxDistance;

        Info(Integer storey, Integer maxDistance) {
            this.height = storey;
            this.maxDistance = maxDistance;
        }
    }


    public Info execute(TreeItem<Integer> header) {
        if (header == null) {
            new Info(0, 0);
        }
        Info lInfo = execute(header.getLeft());
        Info rInfo = execute(header.getRight());
        Integer storey1 = Math.max(lInfo.height, rInfo.height) + 1;
        Integer maxDistance = Math.max(Math.max(lInfo.maxDistance, rInfo.maxDistance), lInfo.height + rInfo.height + 1);
        return new Info(storey1, maxDistance);
    }
}
