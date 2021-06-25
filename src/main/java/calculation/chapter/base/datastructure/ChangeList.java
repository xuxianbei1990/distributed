package calculation.chapter.base.datastructure;

/**
 * 反向链表
 *
 * @author: xuxianbei
 * Date: 2021/6/25
 * Time: 10:18
 * Version:V1.0
 */
public class ChangeList {


    public static void main(String[] args) {
        execute();
    }

    private static void execute() {
        OneWayList<String> oneWayList = new OneWayList();
        oneWayList.add("1");
        oneWayList.add("2");
        oneWayList.add("3");

        OneWayList.Node<String> node = oneWayList.getHead();

        OneWayList.Node<String> pred = null;
        OneWayList.Node<String> next;
        while (node != null) {
            next = node.getNext();
            node.setNext(pred);
            pred = node;
            node = next;

        }
        System.out.println(pred);
    }


}
