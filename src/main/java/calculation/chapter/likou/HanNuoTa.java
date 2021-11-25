package calculation.chapter.likou;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 在经典汉诺塔问题中，有 3 根柱子及 N 个不同大小的穿孔圆盘，盘子可以滑入任意一根柱子。一开始，所有盘子自上而下按升序依次套在第一根柱子上(即每一个盘子只能放在更大的盘子上面)。移动圆盘时受到以下限制:
 * (1) 每次只能移动一个盘子;
 * (2) 盘子只能从柱子顶端滑出移到下一根柱子;
 * (3) 盘子只能叠在比它大的盘子上。
 * <p>
 * 用栈将所有盘子从第一根柱子移到最后一根柱子。
 * 输入：A = [2, 1, 0], B = [], C = []
 * 输出：C = [2, 1, 0]
 *
 * @author: xuxianbei
 * Date: 2021/11/15
 * Time: 17:44
 * Version:V1.0
 */
public class HanNuoTa {


    private Stack<Integer> second = new Stack();

    private Stack<Integer> third = new Stack();

    public Stack<Integer> execute(Stack<Integer> first) {
        List<Stack<Integer>> baseList = new ArrayList<>();
        baseList.add(first);
        baseList.add(second);
        baseList.add(third);
        Integer baseIndex = 0;
        Integer max = 0;
        Integer size = first.size();
        Stack<Integer> indexList = baseList.get(baseIndex);
        while (true) {
            if (CollectionUtils.isEmpty(indexList)) {
                baseIndex++;
                indexList = baseList.get(baseIndex);
            }
            Integer value = indexList.pop();
            if (indexList == third) {
                value = max;
                third.add(value);
                baseIndex = newBaseIndex(++baseIndex);
                indexList = baseList.get(baseIndex);
                continue;
            }
            boolean putSuccess = listPut(baseList.get(newBaseIndex(baseIndex + 1)), value);
            if (!putSuccess) {
                putSuccess = listPut(baseList.get(newBaseIndex(baseIndex + 2)), value);
            }
            if (putSuccess) {
                if (third.size() == size) {
                    return third;
                }
            } else {
                max = Math.max(max, value);
                indexList.add(value);
                baseIndex++;
                indexList = baseList.get(baseIndex);
            }
        }
    }

    private int newBaseIndex(Integer baseIndex) {
        return baseIndex > 2 ? 0 : baseIndex;
    }

    private boolean listPut(List<Integer> list, Integer value) {
        if (list.isEmpty()) {
            list.add(value);
            return true;
        }
        if (list.get(0) > value) {
            list.add(value);
            return true;
        }
        return false;
    }

    public int hanNoTaproblem1(int num, String left, String mid, String right) {
        if (num < 1) {
            return 0;
        }
        return process(num, left, mid, right, left, right);
    }

    private int process(int num, String left, String mid, String right, String from, String to) {
        if (num == 1) {
            if (from.equals(mid) || to.equals(mid)) {
                System.out.println("Move 1 from " + from + " to " + to);
                return 1;
            } else {
                System.out.println("Move 1 from " + from + " to " + mid);
                System.out.println("Move 1 from " + mid + " to " + to);
                return 2;
            }
        }
        if (from.equals(mid) || to.equals(mid)) {
            String another = (from.equals(left) || to.equals(left)) ? right : left;
            int part1 = process(num - 1, left, mid, right, from, another);
            int part2 = 1;
            System.out.println("Move " + num + " from " + from + " to " + to);
            int part3 = process(num - 1, left, mid, right, another, to);
            return part1 + part2 + part3;
        } else {
            int part1 = process(num - 1, left, mid, right, from, to);
            int part2 = 1;
            System.out.println("Move " + num + " from " + from + " to " + mid);
            int part3 = process(num - 1, left, mid, right, to, from);
            int part4 = 1;
            System.out.println("Move " + num + " from " + mid + " to " + to);
            int part5 = process(num - 1, left, mid, right, from, to);
            return part1 + part2 + part3 + part4 + part5;
        }
    }

    public static void main(String[] args) {
        HanNuoTa hanNuoTa = new HanNuoTa();
        hanNuoTa.hanNoTaproblem1(3, "A", "B", "C");
    }

}
