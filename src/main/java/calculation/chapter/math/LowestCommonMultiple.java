package calculation.chapter.math;

/**
 * @author: xuxianbei
 * Date: 2022/7/13
 * Time: 9:50
 * Version:V1.0
 */
public class LowestCommonMultiple {

    public static Integer execute(Integer a, Integer b) {
        int c = a * b;
        if (a < b) {
            int r;
            r = a;
            a = b;
            b = r;
        }
        while (true) {
            int r = a % b;
            if (r == 0) {
                return  c / b;
            } else {
                a=b;
                b=r;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(execute(13, 3));
    }
}
