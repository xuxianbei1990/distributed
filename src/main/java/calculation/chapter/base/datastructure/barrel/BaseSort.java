package calculation.chapter.base.datastructure.barrel;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 题11：基数排序
 * 权重从小到大的排序，
 * 例如：102， 26， 1,203,12,3,6,9，8,7，32,56,78。
 * 创建 0-9个桶，每个桶和原数组一样。然后按照个位，十位，百位以此放入，按照队列形式先进先出。进行排序
 *
 * @author: xuxianbei
 * Date: 2021/10/21
 * Time: 11:26
 * Version:V1.0
 */
public class BaseSort {

    private int barrelCount = 10;

    private List<List<String>> baseArray = new ArrayList();

    private List<String> help = new ArrayList<>();

    public BaseSort() {
        for (int i = 0; i < barrelCount; i++) {
            baseArray.add(new ArrayList<>());
        }
    }

    public int[] sort(int[] array, int digit) {
        if (array == null || array.length == 0) {
            return array;
        }
        help.clear();
        for (int integer : array) {
            help.add(String.format("%0" + digit + "d", integer));
        }
        clearBaseArray();

        for (int i = 1; i <= digit; i++) {
            for (String s : help) {
                int key = Integer.valueOf(s.substring(digit - i, digit - i + 1));
                baseArray.get(key).add(s);
            }
            help.clear();
            for (List<String> list : baseArray) {
                for (String s : list) {
                    if (!StringUtils.isEmpty(s)) {
                        help.add(s);
                    }
                }
            }
            clearBaseArray();
        }

        for (int i = 0; i < help.size(); i++) {
            array[i] = Integer.valueOf(help.get(i));
        }
        return array;
    }

    private void clearBaseArray() {
        for (List<String> list : baseArray) {
            list.clear();
        }
    }

    public static void main(String[] args) {
        BaseSort baseSort = new BaseSort();
        System.out.println(Arrays.toString(baseSort.sort(new int[]{102, 26, 1, 203, 12, 3, 6, 9, 8, 7, 32, 56, 78}, 3)));
    }
}
