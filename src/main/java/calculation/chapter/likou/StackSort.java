package calculation.chapter.likou;

import java.util.Stack;

/**
 * 栈排序
 *
 * @author: xuxianbei
 * Date: 2021/11/15
 * Time: 15:09
 * Version:V1.0
 */
public class StackSort {

    public Stack<Integer> execute(Stack<Integer> stack) {
        Stack<Integer> help = new Stack<>();
        while (!stack.isEmpty()) {
            Integer value = stack.pop();
            if (help.isEmpty()) {
                help.add(value);
            } else {
                Integer helpValue = help.peek();
                if (helpValue < value) {
                    help.add(value);
                } else {
                    refactor(help, value);
                }
            }
        }
        return help;
    }

    private void refactor(Stack<Integer> help, Integer value) {
        Integer helpValue = help.pop();
        if (value > helpValue) {
            help.add(helpValue);
            help.add(value);
            return;
        }
        if (help.isEmpty()) {
            help.add(value);
            help.add(helpValue);
            return;
        } else {
            refactor(help, value);
            help.add(helpValue);
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.add(3);
        stack.add(4);
        stack.add(3);
        stack.add(1);
        stack.add(7);
        stack.add(8);
        stack.add(0);
        StackSort stackSort = new StackSort();
        System.out.println(stackSort.execute(stack));

    }

}
