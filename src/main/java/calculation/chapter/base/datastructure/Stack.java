package calculation.chapter.base.datastructure;

/**
 * 栈
 * 固定数组大小实现; 用1个游标记录当前的位置，用limit 记录长度限制。只要游标小于等于limit即可。
 * 队列：环形数组 用两个游标记录，一个记录进，一个记录出。limit记录长度限制，size记录当前长度。只要size< limit 就移动游标
 * 先进后出
 *
 * @author: xuxianbei
 * Date: 2021/6/23
 * Time: 16:41
 * Version:V1.0
 */
public class Stack {

    private int[] array;

    private int index = 0;

    private int limit;

    public Stack(int size) {
        array = new int[size];
        limit = size;
    }

    public boolean isEmpty() {
        return index == 0;
    }


    public int push(int value) {
        if (index <= limit) {
            array[index] = value;
            index++;
        }
        return index;
    }

    public int pop() {
        int result = -1;
        if (index - 1 <= limit && index - 1 >= 0) {
            result = array[index - 1];
            index--;
        }
        return result;
    }

    public static void main(String[] args) {
        Stack stack = new Stack(5);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        stack.push(5);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }

}
