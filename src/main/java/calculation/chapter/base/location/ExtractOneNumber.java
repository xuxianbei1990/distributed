package calculation.chapter.base.location;

/**
 * 提取一个数二进制1的个数
 * 1.利用 N &(~N + 1) 获取最右侧的1
 * 2. 目标数 ^ 这个数得到剩下的数, 就把最右侧的数给减去了
 *
 * @author: xuxianbei
 * Date: 2021/6/23
 * Time: 11:25
 * Version:V1.0
 */
public class ExtractOneNumber {

    public static int execute(int target) {
        int size = 0;
        while (target != 0) {
            int temp = target & (~target + 1);
            if (temp != 0) {
                size++;
            } else {
                return size;
            }
            target = target ^ temp;
        }
        return size;
    }

    public static void main(String[] args) {
        System.out.println(3 ^ 1);
        System.out.println(5 & 1);
        System.out.println(execute(11));
    }
}
