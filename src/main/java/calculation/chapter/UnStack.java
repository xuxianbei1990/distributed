package calculation.chapter;

import java.util.Stack;

/**
 * 题：如何仅用递归函数和栈操作逆序一个栈
 * 一个栈依次压入：1，2,3,4,5。将栈里的元素逆序。只能使用递归函数，不能使用其他数据结构
 *
 * @author: xuxianbei
 * Date: 2021/10/26
 * Time: 20:25
 * Version:V1.0
 */
public class UnStack {

    public static int getAndRemoveLast(Stack<Integer> stack) {
        Integer value = stack.pop();
        if (stack.isEmpty()){
            return value;
        } else {
            Integer value2 = getAndRemoveLast(stack);
            stack.push(value);
            return value2;
        }
    }

    public static void reserve(Stack<Integer> stack) {
        if (stack.isEmpty()){
            return;
        }
        int i = getAndRemoveLast(stack);
        reserve(stack);
        stack.push(i);
    }


    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        System.out.println(stack);
        reserve(stack);
        System.out.println(stack);
    }
}
