package calculation.chapter.base.datastructure;

/**
 *
 * 题2：栈，基本功能上，实现返回栈中最小元素的功能。
 * 1）pop, push, getMin时间复杂度都是0（1）
 * 2）设计的栈类型可以使用现成的栈结构
 * @author: xuxianbei
 * Date: 2021/6/25
 * Time: 11:31
 * Version:V1.0
 */
public class MinStackValue {
    private Stack stackDate;
    private Stack stackHelp;
    private int min = Integer.MAX_VALUE;


    public MinStackValue(int size) {
        stackDate = new Stack(size);
        stackHelp = new Stack(size);
    }

    public void push(int value) {
        stackDate.push(value);
        min = Math.min(min, value);
        stackHelp.push(min);
    }

    public int popValue() {
       return pop()[0];
    }

    public int[] pop() {
        return new int[]{stackDate.pop(), stackHelp.pop()};
    }

    public int popMin() {
        return pop()[1];
    }

    public int[] popValueAndMin() {
        return pop();
    }

    public static void main(String[] args) {
        MinStackValue minStackValue = new MinStackValue(10);
        minStackValue.push(10);
        minStackValue.push(2);
        minStackValue.push(7);
        minStackValue.push(4);
        System.out.println(minStackValue.popMin());
        System.out.println(minStackValue.popValue());
        System.out.println(minStackValue.popMin());
    }
}
