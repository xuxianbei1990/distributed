package calculation.chapter.base.datastructure;

/**
 * 题3：如何使用栈结构实现队列结构，如何使用队列结构实现栈结构
 *
 * @author: xuxianbei
 * Date: 2021/9/28
 * Time: 11:40
 * Version:V1.0
 */
public class StackQueue {

    private Stack stackPut;

    private Stack stackOut;

    private int limit;

    private int index = 0;

    public StackQueue(int size) {
        limit = size;
        stackPut = new Stack(size);
        stackOut = new Stack(size);
    }

    private void changeStack(Stack orign, Stack dest) {
        while (!orign.isEmpty()) {
            int temp1 = orign.pop();
            dest.push(temp1);
        }
    }

    public void put(int value) {
        if (index >= limit) {
            return;
        }
        if (!stackOut.isEmpty()) {
            changeStack(stackOut, stackPut);
        }
        stackPut.push(value);
        index++;
    }

    public int pop() {
        if (index == 0) {
            return -1;
        }
        index--;
        if (!stackPut.isEmpty()) {
            changeStack(stackPut, stackOut);
        }
        return stackOut.pop();
    }

    public static void main(String[] args) {
        StackQueue stackQueue = new StackQueue(5);
        stackQueue.put(1);
        stackQueue.put(2);
        stackQueue.put(3);
        stackQueue.put(4);
        stackQueue.put(5);
        for (int i = 0; i < stackQueue.limit; i++) {
            System.out.println(stackQueue.pop());
        }
    }
}
