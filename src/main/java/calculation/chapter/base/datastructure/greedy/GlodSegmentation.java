package calculation.chapter.base.datastructure.greedy;

import java.util.Arrays;
import java.util.Stack;

/**
 * 题5：
 * 一块金条切成两半，是需要花费和长度数值一样的铜板的，
 * 比如长度为20的金条，不管怎么切，都要花费20个铜板，一群人想整分整块金条，怎么分最省铜板
 * 例如,给定数组{10,20,30}，代表一共三个人，整块金条长度为60，金条要分成10，20，30三个部分。如果先把长度60的金条分成10和50，花费60;
 * 再把长度50的金条分成20和30，花费50;一共花费110铜板。但如果先把长度60的金条分成30和30，花费60;再把长度30金条分成10和20， 花费30;一共花费90铜板。
 *
 * @author: xuxianbei
 * Date: 2021/12/2
 * Time: 14:03
 * Version:V1.0
 */
public class GlodSegmentation {


    public void execute(int[] peoples, int goldLength) {
        sort(peoples);
        Stack<Integer> stack = new Stack<>();
        for (int people : peoples) {
            stack.add(people);
        }
        while (!stack.isEmpty() && stack.size() > 1) {
            int a = stack.pop();
            int b = stack.pop();
            System.out.println(a + b);
            stack.add(a + b);
        }
    }

    private void sort(int[] peoples) {
        int[] help = new int[peoples.length];
        process(peoples, 0, peoples.length - 1, help);
    }

    private void process(int[] peoples, int left, int length, int[] help) {
        if (left < length) {
            int mid = (left + length) / 2;
            process(peoples, left, mid, help);
            process(peoples, mid + 1, length, help);
            mergy(peoples, left, mid, length, help);
        }
    }

    private void mergy(int[] peoples, int left, int mid, int length, int[] help) {
        int p1 = left;
        int p2 = mid + 1;
        int index = 0;
        while (p1 <= mid && p2 <= length) {
            if (peoples[p1] > peoples[p2]) {
                help[index++] = peoples[p1++];
            } else {
                help[index++] = peoples[p2++];
            }
        }
        while (p1 <= mid) {
            help[index++] = peoples[p1++];
        }

        while (p2 <= length) {
            help[index++] = peoples[p2++];
        }
        index = 0;
        while (left <= length) {
            peoples[left++] = help[index++];
        }


    }

    public static void main(String[] args) {
        GlodSegmentation glodSegmentation = new GlodSegmentation();
        glodSegmentation.execute(new int[]{20, 20, 40}, 80);
    }
}
