package calculation.chapter.base.datastructure.graph;

import java.util.Iterator;
import java.util.List;

/**
 * 题5
 * 图的拓扑排序算法
 * 1）在图中找到所有入度为0的点输出
 * 2）把所有入读为0的点在图中删掉，继续找入读为0的点输出，周而复始
 * 3）图的所有点都被删除后，依次输入的顺序就是拓扑排序
 * 要求：有向图且其中没有环
 * 应用：事件安排，编译顺序
 *
 * @author: xuxianbei
 * Date: 2021/12/11
 * Time: 16:05
 * Version:V1.0
 */
public class TuoPoTu {
    public void execute(List<Node> nodeList) {
        Iterator<Node> iterator = nodeList.iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node.getIn() == 0) {
                for (Node next : node.getNexts()) {
                    next.setIn(next.getIn() - 1);
                }
                System.out.println(node);
                iterator.remove();
            }
        }
    }
}
