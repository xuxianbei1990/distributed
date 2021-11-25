package calculation.chapter.likou;

import java.util.LinkedList;

/**
 * 最大值减去最小值小于或等于Num的子数组数量
 * <p>
 * 题：给定数组arr和整数num， 共返回有多少个子数组满足如下情况：
 * max(arr[i..j]) - min(arr[i..j]) <= num
 * max(arr[i..j])表示子数组arr[i..j]中的最大值， min(arr[i..j])表示子数组arr[i..j]中的最小值
 *
 * @author: xuxianbei
 * Date: 2021/11/25
 * Time: 19:24
 * Version:V1.0
 */
public class MaxSubstractMin {

    public int getNum(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        LinkedList<Integer> qmin = new LinkedList<>();
        LinkedList<Integer> qmax = new LinkedList<>();
        int i = 0;
        int j = 0;
        int res = 0;
        while (i < arr.length) {
            while (j < arr.length) {
                while ((!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[j])) {
                    qmin.pollLast();
                }
                qmin.addLast(j);
                while ((!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[j])) {
                    qmax.pollLast();
                }
                if (arr[qmax.getFirst()] - arr[qmin.getFirst()] > num) {
                    break;
                }
                j++;
            }
            if (qmin.peekFirst() == i) {
                qmin.pollFirst();
            }
            if (qmax.peekFirst() == i) {
                qmax.pollFirst();
            }
            res += j - i;
            i++;
        }
        return res;
    }
}
