package calculation.chapter.base;

import java.util.Arrays;

/**
 * 一个数组中存在2个奇数的数找出来
 * 因为是奇数的个数,假设这两个奇数为A,B，根据N ^ N = 0; 0 ^ N = N;所以奇数的^为：C =A^B;
 * 那么C一定存在一位，该位的值是1，取出来为D。那么D & array[i] 等于0 或者 1。 因为 & 操作是相同是1 ，不同是0. 那么该值要么0，要么非0，
 * 这样就把数组分为两个部分。等于0， 非0，且 要么A=0，要么B=0；
 * D取法 C ^ (~C + 1) 最右侧第一1.
 *
 * @author: xuxianbei
 * Date: 2021/6/21
 * Time: 17:17
 * Version:V1.0
 */
public class TwoOddNumber {

    public static void execute(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        int eor = 0;
        for (int i = 0; i < array.length; i++) {
            eor = eor ^ array[i];
        }
        int merge = eor;
        eor = eor & (~eor + 1);
        for (int i = 0; i < array.length; i++) {
            if ((eor & array[i]) == 0){
               eor = eor ^ array[i];
            }
        }
        System.out.println(eor + ", " + (eor ^ merge));

    }

    public static void main(String[] args) {
        int[] ints = {3, 5, 3, 5, 3, 7};
        System.out.println(Arrays.toString(twoOddTimes(ints)));
        execute(ints);
    }

    public static int[] twoOddTimes(int[] arrays) {
        if (arrays == null || arrays.length == 1) {
            return arrays;
        }
        if (arrays.length == 2) {
            return arrays;
        }
        //所有的数异或一遍得到 eor = a^b
        //既然a和b异或不等于0，那么a和b的某一位肯定不相同，要么a等于1，b等于0，要么a等于0，b等于1
        int eor = 0;
        for (int i = 0; i < arrays.length; i++) {
            eor ^= arrays[i];
        }

        int eor1 = 0;
        //提取二进制最右侧的1
        int rightOne = eor & (~eor + 1);
        //找出所有数中这个位置为1的数，其中包含了出现奇数次的其中一个
        for (int i = 0; i < arrays.length; i++) {
            if ((arrays[i] & rightOne) != 0) {
                //那么将这些数异或就得到了a或者b，即eor1, 因为其他的数都是偶数，会被异或掉
                eor1 ^= arrays[i];
            }
        }
        //a^b^? -> ?是a或者b 那么异或出来就剩下一个不相同的
        int eor2 = eor ^ eor1;
        return new int[]{eor1, eor2};
    }


}
