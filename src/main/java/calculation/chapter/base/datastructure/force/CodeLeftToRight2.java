package calculation.chapter.base.datastructure.force;


/**
 * 题4：
 * 从左往右的尝试模型2
 * 给定两个长度都为N的数组weights和values，weights[i]和values[i]分别代表i号物品的重量和价值。给定一个正数bag，表示一个载重bag的袋子，
 * 你装的物品不能超过这个总量，返回你能装下最多的价值是多少
 *
 * @author: xuxianbei
 * Date: 2021/12/23
 * Time: 16:15
 * Version:V1.0
 */
public class CodeLeftToRight2 {

    public void execute(int[] weights, int[] value, int bag) {
        int max = 0;
        for (int weight : weights) {
            int result = progress(weights, value, bag, 0, 0);
            max = Math.max(result, max);
        }
        System.out.println(max);
    }

    private int progress(int[] weights, int[] values, int rest, int index, int maxValue) {
        if (weights[index] > rest) {
            return 0;
        }
        if (index > weights.length - 1) {
            return 0;
        }

        progress(weights, values, rest - weights[index], ++index, Math.max(maxValue + values[index], maxValue));

        return maxValue;
    }

}
