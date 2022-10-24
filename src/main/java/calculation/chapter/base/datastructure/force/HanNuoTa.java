package calculation.chapter.base.datastructure.force;

/**
 * 题1：
 * 1）打印n层汉诺塔从最左边移动到最右边的全部过程
 * 2）打印一个字符串的全部子序列
 * 3）打印一个字符串的全部子序列，要求不要出现重复字面值的子序列 加一个hashSet
 * 4）打印一个字符串的全部排列
 * 5）打印一个字符串的全部排列，要求不要出现重复的排列
 *
 * @author: xuxianbei
 * Date: 2021/12/15
 * Time: 17:23
 * Version:V1.0
 */
public class HanNuoTa {


    public void execute(Integer n, String a, String b, String c) {
        progress(n, a, b, c);
    }

    public void progress(Integer n, String from, String to, String other) {
        if (n < 1) {
            return;
        }
        if (n == 1) {
            System.out.println(n + ":" + from + "->" + to + "->" + other);
        } else {
            progress(n - 1, from, to, other);
            System.out.println(n + ":" + from + "->" + to);
            progress(n - 1, other, to, from);
            System.out.println(n + ":" + to + "->" + other);
            progress(n - 1, from, to, other);
        }
    }

    public static void main(String[] args) {
        HanNuoTa hanNuoTa = new HanNuoTa();
//        hanNuoTa.execute(3, "A", "B", "C");
//        hanNuoTa.printAllStringSon("abcdef");
        hanNuoTa.printAllStringTogetherSon("abc");
    }

    //打印一个字符串的全部排列
    private void printAllStringTogetherSon(String value) {
        char[] chars = value.toCharArray();
        //确定组合
        for (int i = 1; i < chars.length; i++) {
            //确定取数
            char[] chars1 = new char[i];
            getNum(chars, i, 0, -1, chars1);
        }
    }

    private void getNum(char[] chars, int num, int begin, int index, char[] chars1) {
        if (num == 0) {
            return;
        }
        num--;
        index++;
        for (int i = begin; i < chars.length; i++) {
            chars1[index] = chars[i];
            getNum(chars, num, i + 1, index, chars1);
            if (num == 0) {
                System.out.println(new String(chars1));
            }
        }
    }

    /**
     * 打印一个字符串的全部子序列 例如：abcdef 子序列 abc， bc， bcd，abcde， 是连续的
     */
    public void printAllStringSon(String value) {
        for (int i = 0; i <= value.length(); i++) {
            for (int j = i + 1; j <= value.length(); j++) {
                System.out.println(value.substring(i, j));
            }
        }
    }


}
