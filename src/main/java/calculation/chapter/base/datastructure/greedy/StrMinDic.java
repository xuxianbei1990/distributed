package calculation.chapter.base.datastructure.greedy;

import java.util.Arrays;

/**
 * 题2：
 * 给定一个由字符串组成的数组strs，必须把所有的字符串拼接起来，返回所有可能拼接结果中，字典序最小的结果
 * 字典序：abcdef。。。xyz；可以按照1,2,3,4,5
 *
 * @author: xuxianbei
 * Date: 2021/12/2
 * Time: 10:21
 * Version:V1.0
 */
public class StrMinDic {


    public String execute(String[] strs) {
        //填充所有的值为一样的长度
        int length = getMaxLength(strs);
        //填充长度
        fillLength(length, strs);
        //排序
        String[] result = sort(strs);
        //删除空
        delete(result);

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : result) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    private void delete(String[] strs) {
        for (int i = 0; i < strs.length; i++) {
            strs[i] = strs[i].replace("0", "");
        }
    }

    private String[] sort(String[] strs) {
        String[] help = new String[strs.length];

        progress(strs, 0, strs.length - 1, help);

        return strs;
    }

    private void progress(String[] strs, int left, int length, String[] help) {
        if (length > left) {
            int mid = (length + left) / 2;
            progress(strs, left, mid, help);
            progress(strs, mid + 1, length, help);
            merge(strs, left, mid, length, help);
        }
    }

    private void merge(String[] strs, int left, int mid, int length, String[] help) {
        int p1 = left;
        int p2 = mid + 1;
        int index = 0;
        while (p1 <= mid && p2 <= length) {
            if (strs[p1].compareTo(strs[p2]) > 0) {
                help[index++] = strs[p2++];
            } else {
                help[index++] = strs[p1++];
            }
        }

        while (p1 <= mid) {
            help[index++] = strs[p1++];
        }

        while (p2 <= length) {
            help[index++] = strs[p2++];
        }

        index = 0;
        while (left <= length) {
            strs[left++] = help[index++];
        }

    }

    private void fillLength(int length, String[] strs) {
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            str = fillLength(length, str);
            strs[i] = str;
        }
    }

    private String fillLength(int length, String str) {
        while (str.length() < length) {
            str = str + "0";
        }
        return str;
    }

    private int getMaxLength(String[] strs) {
        int max = 0;
        for (String str : strs) {
            max = Math.max(str.length(), max);
        }
        return max;
    }



    public static void main(String[] args) {
        StrMinDic strMinDic = new StrMinDic();
        System.out.println(strMinDic.execute(new String[]{"a", "cba","ab", "ba", "b", "ba"})) ;
    }

}
