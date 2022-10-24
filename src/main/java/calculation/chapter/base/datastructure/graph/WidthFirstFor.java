package calculation.chapter.base.datastructure.graph;

import org.apache.solr.common.util.Hash;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 宽度优先遍历
 * 1.利用队列实现
 * 2.从源节点开始依次按照宽度进队，然后弹出
 * 3.每弹出一个点，把该点没有进过队列的邻接点放入队列
 * 4.直到队列为空
 *
 * @author: xuxianbei
 * Date: 2021/12/9
 * Time: 14:32
 * Version:V1.0
 */
public class WidthFirstFor {


    public void execute(Node node) {
        LinkedList<Node> linkedList = new LinkedList<>();
        Set<Node> nodeSet = new HashSet<>();
        linkedList.add(node);
        nodeSet.add(node);
        while (!linkedList.isEmpty()) {
            Node node1 = linkedList.pop();
            System.out.println(node1);
            if (!CollectionUtils.isEmpty(node1.getNexts())) {
                for (Node next : node1.getNexts()) {
                    if (nodeSet.add(node)) {
                        linkedList.add(next);
                    }
                }
            }
        }
    }
}
