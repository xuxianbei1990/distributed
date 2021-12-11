package calculation.chapter.base.datastructure.graph;

import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 深度优先遍历
 * 1.利用栈实现
 * 2.从源节点开始把节点按照深度放入栈，然后弹出
 * 3.每弹出一个点，把该节点下一个没有进过栈的邻接点放入栈
 * 4.知道栈为空
 *
 * @author: xuxianbei
 * Date: 2021/12/9
 * Time: 14:51
 * Version:V1.0
 */
public class DepthFirstFor {

    public void execute(Node node) {
        Stack<Node> stack = new Stack<>();
        Set<Node> set = new HashSet<>();
        print(node, stack, set);
        if (!CollectionUtils.isEmpty(node.getNexts())) {
            for (Node next : node.getNexts()) {
                print(next, stack, set);
            }
            while (!stack.isEmpty()) {
                Node node1 = stack.pop();
                for (Node next : node1.getNexts()) {
                    print(next, stack, set);
                }
            }
        }
    }

    private void print(Node node, Stack<Node> stack, Set<Node> set) {
        if (set.add(node)) {
            System.out.println(node);
            stack.add(node);
        }
    }
}
