package calculation.chapter.base.location;

/**
 * 一个数组中存在一个奇数，找到这个数
 *
 * @author: xuxianbei
 * Date: 2021/6/21
 * Time: 17:11
 * Version:V1.0
 */
public class OneOddNumber {


    public static int findOneOdd(int[] array) {
        if (array == null || array.length == 0) {
            return -1;
        }
        int eor = 0;
        for (int i = 0; i < array.length; i++) {
            eor = eor ^ array[i];
        }
        return eor;
    }

    public static void main(String[] args) {
        int[] ints = {1, 2, 4, 5, 2, 4, 1};
        System.out.println(findOneOdd(ints));
    }
}
