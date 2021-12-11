package calculation.chapter.base.datastructure.greedy;

import java.util.Arrays;

/**
 * 题1：
 * 给一个正整数数组，每次取数的时候乘以下标，返回整体最大
 *
 * @author: xuxianbei
 * Date: 2021/12/2
 * Time: 10:17
 * Version:V1.0
 */
public class IntScore {

    public int execute(int[] scores) {
        Arrays.sort(scores);
        int index = 1;
        int sum = 0;
        for (int score : scores) {
            sum = sum + score * index;
        }
        return sum;
    }

}
